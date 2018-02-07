package org.learnless.chap11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static java.util.stream.Collectors.toList;

/**
 * 生成同步和异步的方法
 * Created by learnless on 18.2.3.
 */
public class BestPriceFinder {
    private final List<Shop> shops = Arrays.asList(
            new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("ShopEasy"));

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
     * 使用同步顺序流
     */
    public List<String> getPriceSequential(String product) {
        return shops.stream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(toList());
    }

    /**
     * 使用同步并行流
     *
     * @param product
     * @return
     */
    public List<String> getPriceParallel(String product) {
        return shops.stream()
                .parallel()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(toList());
    }

    /**
     * 以异步的方式运行
     *
     * @param product
     * @return
     */
    public List<String> getPriceAsync(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream()
                .map(shop -> supplyAsync(
                        () -> shop.getPrice(product),
                        executor
                ))  //延迟1秒，采用异步操作
                .map(future -> future.thenApply(Quote::parse))  //延迟不高，，采用同步
                .map(future -> future.thenCompose(
                        quote -> supplyAsync(
                                () -> Discount.applyDiscount(quote),
                                executor
                        )
                ))  //由于这一步有延迟1秒,对两个异步操作进行流水线处理,thenCompose依赖于前一个future，而thenCombine则不依赖,请看下面例子
                .collect(toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    /**
     * 第二步采用不阻塞操作，发现跟前面操作运行效率差不多
     *
     * @param product
     * @return
     */
    public List<String> getPriceAsync2(String product) {
        List<CompletableFuture<String>> collect = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> shop.getPrice(product),
                        executor
                ))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenApply(quote -> Discount.applyDiscount(quote)))
                .collect(toList());

        return collect.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    /**
     * 合并两个独立的CompletableFuture
     *
     * @param product
     * @return
     */
    public List<String> getPriceWithRateAsync(String product) {
        List<CompletableFuture<Double>> futures = new ArrayList<>();
        for (Shop shop : shops) {
            CompletableFuture<Double> futureCombine = CompletableFuture.supplyAsync(() -> shop.calculatePrice(product), executor)
                    .thenCombine(
                            CompletableFuture.supplyAsync(() -> ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD), executor),
                            (price, rate) -> price * rate
                    );
            futures.add(futureCombine);
        }
        return futures.stream()
                .map(CompletableFuture::join)
                .map(price -> String.format("the price is %.2f", price))
                .collect(toList());
    }

    /**
     * 合并两个独立的CompletableFuture之后更多的操作，thenCombine thenApply
     *
     * @param product
     * @return
     */
    public List<String> getPriceWithRateAsync2(String product) {
        List<CompletableFuture<String>> futures = new ArrayList<>();
        for (Shop shop : shops) {
            CompletableFuture<String> futureCombine = CompletableFuture.supplyAsync(() -> shop.calculatePrice(product), executor)
                    .thenCombine(
                            CompletableFuture.supplyAsync(() -> ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD), executor),
                            (price, rate) -> price * rate
                    )
                    .thenApply(price -> String.format("%s price is %.2f", shop.getName(), price));
            futures.add(futureCombine);
        }

        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    /**
     * 在Java7合并两个Futur操作
     *
     * @param product
     * @return
     */
    public List<String> getPriceWithRateInJava7(String product) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<Double>> futures = new ArrayList<>();
        for (Shop shop : shops) {
            Future<Double> futureRate = executorService.submit(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    return ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD);
                }
            });

            Future<Double> futurePrice = executorService.submit(new Callable<Double>() {
                @Override
                public Double call() throws Exception {
                    double price = shop.calculatePrice(product);
                    return price * futureRate.get();
                }
            });

            futures.add(futurePrice);
        }

        List<String> result = new ArrayList<>();
        for (Future<Double> future : futures) {
            String s = null;
            try {
                s = String.format("the price is %.2f", future.get());
                result.add(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * 对性能的优化，由于采用流操作，collect()收集器必须等所有的元素返回才能完成，造成堵塞
     * 可以即时返回流，使用thenAccept能使用CompletableFutue执行完成返回的结果
     */
    public Stream<CompletableFuture<String>> getPriceStream(String product) {
        Stream<CompletableFuture<String>> futureStream = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(
                        quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote))
                ));

        return futureStream;
    }

    /**
     * 对CompletableFuture流进行即时操作
     * @param product
     */
    public void printPriceStream(String product) {
        long start = System.nanoTime();
        CompletableFuture[] futures = (CompletableFuture[]) getPriceStream(product)
                .map(f -> f.thenAccept(
                        s -> System.out.println(String.format("%s (花费了 %d 毫秒)", s, ((System.nanoTime() - start) / 1_000_000)))
                ))
                .toArray(size -> new CompletableFuture[size]);//转换为数组
        //这儿添加一些业务逻辑，比如某个商品达到指定价格时，可以调用anyOf停止流操作
//        CompletableFuture<Object> first = CompletableFuture.anyOf(futures);//返回第一个完成的CompletableFuture对象

        CompletableFuture.allOf(futures).join();    //等待所有操作完成
        System.out.println("所有操作完成，花费的时间为:" + (System.nanoTime() - start) / 1_000_000);
    }


}
