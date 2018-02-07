package org.learnless.chap11;

import static org.learnless.chap11.Utils.delay;
import static org.learnless.chap11.Utils.format;

/**
 * 折扣类
 * Created by learnless on 18.2.5.
 */
public class Discount {
    /**
     * 枚举类
     */
    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLANTINUM(15), DIAMOND(20);
        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    /**
     * 折扣服务应用，返回i结果
     * @param quote
     * @return
     */
    public static String applyDiscount(Quote quote) {
        return String.format("%s price is %.2f", quote.getName(), getDiscountPrice(quote.getPrice(), quote.getCode()));
    }

    /**
     * 延迟一秒，保证同步
     * @param price
     * @param code
     * @return
     */
    private static double getDiscountPrice(double price, Code code) {
        delay();
        return format(price * ((100.0 - code.percentage) / 100));
    }

}
