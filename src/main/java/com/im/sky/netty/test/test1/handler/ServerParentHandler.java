package com.im.sky.netty.test.test1.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author jiangchangwei
 * @date 2020-6-11 下午 9:15
 **/
@ChannelHandler.Sharable
public class ServerParentHandler extends ChannelInboundHandlerAdapter {

    private String clsName = ServerParentHandler.class.getSimpleName();

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        print("channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        print("channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        print("channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        print("channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        print("channelRead");
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        print("channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        print("userEventTriggered");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        print("channelWritabilityChanged");
        super.channelWritabilityChanged(ctx);
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
