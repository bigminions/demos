package com.githup.bigminons.exception;

/**
 * Created by daren on 8/11/2017.
 */
public class UnsatisfiedLinkErrorTest {

    static {
        System.loadLibrary("NoExistsLibs");
    }

    public static void main(String[] args) {
        // Here will throw UnsatisfiedLinkError because of the libs that used is not exists.
    }
}
