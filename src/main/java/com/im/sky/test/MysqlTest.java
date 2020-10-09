package com.im.sky.test;

import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.ConnectionImpl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiangchangwei
 * @date 2020-8-7 下午 1:53
 **/
public class MysqlTest {

    private static AtomicInteger count = new AtomicInteger();

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        ExecutorService executorService = Executors.newFixedThreadPool(40);
        for(int i = 0; i < 1; i++) {
            executorService.execute(new SqlTask());
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {

        }
        System.out.println("count：" + count.intValue());

    }

    private static class SqlTask implements Runnable {
        @Override
        public void run() {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "jcw123");
                String sql = "select sleep(1) from test1";
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet resultSet = pstmt.executeQuery();
                while (resultSet.next()) {
//                    System.out.println(resultSet.getString(1));
                    try {
                        Thread.sleep(100);
                    }catch (Exception e) {}
                }
            }catch (Exception e) {
            }finally {
                count.incrementAndGet();
            }
        }
    }
}
