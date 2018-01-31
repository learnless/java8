package org.learnless.chap07;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.summingLong;

/**
 * 并行流
 * Created by learnless on 18.1.21.
 */
public class ParallelStreamBasic {
    public static void main(String[] args) {
        //1...n累加
        long sum = sum(20);
        long sequantialSum = sequantialSum(20);
        long parallelSum = parallelSum(20);


        System.out.println(sum);
        System.out.println(sequantialSum);
        System.out.println(parallelSum);
    }

    /**
     * Java8以前
     * @param n
     * @return
     */
    static long sum(int n) {
        long sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }


    /**
     * 顺序流
     * 生成一个无穷大数字流，限制其数目
     * iterate很难分成独立的小块，由于依赖于前一次的运行结果
     * @param n
     * @return
     */
    static long sequantialSum(int n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
//                .reduce(0L, Long::sum);
                .collect(summingLong(Long::longValue));
    }

    /**
     * 并行流
     * @param n
     * @return
     */
    static long parallelSum(int n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel() //并行
                .reduce(0L, Long::sum);
    }

    /**
     * 对顺序流做出改进
     * @param n
     * @return
     */
    static long rangeclosed(int n) {
        return LongStream.rangeClosed(1, n)
                .reduce(0L, Long::sum);
    }

    /**
     * 使用并行流
     * @param n
     * @return
     */
    static long rangclosedParallel(int n) {
        return LongStream.rangeClosed(1, n)
                .parallel() //并行
                .reduce(0L, Long::sum);
    }

    /**
     * 不正确的使用并行流，由于Accumulator类中成员变量sum出现竞争情况,造成结果的不正确性
     * @param n
     * @return
     */
    static long forEachResult(int n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n)
                .parallel() //error
                .forEach(accumulator::add);
        return accumulator.sum;
    }

    /**
     * 累加器类
     */
    static class Accumulator {
        private long sum = 0L;
        public void add(long n) {
            sum += n;
        }
    }


}
