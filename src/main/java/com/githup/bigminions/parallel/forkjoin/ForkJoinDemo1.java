package com.githup.bigminions.parallel.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by daren on 2018/3/21.
 */
public class ForkJoinDemo1 {

    static class CountTask extends RecursiveTask<Long> {

        private static final int THRESHOLD = 10000;
        private long start;
        private long end;

        public CountTask (long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            long sum = 0;
            boolean canCompute = (end - start) < THRESHOLD;
            if (canCompute) {
                for (long i = start; i <= end; i++) {
                    sum += i;
                }
            } else {
                long step = (start + end) / 100;
                List<CountTask> countTasks = new ArrayList<>();
                long pos = start;
                for (int i = 0; i < 100; i++) {
                    long lastOne = pos + step;
                    if (lastOne > end) lastOne = end;
                    CountTask subTask = new CountTask(pos, lastOne);
                    pos = lastOne + 1;
                    countTasks.add(subTask);
                    subTask.fork();
                }
                for (CountTask task : countTasks) {
                    sum += task.join();
                }
            }
            return sum;
        }
    }

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        CountTask countTask = new CountTask(0, 200000L);
        ForkJoinTask<Long> result = forkJoinPool.submit(countTask);
        try {
            long sub = result.get();
            System.out.println("(0, 200000L) sum = " + sub);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
