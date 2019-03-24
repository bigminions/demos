package com.githup.bigminions.parallel;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ThreadLocalDemo {

    private static final InheritableThreadLocal<Map<String, String>> DATA_CONTAINER = new InheritableThreadLocal<>();

    static class ParentThread extends Thread {
        @Override
        public void run() {
            // generate nine tasks
            Map<String, String> map = new ConcurrentHashMap<>();
            for (int i = 0; i < 9; i++) {
                map.put("From " + getName() + " Task-" + i, "Run task " + i);
            }
            DATA_CONTAINER.set(map);
            try {
                selfHandle();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void selfHandle () throws InterruptedException {
            // Handler a half of task, left to son.
            List<String> selfHandles = DATA_CONTAINER.get().keySet().stream().skip(4).collect(Collectors.toList());
            selfHandles.stream().map(DATA_CONTAINER.get()::remove).map(v -> getName() + " : " + v).forEach(System.out::println);

            Thread thread = new Thread(getThreadGroup(), () -> {
                if (DATA_CONTAINER.get() == null) return;
                Set<String> sonHandles = DATA_CONTAINER.get().keySet();
                sonHandles.stream().map(DATA_CONTAINER.get()::remove).map(v -> Thread.currentThread().getName() + " :: " + v).forEach(System.out::println);
            }, getName() + "-son", 0, true);
            thread.start();
            thread.join();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            ParentThread parentThread = new ParentThread();
            parentThread.start();
        }
    }
}
