package com.im.sky.tomcat.diy.catalina;

import com.im.sky.tomcat.diy.Lifecycle;

/**
 * @author jiangchangwei
 * @date 2020-9-25 下午 3:47
 **/
public enum LifecycleState {

    NEW(true, null),
    START(true, Lifecycle.START_EVENT),
    STOP(true, Lifecycle.STOP_EVENT);

    private final boolean available;

    private final String lifecycleEvent;

    LifecycleState(boolean available, String lifecycleEvent) {
        this.available = available;
        this.lifecycleEvent = lifecycleEvent;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getLifecycleEvent() {
        return lifecycleEvent;
    }
}
