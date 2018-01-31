package org.learnless.chap08;

/**
 * 定义一个函数接口，故意与Runnable接口函数签名一样
 * Created by learnless on 18.1.30.
 */
@FunctionalInterface
public interface Task {
    public void execute();
}
