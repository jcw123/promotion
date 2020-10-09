package com.im.sky.tomcat.diy.catalina;

import com.im.sky.tomcat.diy.catalina.connector.Request;
import com.im.sky.tomcat.diy.catalina.connector.Response;

/**
 * @author jiangchangwei
 * @date 2020-9-21 下午 8:53
 **/
public interface Valve {

    Valve getNext();

    void setNext(Valve valve);

    void invoke(Request request, Response response);

    boolean isAsyncSupported();
}
