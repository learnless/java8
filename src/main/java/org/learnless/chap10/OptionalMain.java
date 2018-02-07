package org.learnless.chap10;

import org.learnless.chap10.optional.Car;
import org.learnless.chap10.optional.Insurance;
import org.learnless.chap10.optional.Person;
import org.testng.annotations.Test;

import java.util.Optional;

/**
 * Created by learnless on 18.1.31.
 */
public class OptionalMain {

    Insurance insurance = new Insurance();
    Car car = new Car();
    Person person = new Person();

    {
        insurance.setName("平安保险");
        car.setInsurance(Optional.of(insurance));
        person.setCar(Optional.ofNullable(car));
    }

    /**
     * 创建Optional对象的几种方式
     * 1.empty 创建一个空Optional
     * 2.of 如果传入的对象为Null,运行抛出异常
     * 3.ofNullable 传入对象为null,得到的是一个空Optional
     * @throws Exception
     */
    @Test
    public void t1() throws Exception {
        Optional<Insurance> insuranceOptional = Optional.ofNullable(insurance);
        Optional<String> name = insuranceOptional.map(Insurance::getName);

    }

    @Test
    public void t2() throws Exception {
        Optional<Person> optPerson = Optional.of(person);
//        Optional<Optional<Car>> optCar = optPerson.map(Person::getCar); //error
        String name = optPerson.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("unknown");
        System.out.println(name);

    }



}
