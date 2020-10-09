package com.im.sky.javacore.collection;

import com.alibaba.fastjson.JSON;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author jiangchangwei
 * @date 2020-9-25 下午 6:09
 **/
public class SelfConcurrentHashMap {

    public static void main(String[] args) {
        ConcurrentMap<Key, String> map = new ConcurrentHashMap<>();
        for(int i = 0; i < 100; i++) {
            map.put(new Key("test" + i), "test");
        }
        System.out.println(map.get(new Key("test2")));
        System.out.println(JSON.toJSONString(map));
    }

    private static class Key {
        String key;

        public Key(String key) {
            this.key = key;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key1 = (Key) o;
            return Objects.equals(key, key1.key);
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }
}
