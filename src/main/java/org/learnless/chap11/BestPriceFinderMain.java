package org.learnless.chap11;

import java.util.List;
import java.util.function.Supplier;

/**
 * 同步/异步 测试类
 * Created by learnless on 18.2.5.
 */
public class BestPriceFinderMain {
    private static BestPriceFinder bestPriceFinder = new BestPriceFinder();

    public static void main(String[] args) {
//        execute("同步顺序流", () -> bestPriceFinder.getPriceSequential("iphone7s"));
//        execute("同步并行流流", () -> bestPriceFinder.getPriceParallel("iphone7s"));
//        execute("异步操作", () -> bestPriceFinder.getPriceAsync("iphone7s"));
//        execute("异步操作2", () -> bestPriceFinder.getPriceAsync2("iphone7s"));
//        execute("合并两个独立异步操作", () -> bestPriceFinder.getPriceWithRateAsync("iphone7s"));
//        execute("合并两个独立异步操作之后更多的操作", () -> bestPriceFinder.getPriceWithRateAsync2("iphone7s"));
//        execute("合并两个独立异步操作,使用Java7", () -> bestPriceFinder.getPriceWithRateInJava7("iphone7s"));
        bestPriceFinder.printPriceStream("iphone7s");

    }

    public static void execute(String msg, Supplier<List<String>> supplier) {
        long start = System.nanoTime();
        List<String> result = supplier.get();
        System.out.println("查询的结果为:" + result);
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println(msg + "花费的时间为"+duration+"毫秒");
    }
}
