package org.learnless.chap03;

import org.learnless.model.Apple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/**
 * Created by learnless on 18.1.5.
 */
public class Lambdas {

    static List<Apple> apples = Apple.getData();

    public static void main(String[] args) throws IOException {
        Runnable r1 = () -> System.out.println("run1"); //lambda
        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                System.out.println("run2");
            }
        };
//        r1.run();
//        r2.run();

//        process(r1);
//        process(r2);
//        process(() -> System.out.println("lambda"));

        //Function<T, R> T->R apply
        Function<String, Integer> ff = s -> s.length();
        System.out.println(ff.apply("abcfe"));

        //原始类型特化
        IntFunction<String> fff = i -> {
            String degree;
            if (i > 90) degree = "A";
            else if (i > 80) degree = "B";
            else if (i > 70) degree = "C";
            else degree = "D";
            return degree;
        };
        String apply = fff.apply(85);
        System.out.println(apply);

        Function<List<String>, List<Integer>> f = strings -> {
            List<Integer> result = new ArrayList<>();
            for (String s : strings) {
                result.add(s.length());
            }
            return result;
        };
        List<Integer> result = f.apply(Arrays.asList("a", "ab", "abc", "abcd"));
        result.forEach(System.out::println);

        //Predicate<T> T->Boolean test
        Predicate<String> p = s -> s.isEmpty();
        boolean test = p.test("");
        System.out.println(test);

        IntPredicate pp = i -> i > 10;
        boolean test1 = pp.test(11);
        System.out.println(test1);

        //1.逆序
//        apples.sort(comparing(Apple::getWeight).reversed());

        //2。比较器链
//        apples.sort(comparing(Apple::getWeight) //体重排序
//                .reversed()   //逆序
//                .thenComparing(Apple::getColor) //颜色排序
//        );

        //3.negate 非
        List<Apple> notRedApple = new ArrayList<>();
        apples.forEach(apple -> {
            if (negate.test(apple)) {
                notRedApple.add(apple);
            }
        });

        //4.and or
        List<Apple> apples1 = new ArrayList<>();
        apples.forEach(apple -> {
            if (redAndWeightApple.test(apple))
                apples1.add(apple);
        });

        System.out.println("=========原始数据==========");
        apples.forEach(System.out::println);
        System.out.println("=======negate=========");
        notRedApple.forEach(System.out::println);
        System.out.println("=======and or=========");
        apples1.forEach(System.out::println);

        //aneThen 和 compose别去
        Function<Integer, Integer> fn = x -> x + 1;
        Function<Integer, Integer> g = x ->  x * 2;
        Function<Integer, Integer> h = fn.andThen(g);   //g(f(x))
        Function<Integer, Integer> h2 = fn.compose(g);  //f(g(x))
        System.out.println(h.apply(1)); //4
        System.out.println(h2.apply(1));    //3

    }

    public static void process(Runnable r) {
        r.run();
    }

    static Predicate<Apple> redApple = apple -> apple.getColor().equals("red");

    static Predicate<Apple> negate = redApple.negate();

    static Predicate<Apple> redAndWeightApple = redApple
            .and(apple -> apple.getWeight() >= 75)  //and
            .or(apple -> "green".equals(apple.getColor()));  //or

}

