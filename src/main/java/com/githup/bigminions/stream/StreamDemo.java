package com.githup.bigminions.stream;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by daren on 2017/5/16.
 */
public class StreamDemo {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String testText = "" +
            "I was writing an altcoin pool server in C#. Pool server allows multiple people to mine digital currency together, this was going to be the first pool for a specific kind of digital currency but I encountered a very unusual bug.\n" +
            "\n" +
            "To make my C# application work on linux and windows, I was relying on mono. The server basically had to accept http requests by the client and respond to them. Now at the time I was working on this, there were 2 possible types of client known as  a miner. Both of them were pretty similar in their functioning but had a completely different code base.\n" +
            "\n" +
            "All was going well with the server until I realized that while running the server on Linux, one of the client was disconnecting after every request. The same code on Windows worked perfectly fine and on Linux worked fine with one miner but not with the another. And thus began a seemingly endless journey to find a bug that presents itself in very specific situation.\n" +
            "\n" +
            "I do not think of myself as a great programmer and as such the first thing I thought of was a mistake in my own programming. I went through stepping through every line of the code, reduced it down to the simplest possible version  and the bug was still there. After doing this I realized that I actually did not have any clue of the reason the second miner was disconnecting in fact.\n" +
            "\n" +
            "I shot up a packet sniffer and looked at the conversation between the miner and the server and well here also, nothing looked out of the ordinary. I gave up on checking this too.\n" +
            "\n" +
            "Next up, going through the miner's code. Fire up github and look through some of the ugliest, worst-written code I've ever seen to find a reference to a network library that is incomparably harder to read. And once again I thought to myself, I might as well leave my dreams of completing this server than read this code. Then it hit me that I could easily check whether the library was at fault which in turn would mean the miner was at fault or it was my code that caused the problem. I wrote up a simple html server in C++ and connected the miner to this server, and quite to my happiness it worked.\n" +
            "\n" +
            "So I don't have to go through that messy code, but again the problem is in my own code then. I went back to analyzing the packets again. I was already in a state of desperation but I suddenly realized upon seeing the conversation this time that the TCP socket used for http conversation was not being closed by the server when it ran on Windows and it was being closed after every packet on linux. Now one of the miner did not have any problem with this but the other one, took this as some sort of disconnect signal and it decided to reconnect again and start from beginning again, every time.\n" +
            "\n" +
            "Back to hunting through my code once again, after going through my own code for several hours I was confident that it wasn't my mistake. Now the only thing that could've been wrong was the Mono framework. I *ripped out* the entire html server code from the latest version of Mono and created my own small server from it, compiled this on Windows and to my great happiness, or worry, depends upon how you take it. The bug was in the Mono  code. \n" +
            "\n" +
            "So I post about it on Stack Overflow, wait for a few hours and no reply. Go stepping through huge amounts of code and find that there is weird clause that causes Mono to get rid of the old socket and use a new one whenever the response stream is disposed. So after a crazy hunting spree of about a week all I had to do to fix it was remove one line of code, one line containing a call to a single function. And everything began working again.\n" +
            "\n" +
            "Afterward\n" +
            "\n" +
            "I submitted a bug report to Mono and well the Mono people also at first didn't realize anything was wrong. It got fixed after a followup by adding a single if statement to the original server code. ";

    private List<String> strList;

    @Before
    public void beforeTest() {
        testText = testText.replaceAll("[\n.,]", " ");
        String[] testStr = testText.split(" ");
        logger.info("预处理前单词数量：{}", testStr.length);
        Stream<String> strStream = Arrays.stream(testStr);
        strList = strStream.map(s -> s.replaceAll(" ", "")).filter(s -> !s.isEmpty()).collect(Collectors.toList());
        logger.info("预处理后单词数量：{}", strList.size());
    }

    @Test
    public void testSome() {
        Set<String> set = strList.stream().distinct().collect(Collectors.toSet());
        logger.info("不重复单词数量：{}", set.size());

        String maxStr = strList.stream().max(String::compareTo).get();
        logger.info("最大的单词是：{}", maxStr);

        String longestStr = strList.stream().max((a, b) -> a.length() > b.length() ? 1 : -1).get();
        logger.info("最长的单词是：{}", longestStr);

        Integer charSum = strList.stream().mapToInt(String::length).reduce(Integer::sum).getAsInt();
        logger.info("字母数量是：{}", charSum);

        List<String> ten = strList.stream().limit(10).collect(Collectors.toList());
        logger.info("前十个单词是：{}", ten);

        List<String> endTen = strList.stream().skip(strList.size() - 10).collect(Collectors.toList());
        logger.info("后十个单词是：{}", endTen);

        strList.stream().peek(s -> System.out.print("长度为" + s.length())).peek(s -> System.out.print(" -- ")).forEach(System.out::println);

        String[] sortedTen = strList.stream().distinct().sorted().limit(10).toArray(String[]::new);
        logger.info("排序后前十个单词是：{}", Arrays.toString(sortedTen));
    }

    @Test
    public void testParallel() {
        System.out.println("demo 1 ");
        Stream.of("one", "two", "three", "four", "five").parallel().forEach(System.out::println);
        System.out.println("demo 2 ");
        Stream.of("one", "two", "three", "four", "five").parallel().forEachOrdered(System.out::println);
    }

    @Test
    public void testGenerateStreamByMyself() {
        Random seed = new Random();
        Supplier<Integer> randomSupplier = seed::nextInt;
        List<Integer> randomList = Stream.generate(randomSupplier).limit(10).collect(Collectors.toList());
        logger.info("十个随机数：{}", randomList);
    }

    @Test
    public void testPeek() {
        List<String> list = new ArrayList<>();
        Stream.of("one", "two", "three", "four", "five").peek(System.out::println);
        Stream.of("one", "two", "three", "four", "five").peek(str -> {
            System.out.println(str);
            list.add(str);
        });
        System.out.println(list.size());
    }
}
