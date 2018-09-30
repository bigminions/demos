package com.githup.bigminions.parallel.lock;

import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by daren on 2018/9/29.
 */
public class ReadWriteLockDemo {

    private static final Lock LOCK = new ReentrantLock();

    private static final ReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock();

    private static class Data {
        private String  data = (new Date()).toString();

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return data;
        }
    }

    private static class ReadTask implements Runnable {

        Lock lock;
        Data data;

        ReadTask (Lock lock, Data data) {
            this.lock = lock;
            this.data = data;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                Thread.sleep(1000); // 模拟读占时
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " Read data : " + data);
            lock.unlock();
        }
    }

    private static class WriteTask extends ReadTask {

        WriteTask (Lock lock, Data data) {
            super(lock, data);
        }

        @Override
        public void run() {
            lock.lock();
            try {
                Thread.sleep(1000); // 模拟写占时
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            data.setData(Thread.currentThread().getName() + " new Data be write at " + new Date());
            System.out.println("Write data : " + data);
            lock.unlock();
        }
    }

    /**
     * 10个读任务，5个写任务，读写各耗时1s，普通锁需耗时15s左右，读写锁则在小于15s
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Data data = new Data();

        Stopwatch stopwatch = Stopwatch.createStarted();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ReadTask readTask = new ReadTask(LOCK, data);
            WriteTask writeTask = new WriteTask(LOCK, data);
            Thread thread1 = new Thread(readTask);
            thread1.start();
            threads.add(thread1);
            if (i % 2 == 0) {
                Thread thread2 = new Thread(writeTask);
                thread2.start();
                threads.add(thread2);
            }
        }
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("使用单一锁" + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms\n");

        stopwatch = Stopwatch.createStarted();
        threads.clear();
        for (int i = 0; i < 10; i++) {
            ReadTask readTask = new ReadTask(READ_WRITE_LOCK.readLock(), data);
            WriteTask writeTask = new WriteTask(READ_WRITE_LOCK.writeLock(), data);
            Thread thread1 = new Thread(readTask);
            thread1.start();
            threads.add(thread1);
            if (i % 2 == 0) {
                Thread thread2 = new Thread(writeTask);
                thread2.start();
                threads.add(thread2);
            }
        }
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("使用读写锁" + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms\n");;

    }
}
