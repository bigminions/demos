package com.githup.bigminions.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by daren on 2018/7/10.
 * fastjson 的序列化以及反序列化依赖于类里的getter 和 setter方法，
 * 假如不给getNothing添加@JSONField标明不对其做序列化，则会出现问题
 */
public class FastJsonDemo {

    public static void main(String[] args) {
        User user = new User();
        String result = JSON.toJSONString(user);
        System.out.println(result);
    }

    public static class User {
        private int id;
        private String name;

        public String getNickname() {
            return "nickname";
        }

        public void setNickname(String nickname) {
            // nothing to do
        }

        @JSONField(serialize = false, deserialize = false)
        public String getNothing () {
            throw new RuntimeException("nothing to get");
        }
    }
}
