package org.learnless.chap13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 采用函数编程,对一个集合，生成其所有的子集合
 * eg:{1,3,9}子集合有8个
 * {{1,3,9}, {1}, {3}, {9}, {1,3}, {1,9}, {3,9}, {}}
 * Created by learnless on 18.2.6.
 */
public class SubsetsMain {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 3, 9);
        List<List<Integer>> result = subsets(list);
        System.out.println(result);
    }

    private static List<List<Integer>> subsets(List<Integer> list) {
        //为空时，返回{}
        if (list.isEmpty()) {
            List<List<Integer>> ans = new ArrayList<>();
            ans.add(Collections.emptyList());   //list是有元素,为{}
            return ans;
        }

        Integer first = list.get(0);
        List<Integer> rest = list.subList(1, list.size());

        //递归调用
        List<List<Integer>> list1 = subsets(rest);
        //将剩余的元素与首元素整合
        List<List<Integer>> list2 = insertAll(first, list1);

        //整合集合
        return concat(list1, list2);
    }

    private static List<List<Integer>> concat(List<List<Integer>> list1, List<List<Integer>> list2) {
        List<List<Integer>> result = new ArrayList<>(list1);
        result.addAll(list2);
        return result;
    }

    private static List<List<Integer>> insertAll(Integer first, List<List<Integer>> lists) {
        List<List<Integer>> result = new ArrayList<>();
        for (List<Integer> list : lists) {
            List<Integer> subList = new ArrayList<>();
            subList.add(first);
            subList.addAll(list);

            result.add(subList);
        }
        return result;
    }

}
