package com.im.sky.netty.test.test2.filter;

import com.im.sky.netty.test.test2.msg.RequestMessage;
import com.im.sky.netty.test.test2.msg.ResponseMessage;

import java.io.Serializable;
import java.util.Map;

/**
 * @author jiangchangwei
 * @date 2020-6-24 下午 3:38
 **/
public abstract class AbstractFilter implements Filter, Serializable, Cloneable {

    private Filter next;

    private Map<String, Object> configContext;

    abstract public ResponseMessage invoke(RequestMessage request);

    public Filter getNext() {
        return next;
    }

    public void setNext(Filter next) {
        this.next = next;
    }

    public Map<String, Object> getConfigContext() {
        return configContext;
    }

    public void setConfigContext(Map<String, Object> configContext) {
        this.configContext = configContext;
    }
}
