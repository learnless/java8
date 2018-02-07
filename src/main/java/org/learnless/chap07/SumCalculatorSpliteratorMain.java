package org.learnless.chap07;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.summingLong;

/**
 * 使用可分迭代器求和 TODO
 * Created by learnless on 18.1.31.
 */
public class SumCalculatorSpliteratorMain {
    public static void main(String[] args) {
        long sum = sumStream(100);
        System.out.println(sum);
    }

    public static long sumStream(int n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        SumCalcatorSpliterator spliterator = new SumCalcatorSpliterator(numbers);
        Stream<Long> stream = StreamSupport.stream(spliterator, true);
        return sum(stream);
    }

    private static long sum(Stream<Long> stream) {
        return stream.collect(summingLong(Long::valueOf));
    }
}

class SumCalcatorSpliterator implements Spliterator<Long> {
    private final long[] numbers;
    private int currentIndex = 0;

    public SumCalcatorSpliterator(long[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Long> action) {
        action.accept(numbers[currentIndex++]);
        return currentIndex <= numbers.length;
    }

    @Override
    public Spliterator<Long> trySplit() {
        int currentSize = numbers.length - currentIndex;
        if (currentSize < 10) {
            return null;
        }

        for (int pos = currentSize/2+currentIndex; pos < numbers.length; pos++) {
            //数组的复制
            long[] result = new long[pos];
            int t = currentIndex;
            for (int i = 0; i < pos; i++) {
                result[i] = numbers[t];
                t++;
            }
            SumCalcatorSpliterator sumCalcatorSpliterator = new SumCalcatorSpliterator(result);
            currentIndex = pos;
            return sumCalcatorSpliterator;
        }

        return null;
    }

    @Override
    public long estimateSize() {
        return numbers.length - currentIndex;
    }

    @Override
    public int characteristics() {
        return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
    }
}
