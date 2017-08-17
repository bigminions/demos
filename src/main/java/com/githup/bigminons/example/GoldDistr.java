package com.githup.bigminons.example;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by daren on 8/17/2017.
 * 一个金币分配的例子
 */
public class GoldDistr {

    /**
     * 假设一分=$500
     */
    private Map<Player, Integer> scores;
    private int base = 500;

    private Random random = new Random();

    public void before() {
        scores = new HashMap<>();
        int score1 = random.nextInt(100);
        int score2 = random.nextInt(100);
        int score3 = - (score1 + score2);
        scores.put(new Player(1L), score1);
        scores.put(new Player(2L), score2);
        scores.put(new Player(3L), score3);
    }

    @Test
    public void testDist() {
        Map<Player, Integer> money = new HashMap<>();
        Map<Player, Integer> winMoney = new HashMap<>();
        int lost = 0;
        int winScore = 0;
        for (Map.Entry<Player, Integer> entry : scores.entrySet()) {
            Player player = entry.getKey();
            int score = entry.getValue();
            int gold = base * entry.getValue();
            if (gold < 0) {
                if (gold < -player.getGold()) {
                    money.put(player, player.getGold());
                    lost += gold;
                }
            } else {
                winScore += score;
                winMoney.put(player, score);
            }
        }
        for (Map.Entry<Player, Integer> entry : winMoney.entrySet()) {
            // todo 分配金币
        }

    }

    public static class Player {
        private long id;
        private int gold;

        public Player(long id) {
            this.gold = 10000;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }
    }
}
