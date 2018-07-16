package com.githup.bigminions.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

public class CustomAnnotationDemo {

    @Target({ElementType.FIELD, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Hello {
        String value() default "";
    }

    @Hello("world")
    public static void main(String[] args) throws NoSuchMethodException {
        Class cls = CustomAnnotationDemo.class;
        Method method = cls.getMethod("main", String[].class);
        Hello hello = method.getAnnotation(Hello.class);
        System.out.println(hello.value());
    }
}
