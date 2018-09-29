package com.githup.bigminions.parallel;

/**
 * Created by daren on 2018/9/29.
 */
public class SynchronizedDemo {

    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(SynchronizedDemo::lockAndDoSome, "Thread-ONE");
        Thread thread2 = new Thread(SynchronizedDemo::lockAndDoSome1, "Thread-TWO");

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    private static void lockAndDoSome () {
        synchronized (lock) {
            System.out.println("lock by -- " + Thread.currentThread().getName());
            synchronized (lock) {
                System.out.println("lock by -- " + Thread.currentThread().getName());
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("over -- " + Thread.currentThread().getName());
        }
    }

    private static void lockAndDoSome1 () {
        for (int i = 0; i < 30; i++) {
            synchronized (lock) {
                System.out.println("Lock for do something else -- " + Thread.currentThread().getName());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
