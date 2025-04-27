package com.im.sky.mybatis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author jiangchangwei
 * @since 2024/12/24
 */
public class MybatisTest {

    @Test
    public void test() {
        Set<Integer> set1 = new HashSet<>();
    }

    @Test
    public void test1() {
        String s = " ";
        JSONObject jsonObject = JSON.parseObject(s);
        System.out.println(jsonObject == null);
    }
}
