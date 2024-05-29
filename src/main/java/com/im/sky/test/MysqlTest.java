package com.im.sky.test;

import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.ConnectionImpl;

import javax.sql.DataSource;
import javax.xml.transform.Result;
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
//        testKill();
        testSocketTimeout();
    }

    private static void testSocketTimeout() throws Exception {
        Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test?socketTimeout=1000", "root", "jcw123");
        try {
            for(int i = 0; i < 2; i++) {
                System.out.println("test:" + i);
                PreparedStatement statement = con.prepareStatement("select * from t1");
                statement.executeQuery();
                Thread.sleep(12 * 1000);
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(con.isClosed());
        }

    }

    private static void testKill() throws Exception {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "jcw123");
            String sql = "select connection_id()";
            PreparedStatement psmt;
            psmt = con.prepareStatement(sql);
            ResultSet resultSet = psmt.executeQuery();
            resultSet.next();
            String connectionId = resultSet.getString(1);
            System.out.println(connectionId);
            new Thread(() -> {
                try {
                    PreparedStatement sleepSt = con.prepareStatement("select sleep(10)");
                    sleepSt.execute();
                    System.out.println("t1");
                }catch (Exception e) {

                }
            }).start();
            Thread.sleep(2 * 1000);
            try {
                Connection con2 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "jcw123");
                System.out.println("t3");
                psmt = con2.prepareStatement("KILL QUERY ?");
                psmt.setString(1, connectionId);
                psmt.execute();
                System.out.println("t2");
                psmt = con.prepareStatement("select 1");
                System.out.println(psmt.executeQuery().getString(1));
            }catch (Exception e) {
                e.printStackTrace();
            }
        }finally {
            Thread.sleep(1  * 1000);
        }
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
