package org.learnless.chap03;

import org.learnless.model.Apple;

import java.util.Comparator;
import java.util.List;

/**
 * 排序实战
 * Created by learnless on 18.1.18.
 */
public class Sorting {

    public static void main(String[] args) {
        List<Apple> apples = Apple.getData();
        System.out.println("==========未排序前==============");
        apples.forEach(System.out::println);
        //1.传统方式
//        apples.sort(new AppleComparator());

        //2.使用匿名类
//        apples.sort(new Comparator<Apple>() {
//            @Override
//            public int compare(Apple o1, Apple o2) {
//                return o1.getWeight().compareTo(o2.getWeight());
//            }
//        });

        //3.lambda
        //3.1
//        apples.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
        //3.2
//        apples.sort(c);

        //4.使用方式引用
        apples.sort(Comparator.comparing(Apple::getWeight));

        System.out.println("==========排序后==============");
        apples.forEach(System.out::println);
    }

    static Comparator<Apple> c = Comparator.comparing(Apple::getWeight);
}

class AppleComparator implements Comparator<Apple> {

    @Override
    public int compare(Apple o1, Apple o2) {
        return o1.getWeight().compareTo(o2.getWeight());
    }
}


