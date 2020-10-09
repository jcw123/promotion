package com.im.sky.tomcat.diy;

import com.im.sky.tomcat.diy.catalina.LifecycleException;
import com.im.sky.tomcat.diy.catalina.LifecycleState;

/**
 * @author jiangchangwei
 * @date 2020-9-21 下午 8:38
 **/
public abstract class LifecycleBase implements Lifecycle {

    private volatile LifecycleState state = LifecycleState.NEW;

    @Override
    public void start() throws LifecycleException {
        startInternal();
    }

    @Override
    public void stop() throws LifecycleException {
        stopInternal();
    }

    @Override
    public void destroy() throws LifecycleException {
        destroyInternal();
    }

    protected abstract void startInternal() throws LifecycleException;

    protected abstract void stopInternal() throws LifecycleException;

    protected abstract void destroyInternal() throws LifecycleException;

    @Override
    public LifecycleState getState() {
        return state;
    }

    protected synchronized void setState(LifecycleState state) throws LifecycleException {
        setStateInternal(state);
    }

    private synchronized void setStateInternal(LifecycleState state)
            throws LifecycleException {
        this.state = state;
    }
}
