package com.im.sky.spring.annotation;

import java.lang.annotation.*;

/**
 * @author jiangchangwei
 * @date 2020-1-2 下午 5:26
 *
 * 用于直接扫描包进行bean的注册
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BeanScan {
}
