package com.githup.bigminions.parallel.lock;

import java.util.concurrent.CountDownLatch;

/**
 * Created by daren on 2018/9/30.
 * 倒计时器类，对线程计数，当任务量完成时执行下一步操作
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {

        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);

        Runnable task = () -> {
            try {
                Thread.sleep(1000);
                System.out.println("Run some task -- " + Thread.currentThread().getName());
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < count; i++) {
            new Thread(task).start();
        }

        System.out.println("waiting for tasks end");
        countDownLatch.await();
        System.out.println("All tasks end， to run other task.");
    }
}
