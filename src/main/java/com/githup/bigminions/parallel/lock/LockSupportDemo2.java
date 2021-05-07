package com.githup.bigminions.parallel.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * java 中如何实现多个线程交替打印
 */
public class LockSupportDemo2 {

    public static class NodeThread extends Thread {
        private NodeThread nextNode = null;
        private AtomicInteger cnt;

        public NodeThread(String name, AtomicInteger cnt) {
            this.cnt = cnt;
            this.setName(name);
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                LockSupport.park();
                System.out.println(Thread.currentThread().getName() + " : " + cnt.incrementAndGet());
                LockSupport.unpark(nextNode);
            }
            nextNode.interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger cnt = new AtomicInteger();

        NodeThread nodeThread1 = new NodeThread("T-1", cnt);
        NodeThread nodeThread2 = new NodeThread("T-2", cnt);
        NodeThread nodeThread3 = new NodeThread("T-3", cnt);
        nodeThread1.nextNode = nodeThread2;
        nodeThread2.nextNode = nodeThread3;
        nodeThread3.nextNode = nodeThread1;

        nodeThread1.start();
        nodeThread2.start();
        nodeThread3.start();

        Thread.sleep(1000);
        LockSupport.unpark(nodeThread1);

        while (true) {
            if (cnt.get() > 30) {
                nodeThread1.interrupt();
            }
        }
    }
}
