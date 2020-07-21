package com.im.sky.netty.test.test2.error;

import java.io.Serializable;

/**
 * @author jiangchangwei
 * @date 2020-6-23 下午 5:02
 **/
public class JoyCodecException extends RpcException implements Serializable {

    public JoyCodecException() {

    }

    public JoyCodecException(String msg) {
        super(msg);
    }

    public JoyCodecException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
}
