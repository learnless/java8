package org.learnless.chap03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by learnless on 18.1.7.
 */
public class ExecuteAround {

    public static void main(String[] args) throws IOException {
        ExecuteAround executeAround = new ExecuteAround();

        String result = executeAround.processFile();

        //使用函数接口来传递行为 BufferedReader -> String
        result = executeAround.processFile2(br -> br.readLine());
        result = executeAround.processFile2(br -> br.readLine() + br.readLine());

        BufferedReadProcess p = br -> br.readLine() + br.readLine() + br.readLine();
        result = p.proccess(new BufferedReader(new FileReader(executeAround.getClass().getResource("data.txt").getFile())));
        System.out.println(result);
    }

    public String processFile() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(this.getClass().getResource("data.txt").getFile()))) {
            return br.readLine();
        }
    }

    public String processFile2(BufferedReadProcess p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(this.getClass().getResource("data.txt").getFile()))) {
            return p.proccess(br);
        }
    }


}

@FunctionalInterface
interface BufferedReadProcess {
    String proccess(BufferedReader br) throws IOException;
}
