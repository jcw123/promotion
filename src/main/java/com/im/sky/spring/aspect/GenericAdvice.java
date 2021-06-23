package com.im.sky.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @Author: jiangcw
 * @Date: 2019-9-14 下午 5:26
 * @Version 1.0
 */
public class GenericAdvice {

    public void before() {
        System.out.println("before advice");
    }

    public void after() {
        System.out.println("after advice");
    }

    public void iReturn() {
        System.out.println("return advice");
    }

    public void throwsError() {
        System.out.println("throws advice");
    }

    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around advice for before");
        Object o = pjp.proceed(pjp.getArgs());
        System.out.println("declaringType:" + pjp.getSignature().getDeclaringType());
        System.out.println("delaringName:" + pjp.getSignature().getDeclaringTypeName());
        System.out.println("around advice for after");
        return o;
    }
}
