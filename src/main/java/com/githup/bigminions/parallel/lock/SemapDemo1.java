package com.githup.bigminions.parallel.lock;

import java.util.concurrent.Semaphore;

public class SemapDemo1 {

    public static class SecurityCheckThread extends Thread {

        private int seq;
        private Semaphore semaphore;

        public SecurityCheckThread (int seq, Semaphore semaphore) {
            this.seq = seq;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("No." + seq + " 乘客正在查验中");
                Thread.sleep(1000);

                if (seq % 2 == 0) {
                    Thread.sleep(1000);
                    System.out.println("No." + seq + " 乘客，身份可疑！");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new SecurityCheckThread(i + 1, semaphore).start();
        }
    }
}
