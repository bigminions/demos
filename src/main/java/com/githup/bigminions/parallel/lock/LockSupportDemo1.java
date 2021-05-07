package com.githup.bigminions.parallel.lock;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo1 {

    private static final BussLock TEST_LOCK = new BussLock();

    private static class BussLock {
    }

    public static class DemoThread extends Thread {
        @Override
        public void run() {
            LockSupport.park(TEST_LOCK);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DemoThread demoThread = new DemoThread();
        demoThread.start();

        LockSupport.parkUntil(System.currentTimeMillis() + 10 * 1000);
        while (true);
    }
}
