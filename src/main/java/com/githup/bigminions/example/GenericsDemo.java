package com.githup.bigminions.example;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daren on 2018/6/7.
 * java 泛型上下界
 */
public class GenericsDemo {

    static class Box<T> {

        private T t;

        Box(T t) {
            this.t = t;
        }

        T getT() {
            return t;
        }

        void setT(T t) {
            this.t = t;
        }
    }

    static class Food {}

    static class Fruit extends Food {}

    public static void main(String[] args) {
        Object object = new Object();
        Food food = new Food();
        Fruit fruit = new Fruit();

        Box<Food> box = new Box<>(food);
        box.setT(food);
        box.setT(fruit);
//        box.setT(object);  //  illegal (case1)
        food = box.getT();
//        fruit = box.getT(); //  illegal(case2)

        Box<? extends Food> box1= new Box<>(food);
//        box1.setT(food);  //  illegal (case3)
//        box1.setT(fruit);  //  illegal (case4)
//        box1.setT(object);  //  illegal (case5)
        food = box1.getT();
//        fruit = box1.getT();  //  illegal (case6)

        Box<? super Food> box2 = new Box<>(food);
        box2.setT(food);
        box2.setT(fruit);
//        box2.setT(object);  //  illegal (case7)
//        food = box2.getT();  //  illegal (case8)
//        fruit = box2.getT();  //  illegal (case9)
    }

}
