package com.im.sky.netty.example.discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author jiangchangwei
 * @date 2019-12-5 下午 10:22
 **/
public class DiscardClientHandler extends SimpleChannelInboundHandler<Object> {

    private ByteBuf content;

    private ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       this.ctx = ctx;
       content = ctx.alloc().directBuffer(DiscardClient.SIZE).writeZero(DiscardClient.SIZE);
       ctx.writeAndFlush(content);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
       content.release();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("hehe");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
       cause.printStackTrace();
       ctx.close();
    }

    private final ChannelFutureListener trafficGenerator = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if(future.isSuccess()) {
                generateTraffic();
            } else {
                future.cause().printStackTrace();
                future.channel().close();
            }
        }
    };

    private void generateTraffic() {
        ctx.writeAndFlush(content.retainedDuplicate()).addListener(trafficGenerator);
    }
}
