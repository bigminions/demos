package com.githup.bigminions.example;

public class StringTest {

    public static void main(String[] args) {
        String a = "Hello";
        String b = "Hello";
        System.out.println("a == b : " + (a == b));
        System.out.println("a equals b : " + a.equals(b));


        String c = "H";
        String d = "ello";
        String e = c + d;
        String f = new String("Hello");

        System.out.println("e == b : " + (e == b));
        System.out.println("f == b : " + (f == b));

        System.out.println("a.hash = " + a.hashCode());
        System.out.println("b.hash = " + b.hashCode());
        System.out.println("e.hash = " + e.hashCode());
        System.out.println("f.hash = " + f.hashCode());

        int i1 = 10000;
        long l1 = 10000L;
        float f1 = 10000.0000000f;

        System.out.println(i1 == l1); // true
        System.out.println(i1 == f1); // true
    }
}
