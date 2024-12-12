package com.im.sky.test;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

/**
 * @author jiangchangwei
 * @since 2024/11/9
 */
public class JsonCompareTest {

    public static void main(String[] args) throws Exception {
        Map<String, Object> map1 = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        map2.put("3", "1");
        map1.put("name", map2);
        String json1 = JSON.toJSONString(map1);
        String json2 = "{\"name\": \"Jane\", \"age\": 25, \"country\": \"USA\"}";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node1 = mapper.readTree(json1);
        JsonNode node2 = mapper.readTree(json2);

        Set<String> keys1 = new HashSet<>();
        Set<String> keys2 = new HashSet<>();
        extractKeys(node1, keys1);
        extractKeys(node2, keys2);

        System.out.println("Keys in JSON 1 but not in JSON 2: " + difference(keys1, keys2));
        System.out.println("Keys in JSON 2 but not in JSON 1: " + difference(keys2, keys1));
    }

    private static void extractKeys(JsonNode node, Set<String> keys) {
        if(node.isValueNode()) {
            return;
        }
        if(node.isObject()) {
            Iterator<String> iterator =  node.fieldNames();
            while(iterator.hasNext()) {
                String key = iterator.next();
                keys.add(key);
                JsonNode subNode = node.get(key);
                extractKeys(subNode, keys);
            }
        }
    }

    private static Set<String> difference(Set<String> set1, Set<String> set2) {
        Set<String> diff = new HashSet<>(set1);
        diff.removeAll(set2);
        return diff;
    }
}
