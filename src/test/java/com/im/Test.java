package com.im;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.core.Local;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLClassLoader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author jiangchangwei
 * @date 2019-11-18 下午 4:49
 **/
@Slf4j
public class Test {

    class A3 {
        public void say() {
            System.out.println(getClass().getName());
        }
    }

    class B3 extends A3 {

    }

    @org.junit.Test
    public void test20() {
        new B3().say();
    }



    @org.junit.Test
    public void test() {
    }

    public void set(Object a) {
        a = new Object();
    }

    private static class A {}

    private static class AA extends A implements B, BB {
        void getAA() {}

        @Override
        public void getB() {

        }

        @Override
        public void getBB() {

        }
    }

    interface B {
        void getB();
    }

    interface BB {
        void getBB();
    }

    @org.junit.Test
    public void test2() {
        LinkedList<Integer> queue = new LinkedList<>();
        queue.removeFirst();
        Stack<Integer> stack = new Stack<>();
        stack.add(1);
        Deque<Integer> stack1 = new ArrayDeque<>();
    }

    private static String[] toSendPayArray(String sendPay) {
        if(StringUtils.isEmpty(sendPay)) {
            return null;
        }
        int startIndex = 0;
        int len = sendPay.length();
        List<String> list = new ArrayList<>();
        int endIndex;
        while(startIndex < len) {
            endIndex = sendPay.indexOf(',', startIndex);
            if(endIndex == -1) {
                list.add(sendPay.substring(startIndex, len));
                break;
            }else {
                list.add(sendPay.substring(startIndex, endIndex));
                startIndex = endIndex + 1;
            }
        }
        return list.toArray(new String[0]);
    }

    @org.junit.Test
    public void test3() {
       String s = "<str>11111</str>";
        System.out.println(s.indexOf("<//str>", 0));
        System.out.println(s.indexOf("</str>", 0));
    }

    @org.junit.Test
    public void testBit() {
        long a = (long) 1 << 41;
        System.out.println(a / ((long)1000 * 60 * 60 * 24 * 365));
        System.out.println(System.currentTimeMillis() & (~(-1L << 41)));
        Map map = new HashMap();
        byte t = -1;
        System.out.println(t >> 7);
        System.out.println(t >>> 31);
    }

    @org.junit.Test
    public void test4() {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 2);
        map.put(2, 3);
        map.put(3, 4);
        Set<Integer> set = map.keySet();
        set.remove(3);
        map.remove(2);
        System.out.println(JSON.toJSONString(set));
    }

    @org.junit.Test
    public void testCast() {
        int a = 3;
        int b = 4;
        System.out.println((double) (a / b));
    }

    @org.junit.Test
    public void test5() {
        Map<String, Object> map = new HashMap<>();
        map.put("test", new BigDecimal("2.30"));
        System.out.println(JSON.toJSONString(map));
    }

    private void a(List<Long> list) {
        list.forEach(i->{
            System.out.println(i);
        });
        for(Long i : list) {
            System.out.println(i);
        }
    }

    /**
     *  111
     *  011
     *
     *  111
     *  001
     *
     *  111
     */
    @org.junit.Test
    public void test6() {
        int cap = 6;
        int n = cap - 1;
        System.out.println(Integer.MAX_VALUE);
        System.out.println((~(1 << 31)));
        System.out.println(n >>> 1);
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        System.out.println(n);
    }

    @org.junit.Test
    public void test7() {
        Q.builder().a(1).build();
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Q {
        private int a;
    }


    @org.junit.Test
    public void test10() throws Exception {
        Class.forName("com.im.Test1", true,ClassLoader.getSystemClassLoader());
    }

    @org.junit.Test
    public void test11() {
        Map<String, String> map = new HashMap<>();
        map.put("test", "ss33");
        System.out.println(JSON.toJSONString(map));
    }

    @org.junit.Test
    public void test12() {
        int[] arr = new int[]{3, 9, 10, 11, 12, 15};
        Arrays.binarySearch(arr, 10);
        String ss = "abcdef";
        String s = "cd";
        System.out.println(ss.contains(s));
    }

    @org.junit.Test
    public void test13() {
        System.out.println(a() == null);
    }

    public static class TestLoad {

    }


    private Void a() {
        return null;
    }


    public static class T1 {
        static {
            System.out.println("start");
            T2 t2 = new T2();
            System.out.println("end");
        }
    }

    public static class T2 {

    }

    @org.junit.Test
    public void test30() {
        StringJoiner sj = new StringJoiner("|");
        String key = "test";
        JSONArray vJ = new JSONArray();
        Map<String, String> map = new HashMap<>();
        map.put("1", "1");
        vJ.add(map);
        JSONArray iJ = new JSONArray();
        iJ.add(iJ);
        sj.add(vJ.toJSONString());
        sj.add(iJ.toJSONString());
        System.out.println(sj.toString());
    }

    @org.junit.Test
    public void testThrowable() {
        try {
            throwException();
        }catch (Throwable e) {
            e.fillInStackTrace();
            StackTraceElement[] elements = e.getStackTrace();
            for(StackTraceElement element : elements) {
                System.out.println(element);
            }
        }
    }

    private void throwException() {
        try {
            throw new RuntimeException("抛异常了");
        }catch (Exception e) {
            throw e;
        }
    }

    @org.junit.Test
    public void testDateFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String s = LocalDateTime.parse("2022-12-12 00:00:00", formatter).format(formatter);
        System.out.println(s);
        LocalDateTime dateTime = LocalDateTime.of(2023, 12, 11, 11, 11, 11);
        System.out.println(dateTime.format(formatter));
    }
}
