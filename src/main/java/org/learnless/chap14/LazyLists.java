package org.learnless.chap14;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 创建自己的延迟列表
 * 为实现这样一个延迟列表，就得避免tail立即出现在内存在，提供一个Supplier<T>方法
 * Created by learnless on 18.2.7.
 */
public class LazyLists {

    public static void main(String[] args) {
//        primes(20).forEach(System.out::println);
        //由于每次判断素数时，可从已有的素数流获取即可
//        primes(IntStream.iterate(2, i -> i + 1).limit(10)); //error

        //延迟列表
//        LazyList<Integer> numbers = from(2);
//        int first = numbers.head();
//        int second = numbers.tail().head();
//        int third = numbers.tail().tail().head();
//        printAll(primes(numbers), 20);


        execute();

        System.out.println();
    }

    static void execute() {
        long start = System.nanoTime();
        IntStream primes = primes(1000);
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("使用普通流花费的时间为"+duration+"毫秒");

        start = System.nanoTime();
        Stream<Integer> intStream = getIntStream(primes(from(2)), 1000);
        duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("使用延迟列表花费的时间为"+duration+"毫秒");

//        System.out.println(primes.count());
        primes.limit(100).forEach(System.out::println);
        System.out.println("========================");
//        System.out.println(intStream.count());
        intStream.limit(100).forEach(System.out::println);

    }

    /**
     * 生成一个延迟列表列表，递归调用
     * @param n
     * @return
     */
    static LazyList<Integer> from(int n) {
        return new LazyList<Integer>(n, () -> from(n+1));
    }

    /**
     * 延迟列表的应用,生成素数
     * @param numbers
     * @return
     */
    static MyList<Integer> primes(MyList<Integer> numbers) {
        return new LazyList<>(numbers.head(),
                () -> primes(numbers.tail().filter(n -> n % numbers.head() != 0))  //对tail进行过滤出素数
        );
    }

    /**
     * 获取素数流
     * @param numbers
     * @return
     */
    static Stream<Integer> getIntStream(MyList<Integer> numbers, int limit) {
        List<Integer> result = getList(numbers, new ArrayList<Integer>(), limit);
        return result.stream();
    }

    private static List<Integer> getList(MyList<Integer> numbers, List<Integer> list, int limit) {
        if (numbers == null || limit == 0)  return null;

        list.add(numbers.head());
        getList(numbers.tail(), list, limit - 1);
        return list;
    }

    /**
     * 打印
     * @param numbers
     */
    static void printAll(MyList<Integer> numbers, int limit) {
        if (limit == 0 || numbers == null)    return;
        System.out.println(numbers.head());
        printAll(numbers.tail(), limit-1);
    }


    /**
     * 错误操作，由于流消费完，不能递归调用，所以实现一个延迟列表
     * error
     * @param numbers
     * @return
     */
    static IntStream primes(IntStream numbers) {
        int head = head(numbers);
        return IntStream.concat(IntStream.of(head), primes(tail(numbers).filter(n -> n % head != 0)));
    }

    /**
     * 获取流的首元素
     * @param numbers
     * @return
     */
    private static int head(IntStream numbers) {
        return numbers.findFirst().getAsInt();
    }

    /**
     * 排除首元素的其余流
     * @param numbers
     * @return
     */
    private static IntStream tail(IntStream numbers) {
        return numbers.skip(1);
    }


    static IntStream primes(int n) {
        return IntStream.iterate(2, i -> i + 1)
                .filter(LazyLists::isPrime)
                .limit(n);
    }

    static boolean isPrime(int n) {
        int sqrt = (int) Math.sqrt(n);
        return IntStream.rangeClosed(2, sqrt)
                .noneMatch(i -> n % i == 0);
    }

}

//实现自己延迟列表
interface MyList<T> {
    T head();

    MyList<T> tail();

    default boolean isEmpty() {
        return true;
    }

    MyList<T> filter(Predicate<T> p);
}

class MyLinkedList<T> implements MyList<T> {
    private final T head;
    private final MyList<T> tail;

    public MyLinkedList(T head, MyList<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public T head() {
        return head;
    }

    @Override
    public MyList<T> tail() {
        return tail;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public MyList<T> filter(Predicate<T> p) {
        return isEmpty() ?
                this :
                p.test(head()) ?
                    new MyLinkedList<T>(head(), tail().filter(p)) :
                    tail().filter(p);
    }
}

class Empty<T> implements MyList<T> {

    @Override
    public T head() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MyList<T> tail() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MyList<T> filter(Predicate<T> p) {
        return this;
    }

}

class LazyList<T> implements MyList<T> {
    final T head;
    final Supplier<MyList<T>> tail; //关键这一步

    public LazyList(T head, Supplier<MyList<T>> tail) {
        this.head = head;
        this.tail = tail;
    }

    /**
     * 实现一个延迟筛选器
     * @param p
     * @return
     */
    public MyList<T> filter(Predicate<T> p) {
        return isEmpty() ?
                this :
                p.test(head()) ?
                    new LazyList<T>(head(), () -> tail().filter(p)) : //为true表示为素数
                    tail().filter(p);
    }

    @Override
    public T head() {
        return head;
    }

    @Override
    public MyList<T> tail() {
        return tail.get();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
