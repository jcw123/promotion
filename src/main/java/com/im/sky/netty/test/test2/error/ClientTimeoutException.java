package com.im.sky.netty.test.test2.error;

/**
 * @author jiangchangwei
 * @date 2020-6-22 下午 7:55
 **/
public class ClientTimeoutException extends RpcException {

    public  ClientTimeoutException() {

    }

    public ClientTimeoutException(String msg) {
        super(msg);
    }

    public ClientTimeoutException(String msg, Throwable t) {
        super(msg, t);
    }
}
