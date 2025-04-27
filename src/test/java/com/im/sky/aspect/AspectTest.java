package com.im.sky.aspect;

import com.im.sky.spring.bean.IPeople;
import com.im.sky.spring.bean.People;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author jiangchangwei
 * @since 2024/12/20
 */
public class AspectTest {

    @Test
    public void testAdvisor() throws Exception {
        Advice advice = new DefaultInterceptor();
        Pointcut pointcut = new DefaultPointcut();
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, advice);
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.addAdvisor(advisor);
        People people = new People();
        proxyFactory.setTarget(people);
        IPeople iPeople = (IPeople) proxyFactory.getProxy();
        iPeople.say();
    }

    private static class DefaultInterceptor implements MethodInterceptor {

        @Nullable
        @Override
        public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
            System.out.println("start interceptor");
            Object res = invocation.proceed();
            System.out.println("end interceptor");
            return res;
        }
    }

    private static class DefaultBeforeAdvice implements MethodBeforeAdvice {
        @Override
        public void before(Method method, Object[] args, Object target) throws Throwable {
            System.out.println("enter before advice");
        }
    }

    private static class DefaultPointcut extends StaticMethodMatcherPointcut {
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return Objects.equals(method.getName(), "cry");
        }
    }

}
