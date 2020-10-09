package com.im.sky.tomcat.diy.catalina;

import com.im.sky.tomcat.diy.Container;

import javax.servlet.Servlet;

/**
 * @author jiangchangwei
 * @date 2020-9-25 下午 4:42
 **/
public interface Wrapper extends Container {

    void setServlet(Servlet servlet);

    Servlet getServlet();
}
