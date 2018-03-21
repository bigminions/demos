package com.githup.bigminions.parallel.lock.reentrant;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by daren on 2018/3/19.
 */
public class ReentrantLockDemo1 {

    static final ReentrantLock LOCK_1 = new ReentrantLock();
    static final ReentrantLock LOCK_2 = new ReentrantLock();

    static Thread thread1 = new Thread(() -> {
        LOCK_1.lock();
        try {
            Thread.sleep(1000);
            LOCK_2.lockInterruptibly();
            System.out.println("THREAD_DEMO_ONE over task");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (LOCK_1.isHeldByCurrentThread()) LOCK_1.unlock();
            if (LOCK_2.isHeldByCurrentThread()) LOCK_2.unlock();
        }
    }, "THREAD_DEMO_ONE");

    static Thread thread2 = new Thread(() -> {
        LOCK_2.lock();
        try {
            Thread.sleep(1000);
            LOCK_1.lockInterruptibly();
            System.out.println("THREAD_DEMO_TWO over task");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (LOCK_1.isHeldByCurrentThread()) LOCK_1.unlock();
            if (LOCK_2.isHeldByCurrentThread()) LOCK_2.unlock();
        }
    }, "THREAD_DEMO_TWO");

    public static void main(String[] args) {
        thread1.start();
        thread2.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 线程死锁，中断线程1，只有线程2能够完成任务
        thread1.interrupt();
    }
}
