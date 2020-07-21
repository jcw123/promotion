package com.im.sky.netty.test.test1.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.net.SocketAddress;

/**
 * @author jiangchangwei
 * @date 2020-6-11 下午 10:03
 **/
public class ClientOutHandler extends ChannelOutboundHandlerAdapter {

    private String clsName = ClientOutHandler.class.getSimpleName();

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        print("bind");
        super.bind(ctx, localAddress, promise);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        print("connect");
        super.connect(ctx, remoteAddress, localAddress, promise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        print("disconnect");
        super.disconnect(ctx, promise);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        print("close");
        super.close(ctx, promise);
    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        print("deregister");
        super.deregister(ctx, promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        print("read");
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        print("write");
        super.write(ctx, msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        print("flush");
        super.flush(ctx);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        print("handlerAdded");
        super.handlerAdded(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        print("handlerRemoved");
        super.handlerRemoved(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        print("exceptionCaught");
        super.exceptionCaught(ctx, cause);
    }

    private void print(String msg) {
        System.out.println(clsName + ":" + msg);
    }
}
