package org.learnless.chap10;

import org.learnless.chap10.tranditional.Car;
import org.learnless.chap10.tranditional.Insurance;
import org.learnless.chap10.tranditional.Person;
import org.testng.annotations.Test;

/**
 * Created by learnless on 18.1.31.
 */
public class TranditionalMain {

    Insurance insurance = new Insurance();
    Car car = new Car();
    Person person = new Person();

    {
        insurance.setName("平安保险");
        car.setInsurance(insurance);
        person.setCar(car);
    }

    /**
     * 传统获取对象，容易抛空指针异常
     */
    @Test
    public void t1() throws Exception {
        if (person != null) {
            Car car1 = person.getCar();
            if (car1 != null) {
                Insurance insurance1 = car1.getInsurance();
                if (insurance1 != null) {
                    String name = insurance1.getName();
                    System.out.println(name);
                }
            }
        }
    }



}
