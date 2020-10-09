package com.im.sky.tomcat.diy.catalina;

import com.im.sky.tomcat.diy.Container;

/**
 * @author jiangchangwei
 * @date 2020-9-22 下午 8:48
 **/
public interface Contained {

    Container getContainer();

    void setContainer(Container container);
}
