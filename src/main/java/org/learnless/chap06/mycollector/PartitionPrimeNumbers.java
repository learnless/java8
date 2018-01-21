package org.learnless.chap06.mycollector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;


/**
 * 素数分组优化
 * Created by learnless on 18.1.21.
 */
public class PartitionPrimeNumbers {
    public static void main(String[] args) {
        //调用优化后的结果
        Map<Boolean, List<Integer>> map = partitionPrime(100);
//        Map<Boolean, List<Integer>> map = partitionPrimeShort(100);

        print(map);
    }

    public static Map<Boolean, List<Integer>> partitionPrime(int candicate) {
        return IntStream.rangeClosed(2, candicate)
                .boxed()
                .collect(new PrimeNumbersCollector());//调用自定义收集器
    }

    /**
     * 不使用自定义收集器，直接在方法实现，更紧凑些，但可读、重用差些
     * @param candicate
     * @return
     */
    public static Map<Boolean, List<Integer>> partitionPrimeShort(int candicate) {
        return IntStream.rangeClosed(2, candicate)
                .boxed()
                .collect(
                        () -> new HashMap<Boolean, List<Integer>>() {{
                            put(true, new ArrayList<Integer>());
                            put(false, new ArrayList<Integer>());
                        }},
                        (map, item) -> {
                            map.get(isPrime(map.get(true), item)).add(item);
                        },
                        (map1, map2) -> {
                            map1.get(true).addAll(map2.get(true));
                            map1.get(false).addAll(map2.get(false));
                        }
                );
    }

    /**
     * 素数判断
     * @param primes
     * @param candicate
     * @return
     */
    protected static boolean isPrime(List<Integer> primes, int candicate) {
        int sqrt = (int) Math.sqrt(candicate);
        return takeWhile(primes, p -> p <= sqrt).stream()
                .noneMatch(p -> candicate % p == 0);
    }

    /**
     * 优化返回素数列表
     * @param list
     * @param p
     * @param <A>
     * @return
     */
    private static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
        int i = 0;
        for (A item : list) {
            if (!p.test(item)) {    //直到不满足条件时，截取List
                return list.subList(0, i);
            }
            i ++;
        }
        return list;    //条件都满足，还是返回原先的list
    }

    private static void print(Map<Boolean, List<Integer>> map) {
        map.forEach((k, v) -> System.out.println("k = " + k + " ,v = " + v));
    }

}
