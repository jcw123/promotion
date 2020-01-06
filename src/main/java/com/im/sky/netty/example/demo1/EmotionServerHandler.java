package com.im.sky.netty.example.demo1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author jiangchangwei
 * @date 2019-12-7 下午 4:53
 **/
public class EmotionServerHandler extends SimpleChannelInboundHandler<EmotionEnums> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EmotionEnums msg) throws Exception {
        String response = Invoker.invoke(msg);
        ctx.writeAndFlush(response);
    }
}
