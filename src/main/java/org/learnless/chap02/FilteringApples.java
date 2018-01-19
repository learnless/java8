package org.learnless.chap02;

import org.learnless.model.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        apples.add(new Apple(50, "white"));
    }

    public static void main(String[] args) {
        //1.原始方法
        List<Apple> filterColors = filterColor(apples, new AppleFilterColor("green"));
        filterColors.forEach(System.out::println);
        //2.使用匿名类
        List<Apple> filterColors2 = filterColor(apples, new ApplePredicate<Apple>() {
            @Override
            public boolean test(Apple apple) {
                return "blue".equals(apple.getColor());
            }
        });
        filterColors2.forEach(System.out::println);
        //3.lambda
        List<Apple> filterColor3 = filterColor(FilteringApples.apples, apple -> "blue".equals(apple.getColor()));
        filterColor3.forEach(System.out::println);
        //4.抽象化
        List<Apple> filterColor4 = filter(apples, apple -> "red".equals(apple.getColor()));
        filterColor4.forEach(System.out::println);
        //抽象化其他对象也可以使用
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> resultNum = filter(nums, num -> num % 2 == 0);
        resultNum.forEach(System.out::println);

        //其他用法
        apples.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
        apples.forEach(System.out::println);

        //Runnable接口 run方法 ()->void
        Thread t = new Thread(() -> System.out.println("Runnable接口被调用"));
        t.start();
    }

    public static List<Apple> filterColor(List<Apple> apples, ApplePredicate<Apple> p) {
        List result = new ArrayList<>();
        for (Apple apple : apples) {
            if (p.test(apple)) {
                result.add(apple);
            }
        }

        return result;
    }

    /**
     * 使用策略设计模式，定义公有方法，具体子类实现
     */
    interface ApplePredicate <T> {
        boolean test(T t);
    }

    static class AppleFilterColor implements ApplePredicate<Apple> {
        private String color;

        public AppleFilterColor(String color) {
            this.color = color;
        }

        @Override
        public boolean test(Apple apple) {
            return color.equals(apple.getColor());
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    class AppleFilterWeight implements ApplePredicate<Apple> {
        private int weight;

        public AppleFilterWeight(int weight) {
            this.weight = weight;
        }

        @Override
        public boolean test(Apple apple) {
            return weight == apple.getWeight();
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }


    /**
     * 抽象化,使其适应所有的对象
     */
    interface Predicate<T> {
        boolean test(T t);
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = new ArrayList<>();
        for (T t : list) {
            if (p.test(t)) {
                result.add(t);
            }
        }
        return result;
    }


}
