package org.learnless.chap05;

import org.learnless.model.Dish;

import java.util.List;

/**
 * 数值流
 * Created by learnless on 18.1.20.
 */
public class StreamNumberic {
    public static void main(String[] args) {
        List<Dish> menu = Dish.menu;

        int sum = menu.stream()
                .map(Dish::getCalories)
                .reduce(0, Integer::sum);

        //原始流
        //映射到数值流
        int sum1 = menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();

        System.out.println(sum);
        System.out.println(sum1);
    }
}
