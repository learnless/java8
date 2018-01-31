package org.learnless.chap08.designPattern;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 工厂模式
 * Created by learnless on 18.1.31.
 */
public class FactoryMain {
    public static void main(String[] args) {
        MyAnimal pig = AnimalFactory.createAnimal("pig");
        MyAnimal lion = AnimalFactory.createAnimal("lion");

        //使用lambda
        MyAnimal pig1 = AnimalFactory.createAnimal("pig");
        MyAnimal pig2 = AnimalFactory.createAnimal("pig");
    }
}

/**
 * 总工厂，可以设计成单例
 */
class AnimalFactory {
    private AnimalFactory animalFactory;

    final static private Map<String, Supplier<MyAnimal>> map = new HashMap<>();

    static {
        map.put("pig", Pig::new);
        map.put("lion", Lion::new);
    }

    private AnimalFactory() {
    }

    public static MyAnimal createAnimal(String name) {
        switch (name) {
            case "lion":
                return new Lion();
            case "pig":
                return new Pig();
            default:
                throw new RuntimeException("无法获取该对象");
        }
    }

    public static MyAnimal createAnimalLambda(String name) {
        Supplier<MyAnimal> s = map.get(name);
        if (s != null)  return s.get();
        throw new IllegalArgumentException("没有该对象" + name);
    }
}

interface MyAnimal {

}

class Lion implements MyAnimal {
    public Lion() {
        System.out.println("获取狮子对象");
    }
}

class Pig implements MyAnimal {
    public Pig() {
        System.out.println("获取猪对象");
    }
}