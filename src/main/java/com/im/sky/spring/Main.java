package com.im.sky.spring;

import com.im.sky.spring.bean.People;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: jiangcw
 * @Date: 2019-9-14 上午 10:30
 * @Version 1.0
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-config.xml");
        People people = context.getBean(People.class);
//        people.say();
        System.out.println("name:" + people.getName());
//        Properties properties = System.getProperties();
//        Map<String, String> map = System.getenv();
//        System.out.println("end");
    }
}
