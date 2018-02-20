package com.githup.bigminions.java9;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class VarHandleTest {

    static class Foo {
        long id;
        String name;
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Foo foo = new Foo();
        foo.id = 1L;
        foo.name = "hello";

        VarHandle varHandle = MethodHandles.lookup().findVarHandle(Foo.class, "name", String.class);
        varHandle.accessModeType(VarHandle.AccessMode.GET);

        System.out.println(varHandle.get(foo));
    }
}
