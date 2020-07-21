package com.im.sky.netty.test.test2.server;

import io.netty.channel.Channel;

/**
 * @author jiangchangwei
 * @date 2020-6-23 下午 3:31
 **/
public interface ServerHandler {

    void registerProcessor(String instanceName, Invoker invoker);

    void unregisterProcessor(String instanceName);

    void handlerRequest(Channel channel, Object requestMsg);

    void shutdown();
}
