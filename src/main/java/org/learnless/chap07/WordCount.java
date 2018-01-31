package org.learnless.chap07;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * 统计单词数
 * Created by learnless on 18.1.30.
 */
public class WordCount {
    public static final String SENTENCE =
            " Nel   mezzo del cammin  di nostra  vita " +
                    "mi  ritrovai in una  selva oscura" +
                    " che la  dritta via era   smarrita ";

    public static void main(String[] args) {
        System.out.println("使用原始迭代，单词记数为:" + countWordsIteratively(SENTENCE));
        //将字符串转换为流
        Stream<Character> stream = IntStream.range(0, SENTENCE.length()).mapToObj(SENTENCE::charAt);
        System.out.println("使用stream api顺序流，单词记数为:" + countWords(stream));
        System.out.println("使用stream api并行流计数出错，单词记数为:" + countWordsParallel(SENTENCE));
        System.out.println("使用stream api并行流计数可分迭代器，单词记数为:" + countWordsIterator(SENTENCE));

    }

    /**
     * 原始迭代
     * @param s
     * @return
     */
    public static int countWordsIteratively(String s) {
        boolean lastSpace = true;
        int count = 0;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                //当前字符为空格时
                lastSpace = true;
            } else {
                //当前字符非空格时，如果记录的最后的空格状态为true，将计数加1
                if (lastSpace)  count++;
                lastSpace = false;
            }
        }

        return count;
    }

    /**
     * 函数式实现,顺序流
     * @param stream
     * @return
     */
    public static int countWords(Stream<Character> stream) {
        return stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine)
                .getCount();
    }

    /**
     * 错误使用并行流
     * @param s
     * @return
     */
    public static int countWordsParallel(String s) {
        Stream<Character> stream = IntStream.range(0, s.length()).mapToObj(s::charAt).parallel();
        return stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine)
                .getCount();
    }

    public static int countWordsIterator(String s) {
        Spliterator spliterator = new WordCounterSpliterator(s);
        Stream<Character> stream = StreamSupport.stream(spliterator, true);//并行处理
        return countWords(stream);
    }


    /**
     * 成员内部类，用来遍历Character流计数类
     */
    static class WordCounter {
        private int count;
        private boolean lastSpace;

        public WordCounter(int count, boolean lastSpace) {
            this.count = count;
            this.lastSpace = lastSpace;
        }

        /**
         * 累加器
         * @param c
         * @return
         */
        public WordCounter accumulate(Character c) {
            /**
             * 当前字符为空时,如果状态为true，返回当前对象，否则表示上个字符非空，应该new一个新的对象
             * 同理，字符不为空，如歌状态为false,表示上个字符也非空，直接返回当前对象，否则newy一个新的对象，并将计数加1
             */
            if (Character.isWhitespace(c)) {
                return lastSpace ? this : new WordCounter(count, true);
            } else {
                return lastSpace ? new WordCounter(count + 1, false) : this;
            }
        }

        /**
         * 合并
         * @param wordCounter
         * @return
         */
        public WordCounter combine(WordCounter wordCounter) {
            return new WordCounter(count + wordCounter.count, wordCounter.lastSpace);
        }

        public int getCount() {
            return count;
        }

    }

    /**
     * 实现可分迭代器，能并行编程
     */
    static class WordCounterSpliterator implements Spliterator<Character> {
        private final String string;
        private int currentChar = 0;    //字符当前的位置

        public WordCounterSpliterator(String string) {
            this.string = string;
        }

        /**
         * 类似于普通的iterator，还有要执行的元素返回true
         * @param action
         * @return
         */
        @Override
        public boolean tryAdvance(Consumer<? super Character> action) {
            //消费字符
            action.accept(string.charAt(currentChar++));
            //当前位置小于字符长度返回true
            return currentChar < string.length();
        }

        /**
         * 把一些元素划分给第二个Spliterator并行处理
         * @return
         */
        @Override
        public Spliterator<Character> trySplit() {
            //当划分的字符串足够小就停止划分，返回null,这里设置为10
            //否则一运到空格时，就将其一半划分出去
            int currentSize = string.length() - currentChar;
            if (currentSize < 10)   return null;

            for (int pos = currentSize/2+currentChar; pos < string.length(); pos++) {
                if (Character.isWhitespace(string.charAt(pos))) {
                    WordCounterSpliterator wordCounterSpliterator = new WordCounterSpliterator(string.substring(currentChar, pos));
                    currentChar = pos;
                    return wordCounterSpliterator;
                }
            }

            return null;
        }

        /**
         * 估计剩余的元素
         * @return
         */
        @Override
        public long estimateSize() {
            return string.length() - currentChar;
        }

        @Override
        public int characteristics() {
            return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
        }
    }

}
