package com.githup.bigminions.parallel;

public class ThreadLocalDemo2 {

    public static void main(String[] args) throws InterruptedException {
        String lock = "LOCK";

        ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "MAIN");

        Thread threadA = new Thread(() -> {
            threadLocal.set("I am threadA");

            try {
                synchronized (lock) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("ThreadA print : " + threadLocal.get());
        });

        Thread threadB = new Thread(() -> {
            threadLocal.set("I am threadB");

            try {
                synchronized (lock) {
                    lock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("ThreadB print : " + threadLocal.get());
        });

        threadA.start();
        threadB.start();


        // 1s后随机唤醒某个线程
        Thread.sleep(1000);
        synchronized (lock) {
            lock.notify();
            lock.notify();
        }

        threadA.join();
        threadB.join();

        System.out.println("MainThread print : " + threadLocal.get());
    }
}
