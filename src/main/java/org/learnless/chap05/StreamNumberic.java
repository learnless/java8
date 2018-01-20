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

        //数值范围 IntStream LongStream
        //获取[1,100]偶数流
        IntStream intStream1 = IntStream.rangeClosed(1, 100)
                .filter(i -> i % 2 == 0);

        //勾股数
        Stream<int[]> triples = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a ->
                        IntStream.rangeClosed(a, 100)
                                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                                .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                );

        System.out.println(sum);
        System.out.println(max);
//        intStream1.forEach(System.out::println);
        triples.limit(5)
                .forEach(t -> System.out.println(t[0] + " , " + t[1] + " , " + t[2]));
    }
}
