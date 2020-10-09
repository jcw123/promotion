package com.im.sky.tomcat.diy;

/**
 * @author jiangchangwei
 * @date 2020-9-21 下午 8:36
 **/
public interface Container extends Lifecycle {

    Container[] findChildren();

    void removeChild(Container child);

    void addChild(Container child);

    Container getParent();

    void setParent(Container container);

    String getName();
}
