package org.learnless.chap08.designPattern;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * 责任链模式
 * Created by learnless on 18.1.31.
 */
public class ChainOfResponsibilityMain {
    public static void main(String[] args) {
        //传统方式
        ProccessingObject groupLeader = new GroupLeader();
        ProccessingObject manager = new Manager();
        groupLeader.setSuccessor(manager);
        String r = (String) groupLeader.handle("hahaha");
        String result = (String) manager.handle("haha");
        System.out.println(r);
        System.out.println(result);

        //lambdab表达式
        UnaryOperator<String> groupLeader1 = s -> "组长处理好了" + s;
        UnaryOperator<String> manager1 = s -> "项目经理处理好了" + s;
        Function<String, String> f = groupLeader1.andThen(manager1);
        String lambda = f.apply("lambda");
        System.out.println(lambda);
    }
}

abstract class ProccessingObject<T> {
    protected ProccessingObject<T> successor;   //处理对象

    abstract T handle(T t);

    public ProccessingObject<T> getSuccessor() {
        return successor;
    }

    public void setSuccessor(ProccessingObject<T> successor) {
        this.successor = successor;
    }
}

class GroupLeader extends ProccessingObject<String> {

    @Override
    String handle(String s) {
        if (getSuccessor() != null) {
            return getSuccessor().handle(s);
        }
        return "组长处理好了" + s;
    }
}

class Manager extends ProccessingObject<String> {

    @Override
    String handle(String s) {
        if (getSuccessor() != null) {
            return getSuccessor().handle(s);
        }
        return "项目经理处理好了" + s;
    }
}
class Diretor extends ProccessingObject<String> {

    @Override
    String handle(String s) {
        if (getSuccessor() != null) {
            return getSuccessor().handle(s);
        }
        return "总监处理好了" + s;
    }
}

