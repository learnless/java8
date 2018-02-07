package org.learnless.chap11.v1;

import java.util.List;
import java.util.function.Supplier;

/**
 * 同步/异步 测试类
 * Created by learnless on 18.2.3.
 */
public class BestPricesFinderMain {
    static BestPriceFinder bestPriceFinder = new BestPriceFinder();

    public static void main(String[] args) {
        execute("同步顺序流", () -> bestPriceFinder.findPricesSequential("iphone6s"));
        execute("同步并行流", () -> bestPriceFinder.findPricesParallel("iphone6s"));
        execute("异步流,不使用自定义执行器", () -> bestPriceFinder.findPricesAsync("iphone6s"));
        execute("异步流,使用自定义执行器", () -> bestPriceFinder.findPricesAsyncUE("iphone6s"));
    }

    private static void execute(String msg, Supplier<List<String>> supplier) {
        long start = System.nanoTime();
        List<String> result = supplier.get();
        System.out.println("查询的结果为：" + result);
        long duration = ((System.nanoTime()) - start) / 1_000_100;
        System.out.println(msg + "花费的时间为"+duration+"毫秒");
    }
}
