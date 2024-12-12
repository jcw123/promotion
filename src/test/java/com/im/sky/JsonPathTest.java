package com.im.sky;

import com.alibaba.fastjson.JSON;
import com.jayway.jsonpath.*;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author jiangchangwei
 * @since 2024/11/8
 */
public class JsonPathTest {

    @Test
    public void test() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "tom");
        map.put("age", 18);
        ReadContext readContext = JsonPath.parse(JSON.toJSONString(map));
        String name = readContext.read("$.name");
        System.out.println(name);
    }

    @Test
    public void test2() {
        String jsonString = "{"
                + "\"name\": \"John\","
                + "\"age\": 30,"
                + "\"address\": {"
                + "    \"street\": \"123 Main St\","
                + "    \"city\": \"New York\""
                + "},"
                + "\"phones\": [\"123-456-7890\", \"987-654-3210\"]"
                + "}";

        ReadContext ctx = JsonPath.parse(jsonString);
        Object o = ctx.json();
        String s = ctx.read("$.phones[1]");
        System.out.println(s);
    }
}
