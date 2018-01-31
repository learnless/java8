package org.learnless.chap08.designPattern;

/**
 * 模版设计模式-把子类相同的方法抽象出来，放在父类中
 * Created by learnless on 18.1.30.
 */
public class Template {
    public static void main(String[] args) {
        //原始调用
        Animal monkey = new Monkey();
        Animal elephant = new Elephant();
        monkey.execute();
        elephant.execute();

    }


}

/**
 * 父类
 */
abstract class Animal {
    public abstract void run();

    public abstract void sleep();

    public final void execute() {
        run();
        sleep();
    }
}

/**
 * 具体子类
 */
class Monkey extends Animal {

    @Override
    public void run() {
        System.out.println("monkey run!!!");
    }

    @Override
    public void sleep() {
        System.out.println("monkey sleep!!");
    }
}

class Elephant extends Animal {

    @Override
    public void run() {
        System.out.println("elephant run!!");
    }

    @Override
    public void sleep() {
        System.out.println("elephant sleep!!");
    }
}
