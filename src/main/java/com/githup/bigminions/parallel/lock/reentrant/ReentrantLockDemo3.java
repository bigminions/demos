package com.githup.bigminions.parallel.lock.reentrant;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by daren on 2018/3/19.
 */
public class ReentrantLockDemo3 {

    static final ReentrantLock LOCK_1 = new ReentrantLock();
    static final ReentrantLock LOCK_2 = new ReentrantLock();

    static Thread thread1 = new Thread(() -> {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (LOCK_1.tryLock()) {
                try {
                    Thread.sleep(500);
                    if (LOCK_2.tryLock()) {
                        System.out.println("THREAD_DEMO_ONE over task");
                        LOCK_2.unlock();
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    LOCK_1.unlock();
                }
            }
        }
    }, "THREAD_DEMO_ONE");

    static Thread thread2 = new Thread(() -> {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (LOCK_2.tryLock()) {
                try {
                    Thread.sleep(500);
                    if (LOCK_1.tryLock()) {
                        System.out.println("THREAD_DEMO_ONE over task");
                        LOCK_1.unlock();
                        return;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    LOCK_2.unlock();
                }
            }
        }
    }, "THREAD_DEMO_TWO");

    public static void main(String[] args) {
        thread1.start();
        thread2.start();
        // 尝试
    }
}
