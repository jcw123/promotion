package com.im.sky.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author jiangchangwei
 * @since 2024/12/20
 */
public class PeopleAdvice {

    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("开始进入切面");
        Object o = joinPoint.proceed();
        System.out.println("结束接入切面");
        return o;
    }
}
