package com.githup.bigminions.jvm.s2;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class OutOfMemoryErrorTest {

    public static class HeapOut {

        /**
         * 测试堆溢出 此处设置 -Xms20m -Xmx20m --XX:+HeapDumpOnOutOfMemoryError，模拟堆内存不够
         * @param args
         * 结果：
         *
         * java.lang.OutOfMemoryError: Java heap space
         * Dumping heap to java_pid15000.hprof ...
         * Heap dump file created [30323080 bytes in 0.097 secs]
         * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
         * at java.base/java.util.Arrays.copyOf(Arrays.java:3719)
         * at java.base/java.util.Arrays.copyOf(Arrays.java:3688)
         * at java.base/java.util.ArrayList.grow(ArrayList.java:237)
         * at java.base/java.util.ArrayList.grow(ArrayList.java:242)
         * at java.base/java.util.ArrayList.add(ArrayList.java:467)
         * at java.base/java.util.ArrayList.add(ArrayList.java:480)
         * at com.githup.bigminions.jvm.s2.OutOfMemoryErrorTest$HeapOut.main(OutOfMemoryErrorTest.java:17)
         */
        public static void main(String[] args) {
            List<Object> list = new ArrayList<>();
            while (true) {
                list.add(new Object());
            }
        }
    }

    public static class StackOverflow {

        public static int stackLength = 0;

        /**
         * 测试stack溢出 无限递归导致对stack的占用无限增大
         * @param args
         * 结果：
         *
         * Exception in thread "main" Now stackLength = 28411
         * java.lang.StackOverflowError
         * at com.githup.bigminions.jvm.s2.OutOfMemoryErrorTest$StackOverflow.stack(OutOfMemoryErrorTest.java:50)
         * at com.githup.bigminions.jvm.s2.OutOfMemoryErrorTest$StackOverflow.stack(OutOfMemoryErrorTest.java:50)
         * at com.githup.bigminions.jvm.s2.OutOfMemoryErrorTest$StackOverflow.stack(OutOfMemoryErrorTest.java:50)
         * at com.githup.bigminions.jvm.s2.OutOfMemoryErrorTest$StackOverflow.stack(OutOfMemoryErrorTest.java:50)
         */
        public static void main(String[] args) {
            try {
                stack();
            } catch (Throwable t) {
                System.out.println("Now stackLength = " + stackLength);
                throw t;
            }
        }

        public static void stack() {
            stackLength++;
            stack();
        }
    }

    public  static class MetaspaceOut {

        /**
         * 测试metaspace溢出 无限生成动态类填满方法区直到溢出 使用cglib
         * 此处设置 -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m
         * @param args
         *
         * 结果：
         * Exception in thread "main" java.lang.OutOfMemoryError: MetaspaceOut
         * at net.sf.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:345)
         * at net.sf.cglib.proxy.Enhancer.generate(Enhancer.java:492)
         * at net.sf.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:114)
         * at net.sf.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:291)
         * at net.sf.cglib.proxy.Enhancer.createHelper(Enhancer.java:480)
         * at net.sf.cglib.proxy.Enhancer.create(Enhancer.java:305)
         * at com.githup.bigminions.jvm.s2.OutOfMemoryErrorTest$PermGen.main(OutOfMemoryErrorTest.java:88)
         */
        public static void main(String[] args) {
            while (true) {
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(MetaspaceOut.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invokeSuper(o, args);
                    }
                });
                enhancer.create();
            }
        }
    }

    public static class DirectMemoryOut {

        /**
         * 模拟直接内存溢出
         * 结果：
         *  Exception in thread "main" java.lang.OutOfMemoryError
         *  at java.base/jdk.internal.misc.Unsafe.allocateMemory(Unsafe.java:616)
         *  at jdk.unsupported/sun.misc.Unsafe.allocateMemory(Unsafe .java:463)
         *  at com.githup.bigminions.jvm.s2.OutOfMemoryErrorTest$DirectMemoryOut.main(OutOfMemoryErrorTest.java:127)
         * @param args
         * @throws IllegalAccessException
         */
        public static void main(String[] args) throws IllegalAccessException {
            Field unsafeField = Unsafe.class.getDeclaredFields()[0];
            unsafeField.setAccessible(true);
            Unsafe unsafe = (Unsafe) unsafeField.get(null);
            while (true) {
                unsafe.allocateMemory(1024 * 1024);
            }
        }
    }
}
