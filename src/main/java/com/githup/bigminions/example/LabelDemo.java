package com.githup.bigminions.example;

/**
 * Created by daren on 2018/3/21.
 */
public class LabelDemo {

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (i * j % 10 == 1) {
                    System.out.println("TEST_1 : " + i + " * " + j + " % 10 = 1");
                    break;
                }
            }
        }

        testLabel:
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                if (i * j % 10 == 1) {
                    System.out.println("TEST_2 : " + i + " * " + j + " % 10 = 1");
                    break testLabel;
                }
            }
        }
    }
}
