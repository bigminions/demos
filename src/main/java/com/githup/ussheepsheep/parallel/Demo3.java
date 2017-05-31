package com.githup.ussheepsheep.parallel;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by daren on 2017/5/31.
 * 实现一个生产者-消费者模型
 * 生产者每个生产周期内生产一个数字
 * 消费者每个消费周期内消费一个数字
 * 当待消费数字个数 < 10个时，唤醒生产者，使消费者进入等待
 * 当待消费数字个数 > 10个时，唤醒消费者，使生产者进入等待
 */
public class Demo3 {

    public static Queue<Integer> produce = new ConcurrentLinkedQueue<>();

    public static final int PRODUCE_TIME = 1000;
    public static final int CONSUME_TIME = 1000;

    public static class Producer {

        public void toProduce() throws Exception {
            Random random = new Random();
            while (true) {
                if (produce.size() > 10) {
                    toWait();
                }
                produce.add(random.nextInt());
                Thread.sleep(PRODUCE_TIME);
            }
        }

        public void toWait() throws Exception {
            synchronized (this) {
                wait();
            }
        }

        public void toNotify() {
            synchronized (this) {
                notify();
            }
        }

    }

    public static class Consumer {

        public void toConsumer() throws Exception {
            String threadName = Thread.currentThread().getName();
            while (true) {
                Integer integer = produce.poll();
                if (integer == null) {
                    toWait();
                } else {
                    System.out.println(threadName + " : 消费数字 " + integer + " 待消费数字有 " + produce.size() + " 个");
                }
                Thread.sleep(CONSUME_TIME);
            }
        }

        public void toWait() throws Exception {
            synchronized (this) {
                wait();
            }
        }

        public void toNotify() {
            synchronized (this) {
                notify();
            }
        }

    }

    public static void main(String[] args) throws Exception {
        Producer producer1 = new Producer();
        Producer producer2 = new Producer();

        Consumer consumer1 = new Consumer();
        Consumer consumer2 = new Consumer();

        Runnable runnable1 = () -> {
            try {
                producer1.toProduce();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Runnable runnable2 = () -> {
            try {
                producer2.toProduce();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Runnable runnable3 = () -> {
            try {
                consumer1.toConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Runnable runnable4 = () -> {
            try {
                consumer2.toConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        Thread thread1 = new Thread(runnable1, "producer 1");
        Thread thread2 = new Thread(runnable2, "producer 2");
        Thread thread3 = new Thread(runnable3, "consumer 1");
        Thread thread4 = new Thread(runnable4, "consumer 2");
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        while (true) {
            if (produce.size() < 10) {
                producer1.toNotify();
                producer2.toNotify();
            } else {
                consumer1.toNotify();
                consumer2.toNotify();
            }
        }
    }
}
