package com.im.sky.netty.test.test2.transport;

/**
 * @author jiangchangwei
 * @date 2020-6-16 上午 9:49
 **/
public interface ClientTransport {

    void reconnect();

    void shutdown();

    ClientTransportConfig getConfig();


}
