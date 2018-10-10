package com.githup.bigminions.parallel.executor;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by daren on 2018/10/10.
 */
public class ExecutorDemo3 {

    public static void main(String[] args) {

        class Task implements Runnable {

            String taskName;

            Task (String taskName) {
                this.taskName = taskName;
            }

            @Override
            public void run() {
                int time = new Random().nextInt(1000);
                System.out.println(Thread.currentThread().getName() + " : run " + taskName + " -- sleep " + time + " ms");
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 4, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100), (r, e) -> {
            System.out.println("the task be reject : " + ((Task) r).taskName);
        }) {

            // 重写beforeExecute 和 afterExecute 实现追踪和监控功能
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("start -- " + ((Task) r).taskName);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("over -- " + ((Task) r).taskName);
            }
        };

        for (int i = 0; i < 1000; i++) {
            threadPoolExecutor.execute(new Task("task-" + i));
        }
    }
}
