package org.learnless.chap05;

import org.learnless.model.Trader;
import org.learnless.model.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * 练习 stream
 * Created by learnless on 18.1.19.
 */
public class PuttingIntoPractice {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        //Query1 找出2011发生的所有交易，按交易额排序（低->高）
        List<Transaction> result1 = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());
        System.out.println("==========1===========");
        result1.forEach(System.out::println);

        //Query2 交易员都在哪些不同的城市工作过
        List<String> result2 = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(toList());
        
        //或者
        Set<String> result22 = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .collect(toSet());
        System.out.println("==========2===========");
        result2.forEach(System.out::println);

        //Query3 查找所有剑桥的交易员，按姓名顺序排序
        List<Trader> result3 = transactions.stream()
                .map(Transaction::getTrader)
                .distinct()
                .filter(trader -> "Cambridge".equals(trader.getCity()))
                .sorted(comparing(Trader::getName))
                .collect(toList());
        System.out.println("==========3===========");
        result3.forEach(System.out::println);


        //Query4 查找所有交易员的名字字符串，按字母排序
        String result4 = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);
        System.out.println("==========4===========");
        System.out.println(result4);

        //Query5 有交易员在米兰工作过
        boolean result5 = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println("==========5===========");
        System.out.println(result5);

        //Query6 打印生活在剑桥的交易员的所有交易额
        System.out.println("==========6===========");
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        //Query7 所有的交易额最高是多少
        Optional<Integer> result7 = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        System.out.println("==========7===========");
        System.out.println(result7.get());

        //Query8 找出交易额最小的交易
        Optional<Transaction> result8 = transactions.stream()
                .reduce((t1, t2) -> t1.getValue() > t2.getValue() ? t2 : t1);
        System.out.println("==========8===========");
        System.out.println(result8.get());

    }
}
