package com.im.sky.tomcat.diy.catalina;

import com.im.sky.tomcat.diy.Container;

import java.util.Set;

/**
 * @author jiangchangwei
 * @date 2020-9-25 下午 4:47
 **/
public interface Pipeline extends Contained {

    Valve getBasic();

    void setBasic(Valve valve);

    void addValve(Valve valve);

    Valve[] getValves();

    void removeValve(Valve valve);

    Valve getFirst();

    boolean isAsyncSupported();

    void findNonAsyncValves(Set<String> result);
}
