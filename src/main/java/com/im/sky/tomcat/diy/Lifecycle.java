package com.im.sky.tomcat.diy;

import com.im.sky.tomcat.diy.catalina.LifecycleException;
import com.im.sky.tomcat.diy.catalina.LifecycleState;

/**
 * @author jiangchangwei
 * @date 2020-9-21 下午 8:37
 **/
public interface Lifecycle {

    String START_EVENT = "start";

    String STOP_EVENT = "stop";

    void start() throws LifecycleException;

    void stop() throws LifecycleException;

    void destroy() throws LifecycleException;

    LifecycleState getState();

}
