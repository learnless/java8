package org.learnless.chap10.optional;


import java.util.Optional;

/**
 * Created by learnless on 18.1.31.
 */
public class Person {
    private Optional<Car> car;

    public Optional<Car> getCar() {
        return car;
    }

    public void setCar(Optional<Car> car) {
        this.car = car;
    }
}
