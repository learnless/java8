package org.learnless.chap11;

import java.util.Random;

/**
 * Created by learnless on 18.2.5.
 */
public class Shop {
    private String name;
    private Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    /**
     * @param product
     * @return name:price:code
     */
    public String getPrice(String product) {
        double price = calculatePrice(product);
        //随机获取折扣率
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    public String getPrice(double price) {
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", name, price, code);
    }

    public double calculatePrice(String product) {
        //让其计算延迟一秒
        Utils.delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1); //让其价格随机获取
    }

    public String getName() {
        return name;
    }
}
