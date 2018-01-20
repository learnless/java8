package org.learnless.chap05;

import org.learnless.model.Dish;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 数值流
 * Created by learnless on 18.1.20.
 */
public class StreamNumberic {
    public static void main(String[] args) {
        List<Dish> menu = Dish.menu;

        int sum = menu.stream()
                .map(Dish::getCalories) //对象流
                .reduce(0, Integer::sum);

        //映射到数值流
        int sum1 = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();

        //转换为对象流
        IntStream intStream = menu.stream().mapToInt(Dish::getCalories);
        Stream<Integer> integerStream = intStream.boxed();  //将数值流转换为对象流

        //没有最大值，提供一个默认值
        OptionalInt optionalInt = menu.stream()
                .mapToInt(Dish::getCalories)
                .max();
        int max = optionalInt.orElse(1);

        System.out.println(sum);
        System.out.println(max);
    }
}
