package com.githup.bigminions.parallel.lock.reentrant;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by daren on 2018/3/19.
 */
public class ReentrantLockDemo5 {

    static final ArrayBlockingQueue<String> STRING_ARRAY_BLOCKING_QUEUE = new ArrayBlockingQueue<String>(1);

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            try {
                STRING_ARRAY_BLOCKING_QUEUE.put("hello " + System.currentTimeMillis());
                System.out.println(Thread.currentThread().getName() + " put over.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread thread1 = new Thread(runnable, "THREAD_DEMO_1");
        Thread thread2 = new Thread(runnable, "THREAD_DEMO_2");

        thread1.start();
        thread2.start();

        System.out.println(STRING_ARRAY_BLOCKING_QUEUE.take()); // 假如此处不取出，队列会一直阻塞而无法放入新的元素
    }
}
