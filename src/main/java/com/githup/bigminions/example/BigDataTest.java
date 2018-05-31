package com.githup.bigminions.example;

import com.google.common.base.Stopwatch;
import org.junit.Test;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by daren on 2018/5/17.
 */
public class BigDataTest {

    @Test
    public void testMap() {
        ConcurrentHashMap map = new ConcurrentHashMap();
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 1000000; i++) {
            map.put(i, "value=" + i);
        }
        System.out.println("insert 1000000 used " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
        stopwatch.reset();
        stopwatch.start();
        System.out.println(map.get(100));;
        System.out.println("get from 1000000 used " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    public void testSet() {
        HashSet set = new HashSet();
        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 1000000; i++) {
            set.add(i);
        }
        System.out.println("insert 1000000 used " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
        stopwatch.reset();
        stopwatch.start();
        // 随机
        set.remove(1000);
        set.remove(12345);
        set.remove(54321);
        set.remove(65432);
        set.remove(77777);
        System.out.println("get random 5 from 1000000 used " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }
}
