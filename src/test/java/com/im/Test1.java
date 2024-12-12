package com.im;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-04-21 20:00
 **/
public class Test1 {

    @Test
    public void test1(){
        String s = "11";
        System.out.println(JSON.toJSONString(s));
    }

    @Test
    public void test2() {
        int m = 11;
        System.out.println(JSON.toJSONString(m));
    }

    @Test
    public void test3() throws Exception {
        String s = new ObjectMapper().writeValueAsString("11");
        s = new ObjectMapper().writeValueAsString(s);
        System.out.println(s);
    }
}
