package com.im.sky.spring.annotation;

import java.lang.annotation.*;

/**
 * @author jiangchangwei
 * @date 2020-9-17 下午 5:51
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RPCBean {

    String name();
}
