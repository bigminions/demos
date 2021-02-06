package com.githup.bigminions.gc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class GCLog {

    // 一个足够大的size使程序运行足够长的时间，借此观察其中的gc情况
    private static final int SIZE = 10240;

    private static final Map<Integer, List<Contain>> map = new ConcurrentHashMap(SIZE);

    private static class Contain {
        private String s;

        public Contain(String s) {
            this.s = s;
        }
    }

    public static void main(String[] args) {
        IntStream.range(0, SIZE).forEach(i -> {
            map.put(i, new ArrayList<>());
        });

        Executor executor = Executors.newFixedThreadPool(10);

        IntStream.range(0, SIZE).forEach(i -> {
            executor.execute(() -> {
                try {
                    randomAddAndRemove(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private static int randomAddAndRemove(int i) throws InterruptedException {
        int random = new Random().nextInt(SIZE);

        // 随机加元素
        Thread.sleep(random);
        for (int j = 0; j < random; j++) {
            String s = j + System.currentTimeMillis() + "";
            map.get(i).add(new Contain(s));
            System.out.println(Thread.currentThread().getName() + " random add " + s);
        }

        // 随机减一半元素
        Thread.sleep(random / 2);
        for (int j = random / 2; j > 0; j--) {
            System.out.println(Thread.currentThread().getName() + " random remove " + map.get(i).remove(j).s);;
        }

        return random;
    }
}
