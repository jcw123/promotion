package com.im.sky.tomcat.diy.catalina.core;

import com.im.sky.tomcat.diy.ContainerBase;
import com.im.sky.tomcat.diy.catalina.LifecycleException;
import com.im.sky.tomcat.diy.catalina.Wrapper;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.Enumeration;

/**
 * @author jiangchangwei
 * @date 2020-9-25 下午 4:46
 **/
public class StandardWrapper extends ContainerBase implements ServletConfig, Wrapper {


    @Override
    public void setServlet(Servlet servlet) {

    }

    @Override
    public Servlet getServlet() {
        return null;
    }

    @Override
    public String getServletName() {
        return null;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public String getInitParameter(String s) {
        return null;
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return null;
    }

}
