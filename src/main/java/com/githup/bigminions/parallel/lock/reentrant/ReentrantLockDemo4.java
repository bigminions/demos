package com.githup.bigminions.parallel.lock.reentrant;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by daren on 2018/3/20.
 */
public class ReentrantLockDemo4 {

    static final ReentrantLock LOCK = new ReentrantLock();

    static final Condition CONDITION = LOCK.newCondition();

    static final Thread thread = new Thread(() -> {
        try {
            LOCK.lock();
            CONDITION.await();
            System.out.println("Notify and run task continue ...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    });

    public static void main(String[] args) throws InterruptedException {
        thread.start();
        Thread.sleep(2000);
        LOCK.lock();
        CONDITION.signal();
        LOCK.unlock();
    }
}
