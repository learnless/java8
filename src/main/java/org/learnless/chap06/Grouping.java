package org.learnless.chap06;

import org.learnless.model.Dish;

import java.util.*;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

/**
 * 分组
 * Created by learnless on 18.1.20.
 */
public class Grouping {
    public static void main(String[] args) {
        List<Dish> menu = Dish.menu;

        //按菜肴类型分组
        Map<Dish.Type, List<Dish>> map = menu.stream()
                .collect(groupingBy(Dish::getType));

        //按热量分组
        Map<CalorType, List<Dish>> map2 = menu.stream()
                .collect(groupingBy(dish -> {
                    if (dish.getCalories() < 400) {
                        return CalorType.DIET;
                    } else if (dish.getCalories() < 700) {
                        return CalorType.NORMAL;
                    } else {
                        return CalorType.FAT;
                    }
                }));

        //多级分组
        //菜肴类型 热量 Map<Type, Map<CalorType, List>>
        Map<Dish.Type, Map<CalorType, List<Dish>>> map3 = menu.stream()
                .collect(groupingBy(Dish::getType, groupingBy(dish -> {
                            if (dish.getCalories() < 400) {
                                return CalorType.DIET;
                            } else if (dish.getCalories() < 700) {
                                return CalorType.NORMAL;
                            } else {
                                return CalorType.FAT;
                            }
                        }))
                );

        //按子组收集数据
        Map<Dish.Type, Long> map4 = menu.stream()
                .collect(groupingBy(Dish::getType, counting()));

        Map<Dish.Type, Optional<Dish>> map5 = menu.stream()
                .collect(groupingBy(Dish::getType,
                        maxBy(comparingInt(Dish::getCalories))));

        //将收集的结果转换为另一种类型，比如上面类型为Optional没用，得转换
        Map<Dish.Type, Dish> map6 = menu.stream()
                .collect(groupingBy(Dish::getType,
                        collectingAndThen(maxBy(comparingInt(Dish::getCalories)),
                                Optional::get)
                ));

        //例子 分组中获取总能力
        Map<Dish.Type, Integer> map7 = menu.stream()
                .collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));

        //例子 由类型分组，每组再由热量获取集合
        Map<Dish.Type, Set<CalorType>> map8 = menu.stream()
                .collect(groupingBy(Dish::getType,
                        mapping(dish -> {
                                    if (dish.getCalories() < 400) {
                                        return CalorType.DIET;
                                    } else if (dish.getCalories() < 700) {
                                        return CalorType.NORMAL;
                                    } else {
                                        return CalorType.FAT;
                                    }
                                },
                                toSet() 
                        )));

        //收集分组名字
        Map<Dish.Type, Set<String>> map9 = menu.stream()
                .collect(groupingBy(Dish::getType, mapping(Dish::getName, toSet())));


        System.out.println("===========map1===========");
        map.forEach((k, v) -> System.out.println("k = " + k + " ,v = " + v));
        System.out.println("===========map2===========");
        map2.forEach((k, v) -> System.out.println("k = " + k + " ,v = " + v));
        System.out.println("===========map3===========");
        map3.forEach((k, v) -> System.out.println("k = " + k + " ,v = " + v));
        System.out.println("===========map4===========");
        map4.forEach((k, v) -> System.out.println("k = " + k + " ,v = " + v));
        System.out.println("===========map5===========");
        map5.forEach((k, v) -> System.out.println("k = " + k + " ,v = " + v));
        System.out.println("===========map6===========");
        map6.forEach((k, v) -> System.out.println("k = " + k + " ,v = " + v));
        System.out.println("===========map7===========");
        map7.forEach((k, v) -> System.out.println("k = " + k + " ,v = " + v));
        System.out.println("===========map9===========");
        map8.forEach((k, v) -> System.out.println("k = " + k + " ,v = " + v));
    }

    public enum CalorType {DIET, NORMAL, FAT}

}

