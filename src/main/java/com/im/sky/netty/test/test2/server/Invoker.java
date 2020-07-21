package com.im.sky.netty.test.test2.server;

import com.im.sky.netty.test.test2.msg.BaseMessage;
import com.im.sky.netty.test.test2.msg.ResponseMessage;

/**
 * @author jiangchangwei
 * @date 2020-6-23 下午 3:30
 **/
public interface Invoker {

    ResponseMessage invoke(BaseMessage msg);
}
