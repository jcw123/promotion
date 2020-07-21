package com.im.sky.limit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

/**
 * @author jiangchangwei
 * @date 2020-5-20 下午 7:24
 **/
public class TimeWindowLimitV2 {

    private static final int slices = 10;

    private static ConcurrentMap<String, List<Counter>> map = new ConcurrentHashMap<>();

    private static boolean isLimit(String key) {
        List<Counter> list = map.computeIfAbsent(key,  k -> {
            ArrayList<Counter> tmpList = new ArrayList<>();
            for(int i = 0; i < slices; i++) {
                tmpList.add(new Counter());
            }
            return tmpList;
        });
        long time = System.currentTimeMillis();
        int m = (int)time % 1000;
        int index = m / (1000 / slices);
        Counter counter = list.get(index);
        if(counter.count == -1) {
            counter.count++;
            counter.time = time;
        }else {
            if(time - counter.time >= 1000 / slices) {
                counter.count = 0;
                counter.time = time;
            }else {
                counter.count++;
            }
        }
        int count = map.get(key).stream().map(Counter::getCount).reduce(Integer::sum).orElse(0);
        return count > 10;
    }


    private static class Counter {

        int count = -1;

        long time = -1;

        public int getCount() {
            return count;
        }

        public long getTime() {
            return time;
        }
    }
}
