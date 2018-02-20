package com.githup.bigminions.java9;

public interface UsePrivateMethodInInterface {

    /**
     * 可以在java9的接口中添加私有方法
     * @return
     */
    private String doSomePrivateThing () {
        return "do some private things";
    }

    void doSomething();
}
