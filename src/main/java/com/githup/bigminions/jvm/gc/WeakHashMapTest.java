package com.githup.bigminions.jvm.gc;

import java.util.WeakHashMap;

public class WeakHashMapTest {

    public static void main(String[] args) {
        Key keyOne = new Key("foo");
        Key keyTwo = new Key("bar");
        WeakHashMap<Key, House> weakHashMap = new WeakHashMap<>();
        weakHashMap.put(keyOne, new House());
        weakHashMap.put(keyTwo, new House());

        keyOne = null;
        weakHashMap.keySet().forEach(System.out::println);

        System.out.println("before gc " + weakHashMap.size());
        System.gc();
        System.runFinalization();
        System.out.println("after gc " + weakHashMap.size());
    }

    static class Key {
        String name;
        Key (String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Key{" + "name='" + name + '\'' + '}';
        }
    }
}
