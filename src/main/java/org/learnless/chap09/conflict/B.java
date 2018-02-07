package org.learnless.chap09.conflict;

/**
 * 由于B继承A，比A更具体，所以优先级高
 * Created by learnless on 18.1.31.
 */
public interface B {
    default void hello() {
        System.out.println("from B");
    }
}
