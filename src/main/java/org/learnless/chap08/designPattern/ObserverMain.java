package org.learnless.chap08.designPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式
 * Created by learnless on 18.1.31.
 */
public class ObserverMain {
    public static void main(String[] args) {
        //传统方式
        Subject subject = new SubjectImpl();
        subject.registerObserver(new ObserverA());
        subject.registerObserver(new ObserverB());
        subject.notifyObservers("你们好！！！");

        //lambda表达式，无需显示实例化观察者对象
        Subject subject1 = new SubjectImpl();
        subject1.registerObserver(msg -> {
            System.out.println("lambda表示式收到的信息:" + msg);
        });
        subject1.registerObserver(msg -> {
            System.out.println("lambda表示式收到的信息:" + msg);
        });
        subject1.notifyObservers("你们好!!");
    }
}

/**
 * 被观察者,一般提供注册和通知给观察者方法
 */
interface Subject {
    void registerObserver(Observer observer);
    void notifyObservers(String msg);
}

class SubjectImpl implements Subject {
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(String msg) {
        observers.forEach(observer -> observer.notify(msg));
    }
}

/**
 * 观察者，一般有收到通知方法
 */
@FunctionalInterface
interface Observer {
    void notify(String msg);
}

class ObserverA implements Observer {

    @Override
    public void notify(String msg) {
        System.out.println("观察者A收到的信息:" + msg);
    }
}

class ObserverB implements Observer {

    @Override
    public void notify(String msg) {
        System.out.println("观察者B收到的信息:" + msg);
    }
}
