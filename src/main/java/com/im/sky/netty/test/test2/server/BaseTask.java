package com.im.sky.netty.test.test2.server;

import java.util.Comparator;

/**
 * @author jiangchangwei
 * @date 2020-6-23 下午 4:27
 **/
public abstract class BaseTask implements Runnable, Comparator<BaseTask> {

    private int priority;

    private long timeout;

    @Override
    public void run() {
        doRun();
    }

    protected abstract void doRun();

    @Override
    public int compare(BaseTask o1, BaseTask o2) {
        return o1.getPriority() - o2.getPriority();
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseTask baseTask = (BaseTask) o;
        return priority == baseTask.priority;
    }

    @Override
    public int hashCode() {
       return priority;
    }
}
