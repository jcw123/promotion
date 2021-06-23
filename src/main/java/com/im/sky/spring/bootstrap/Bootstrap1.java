package com.im.sky.spring.bootstrap;

import com.im.sky.spring.bean.IPeople;
import com.im.sky.spring.bean.People;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jiangchangwei
 * @date 2020-9-15 下午 7:55
 **/
public class Bootstrap1 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config.xml");
        IPeople people = applicationContext.getBean(IPeople.class);
        people.say();
    }
}
