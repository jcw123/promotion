package com.im.sky;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.google.gson.Gson;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2024-07-16 21:25
 **/
public class JSONTest {

    @Test
    public void test() {
        A a = new A();
        a.getAa();
        System.out.println(new Gson().toJson(a));
    }


    public static class A {

        @JSONField(serialize = true)
        private String aa;

        private String getAa() {
            return aa;
        }
    }

    @Test
    public void test2() {
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(JSON.toJSONString(ldt));
    }

    @Test
    public void test3() {
        Date date = new Date();
        System.out.println(JSON.toJSONString(date));
    }

    @Test
    public void test4() {
        Map<Integer, Integer> map2 = new HashMap<>();
        map2.put(1, 1);
        System.out.println(JSON.toJSONString(map2));
        String s = "{1:1}";
        Map<Integer, Integer> map = JSON.parseObject(s, new TypeReference<Map<Integer, Integer>>(){});
        System.out.println(map);
    }
}
