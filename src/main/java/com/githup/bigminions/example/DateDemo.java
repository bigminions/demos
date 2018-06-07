package com.githup.bigminions.example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Created by daren on 2018/6/7.
 */
public class DateDemo {

    public static void main(String[] args) {
        String date = "20180605";
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyyMMdd"));
        long diff = ChronoUnit.DAYS.between(localDate, LocalDate.now());
        System.out.println(diff);
    }
}
