package org.learnless.chap08.designPattern;

/**
 * 策略模式
 * Created by learnless on 18.1.30.
 */
public class Strategy {
    public static void main(String[] args) {
        //原始方式
        Validate charValidate = new CharValidate();
        Validate numberValidate = new NumberValidate();
        System.out.println(charValidate.execute("aejfjiAEFEF"));
        System.out.println(numberValidate.execute("213424238"));

        //使用lambda,不需要声明实现类即可实现
        Validate v1 = s -> s.matches("[a-zA-Z]+");
        System.out.println(v1.execute("aejfjiAEFEF"));
        Validate v2 = s -> s.matches("\\d+");
        System.out.println(v2.execute("343434343"));

    }


    @FunctionalInterface
    public interface Validate {
        public boolean execute(String s);
    }

    public static class CharValidate implements Validate {

        @Override
        public boolean execute(String s) {
            return s.matches("[a-zA-Z]+");
        }
    }

    public static class NumberValidate implements Validate {

        @Override
        public boolean execute(String s) {
            return s.matches("\\d+");
        }
    }


}

