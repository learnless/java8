package org.learnless.chap08;

/**
 * Created by learnless on 18.1.30.
 */
public class LambdaTest {
    public static void main(String[] rgs) {
//        doSome(() -> System.out.println()); //error
        doSome((Task) () -> System.out.println("right"));
    }

    public static void doSome(Runnable runnable) {
        runnable.run();
    }

    public static void doSome(Task task) {
        task.execute();
    }
}
