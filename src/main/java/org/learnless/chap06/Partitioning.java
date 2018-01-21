package org.learnless.chap06;

import org.learnless.model.Dish;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.partitioningBy;

/**
 * 分区，分组的特殊情况，由一个返回值为布尔类型的谓语，凡是能用分区实现的都可以用分组实现
 * Created by learnless on 18.1.20.
 */
public class Partitioning {
    public static void main(String[] args) {
        List<Dish> menu = Dish.menu;

        Map<Boolean, List<Dish>> example1 = menu.stream()
                .collect(groupingBy(Dish::isVegetarian));

        Map<Boolean, List<Dish>> map1 = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian));

        //二级收集器
        Map<Boolean, Map<Dish.Type, List<Dish>>> map2 = menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        groupingBy(Dish::getType)));

        //是否为素数分组
        Map<Boolean, List<Integer>> map3 = partitionPrime(20);


        System.out.println("===========map1===========");
        map1.forEach((k, v) -> System.out.println("k = " + k + " ,v = " + v));
        System.out.println("===========map2===========");
        map2.forEach((k, v) -> System.out.println("k = " + k + " ,v = " + v));
        System.out.println("===========map3===========");
        map3.forEach((k, v) -> System.out.println("k = " + k + " ,v = " + v));
    }

    static Map<Boolean, List<Integer>> partitionPrime(int n) {
        Map<Boolean, List<Integer>> map = IntStream.rangeClosed(2, n)
                .boxed()
                .collect(partitioningBy(i -> isPrime(i)));
        return map;
    }

    static boolean isPrime(int i) {
        int sqrt = (int) Math.sqrt(i);
        return IntStream.rangeClosed(2, sqrt).noneMatch(j -> i % j == 0);
    }
}
