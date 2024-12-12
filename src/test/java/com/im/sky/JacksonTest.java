package com.im.sky;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jayway.jsonpath.JsonPath;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;


@RunWith(JUnit4.class)
public class JacksonTest {

    XmlMapper objectMapper;

    @Before
    public void before() {
         objectMapper = new XmlMapper();
    }


    @Test
    public void test() throws Exception{
        MyObject myObject = new MyObject();
        myObject.setName("1");
        String s = objectMapper.writeValueAsString(myObject);
        System.out.println(s);
    }

    @Test
    public void test2() throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("name", "1");
        map.put("age", 20);
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        map.put("birthday", list1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "2");
        map2.put("age1", 30);
        List<String> list2 = new ArrayList<>();
        list2.add("1");
        list2.add("3");
        map2.put("birthday", list2);
        List<Item> items = compare(map, map2);
        System.out.println(new ObjectMapper().writeValueAsString(items));

    }

    private List<Item> compare(Object o1, Object o2) throws Exception {
        List<Item> items = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode j1 = objectMapper.valueToTree(o1);
        JsonNode j2 = objectMapper.valueToTree(o2);
        compare(j1, j2, "$", items);
        return items;
    }

    private void compare(JsonNode j1, JsonNode j2, String path, List<Item> items) throws Exception {
        if(j1 == null && j2 == null) {
            return;
        }
        if(j1 == null || j2 == null) {
            Item item = new Item();
            item.setPath(path);
            item.setSame(false);
            item.setSrcValue(Optional.ofNullable(j1).map(JsonNode::asText).orElse(null));
            item.setDstValue(Optional.ofNullable(j2).map(JsonNode::asText).orElse(null));
            items.add(item);
            return;
        }
        if(j1.isValueNode() && j2.isValueNode()) {
            if(!Objects.equals(j1.asText(), j2.asText())) {
                Item item = new Item();
                item.setPath(path);
                item.setSame(false);
                item.setSrcValue(j1.asText());
                item.setDstValue(j2.asText());
                items.add(item);
            }
            return;
        }
        if(j1.isArray() && j2.isArray()) {
            int size1 = j1.size();
            int size2 = j2.size();
            if(size1 != size2) {
                String newPath = path + ".size";
                Item item = new Item()
                        .setPath(newPath)
                        .setSame(false)
                        .setSrcValue(String.valueOf(size1))
                        .setDstValue(String.valueOf(size2));
                items.add(item);
                return;
            }else {
                for(int i = 0; i < size1; i++) {
                    compare(j1.get(i), j2.get(i), path + "[" + i + "]", items);
                }
            }
        }else if(j1.isObject() && j2.isObject()) {
            Set<String> visited = new HashSet<>();
            Iterator<Map.Entry<String, JsonNode>> it = j1.fields();
            while(it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                String key = entry.getKey();
                JsonNode value = entry.getValue();
                if(visited.contains(key)) {
                    continue;
                }
                visited.add(key);
                JsonNode value2 = j2.get(key);
                if(value2 == null) {
                    Item item = new Item()
                            .setPath(path + "." + key)
                            .setSame(false)
                            .setSrcValue(value)
                            .setDstValue(null);
                    items.add(item);
                }else {
                    compare(value2, value, path + "." + key, items);
                }
            }
        }else {
            Item item = new Item()
                    .setSame(false)
                    .setPath(path)
                    .setSrcValue(j1.asText())
                    .setDstValue(j2.asText());
            items.add(item);
        }

    }


    @Data
    @Accessors(chain = true)
    private static class Item {
        private String path;

        private Object srcValue;

        private Object dstValue;

        private boolean same;
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "test")
    public static class MyObject {

        @Setter
        @Getter
        @XmlElement(name = "name")
        private String name;
    }
}
