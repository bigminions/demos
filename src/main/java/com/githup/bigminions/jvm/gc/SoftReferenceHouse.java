package com.githup.bigminions.jvm.gc;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * soft reference
 * before oom clear the object.
 */
public class SoftReferenceHouse {

    /**
     * setting -Xms10m -Xmx10m -Xlog:gc
     * @param args
     */
    public static void main(String[] args) {
        List<SoftReference<House>> houses = new ArrayList<>();
        ReferenceQueue<House> queue = new ReferenceQueue<>();

        for (int i = 0; i < 100000; i++) {
            houses.add(new SoftReference<>(new House(), queue));
            System.out.println("i = " + i);
        }

        long count = houses.stream().map(SoftReference::get).filter(Objects::nonNull).count();
        System.out.println("At last, the count of not null = " + count);
        int i = 0;
        while (queue.poll() != null) {
            i++;
        }
        System.out.println("The queue size = " + i);

        System.gc();
        count = houses.stream().map(SoftReference::get).filter(Objects::nonNull).count();
        // It prove the gc did not clear the object.
        System.out.println("After gc, the count of not null = " + count);
    }
}

class House {
    private static final Integer DOOR_NUMBER = 2000;
    public Door[] doors = new Door[DOOR_NUMBER];

    class Door {}

}
