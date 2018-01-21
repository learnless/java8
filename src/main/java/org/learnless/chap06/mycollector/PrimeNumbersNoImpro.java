package org.learnless.chap06.mycollector;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;

/**
 * 未优化的素数分组
 * Created by learnless on 18.1.21.
 */
public class PrimeNumbersNoImpro {
    public static void main(String[] args) {
        Map<Boolean, List<Integer>> map = partitionPrime(100);
        print(map);
    }

    public static Map<Boolean, List<Integer>> partitionPrime(int candicate) {
        return IntStream.rangeClosed(2, candicate)
                .boxed()
                .collect(groupingBy(i -> isPrime(i)));
    }

    private static boolean isPrime(int candicate) {
        int sqrt = (int) Math.sqrt(candicate);
        return IntStream.rangeClosed(2, sqrt)
                .noneMatch(i -> candicate % i == 0);
    }

    private static void print(Map<Boolean, List<Integer>> map) {
        map.forEach((k, v) -> System.out.println("k = " + k + " ,v = " + v));
    }

}
