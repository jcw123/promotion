package com.im.sky.spring.bootstrap;

import com.im.sky.spring.bean.IPeople;
import com.im.sky.spring.bean.InnerBeanInject;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jiangchangwei
 * @date 2020-9-15 下午 7:55
 **/
public class Bootstrap1 {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-aop-test.xml");
        IPeople people = applicationContext.getBean(IPeople.class);
        people.cry();
    }
}
