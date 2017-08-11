package com.githup.ussheepsheep.parallel;

/**
 * Created by daren on 2017/5/31.
 * 实现一个简单的锁
 */
public class Demo5 {

    public static class Lock {
        private boolean locked = false;
        private Thread lockingThread = null;

        public synchronized void lock() throws Exception {
            // 防止虚假唤醒
            while (locked) {
                wait();
            }
            locked = true;
            lockingThread = Thread.currentThread();
        }

        public synchronized void unlock() throws Exception {
            if (!Thread.currentThread().equals(lockingThread)) {
                throw new IllegalMonitorStateException("Calling thread not locked this lock");
            }
            locked = false;
            lockingThread = null;
            notify();
        }
    }

    public static void main(String[] args) throws Exception {
        Lock lock = new Lock();
        lock.lock();

        new Thread(() -> {
            try {
                lock.unlock();
            } catch (Exception e) {
                System.out.println("has error : " + e.getMessage());
            }
        }).start();

    }
}
