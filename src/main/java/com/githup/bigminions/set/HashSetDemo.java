package com.githup.bigminions.set;

import java.util.HashSet;

/**
 * Created by daren on 2018/8/6.
 */
public class HashSetDemo {

    public static void main(String[] args) {
        HashSet hashSet = new HashSet();
        hashSet.add("One");
        hashSet.add("Two");
        hashSet.contains("Three");

        HashSet<De> des = new HashSet<>();
        des.add(new De());
        des.add(new De());
        System.out.println(des.size());
    }

    public static class De {
        @Override
        public boolean equals(Object obj) {
            return true;
        }

        @Override
        public int hashCode() {
            return De.class.getSimpleName().hashCode();
        }
    }
}
