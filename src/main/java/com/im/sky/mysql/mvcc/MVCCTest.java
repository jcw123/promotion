package com.im.sky.mysql.mvcc;

import com.alibaba.fastjson.JSON;
import java.util.Arrays;
import java.util.List;

/**
 * @author jiangchangwei
 * @date 2020-9-16 下午 8:50
 **/
public class MVCCTest {

    public static void main(String[] args) {
        Table table = new Table();
        table.insert(new Row(1, 18, "test1"));
        table.insert(new Row(2, 18, "test2"));
        System.out.println(JSON.toJSONString(table));

        new Thread(() -> {
            try {
                table.update(1L, "test33");
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                List<Row> rows = table.select(Arrays.asList(1L));
                System.out.println(JSON.toJSONString(rows));
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
