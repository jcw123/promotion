package com.im.sky.netty.example.echo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author jiangchangwei
 * @date 2019-12-6 下午 12:40
 **/
public class EchoServer {

    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        new EchoServer(8889).run();
    }

    public void run() throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap.group(bossGroup, workerGroup)
                .channelFactory(new ChannelFactory<ServerChannel>() {
                    @Override
                    public ServerChannel newChannel() {
                        System.out.println("create server socket channel");
                        return new NioServerSocketChannel();
                    }
                })
                .handler(new EchoServerParentHandler())
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast("t1", new EchoServerHandler());
                    }
                })
                .option(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        ChannelFuture future = bootstrap.bind("127.0.0.1", port)
                .addListener(new GenericFutureListener<Future<? super Void>>() {
                    @Override
                    public void operationComplete(Future<? super Void> future) throws Exception {
                        System.out.println("bind 8889 complete");
                    }
                })
                .sync();
        future.channel().closeFuture().sync();
    }
}
