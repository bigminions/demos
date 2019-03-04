package com.githup.bigminions.parallel.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo1 {

    public static class BaseHeathChecker extends Thread {
        private String name;
        private boolean done;
        private CountDownLatch countDownLatch;

        public BaseHeathChecker (String name, CountDownLatch countDownLatch) {
            this.name = name;
            this.countDownLatch = countDownLatch;
            setUncaughtExceptionHandler(((t, e) -> {
                System.out.println(t.getName() + " : " + e.getMessage());
            }));
        }

        public boolean isDone() {
            return done;
        }

        public void run() {
            try {
                if (Math.random() > 0.7) {
                    throw new RuntimeException(name + "检查时发生错误");
                }

                long time = Double.valueOf(Math.random()).longValue();
                Thread.sleep(time);
                done = true;
                System.out.println(name + "检查完成");
            } catch (Exception e) {
                done = false;
                getUncaughtExceptionHandler().uncaughtException(currentThread(), e);
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        List<BaseHeathChecker> allTask = new ArrayList<>();
        allTask.add(new BaseHeathChecker("network", countDownLatch));
        allTask.add(new BaseHeathChecker("io", countDownLatch));
        allTask.add(new BaseHeathChecker("dns", countDownLatch));

        allTask.forEach(Thread::start);
        countDownLatch.await();

        boolean flag = allTask.stream().allMatch(BaseHeathChecker::isDone);
        if (flag) {
            System.out.println("All done!");
        } else {
            allTask.stream().filter(baseHeathChecker -> !baseHeathChecker.isDone()).forEach(baseHeathChecker -> {
                System.out.println(baseHeathChecker.name + "检查失败");
            });
        }

    }
}
