package com.im.sky.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @Author: jiangcw
 * @Date: 2019-9-14 上午 10:40
 * @Version 1.0
 */
@Service
@Lazy(value = true)
public class People implements ApplicationContextAware {

    @Value("${lover:zh}")
    private String lover;

    private static String name;

    @Value("${people.name}")
    public void setName(String name) {
        People.name = name;
        System.out.println("name:" + name);
    }

    public String getName() {
        return name;
    }

    public void say() {
        System.out.println("说人话");
    }

    public void cry() {
        System.out.println("痛苦才会哭");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        applicationContext.getBean(People.class).say();
    }
}
