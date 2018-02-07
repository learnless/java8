package org.learnless.chap11.v1;

import java.util.concurrent.Future;

/**
 * 测试异步方法
 * Created by learnless on 18.2.2.
 */
public class ShopMain {
    public static void main(String[] args) {
        Shop shop = new Shop("taobao");
        long start = System.nanoTime();
        Future<Double> future = shop.getPriceAsync("iphone7");
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.print("执行异步操作花费的时间：" + duration + " 毫秒");

        //可以在计算价格期间执行其他的任务
        doSomethingElse();

        //获取价格，此时价格未知会阻塞
        try {
            double price = future.get();
            System.out.printf("价格为 %.2f%n", price);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        long duration2 = (System.nanoTime() - start) / 1_000_000;
        System.out.println("执行异步操作等到获取价格花费的时间：" + duration2 + " 毫秒");
    }

    private static void doSomethingElse() {
        System.out.println("");
    }
}
