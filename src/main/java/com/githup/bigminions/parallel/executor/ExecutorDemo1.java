package com.githup.bigminions.parallel.executor;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executors.newSingleThreadExecutor();
 * 只有一个线程的线程池，任务最后都已队列进入处理，先进先出，因此并不会产生竞争,计数器计数正常
 */
public class ExecutorDemo1 {

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final ExecutorService fixedExecutor = Executors.newFixedThreadPool(4);

    private static Integer counter = 0;
    private static Integer counter1 = 0;

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i < 30000; i++) {
                String threadName = Thread.currentThread().getName();

                // 线程池内只有一个线程 不会出现竞争
//                executor.execute(() -> {
//                    System.out.println(Thread.currentThread().getName() + " : Run a task From " + threadName + "_" + counter++);
//                });

                // 线程池内最多有4个线程，会出现竞争而导致counter1数量不对
                fixedExecutor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " : Run a task From " + threadName + "_" + counter1++);
                });
            }
        };

        Thread A = new Thread(runnable, "MAIN_TEST_A");
        Thread B = new Thread(runnable, "MAIN_TEST_B");
        Thread C = new Thread(runnable, "MAIN_TEST_C");

        A.start();
        B.start();
        C.start();

        A.join();
        B.join();
        C.join();

//        executor.shutdown();
    }

}
