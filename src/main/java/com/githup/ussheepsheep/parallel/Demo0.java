package com.githup.ussheepsheep.parallel;

import java.util.Random;

/**
 * Created by daren on 2017/5/23.
 * 0. 如何写一个java多线程example
 */
public class Demo0 {

    private static final Random random = new Random();
    private static final int bound = 10;

    public static void main(String[] args) {
        Runnable runnable1 = () -> {
            String threadName = Thread.currentThread().getName();
            for(int i = 0; i < 10; i++) {
                int num = random.nextInt(bound);
                System.out.println(threadName + " 生成一个负数：" + (-num));
                try {
                    Thread.sleep(num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable runnable2 = () -> {
            String threadName = Thread.currentThread().getName();
            for(int i = 0; i < 10; i++) {
                int num = random.nextInt(bound);
                System.out.println(threadName + " 生成一个正数：" + (num));
                try {
                    Thread.sleep(num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread thread1 = new Thread(runnable1, "negative");
        Thread thread2 = new Thread(runnable2, "positive");

        thread1.start();
        thread2.start();
    }

}
