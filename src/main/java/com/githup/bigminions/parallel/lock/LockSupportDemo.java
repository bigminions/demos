package com.githup.bigminions.parallel.lock;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by daren on 2018/3/20.
 */
public class LockSupportDemo {

    static Object u = new Object();


    static class ChangeObjectThread extends Thread {

        public ChangeObjectThread (String name) {
            super(name);
        }

        @Override
        public void run() {
            synchronized (u) {
                System.out.println("In " + getName());
                LockSupport.park();
                System.out.println("In " + getName() + " DONE");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new ChangeObjectThread("THREAD_DEMO_1");
        Thread thread2 = new ChangeObjectThread("THREAD_DEMO_2");

        thread1.start();
        Thread.sleep(1000);
        thread2.start();

        LockSupport.unpark(thread1);
        LockSupport.unpark(thread2);

        thread1.join();
        thread2.join();
    }
}
