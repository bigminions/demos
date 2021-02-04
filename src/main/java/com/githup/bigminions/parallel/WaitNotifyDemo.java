package com.githup.bigminions.parallel;

import java.util.LinkedList;
import java.util.Queue;

public class WaitNotifyDemo {

    static class BlockingQueue {
        Queue<String> buffer = new LinkedList<String>();

        public void give(String data) {
            buffer.add(data);
            synchronized (this) {
                notify();                   // Since someone may be waiting in take!
            }
        }

        public String take() throws InterruptedException {
            while (buffer.isEmpty())
                wait();
            return buffer.remove();
        }
    }

    public static void main(String[] args) {
        BlockingQueue blockingQueue = new BlockingQueue();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                blockingQueue.give("" + i);
            }
        }).start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    synchronized (blockingQueue) {
                        System.out.println(blockingQueue.take());;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
