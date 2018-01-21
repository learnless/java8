package org.learnless.chap06;

import org.learnless.model.Dish;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

/**
 * 归于与汇总
 * Created by learnless on 18.1.20.
 */
public class Reducing {
    public static void main(String[] args) {
        List<Dish> menu = Dish.menu;
        //counting
        long count = menu.stream().count();
        Long count2 = menu.stream().collect(Collectors.counting());

        //maxBy
        Optional<Dish> optionalMax = menu.stream()
                .reduce((t1, t2) -> t1.getCalories() > t2.getCalories() ? t1 : t2);
        Optional<Dish> optionalMax2 = menu.stream()
                .collect(maxBy(comparing(Dish::getCalories)));
        int max3 = menu.stream()
                .collect(reducing(0, Dish::getCalories, Integer::max));

        //summingInt
        Optional<Integer> sum = menu.stream()
                .map(Dish::getCalories)
                .reduce(Integer::sum);
        int sum2 = menu.stream()
                .collect(summingInt(Dish::getCalories));

        //一次性获取属性，能获取总和 平均值 最大值 最小值等
        IntSummaryStatistics intSummaryStatistics = menu.stream()
                .collect(summarizingInt(Dish::getCalories));

        //joining 连接字符串
        Optional<String> stringOptional = menu.stream()
                .map(Dish::getName)
                .reduce((s1, s2) -> s1 + s2);
        String collect = menu.stream()
                .map(Dish::getName)
                .collect(joining());
        String collect2 = menu.stream()
                .map(Dish::getName)
                .collect(joining(","));

        //广义的归约，reduce只是collect特殊情况
        int sum3 = menu.stream()
                .collect(reducing(0, Dish::getCalories, (i, j) -> i + j));
        int sum4 = menu.stream()
                .collect(reducing(0, Dish::getCalories, Integer::sum));//更减


        System.out.println(count2);
        System.out.println(optionalMax.get());
        System.out.println(optionalMax2.get());
        System.out.println(max3);
        System.out.println(sum.get());
        System.out.println(sum2);
        System.out.println(stringOptional.get());
        System.out.println(collect);
        System.out.println(collect2);
        System.out.println(sum3);
        System.out.println(sum4);
    }
}
