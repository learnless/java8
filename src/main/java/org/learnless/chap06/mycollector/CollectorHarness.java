package org.learnless.chap06.mycollector;

/**
 * 性能测试
 * Created by learnless on 18.1.21.
 */
public class CollectorHarness {
    public static void main(String[] args) {
        long faster = Long.MAX_VALUE;
        long start = 0L, end = 0L;

        for (int i = 0; i < 10; i++) {  //测试次数为10，从中取出运行时间最短
            start = System.nanoTime();
            PartitionPrimeNumbers.partitionPrime(1_000_0000); //优化过，一千万个正整数测试
            end = System.nanoTime();
            long duration = (end - start) / 1_000_000;
            if (faster > duration)  faster = duration;
        }
        System.out.println("优化过素数分组，测试时间为 " + faster + " 毫秒");

        faster = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            start = System.nanoTime();
            PrimeNumbersNoImpro.partitionPrime(1_000_0000);   //未优化过
            end = System.nanoTime();
            long duration = (end - start) / 1_000_000;
            if (faster > duration)  faster = duration;
        }

        System.out.println("未优化过素数分组，测试时间为 "+faster+" 毫秒");
    }
}
