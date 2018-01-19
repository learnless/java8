package org.learnless.chap01;

import org.learnless.model.Apple;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by learnless on 18.1.4.
 */
public class FilteringApples {
    static List<Apple> apples;

    static {
        apples = new ArrayList<>();
        apples.add(new Apple(50, "red"));
        apples.add(new Apple(75, "orange"));
        apples.add(new Apple(75, "green"));
        apples.add(new Apple(75, "green"));
        apples.add(new Apple(75, "blue"));
        apples.add(new Apple(100, "blue"));
        apples.add(new Apple(150, "white"));
    }

    public static void main(String[] args) {
        FilteringApples filteringApples = new FilteringApples();
//        filteringApples.origin();
//        filteringApples.filterApples(apple -> apple.getWeight() == 75 && "green".equals(apple.getColor()));
        filteringApples.filterApples2();

    }

    //使用原始方法
    void origin() {
        //比如筛选出75g green苹果
        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            if (apple.getWeight() == 75 && "green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        result.forEach(System.out::println);
    }

    //使用lambda
    void filterApples(Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : apples) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        result.forEach(System.out::println);
    }

    //使用stream
    void filterApples2() {
        apples.stream().filter(apple -> apple.getWeight() == 75 && "green".equals(apple.getColor())).forEach(System.out::println);
    }

}
