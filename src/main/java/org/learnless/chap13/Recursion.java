package org.learnless.chap13;

import java.util.stream.IntStream;

/**
 * 递归与迭代
 * 累积
 * Created by learnless on 18.2.6.
 */
public class Recursion {
    public static void main(String[] args) {
        System.out.println(factorialIterative(5));  //使用传统的迭代方式
        System.out.println(factorialRecursive(5));  //使用递归
        System.out.println(factorialStreams(5));    //使用流
        System.out.println(factorialTailRecursive(5));  //尾-递,推荐，栈上不需保存递归中间变量
    }

    private static int factorialTailRecursive(int i) {
        return factorialHelper(1, i);
    }

    private static int factorialHelper(int a, int b) {
        return b == 1 ? a : factorialHelper(a * b, b - 1);
    }

    private static int factorialStreams(int i) {
        return IntStream.rangeClosed(1, i).reduce(1, (a, b) -> a * b);
    }

    private static int factorialRecursive(int i) {
        if (i == 1) {
            return 1;
        }

        return i * factorialRecursive(i - 1);
    }

    private static int factorialIterative(int i) {
        int sum = 1;
        for (int j = 1; j <= i; j++) {
            sum *= j;
        }
        return sum;
    }
}
