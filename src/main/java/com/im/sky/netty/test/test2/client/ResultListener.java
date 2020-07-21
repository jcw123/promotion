package com.im.sky.netty.test.test2.client;

/**
 * @author jiangchangwei
 * @date 2020-6-18 下午 4:12
 **/
public interface ResultListener {

    boolean operationComplete(MsgFuture future);
}
