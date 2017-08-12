package com.githup.bigminons.mockito;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;

/**
 * Created by daren on 2017/5/6.
 * reference from https://dzone.com/refcardz/mockito
 */
public class MockitoDemo {

    @Mock
    private Map<String, String> map;

    /**
     * monit a real object
     */
    @Spy
    private Map<String, String> spyMap = new HashMap<>();

    @Before
    public void beforeMock() {
        /**
         * when use annotation : @Mock
         * the class must be init
         */
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testList() {
        List mockList = mock(List.class);
        mockList.add("one");
        mockList.add("two");
        when(mockList.get(0)).thenReturn("three");
        String str = (String) mockList.get(0);
        Assert.assertEquals("three", str);
    }

    @Test
    public void testMap() {
        map.put("one", "1");
        map.put("two", "2");
        String str = map.get("one");
        Assert.assertEquals(null, str);
        when(map.get("one")).then((obj) -> {
            System.out.println(Arrays.toString(obj.getArguments()));;
            return String.valueOf(System.currentTimeMillis());
        });
        System.out.println(map.get("one"));
        System.out.println(map.size());
    }


    @Test
    public void testSpyMap() {
        spyMap.put("one", "1");
        spyMap.put("two", "2");
        String str = spyMap.get("one");
        Assert.assertEquals("1", str);
        when(spyMap.get("one")).then((obj) -> {
            System.out.println(Arrays.toString(obj.getArguments()));;
            return String.valueOf(System.currentTimeMillis());
        });
        System.out.println(spyMap.get("one"));
        System.out.println(spyMap.size());
    }

    @Test
    public void testGivenAndWhen() {
        when(map.get("one")).thenReturn("1");
        given(map.get("two")).willReturn("2");
        System.out.println(map.get("one"));
        System.out.println(map.get("two"));
    }

    @Test
    public void testMatch() {
        when(map.get(contains("key-"))).thenReturn("value");
        System.out.println(map.get("key-one"));
        System.out.println(map.get("one"));
    }

    @Test
    public void testMulti() {
        when(map.get("one")).thenReturn("1", "$1", "￥1");
        Assert.assertEquals("1", map.get("one"));
        Assert.assertEquals("$1", map.get("one"));
        Assert.assertEquals("￥1", map.get("one"));
    }

    /**
     * If the method return void
     */
    @Test
    public void testVoid() {
        doAnswer((obj) -> {
            System.out.println("doing something : doAnswer");
            return null;
        }).when(map).clear();
        System.out.println("I will clean some thing");
        map.clear();
    }

    @Test
    public void verfiy() {
        verify(map, never()).entrySet();
//        next stagement will be error and cna not be catch
//        verify(map, times(2)).entrySet();

//        no interaction with any method of the given mock
//        if I add :
//           map.get("one");
//        will be error
        verifyZeroInteractions(map);

    }
}
