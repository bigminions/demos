package com.githup.bigminions.parallel;

/**
 * Created by daren on 2017/5/31.
 * ThreadLocal :
 *     该类中有一个threadLocals的map，保存不同thread的set进去的value，取的时候根据currentThread取
 */
public class Demo4 {

    public static class A implements Runnable {

        private ThreadLocal<String> threadLocal = new ThreadLocal<>();

        @Override
        public void run() {
            threadLocal.set("threadLocal : " + System.nanoTime());
            out();
        }

        public void out() {
            System.out.println(threadLocal.get());
        }
    }

    public static void main(String[] args) throws Exception {
        A a = new A();
        Thread thread1 = new Thread(a);
        Thread thread2 = new Thread(a);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
