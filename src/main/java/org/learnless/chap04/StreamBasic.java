package org.learnless.chap04;

import org.learnless.model.Dish;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

/**
 * Created by learnless on 18.1.18.
 */
public class StreamBasic {
    public static void main(String[] args) {
        List<Dish> menu = Dish.menu;
        System.out.println("===============原始数据==================");
        menu.forEach(System.out::println);

        //需求：求出热量低于400，按卡路排序，获取食物名的集合

        //Java7
        List<Dish> lowDish = new ArrayList<>();
        for (Dish dish : menu) {
            if (dish.getCalories() <= 400)
                lowDish.add(dish);
        }
        lowDish.sort(new Comparator<Dish>() {   //按卡路排序
            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(), o2.getCalories());
            }
        });
        List<String> lowNames = new ArrayList<>();  //获取食物名字
        for (Dish dish : lowDish) {
            lowNames.add(dish.getName());
        }
        System.out.println("===============Java7==================");
        lowNames.forEach(System.out::println);

        //lambda
        List<Dish> lowDish2 = new ArrayList<>();
        Predicate<Dish> p = dish -> dish.getCalories() <= 400;
        menu.forEach(dish -> {
            if (p.test(dish))
                lowDish2.add(dish);
        });
        lowDish2.sort(comparing(Dish::getCalories));
        List<String> lowNames2 = new ArrayList<>();
        lowDish2.forEach(dish -> lowNames2.add(dish.getName()));
        System.out.println("===============lambda==================");
        lowNames2.forEach(System.out::println);

        //stream
//        List<Dish> lowDish3 = menu.stream().filter(dish -> dish.getCalories() <= 400).collect(toList());
        List<String> lowNames3 = menu.stream().filter(dish -> dish.getCalories() <= 400).sorted(comparing(Dish::getCalories)).map(Dish::getName).collect(toList());
        System.out.println("===============stream==================");
        lowNames3.forEach(System.out::println);

    }
}
