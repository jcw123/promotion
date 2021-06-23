package com.im.sky.spring.bootstrap;

import com.im.sky.spring.advice.HelloMethodBeforeAdvice;
import com.im.sky.spring.bean.IPeople;
import com.im.sky.spring.bean.People;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;

public class Bootstrap2 {

    public static void main(String[] args) {
        NameMatchMethodPointcutAdvisor advisor = new NameMatchMethodPointcutAdvisor();
        MethodBeforeAdvice advice = new HelloMethodBeforeAdvice();
        advisor.setMappedNames("say","cry");
        advisor.setAdvice(advice);
        IPeople people = new People();
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(advisor);
        proxyFactory.setTarget(people);
        IPeople iPeople = (IPeople)proxyFactory.getProxy();
        iPeople.say();
        iPeople.cry();
    }
}
