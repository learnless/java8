package org.learnless.chap04;

import org.learnless.model.Dish;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by learnless on 18.1.18.
 */
public class StreamBasic2 {
    public static void main(String[] args) {
        List<Dish> menu = Dish.menu;
        System.out.println("===========原始数据===============");
        menu.forEach(System.out::println);

        List<String> result = menu.stream()
                .filter(dish -> dish.getCalories() >= 400)
                .limit(3)
                .map(Dish::getName)
                .collect(toList());
        System.out.println();
        result.forEach(System.out::println);

        //流只能被消费一次
        Stream<Dish> stream = menu.stream();
        System.out.println();
        stream.forEach(System.out::println);
//        stream.forEach(System.out::println);  //error stream has already been operated upon or closed


    }
}
