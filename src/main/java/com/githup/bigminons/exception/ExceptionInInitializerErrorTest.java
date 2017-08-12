package com.githup.bigminons.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by daren on 8/11/2017.
 */
public class ExceptionInInitializerErrorTest {

    public static Map<String, String> map = new HashMap<String, String>(){
        {
            System.out.println("Init code in hashMap");
            map.put("test", "test");
        }
    };

    public static void main(String[] args) {
        // Here will throw ExceptionInInitializerError because we use map in init code when the object didn't init done and is null. So the init is error. .
    }
}
