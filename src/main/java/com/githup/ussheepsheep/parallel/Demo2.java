package com.githup.ussheepsheep.parallel;


/**
 * Created by daren on 2017/5/31.
 * 多个资源竞争同意资源，会产生竟态，在demo中，forNum越大则出现的可能性越大
 */
public class Demo2 {

    private static final Counter counter = new Counter();

    private static final int forNum = 10000;

    public static void main(String[] args) {
        Runnable runnable = () -> {
            String threadName = Thread.currentThread().getName();
            for(int i = 0; i < forNum; i++) {
                counter.count(); // 每次运行均计数
            }
        };

        Thread thread1 = new Thread(runnable, "one");
        Thread thread2 = new Thread(runnable, "two");
        Thread thread3 = new Thread(runnable, "three");
        Thread thread4 = new Thread(runnable, "four");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        while (thread1.isAlive() || thread2.isAlive() || thread3.isAlive() || thread4.isAlive()); // 等待线程都执行完毕

        System.out.println("for循环运行了 " + counter.getCount() + " 次，期待值 ：" + forNum * 4);
    }

    static class Counter {
        private int count = 0;

        public void count() {
            /**
             * 通过添加同步块可以使同一时间只有一个线程运行该代码，此处对同一对象生效
             * 假如想限制同一个类但是不同对象使用，则使用
             * synchronized (Counter.class) {
             *     count++;
             * }
             */
            synchronized (this) {
                count++;
            }
        }

        public int getCount() {
            return this.count;
        }
    }
}
