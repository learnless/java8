package org.learnless.chap11;

/**
 * 货币转换服务
 * Created by learnless on 18.2.5.
 */
public class ExchangeService {
    public enum Money {
        USD(1.0), EUR(1.35387), GBP(1.69715), CAD(.92106), MXN(.07683);
        private final double rate;

        Money(double rate) {
            this.rate = rate;
        }
    }

    /**
     * 货币率转换服务
     * @param source
     * @param destination
     * @return
     */
    public static double getRate(Money source, Money destination) {
        return getRateDelay(source, destination);
    }

    /**
     * 对其进行延迟处理
     * @param source
     * @param destination
     * @return
     */
    private static double getRateDelay(Money source, Money destination) {
        Utils.delay();
        return destination.rate / source.rate;
    }
}
