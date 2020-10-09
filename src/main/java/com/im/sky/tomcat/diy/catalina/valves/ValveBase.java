package com.im.sky.tomcat.diy.catalina.valves;

import com.im.sky.tomcat.diy.Container;
import com.im.sky.tomcat.diy.LifecycleBase;
import com.im.sky.tomcat.diy.catalina.Contained;
import com.im.sky.tomcat.diy.catalina.Valve;

/**
 * @author jiangchangwei
 * @date 2020-9-25 下午 3:40
 **/
public abstract class ValveBase extends LifecycleBase implements Contained, Valve {

    public ValveBase() {
        this(false);
    }

    public ValveBase(boolean asyncSupported) {
        this.asyncSupported = asyncSupported;
    }

    protected boolean asyncSupported;

    protected Container container = null;

    protected Valve next = null;

    @Override
    public Container getContainer() {
        return container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = container;
    }

    @Override
    public Valve getNext() {
        return next;
    }

    @Override
    public void setNext(Valve valve) {
        this.next = valve;
    }

    @Override
    public boolean isAsyncSupported() {
        return asyncSupported;
    }
}
