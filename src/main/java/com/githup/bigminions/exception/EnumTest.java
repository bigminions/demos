package com.githup.bigminions.exception;

/**
 * Created by daren on 2018/1/15.
 */
public class EnumTest {

    public enum Number {
        ONE,
        TWO,
        THREE,
        FOUR

        ;
        private String comment;
        static {
            for (Number number : Number.values()) {
                number.init();
            }
        }

        private void init() {
            switch (this) {
                case ONE:
                    this.comment = "I am 1";
                    break;
                case TWO:
                    this.comment = "I am 2";
                    break;
            }
        }

    }

    public static void main(String[] args) {
        for (Number number : Number.values()) {
            System.out.println("name : " + number.name());
        }
    }


}
