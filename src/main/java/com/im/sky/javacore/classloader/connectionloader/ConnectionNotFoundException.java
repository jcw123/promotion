package com.im.sky.javacore.classloader.connectionloader;

/**
 * @author jiangchangwei
 * @date 2020-9-21 下午 4:28
 **/
public class ConnectionNotFoundException extends RuntimeException {

    public ConnectionNotFoundException() {
        super();
    }

    public ConnectionNotFoundException(String msg) {
        super(msg);
    }

    public ConnectionNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
