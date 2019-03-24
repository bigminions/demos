package com.githup.bigminions.parallel;

import java.util.concurrent.ThreadLocalRandom;

public class CSGameByThreadLocal {

    private static final Integer BULLET_NUMBER = 1500;
    private static final Integer KILLED_ENEMIES = 0;
    private static final Integer LIFE_VALUE = 10;
    private static final Integer TOTAL_PLAYERS = 10;
    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private static final ThreadLocal<Integer> BULLET_NUMBER_THREAD_LOCAL = new ThreadLocal<>() {
        @Override
        protected Integer initialValue() {
            return BULLET_NUMBER;
        }
    };

    private static final ThreadLocal<Integer> KILLED_ENEMIES_THREAD_LOCAL = new ThreadLocal<>() {
        @Override
        protected Integer initialValue() {
            return KILLED_ENEMIES;
        }
    };

    private static final ThreadLocal<Integer> LIFE_VALUE_THREAD_LOCAL = new ThreadLocal<>() {
        @Override
        protected Integer initialValue() {
            return LIFE_VALUE;
        }
    };

    private static class Player extends Thread {
        @Override
        public void run() {
            Integer bullets = BULLET_NUMBER_THREAD_LOCAL.get() - ThreadLocalRandom.current().nextInt(BULLET_NUMBER);
            Integer killEnemies = KILLED_ENEMIES_THREAD_LOCAL.get() + ThreadLocalRandom.current().nextInt(TOTAL_PLAYERS / 2);
            Integer lifeValue = LIFE_VALUE_THREAD_LOCAL.get() - ThreadLocalRandom.current().nextInt(LIFE_VALUE);
            System.out.println(getName() + " : BULLET_NUMBER is " + bullets);
            System.out.println(getName() + " : KILLED_ENEMIES is " + killEnemies);
            System.out.println(getName() + " : LIFE_VALUE is " + lifeValue + "\n");
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < TOTAL_PLAYERS; i++) {
            new Player().start();
        }
    }
}
