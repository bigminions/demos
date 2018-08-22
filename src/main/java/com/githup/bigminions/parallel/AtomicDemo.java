package com.githup.bigminions.parallel;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * Created by daren on 2018/7/23.
 */
public class AtomicDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(100);
        int value = atomicInteger.updateAndGet(operand -> operand + 1);
        System.out.println(value);
    }
}
