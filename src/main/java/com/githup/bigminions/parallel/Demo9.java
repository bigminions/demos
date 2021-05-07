package com.githup.bigminions.parallel;

import java.util.concurrent.atomic.AtomicInteger;

public class Demo9 {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger cnt = new AtomicInteger();
        Thread thread = new Thread() {
            @Override
            public void run() {
                // 用户代码需要对中断标志做出响应
                while (!this.isInterrupted()) {
                    System.out.println(cnt.incrementAndGet());
                }
            }
        };


        thread.start();
        Thread.sleep(1000);
        thread.interrupt();


        thread.join();

    }
}
