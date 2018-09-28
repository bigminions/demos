package com.githup.bigminions.parallel;

/**
 * Created by daren on 2018/9/28.
 */
public class GoodSuspend {

    public static Object u = new Object();

    public static class ChangeObjectThread extends Thread {
        volatile boolean suspendme = false;

        public void suspendMe () {
            suspendme = true;
        }

        public void resumeMe () {
            suspendme = false;
            synchronized (this) {
                notify();
            }
        }

        @Override
        public void run() {
            while (true) {

                synchronized (this) {
                    while (suspendme) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                synchronized (u) {
                    System.out.println("In ChangeObjectThread");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.yield();

            }
        }
    }

    public static class ReadObjectThread extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (u) {
                    System.out.println("In ReadObjectThread");
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChangeObjectThread cot = new ChangeObjectThread();
        ReadObjectThread rot = new ReadObjectThread();
        cot.start();
        rot.start();

        Thread.sleep(1000);
        cot.suspendMe();
        System.out.println("suspend cot 2 sec");
        Thread.sleep(2000);
        System.out.println("resume cot ");
        cot.resumeMe();
        System.out.println("-----------------------");
    }
}
