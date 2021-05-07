package com.githup.bigminions.parallel;

import java.util.concurrent.atomic.AtomicInteger;

public class Demo8 {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger cnt = new AtomicInteger();
        Thread thread = new Thread(() -> {
            synchronized (cnt) {
                try {
                    System.out.println("now going to wait");
                    // wait方法会响应中断
                    cnt.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        thread.start();
        Thread.sleep(1000);
        thread.interrupt();

        thread.join();

    }
}
