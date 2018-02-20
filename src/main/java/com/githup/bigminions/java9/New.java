package com.githup.bigminions.java9;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.System.Logger.Level.INFO;

/**
 * java 9 部分新特性使用
 */
public class New {

    /**
     * 日志API : 可以保证JDK和应用使用同样的日志实现
     */
    private static final System.Logger LOGGER = System.getLogger("Main");

    static class Person {

        private String name;
        private int gender;
        private LocalDate birthday;
        Person () {}

        Person (String name, int gender, LocalDate birthday) {
            this.name = name;
            this.gender = gender;
            this.birthday = birthday;
        }

    }

    private static Map<Long, Person> personMap;
    private static List<Person> personList;

    static {

        /**
         * Map, List, Set 中新增加的of方法，用来创建不可变的Map，List或set
         */
        personMap = Map.of(
                1L, new Person("Foo", 1, LocalDate.of(2000, 1, 2)),
                2L, new Person("Bar", 0, LocalDate.of(2001, 12, 30)),
                3L, new Person("Tony", 0, LocalDate.of(1999, 12, 30)),
                4L, new Person("Tom", 0, LocalDate.of(1998, 11, 30))
        );
        personList = personMap.values().stream().collect(Collectors.toList());
        try {
            personMap.put(4L, new Person());
        } catch (UnsupportedOperationException e) {
            LOGGER.log(INFO, "Get exception because map is immutable", e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("sum cnt : " + personMap.size());

        /**
         * stream 新增 dropWhile method 丢弃从开头开始就满足断言的元素直至第一个不满足断言的元素
         * 因此排序的与否，降序或升序都会对结果有影响
         */
        long size1 = personList.stream().dropWhile(person ->
                person.birthday.isBefore(LocalDate.of(2000, 1, 1))
        ).count();

        long size2 = personList.stream().sorted((p1, p2) ->
                p1.birthday.isAfter(p2.birthday) ? 1 : -1
        ).dropWhile(person ->
                person.birthday.isBefore(LocalDate.of(2000, 1, 1))
        ).count();

        /**
         * stream 新增 takeWhile method 提取从开头开始就满足断言的元素直至第一个不满足断言的元素
         * 因此排序的与否，降序或升序都会对结果有影响
         */
        long size3 = personList.stream().takeWhile(person ->
                person.birthday.isAfter(LocalDate.of(2000, 1, 1))
        ).count();

        long size4 = personList.stream().sorted((p1, p2) ->
                p2.birthday.isAfter(p1.birthday) ? 1 : -1
        ).takeWhile(person ->
                person.birthday.isAfter(LocalDate.of(2000, 1, 1))
        ).count();


        System.out.println("size1 = " + size1);
        System.out.println("size2 = " + size2);
        System.out.println("size3 = " + size3);
        System.out.println("size4 = " + size4);

        /**
         * Optional 类新增 ifPresentOrElse method
         */
        Optional.empty().ifPresentOrElse(o -> {
            System.out.println("NOT EMPTY");
        }, () -> {
            System.out.println("EMPTY");
        });

        /**
         * Optional 类新增 or method
         */
        System.out.println(Optional.empty().or(() -> Optional.of("EMPTY")).get());
    }
}
