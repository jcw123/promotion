package com.im.sky.spring.bootstrap;

import com.im.sky.spring.model.People;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author jiangchangwei
 * @date 2020-9-15 下午 7:55
 **/
public class Bootstrap1 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.im.sky.spring");
        People people = applicationContext.getBean(People.class);
        people.say();
    }
}
