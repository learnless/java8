package org.learnless.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by learnless on 18.1.4.
 */
public class Apple {
    private Integer weight;
    private String color;

    public static List<Apple> getData() {
        List<Apple> apples = new ArrayList<>();
        apples = new ArrayList<>();
        apples.add(new Apple(50, "red"));
        apples.add(new Apple(75, "gray"));
        apples.add(new Apple(75, "green"));
        apples.add(new Apple(75, "orange"));
        apples.add(new Apple(75, "blue"));
        apples.add(new Apple(100, "blue"));
        apples.add(new Apple(100, "red"));
        apples.add(new Apple(150, "red"));
        apples.add(new Apple(150, "white"));
        apples.add(new Apple(50, "white"));
        apples.add(new Apple(75, "black"));

        return apples;
    }

    public Apple(int weight, String color) {
        this.weight = weight;
        this.color = color;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "weight=" + weight +
                ", color='" + color + '\'' +
                '}';
    }
}
