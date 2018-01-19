package org.learnless.chap05;

import org.learnless.model.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by learnless on 18.1.19.
 */
public class StreamBasic {
    public static void main(String[] args) {
        List<Dish> menu = Dish.menu;
//        menu.forEach(System.out::println);

        //筛选 filter distinct
//        filter(menu);

        //切片 skip limit
//        cut(menu);

        //映射 map flatMap
//        map(menu);

        //匹配 allMatch anyMatch noneMatch
//        match(menu);

        //查找 findAny findFirst
//        find(menu);

        //规约 reduce
        reduce(menu);
    }

    private static void reduce(List<Dish> menu) {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6);
        //sum
        int sum = nums.stream().reduce(0, (a, b) -> a + b);
        int sum2 = nums.stream().reduce(0, Integer::sum);   //令一种写法
        Optional<Integer> sum3 = nums.stream().reduce((a, b) -> a + b); //无初始值

        //min
        Optional<Integer> min = nums.stream().reduce(Integer::min);
        Optional<Integer> min2 = nums.stream().reduce((x, y) -> x > y ? y : x);

        //max
        Optional<Integer> max = nums.stream().reduce(Integer::max);
        Optional<Integer> max2 = nums.stream().reduce((x, y) -> x > y ? x : y);

        //count
        long count = menu.stream().count();
        Optional<Integer> count2 = menu.stream().map(d -> 1).reduce(Integer::sum);

        System.out.println(sum);
        System.out.println(sum2);
        System.out.println(sum3.get());
        System.out.println(min.get());
        System.out.println(min2.get());
        System.out.println(max.get());
        System.out.println(max2.get());
        System.out.println(count);
        System.out.println(count2.get());
    }

    private static void find(List<Dish> menu) {
        Optional<Dish> dish = menu.stream().filter(Dish::isVegetarian).findAny();   //当前任意元素
        //Optionnal用法
        //isPresent 有值返回true
        boolean present = dish.isPresent();
        //ifPresent 有值执行代码块
        dish.ifPresent(d -> System.out.println(d));
        //get 值存在返回值，否则抛出NoSuchElement
        Dish dish1 = dish.get();
        //orElse 值存在返回值，否则返回默认值

        System.out.println(dish1);
    }

    private static void match(List<Dish> menu) {
        boolean b = menu.stream().allMatch(dish -> dish.getCalories() < 1000);
        boolean b1 = menu.stream().anyMatch(Dish::isVegetarian);
        boolean b2 = menu.stream().noneMatch(dish -> dish.getCalories() > 1000);

        System.out.println();
        System.out.println(b2);
    }

    private static void map(List<Dish> menu) {
        List<Integer> result3 = menu.stream()   //将菜肴名字的长度转化为集合
                .map(Dish::getName)
                .map(String::length)
                .collect(toList());
        System.out.println();
        result3.forEach(System.out::println);

        //将菜单的名字转化为字符表
        List<String> words = menu.stream().map(Dish::getName).collect(toList());
        List<String[]> result4 = words.stream() //Stream<String>
                .map(word -> word.split(""))    //String<String[]>
                .distinct()
                .collect(toList()); //List<String[]> 错误
        System.out.println();
        result4.forEach(System.out::println);
        //正确做法，流的扁平化
        List<String> result5 = words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)    //扁平化 Stream<String>
                .distinct()
                .collect(toList());
        System.out.println();
        result5.forEach(System.out::println);

        //求数组的平方
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> result6 = list.stream()
                .map(i -> i * i)
                .collect(toList());
        System.out.println();
        result6.forEach(System.out::println);

        //[1,2,3] [1,2] -> [(1,1),(1,2),(2,1),(2,2),(3,1),(3,2)]
        List<Integer> num1 = Arrays.asList(1,2,3);
        List<Integer> num2 = Arrays.asList(1,2);
        List<int[]> result7 = num1.stream()
                .flatMap(i -> num2.stream()
                        .map(j -> new int[]{i, j})
                )
                .collect(toList());
        result7.forEach(System.out::print);

    }

    private static void cut(List<Dish> menu) {
        List<Dish> result2 = menu.stream().filter(dish -> dish.getCalories() >= 400).skip(3).limit(3).collect(toList());
        System.out.println();
        result2.forEach(System.out::println);
    }

    private static void filter(List<Dish> menu) {
        List<Dish> result = menu.stream().filter(Dish::isVegetarian).collect(toList());
        System.out.println();
        result.forEach(System.out::println);
    }


}
