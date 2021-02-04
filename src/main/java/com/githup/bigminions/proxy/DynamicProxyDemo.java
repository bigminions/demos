package com.githup.bigminions.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class DynamicProxyDemo {

    interface Human {
        void sayHi(String someone);

        void playGame();

        String giveMeMoney();
    }

    /**
     * 使用java自带的动态代理
     * @param args
     */
    public static void main(String[] args) {
        InvocationHandler invocationHandler = (proxy, method, args1) -> {
            if (method.getName().equals("sayHi")) {
                System.out.println("Hi, " + args1[0].toString());
            }

            if (method.getName().equals("playGame")) {
                System.out.println("Sorry, I need to work");
            }

            if (method.getName().equals("giveMeMoney")) {
                return "nothing";
            }

            return null;
        };

        Human human = (Human) Proxy.newProxyInstance(Human.class.getClassLoader(), new Class[]{Human.class}, invocationHandler);
        human.playGame();
        human.sayHi("Jacky");
        System.out.println("Can you give me some money? " + human.giveMeMoney());
    }


}
