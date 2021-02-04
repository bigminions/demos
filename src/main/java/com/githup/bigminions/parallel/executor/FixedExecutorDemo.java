package com.githup.bigminions.parallel.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class FixedExecutorDemo {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService fixedExe = Executors.newFixedThreadPool(100);

        fixedExe.execute(() -> {
            System.out.println("First task");
        });

        Thread.sleep(100);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) fixedExe;

        System.out.println("1 当前工作线程数有：" + executor.getPoolSize());
        boolean flag = executor.prestartCoreThread();
        System.out.println("2 当前工作线程数有：" + executor.getPoolSize() + ", flag = " + flag);
        flag = executor.prestartCoreThread();
        System.out.println("2 当前工作线程数有：" + executor.getPoolSize() + ", flag = " + flag);
        int cnt = executor.prestartAllCoreThreads();
        System.out.println("3 当前工作线程数有：" + executor.getPoolSize() + ", preCnt = " + cnt);

        executor.shutdown();
    }
}
