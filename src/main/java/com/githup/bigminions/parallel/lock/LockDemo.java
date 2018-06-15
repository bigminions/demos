package com.githup.bigminions.parallel.lock;

import java.time.LocalDateTime;

/**
 * Created by daren on 2018/6/15.
 */
public class LockDemo {

    private static final Object LOCK = new Object();

    public static void main(String[] args) {
        System.out.println("start " + LocalDateTime.now());
        synchronized (LOCK) {
            try {
                LOCK.wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("end " + LocalDateTime.now());
    }
}
