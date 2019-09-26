package com.im.sky.mybatis;

import com.im.sky.mybatis.domain.Person;
import com.im.sky.spring.bean.People;
import org.apache.ibatis.builder.SqlSourceBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: jiangcw
 * @Date: 2019-9-16 下午 8:11
 * @Version 1.0
 */
public class Test {

    private static SqlSession sqlSession;

    static {
        try {
            InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(in);
            sqlSession = sessionFactory.openSession(true);
        }catch (Exception e) {}
    }

    public static void main(String[] args) throws Exception{
        GenericDao dao = sqlSession.getMapper(GenericDao.class);
        Map<String,Object> map = new HashMap<>();
        map.put("id", 7);
        map.put("age", 1521);
//        dao.insert(map);
//        selectOne();
//        insert();
//        update();
//        delete();
        selectOnePerson();
    }

    private static void selectOne() {
        Object o = sqlSession.selectOne("com.im.sky.mybatis.GenericDao.findAnyOne");
        System.out.println(o);
    }

    private static void insert() {
        Map<String,Object> map = new HashMap<>();
        map.put("id", 7);
        map.put("age", 1521);
        int rows = sqlSession.insert("com.im.sky.mybatis.GenericDao.insert", map);
        System.out.println(rows);
    }

    private static void update() {
        Map<String,Object> map = new HashMap<>();
        map.put("id", 3);
        map.put("age", 1521);
        int rows = sqlSession.update("com.im.sky.mybatis.GenericDao.update", map);
        System.out.println(rows);
    }

    private static void delete() {
        Map<String,Object> map = new HashMap<>();
        map.put("id", 3);
        map.put("age", 1521);
        int rows = sqlSession.delete("com.im.sky.mybatis.GenericDao.delete", map);
        System.out.println(rows);
    }

    private static void selectOnePerson() {
        Map<String,Object> map = new HashMap<>();
        map.put("id", 4);
        map.put("age", 1521);
        while (true) {
            Person p = sqlSession.selectOne("com.im.sky.mybatis.GenericDao.selectOnlyPerson", map);
            System.out.println(p.getAge());
            try{
                Thread.sleep(5000);
            }catch (Exception e) {}
        }
    }
}
