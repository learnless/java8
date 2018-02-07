package org.learnless.chap11;

/**
 * 折扣服务
 * Created by learnless on 18.2.5.
 */
public class Quote {
    private final String name;
    private final double price;
    private final Discount.Code code;

    public Quote(String name, double price, Discount.Code code) {
        this.name = name;
        this.price = price;
        this.code = code;
    }

    /**
     * 解析价格字符串 name:price:code
     * @param msg
     */
    public static Quote parse(String msg) {
        String[] strings = msg.split(":");
        String name = strings[0];
        double price = Double.parseDouble(strings[1]);
        Discount.Code code = Discount.Code.valueOf(strings[2]);
        return new Quote(name, price, code);
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Discount.Code getCode() {
        return code;
    }
}
