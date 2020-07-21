package com.im.sky.netty.test.test2.error;

import com.im.sky.netty.test.test2.msg.MessageHeader;

import java.io.Serializable;

/**
 * @author jiangchangwei
 * @date 2020-6-22 下午 6:50
 **/
public class RpcException extends RuntimeException implements Serializable {

    protected String errorMsg;

    private transient MessageHeader msgHeader;

    // 需要序列化支持
    protected RpcException() {
    }


    public RpcException(MessageHeader header, Throwable e) {
        super(e);
        this.msgHeader = header;

    }

    public RpcException(MessageHeader header, String errorMsg) {
        super(errorMsg);
        this.msgHeader = header;
        this.errorMsg = errorMsg;
    }

    protected RpcException(Throwable e) {
        super(e);
    }

    public RpcException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public RpcException(String errorMsg, Throwable e) {
        super(errorMsg, e);
        this.errorMsg = errorMsg;
    }

    public MessageHeader getMsgHeader() {
        return msgHeader;
    }

    public void setMsgHeader(MessageHeader msgHeader) {
        this.msgHeader = msgHeader;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


    public String toString() {
        String s = getClass().getName();
        String message = this.errorMsg;
        return (message != null) ? (s + ": " + message) : s;
    }
}
