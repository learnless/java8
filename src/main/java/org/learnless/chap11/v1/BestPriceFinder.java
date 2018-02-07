package org.learnless.chap11.v1;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static java.util.stream.Collectors.toList;

/**
 * 生成同步和异步的方法
 * Created by learnless on 18.2.3.
 */
public class BestPriceFinder {
    private final List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll")/*,
            new Shop("ShopEasy")*/);

    /**
     * 使用定制的执行器，自定义创建线程池中线程数目
     * 线程数目增加时，运行性能明显提高，当达到某个临近点后就成下降趋势
     * 这儿创建线程数目跟shops大小一样，上限为100
     */
    private final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread t = new Thread(r);
                    t.setDaemon(true);  //守护线程，不会阻止程序的关停
                    return t;
                }
            }
    );

    /**
     * 使用同步方法获取价格
     * 1.顺序流
     * 2.并行流
     */
    /**
     * 同步并行流
     *
     * @param product
     * @return
     */
    public List<String> findPricesSequential(String product) {
        return shops.stream()   //顺序流
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    /**
     * 同步并行流
     *
     * @param product
     * @return
     */
    public List<String> findPricesParallel(String product) {
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    /**
     * 使用异步请求,不使用自定义执行器
     *
     * @param product
     * @return
     */
    public List<String> findPricesAsync(String product) {
        List<CompletableFuture<String>> pricesFuture = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))
                ))
                .collect(toList());

        return pricesFuture.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    /**
     * 使用异步请求,使用自定义执行器
     * @param product
     * @return
     */
    public List<String> findPricesAsyncUE(String product) {
        List<CompletableFuture<String>> collect = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)),
                        executor
                ))
                .collect(toList());
        return collect.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }


}
