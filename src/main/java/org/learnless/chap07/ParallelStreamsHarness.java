package org.learnless.chap07;

import java.util.function.Function;

/**
 * 单词统计测试类
 * Created by learnless on 18.1.28.
 */
public class ParallelStreamsHarness {
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("Java8以前,运行的时间为: " + measureSumperf(ParallelStreamBasic::sum, 10_000_000) + "ms");
        System.out.println("==========================================");
        System.out.println("Java8顺序流,运行的时间为: " + measureSumperf(ParallelStreamBasic::sequantialSum, 10_000_000) + "ms");
        System.out.println("==========================================");
        System.out.println("Java8并行流,运行的时间为: " + measureSumperf(ParallelStreamBasic::parallelSum, 10_000_000) + "ms");
        System.out.println("==========================================");
        System.out.println("Java8顺序流改进,运行的时间为: " + measureSumperf(ParallelStreamBasic::rangeclosed, 10_000_000) + "ms");
        System.out.println("==========================================");
        System.out.println("Java8顺序流改进使用并行流,运行的时间为: " + measureSumperf(ParallelStreamBasic::rangclosedParallel, 10_000_000) + "ms");
        System.out.println("==========================================");
        System.out.println("Java8不正确的使用顺序流ForEach: " + measureSumperf(ParallelStreamBasic::forEachResult, 10_000_000) + "ms");
        System.out.println("==========================================");
        System.out.println("Java8使用分支/合并框架求和，运行的结果为: " + measureSumperf(ForkJoinSumCalculator::forkJoinSum, 10_000_000) + "ms");
    }

    /**
     * 获取运行时间
     * @param f Java8 Function接口
     * @param n 测试的数量
     * @return
     */
    public static <T, R> long measureSumperf(Function<T, R> f, T n) {
        long value = Long.MAX_VALUE;
        //十次测试，获取最短运行时间那次
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            R sum = f.apply(n);  //运行的结果
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("运行的结果：" + sum);
            if (duration < value) {
                value = duration;
            }
        }

        return value;
    }
}
