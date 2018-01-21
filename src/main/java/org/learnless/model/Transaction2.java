package org.learnless.model;

import java.util.Arrays;
import java.util.List;

/**
 * Created by learnless on 18.1.20.
 */
public class Transaction2 {
    private Currency currency;  //货币类型
    private double value;

    public Transaction2() {
    }

    public Transaction2(Currency currency, double value) {
        this.currency = currency;
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return currency + " " + value;
    }

    public enum Currency {
        EUR, USD, JPY, GBP, CHF
    }

    public static List<Transaction2> transactions() {
        List<Transaction2> transactions = Arrays.asList(
                new Transaction2(Currency.EUR, 1500.0),
                new Transaction2(Currency.USD, 2300.0),
                new Transaction2(Currency.GBP, 9900.0),
                new Transaction2(Currency.EUR, 1100.0),
                new Transaction2(Currency.JPY, 7800.0),
                new Transaction2(Currency.CHF, 6700.0),
                new Transaction2(Currency.EUR, 5600.0),
                new Transaction2(Currency.USD, 4500.0),
                new Transaction2(Currency.CHF, 3400.0),
                new Transaction2(Currency.GBP, 3200.0),
                new Transaction2(Currency.USD, 4600.0),
                new Transaction2(Currency.JPY, 5700.0),
                new Transaction2(Currency.EUR, 6800.0)
        );

        return transactions;
    }
}
