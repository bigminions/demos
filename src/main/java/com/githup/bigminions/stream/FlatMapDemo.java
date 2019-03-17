package com.githup.bigminions.stream;

import java.util.*;
import java.util.stream.Collectors;

public class FlatMapDemo {

    public static class Student {
        private String name;
        private Set<String> books;

        Student(String name) {
            this.name = name;
        }

        void addBook(String book) {
            if (books == null)
                books = new HashSet<>();
            books.add(book);
        }

        public Set<String> getBooks() {
            return books;
        }

        public void setBooks(Set<String> books) {
            this.books = books;
        }
    }

    public static void main(String[] args) {
        String[][] data = new String[][]{{"a", "b"}, {"c", "d"}, {"e", "f"}};
        Arrays.stream(data).flatMap(Arrays::stream).filter("a"::equals).findFirst().ifPresent(System.out::println);

        Student student1 = new Student("foo");
        student1.addBook("Java 8 in Action");
        student1.addBook("Spring Boot in Action");
        student1.addBook("Effective Java (2nd Edition)");

        Student student2 = new Student("bar");
        student2.addBook("Learning Python, 5th Edition");
        student2.addBook("Effective Java (2nd Edition)");

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        List<String> allBooks = students.stream().map(Student::getBooks).flatMap(Collection::stream).distinct().collect(Collectors.toList());
        allBooks.forEach(System.out::println);
    }
}
