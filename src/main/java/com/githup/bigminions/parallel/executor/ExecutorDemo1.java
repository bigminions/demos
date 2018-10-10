package com.githup.bigminions.parallel.executor;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Executors.newSingleThreadExecutor();
 * 只有一个线程的线程池，任务最后都已队列进入处理，先进先出，因此并不会产生竞争,计数器计数正常
 */
public class ExecutorDemo1 {

    private static final Executor executor = Executors.newSingleThreadExecutor();

    private static Integer counter = 0;

    public static void main(String[] args) {
        Runnable runnable = () -> {
            for (int i = 0; i < 30; i++) {
                String threadName = Thread.currentThread().getName();
                executor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " : Run a task From " + threadName + "_" + counter++);
                });
            }
        };

        Thread A = new Thread(runnable, "MAIN_TEST_A");
        Thread B = new Thread(runnable, "MAIN_TEST_B");
        Thread C = new Thread(runnable, "MAIN_TEST_C");

        A.start();
        B.start();
        C.start();
    }

}
