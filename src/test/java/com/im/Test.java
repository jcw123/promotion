package com.im;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author jiangchangwei
 * @date 2019-11-18 下午 4:49
 **/
public class Test {

    @org.junit.Test
    public void test() {
        List<Integer> list = new ArrayList<>(0);
        Integer[] m = list.toArray(new Integer[1]);
        System.out.println(m.length);
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

    private static

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

}
