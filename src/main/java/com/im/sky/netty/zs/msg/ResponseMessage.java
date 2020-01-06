package com.im.sky.netty.zs.msg;

import com.alibaba.fastjson.JSON;

public class ResponseMessage extends BaseMessage {

    private Object response;

    private Throwable throwable;

    public ResponseMessage(boolean initHeader) {
        super(initHeader);
    }

    public ResponseMessage() {
        super(true);
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
