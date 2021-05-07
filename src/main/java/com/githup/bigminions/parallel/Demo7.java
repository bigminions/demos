package com.githup.bigminions.parallel;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by daren on 2017/5/23.
 * 0. 如何写一个java多线程example
 */
public class Demo7 {

    private static final AtomicInteger incr = new AtomicInteger(0);

    public static class Thread1 extends Thread {
        byte[] lock = new byte[0];
        byte[] nextLock = null;

        @Override
        public void run() {
            try {
                synchronized (this) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while(incr.get() < 100) {
                System.out.println(Thread.currentThread().getName() + " : " + incr.incrementAndGet());
                try {
                    nextLock.notify();
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread1 thread1 = new Thread1();
        Thread1 thread2 = new Thread1();
        Thread1 thread3 = new Thread1();
        thread1.nextLock = thread2.lock;
        thread2.nextLock = thread3.lock;
        thread3.nextLock = thread1.lock;

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.notify();

        Thread.sleep(3000);
    }

}
