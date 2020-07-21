package com.im.sky.netty.test.test2.transport;

import com.im.sky.netty.test.test2.codec.java.JavaDecoder;
import com.im.sky.netty.test.test2.codec.java.JavaEncoder;
import com.im.sky.netty.test.test2.codec.joy.JoyDecoder;
import com.im.sky.netty.test.test2.codec.joy.JoyEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author jiangchangwei
 * @date 2020-6-23 下午 3:27
 **/
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private ServerChannelHandler serverChannelHandler;

    private ConnectionChannelHandler connectionChannelHandler;

    private ServerTransportConfig serverTransportConfig;

    public ServerChannelInitializer(ServerTransportConfig serverTransportConfig) {
        this.serverTransportConfig = serverTransportConfig;
        this.serverChannelHandler = new ServerChannelHandler(serverTransportConfig);
        this.connectionChannelHandler = new ConnectionChannelHandler(serverTransportConfig);
    }


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(connectionChannelHandler)
                .addLast(new JoyDecoder(serverTransportConfig.getPayload()))
                .addLast(new JoyEncoder())
                .addLast(serverChannelHandler);
    }
}
