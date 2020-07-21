package com.im.sky.netty.test.test1;

import com.google.common.base.Charsets;
import com.im.sky.netty.test.test1.handler.ClientInHandler;
import com.im.sky.netty.test.test1.handler.ClientOutHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author jiangchangwei
 * @date 2020-6-12 下午 4:13
 **/
public class Client {

    public static void main(String[] args) {
        Channel channel = new Client().run();
        if(channel != null) {
            int i = 0;
            while(true) {
                System.out.println(i++);
                ByteBuf msg = Unpooled.buffer(10);
                msg.writeCharSequence("你好吗?", Charsets.UTF_8);
                ChannelFuture future = channel.writeAndFlush(msg)
                        .addListener(new GenericFutureListener<Future<? super Void>>() {
                            @Override
                            public void operationComplete(Future<? super Void> future) throws Exception {
                                if(future.isSuccess()) {
                                    System.out.println("操作完成");
                                }else {
                                    System.out.println("操作失败");
                                    System.out.println(future.cause());
                                }
                            }
                        });
                try {
                    Thread.sleep(5000);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Channel run() {
        Bootstrap bootstrap = new Bootstrap();

        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(1);
        try {
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
//                    .option(ChannelOption.SO_TIMEOUT, 100)
                    .attr(AttributeKey.newInstance("age"), 18)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("initChannel");
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ClientInHandler());
                            pipeline.addLast(new ClientOutHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8888).syncUninterruptibly().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    System.out.println("客户端连接成功");
                }
            });
            return future.channel();
        }catch (Exception e) {
            eventLoopGroup.shutdownGracefully();
        }
        return null;
    }
}
