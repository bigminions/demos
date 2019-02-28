package com.githup.bigminions.set;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayListDemo {

    public static void main(String[] args) {
        List<String> master = new ArrayList<>();
        master.add("hello");
        master.add("world");
        master.add("!");

        List<String> branch = master.subList(0, 2);

        /**
         * 对master的操作不注释将抛出 ConcurrentModificationException
         */
//        master.remove(0);
//        master.add("some");
//        master.clear();

        branch.add("one");
        branch.add("two");

        System.out.println(master);
    }

}
