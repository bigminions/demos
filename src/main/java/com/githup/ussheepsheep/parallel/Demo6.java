package com.githup.ussheepsheep.parallel;

import java.util.Random;

/**
 * Created by daren on 2017/6/1.
 */
public class Demo6 {

    private static final Random random = new Random();

    public static class Semaphore {

        private int signals = 0;
        private int bound = 0;

        public Semaphore (int bound) {
            this.bound = bound;
        }

        public void take() throws InterruptedException {
            synchronized (this) {
                while (signals == bound) {
                    wait();
                }
                signals++;
                notify();
            }
        }

        public void release() throws InterruptedException {
            synchronized (this) {
                while (signals == 0) {
                    wait();
                }
                signals--;
                notify();
            }
        }
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(1); //semaphore作为锁使用
        Runnable runnable = () -> {
            try {
                while (true) {
                    try {
                        semaphore.take();
                        int sleepTime = random.nextInt(5) * 1000;
                        System.out.println(Thread.currentThread().getName() + " do something, and will sleep " + sleepTime + " s");
                        Thread.sleep(sleepTime);
                    } finally {
                        semaphore.release(); //把这行注释，出现锁死
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread1.start();
        thread2.start();
    }
}
