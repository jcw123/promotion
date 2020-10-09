package com.im.sky;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jiangchangwei
 * @date 2019-11-20 下午 12:34
 **/
public class MapTest {

    public static void main(String[] args) {
        final HashMap<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    map.put(UUID.randomUUID().toString(), "");
                }
            }).start();
        }
    }

    @Test
    public void testHashMapSafe() throws InterruptedException {
        Map<String, String> map = new HashMap<>();
        new Thread(()-> {
            while(true) {
                map.put("test2", "1");
//                try {
//                    Thread.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }).start();

        new Thread(() -> {
            while(true) {
                map.put("test", "1");
//                try {
//                    Thread.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }).start();
        Thread.sleep(10000);
    }

    @Test
    public void testHashMap() {
        HashMap<String, String> map = new HashMap<>();
        map.remove("1");
        map.remove("1", "1");
        map.entrySet();
        map.size();
        map.clear();
        map.putAll(new HashMap<>());
        map.put("1", "1");
        map.get("1");
    }

    @Test
    public void testConcurrentHashMap() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>(2);
        map.put("1", "1");
        map.put("2", "1");
        map.put("3", "1");
        map.put("4","1");
        map.put("5", "1");
        map.compute("2", (k, v) -> {return v + 1;});
        System.out.println(map.get("1"));
    }

    @Test
    public void testableSizeFort() {
        int cap = 8;
        System.out.println(tableSizeFor(9));
        byte m = -1;
        byte n = 2;
        System.out.println(m >> 1);
        System.out.println((m >>> 1));
        System.out.println(m);
        System.out.println((m^n));
        System.out.println(m | n);
        System.out.println(m & n);
        System.out.println(~m);
    }

    static final int tableSizeFor(int cap) {
        int MAXIMUM_CAPACITY = 1 << 30;
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
