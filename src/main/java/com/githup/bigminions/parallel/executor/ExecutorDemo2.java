package com.githup.bigminions.parallel.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Executors.newScheduledThreadPool(corePoolSize);
 * 有corePoolSize个线程的线程池，任务并发执行，产生竞争，计数器计数可能不正常
 */
public class ExecutorDemo2 {

    private static final Executor executor = Executors.newFixedThreadPool(2);

    private static Integer counter = Integer.valueOf(0);

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
