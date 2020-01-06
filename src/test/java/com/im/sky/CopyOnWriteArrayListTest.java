package com.im.sky;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jiangchangwei
 * @date 2020-1-3 下午 2:47
 **/
public class CopyOnWriteArrayListTest {

    /**
     * 线程安全的arraylist
     */
    @Test
    public void testCopyOnWriteArrayList() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>(new Integer[]{3,1,2});
        list.addIfAbsent(1);
        list.add(1,4);
        System.out.println(list.size());
        System.out.println(JSON.toJSONString(list));
    }
}
