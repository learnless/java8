package org.learnless.chap07;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * 使用分支/合并求和
 * 采用分治思想
 * Created by learnless on 18.1.28.
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> {
    private static final long THRESHOLD = 10_000;   //不再任务分解为子任务时数组大小

    private final long[] numbers;
    private final int start;    //开始位置
    private final int end;  //结束位置

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    public ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        //判断当前分支的数组是否大于默认值,小于计算求和，否则继续分支
        int len = end - start;
        if (len <= THRESHOLD) {
            return computeSequentially();
        }
        //分支/合并步骤 fork compute join
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, (start+end)/2);  //左分支
        leftTask.fork();    //左分支
        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, (start+end)/2, end);   //右分支
        Long rightResult = rightTask.compute();
        Long leftResult = leftTask.join();  //等leftTask任务完成才继续下一步执行

        return leftResult + rightResult;
    }


    /**
     * 数组求和
     * @return
     */
    private long computeSequentially() {
        long sum = 0L;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    /**
     * 调用
     * @param n
     * @return
     */
    public static long forkJoinSum(int n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();    //实际求出运行的结果比运行的大，由于这里把数字流放进一个数组里，耗时较大
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task); //向线程池加入任务
    }
}
