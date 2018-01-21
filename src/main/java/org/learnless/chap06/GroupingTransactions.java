package org.learnless.chap06;

import org.learnless.model.Transaction2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * 收集器分组
 * Created by learnless on 18.1.20.
 */
public class GroupingTransactions {
    public static void main(String[] args) {
        List<Transaction2> transactions = Transaction2.transactions();
        //Java7 按货币类型分组
//        groupImperatively(transactions);
        //Java8 分组
        groupFunctionally(transactions);

    }

    private static void groupFunctionally(List<Transaction2> transactions) {
        Map<Transaction2.Currency, List<Transaction2>> map = transactions.stream()
                .collect(groupingBy(Transaction2::getCurrency));
        map.forEach((k, v) -> System.out.println("k = " + k + " ,v = " + v));
    }

    private static void groupImperatively(List<Transaction2> transactions) {
        Map<Transaction2.Currency, List<Transaction2>> map = new HashMap<>();
        for (Transaction2 t : transactions) {
            Transaction2.Currency currency = t.getCurrency();
            List<Transaction2> list = map.get(currency);
            if (list == null) {
                list = new ArrayList<>();
                map.put(currency, list);
            }
            list.add(t);
        }

        map.forEach((k, v) -> System.out.println("k = " + k + " ,v = " + v));
    }
}
