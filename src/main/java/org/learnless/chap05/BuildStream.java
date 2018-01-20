package org.learnless.chap05;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 创建流
 * Created by learnless on 18.1.20.
 */
public class BuildStream {
    public static void main(String[] args) {
        BuildStream buildStream = new BuildStream();
        //由值创建流
        Stream.of("a", "bb", "ccc", "D")   //创建流
                .map(String::toLowerCase)
                .forEach(System.out::println);

        //由数组创建流
        int[] arrays = {1, 2, 3, 4};
        int sum = Arrays.stream(arrays).sum();

        //文件流
        String file = "E:\\IntelliJIDEA\\java8\\src\\main\\resources\\org\\learnless\\chap05\\data.txt";
        try (Stream<String> lines = Files.lines(Paths.get(file), Charset.defaultCharset())) {
            List<String> collect = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .collect(toList());
            collect.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //创建无限流
        //1.迭代
        Stream.iterate(0, n -> n+2)
                .limit(10)
                .forEach(System.out::println);

        //斐波纳契元组序列 (0,1),(1,1),(1,2),(2,3),(3,5)
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(10)
                .forEach(t -> System.out.println("(" + t[0] + "," + t[1] + ")"));

        //2.生成
        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::println);

        //生成一个全为1的无限流
        IntStream intStream = IntStream.generate(() -> 1);


        System.out.println(sum);
    }
}
