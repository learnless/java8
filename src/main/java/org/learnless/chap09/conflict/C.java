package org.learnless.chap09.conflict;

/**
 * Created by learnless on 18.1.31.
 */
public class C implements A, B {
    public static void main(String[] args) {
        new C().hello();
    }

    @Override
    public void hello() {
        B.super.hello();
    }
}
