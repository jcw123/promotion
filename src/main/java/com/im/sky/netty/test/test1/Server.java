package com.im.sky.netty.test.test1;

import com.im.sky.netty.test.test1.handler.ServerInHandler;
import com.im.sky.netty.test.test1.handler.ServerOutHandler;
import com.im.sky.netty.test.test1.handler.ServerParentHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author jiangchangwei
 * @date 2020-6-11 下午 8:43
 **/
public class Server {

    public static void main(String[] args) {
        new Server().run();
    }


    public void run() {
        // io事件线程
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new ServerParentHandler())
//                    .option(ChannelOption.SO_TIMEOUT, 100)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ServerInHandler());
                            pipeline.addLast(new ServerOutHandler());
                        }
                    })
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childAttr(AttributeKey.newInstance("name"), "jcw");
            ChannelFuture future = serverBootstrap.bind(8888)
                    .addListener(new GenericFutureListener<Future<? super Void>>() {
                        @Override
                        public void operationComplete(Future<? super Void> future) throws Exception {
                            System.out.println("服务端绑定8888端口成功");
                        }
                    })
                    .syncUninterruptibly();
            future.channel().closeFuture().syncUninterruptibly();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
