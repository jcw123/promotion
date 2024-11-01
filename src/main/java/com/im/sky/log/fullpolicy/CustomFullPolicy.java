package com.im.sky.log.fullpolicy;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.async.DiscardingAsyncQueueFullPolicy;
import org.apache.logging.log4j.core.async.EventRoute;

public class CustomFullPolicy extends DiscardingAsyncQueueFullPolicy {

    public CustomFullPolicy(Level thresholdLevel) {
        super(thresholdLevel);
    }

    @Override
    public EventRoute getRoute(long backgroundThreadId, Level level) {
        System.out.println("队列满，被丢弃了");
        return super.getRoute(backgroundThreadId, level);
    }


}
