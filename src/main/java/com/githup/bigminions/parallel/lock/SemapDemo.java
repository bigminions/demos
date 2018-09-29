package com.githup.bigminions.parallel.lock;

import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by daren on 2018/9/29.
 */
public class SemapDemo {

    private static final Semaphore semaphore = new Semaphore(4);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        Stopwatch stopwatch = Stopwatch.createStarted();
        List<Future> futures = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            Future future = executorService.submit(() -> {
                try {
                    semaphore.acquire();
                    Thread.sleep(1000);
                    System.out.println("Do something -- " + Thread.currentThread().getName());
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            futures.add(future);
        }

        while (!futures.isEmpty()) {
            futures.removeIf(Future::isDone);
            Thread.sleep(100);
        }

        // 可用信号量设置为4，20个任务则需分5次完成，需要20s
        System.out.println("used time = " + stopwatch.elapsed(TimeUnit.SECONDS) + " s");;
        executorService.shutdown();
    }
}
