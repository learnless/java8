package org.learnless.chap14;

import java.util.function.DoubleUnaryOperator;

/**
 * 科里化:具备两参数(x,y)的函数f转换为一个参数的函数g
 * f(x,y) -> yg(x)
 * Created by learnless on 18.2.6.
 */
public class Currying {
    public static void main(String[] args) {
        DoubleUnaryOperator convertCtoF = curriedConverter(9.0/5, 32);
        DoubleUnaryOperator convertUSDtoGBP = curriedConverter(0.6, 0);
        DoubleUnaryOperator convertKmtoMi = curriedConverter(0.6214, 0);

        System.out.println(convertCtoF.applyAsDouble(24));
        System.out.println(convertUSDtoGBP.applyAsDouble(100));
        System.out.println(convertKmtoMi.applyAsDouble(20));

        DoubleUnaryOperator convertFtoC = expandedCurriedConverter(-32, 5.0 / 9, 0);
        System.out.println(convertFtoC.applyAsDouble(98.6));

    }

    /**
     * 格式要求
     * @param x
     * @param y
     * @param z
     * @return
     */
    static double converter(double x, double y, double z) {
        return x * y + z;
    }

    /**
     * 转换使用两个函数，符合科里化
     * @param y
     * @param z
     * @return
     */
    static DoubleUnaryOperator curriedConverter(double y, double z) {
        return (double x) -> x * y + z;
    }

    static DoubleUnaryOperator expandedCurriedConverter(double w, double y, double z) {
        return (double x) -> (x + w) * y + z;
    }

}
