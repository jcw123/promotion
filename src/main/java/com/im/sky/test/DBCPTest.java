package com.im.sky.test;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.PoolingConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @author jiangchangwei
 * @program promotion
 * @description
 * @date 2023-04-25 23:11
 **/
public class DBCPTest {

    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        BasicDataSource basicDataSource = new BasicDataSource();
        Connection connection = null;
        try {
            basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
            basicDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test?socketTimeout=3000");
            basicDataSource.setUsername("root");
            basicDataSource.setPassword("jcw123");
            basicDataSource.setMaxWaitMillis(200);
            basicDataSource.setMaxIdle(2);
            basicDataSource.setMaxTotal(4);
            basicDataSource.setMinIdle(2);
            connection = basicDataSource.getConnection();
            PreparedStatement psmt = connection.prepareStatement("select sleep(4)");
            psmt.executeQuery();
//            Connection connectio1 = basicDataSource.getConnection();
//            Connection connectio2 = basicDataSource.getConnection();
//            Connection connectio3 = basicDataSource.getConnection();
//            Connection connectio4 = basicDataSource.getConnection();
//            psmt.executeQuery();
//            psmt.close();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("test");
        }finally {
            connection.close();
            System.out.println("M");
        }
    }
}
