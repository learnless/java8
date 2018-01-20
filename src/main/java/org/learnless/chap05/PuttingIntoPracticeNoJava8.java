package org.learnless.chap05;

import org.learnless.model.Trader;
import org.learnless.model.Transaction;

import java.util.*;

/**
 * 练习 非Java8
 * Created by learnless on 18.1.19.
 */
public class PuttingIntoPracticeNoJava8 {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(brian, 2011, 200),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        //Query1 找出2011发生的所有交易，按交易额排序（低->高）
        List<Transaction> result1 = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getYear() == 2011)
                result1.add(t);
        }
        result1.sort(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o1.getValue() - o2.getValue();
            }
        });

        System.out.println("==========1===========");
        result1.forEach(System.out::println);

        //Query2 交易员都在哪些不同的城市工作过
        Set<String> result2 = new HashSet<>();
        for (Transaction t : transactions) {
            result2.add(t.getTrader().getCity());
        }
        System.out.println("==========2===========");
        result2.forEach(System.out::println);

        //Query3 查找所有剑桥的交易员，按姓名顺序排序
        List<Trader> result3 = new ArrayList<>();
        for (Transaction t : transactions) {
            if (!result3.contains(t.getTrader()))
                result3.add(t.getTrader());
        }
        result3.sort(new Comparator<Trader>() {
            @Override
            public int compare(Trader o1, Trader o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        System.out.println("==========3===========");
        result3.forEach(System.out::println);


        //Query4 查找所有交易员的名字字符串，按字母排序
        StringBuffer result4 = new StringBuffer();
        List<String> list4 = new ArrayList<>();
        for (Transaction t : transactions) {
            if (!list4.contains(t.getTrader().getName()))
                list4.add(t.getTrader().getName());
        }
        list4.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        for (String s : list4) {
            result4.append(s);
        }

        System.out.println("==========4===========");
        System.out.println(result4);

        //Query5 有交易员在米兰工作过
        boolean result5 = false;
        for (Transaction t : transactions) {
            if ("Milan".equals(t.getTrader().getCity())) {
                result5 = true;
                break;
            }
        }

        System.out.println("==========5===========");
        System.out.println(result5);

        //Query6 打印生活在剑桥的交易员的所有交易额
        System.out.println("==========6===========");
        for (Transaction t : transactions) {
            if ("Cambridge".equals(t.getTrader().getCity()))
                System.out.println(t.getValue());
        }

        //Query7 所有的交易额最高是多少
        int result7 = 0;
        for (Transaction t : transactions) {
            if (t.getValue() > result7)
                result7 = t.getValue();
        }

        System.out.println("==========7===========");
        System.out.println(result7);

        //Query8 找出交易额最小的交易
//        Optional<Transaction> result8 = transactions.stream()
//                .reduce((t1, t2) -> t1.getValue() > t2.getValue() ? t2 : t1);
        int index = 0;
        for (int i = 1; i < transactions.size(); i++) {
            if (transactions.get(i).getValue() < transactions.get(index).getValue())
                index = i;
        }

        System.out.println("==========8===========");
        System.out.println(transactions.get(index));

    }
}
