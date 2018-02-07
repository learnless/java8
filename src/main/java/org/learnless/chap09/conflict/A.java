package org.learnless.chap09.conflict;

/**
 * Created by learnless on 18.1.31.
 */
public interface A {
    default void hello() {
        System.out.println("from A");
    }
}
