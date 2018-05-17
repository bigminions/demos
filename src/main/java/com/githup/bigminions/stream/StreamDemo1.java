package com.githup.bigminions.stream;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by daren on 2018/4/28.
 */
public class StreamDemo1 {

    @Test
    public void testSkipAndLimit() {
        List<Integer> numbers = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> page = numbers.stream().skip(2).limit(2).collect(Collectors.toList());
        System.out.println(page);

        List<Integer> page2 = numbers.stream().skip(20).limit(2).collect(Collectors.toList());
        System.out.println(page2);

    }

    @Test
    public void testReduceStr() {
        List<Integer> numbers = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        String numbersStr = numbers.stream().map(String::valueOf).reduce("", (s, s2) -> String.join(",", s, s2));
        System.out.println(numbersStr);
    }
}
