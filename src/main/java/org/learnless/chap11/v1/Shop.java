package org.learnless.chap11.v1;

import org.learnless.chap11.Utils;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by learnless on 18.2.2.
 */
public class Shop {
    private String name;
    private Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    /**
     * 采用同步的方式获取价格
     * @param product
     * @return
     */
    public double getPrice(String product) {
        return calculatePrice(product);
    }

    /**
     * 使用异步的方式获取价格
     * @param product
     * @return
     */
//    public Future<Double> getPriceAsync(String product) {
//        CompletableFuture<Double> future = new CompletableFuture<>();
//        new Thread(() -> {
//            //为防止在调用get方法永久阻塞，得捕获异常
//            try {
//                double price = calculatePrice(product);
//                //由于计算价格需要时间长，设置Future的返回值
//                future.complete(price);
//            } catch (Exception e) {
//                future.completeExceptionally(e);
//            }
//        }).start();
//        return future;
//    }

    /**
     * 使用工厂方法，完成上述方法的所有功能，包括捕获异常处理
     * @param product
     * @return
     */
    public Future<Double> getPriceAsync(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    private double calculatePrice(String product) {
        //让其计算延迟一秒
        Utils.delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1); //让其价格随机获取
    }

    public String getName() {
        return name;
    }
}
