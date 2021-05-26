package com.githup.bigminions.example;

import java.util.HashMap;
import java.util.stream.IntStream;

public class HashMapDebug {

    public static class SameHashObject {
        @Override
        public int hashCode() {
            return Integer.MAX_VALUE;
        }
    }

    public static void main(String[] args) {
        HashMap map = new HashMap(32);
        int elements = 100;
        System.out.println("准备放入元素 ： " + elements);
        IntStream.range(0, elements).forEach(i -> {
            map.put(new SameHashObject(), i);
        });

        System.out.println(map.size());

        FastJsonDemo.User u = new FastJsonDemo.User();
        u.setNickname("hello");
    }
}
