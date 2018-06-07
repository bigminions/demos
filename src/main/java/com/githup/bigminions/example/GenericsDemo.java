package com.githup.bigminions.example;

/**
 * Created by daren on 2018/6/7.
 * java 泛型上下界
 */
public class GenericsDemo {

    static class Box<T> {

        private T t;

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }
    }

    static class Food {}

    static class Fruit extends Food {}

    public static void main(String[] args) {
        Object object = new Object();
        Food food = new Food();
        Fruit fruit = new Fruit();
        Box<Food> box = new Box<>();
        Box<? extends Food> box1 = new Box<>();
        Box<? super Food> box2 = new Box<>();

//        box.setT(object);  //  illegal
        box.setT(food);
        box.setT(fruit);
        object = box.getT();
        food = box.getT();
//        fruit = box.getT(); //  illegal

//        box1.setT(object);  //  illegal
//        box1.setT(food);  //  illegal
//        box1.setT(fruit);  //  illegal
        object = box1.getT();
        food = box1.getT();
//        fruit = box1.getT();  //  illegal

//        box2.setT(object);  //  illegal
        box2.setT(food);
        box2.setT(fruit);
        object = box2.getT();
//        food = box2.getT();  //  illegal
//        fruit = box2.getT();  //  illegal
    }
}
