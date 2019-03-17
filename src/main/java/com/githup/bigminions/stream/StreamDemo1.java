package com.githup.bigminions.stream;

import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
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

    @Test
    public void testReduceCompute() {
        List<Integer> numbers = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        int sum = numbers.stream().reduce((a, b) -> a + b).get();
        System.out.println(numbers + " : the sum is " + sum);

        int min = numbers.stream().reduce((a, b) -> a > b ? b : a).get();
        System.out.println(numbers + " : the min is " + min);

        int max = numbers.stream().reduce((a, b) -> a > b ? a : b).get();
        System.out.println(numbers + " : the max is " + max);

        List<Long> bigNumbers = new ArrayList<>();
        for (long i = 0; i < 10000000; i++) {
            bigNumbers.add(i);
        }
        Stopwatch stopwatch = Stopwatch.createStarted();

        /**
         * 流式计算时，使用并发流时第三个参数才有效 第三个参数时combiner，用于最后组合并发计算完的数据
         */
        long bigSum = bigNumbers.parallelStream().reduce(0L, (tempsum, e) -> tempsum + e, (tempsum1, tempsum2) -> {
            System.out.println(tempsum1 + " + " + tempsum2);
            return tempsum1 + tempsum2;
        });
        System.out.println("1 - 10000000 sum is " + bigSum + ", parallel used time is " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");

        stopwatch.reset();
        stopwatch.start();

        /**
         * 单流时第三个参数不执行
         */
        bigSum = bigNumbers.stream().reduce(0L, (tempsum, e) -> tempsum + e, (tempsum1, tempsum2) -> {
            System.out.println(tempsum1 + " + " + tempsum2);
            return tempsum1 + tempsum2;
        });
        System.out.println("1 - 10000000 sum is " + bigSum + ", serial used time is " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + "ms");
    }
}
