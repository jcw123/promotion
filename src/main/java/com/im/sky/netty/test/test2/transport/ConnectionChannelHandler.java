package com.im.sky.netty.test.test2.transport;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jiangchangwei
 * @date 2020-6-23 下午 6:37
 **/
@ChannelHandler.Sharable
public class ConnectionChannelHandler extends ChannelInboundHandlerAdapter {

    private final ServerTransportConfig transportConfig;

    public ConnectionChannelHandler(ServerTransportConfig serverTransportConfig) {
        this.transportConfig = serverTransportConfig;
    }

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        int now = counter.incrementAndGet();
        if(now > transportConfig.getMaxConnection()) {
            ctx.channel().close();
        }
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        counter.decrementAndGet();
    }
}
