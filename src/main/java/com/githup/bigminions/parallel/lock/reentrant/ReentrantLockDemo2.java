package com.githup.bigminions.parallel.lock.reentrant;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by daren on 2018/3/19.
 */
public class ReentrantLockDemo2 {

    static final ReentrantLock LOCK_1 = new ReentrantLock();
    static final ReentrantLock LOCK_2 = new ReentrantLock();

    static Thread thread1 = new Thread(() -> {
        try {
            LOCK_1.lock();
            Thread.sleep(2000);
            if (LOCK_2.tryLock(1000, TimeUnit.MILLISECONDS)) {
                System.out.println("THREAD_DEMO_ONE over task");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (LOCK_1.isHeldByCurrentThread()) LOCK_1.unlock();
            if (LOCK_2.isHeldByCurrentThread()) LOCK_2.unlock();
        }
    }, "THREAD_DEMO_ONE");

    static Thread thread2 = new Thread(() -> {
        try {
            LOCK_2.lock();
            Thread.sleep(2000);
            if (LOCK_1.tryLock(1000, TimeUnit.MILLISECONDS)) {
                System.out.println("THREAD_DEMO_TWO over task");
            }
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
        // 由于有等待时间，并不会造成线程死锁，超时线程会退出任务执行，因此会丢失一个任务
    }
}
