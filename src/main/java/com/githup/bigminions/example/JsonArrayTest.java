package com.githup.bigminions.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by daren on 2018/9/27.
 */
public class JsonArrayTest {

    public static void main(String[] args) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add("one");
        jsonArray.add("two");
        jsonArray.add("three");
        jsonArray.add("four");

        String array = jsonArray.toJSONString();
        System.out.println(array);
        JSONArray jsonArray1 = JSON.parseArray(array);

        for (Object object : jsonArray1) {
            System.out.println(object);
        }
    }
}
