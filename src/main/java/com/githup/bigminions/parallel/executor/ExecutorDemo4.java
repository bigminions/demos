package com.githup.bigminions.parallel.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Executors.newSingleThreadExecutor();
 * 只有一个线程的线程池，任务最后都已队列进入处理，先进先出，因此并不会产生竞争,计数器计数正常
 */
public class ExecutorDemo4 {

    private static final AtomicInteger counter = new AtomicInteger();

    private static final ExecutorService executor = Executors.newSingleThreadExecutor(r -> {
        String name = "DDD-" + counter.incrementAndGet();
        System.out.println("开始创建线程： " + name);
        return new Thread(new ThreadGroup("TESTING-THREAD"), r, name);
    });


    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            for (int i = 0; i < 3; i++) {

                executor.execute(() -> {
                    System.out.println("Be run by " + Thread.currentThread().getName());
                    if (System.currentTimeMillis() % 2 == 0) {
                        throw new RuntimeException("Bad time");
                    }
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
