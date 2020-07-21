package com.im.sky.netty.test.test2.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author jiangchangwei
 * @date 2020-6-16 上午 9:54
 **/
@Slf4j
public class JoyServerTransport implements ServerTransport {

    private ServerTransportConfig config;

    public JoyServerTransport(ServerTransportConfig config) {
        this.config = config;
    }

    @Override
    public Boolean start() {
        boolean flag = Boolean.FALSE;
        Class<? extends ServerChannel> clazz = NioServerSocketChannel.class;
        if(config.isUseEpoll()) {
            clazz = EpollServerSocketChannel.class;
        }
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(config.getParentEventLoopGroup(), config.getChildEventLoopGroup())
                .channel(clazz)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, config.getCONNECT_TIMEOUT())
                .option(ChannelOption.SO_BACKLOG, config.getBACKLOG())
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                .childOption(ChannelOption.SO_KEEPALIVE, config.isKEEPALIVE())
                .childOption(ChannelOption.TCP_NODELAY, config.isTCP_NO_DELAY())
                .childOption(ChannelOption.ALLOCATOR, PooledBufHolder.getInstance())
                .childOption(ChannelOption.WRITE_BUFFER_WATER_MARK, WriteBufferWaterMark.DEFAULT)
                .childOption(ChannelOption.SO_RCVBUF, 8192 * 128)
                .childOption(ChannelOption.SO_SNDBUF, 8192 * 128)
                .childHandler(new ServerChannelInitializer(config));
        ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(config.getHost(), config.getPort()));
        ChannelFuture channelFuture = future.addListener(v -> {
           if(v.isSuccess()) {
               log.info("Server has success bind to {}:{}", config.getHost(), config.getPort());
           }else {
               log.error("Server has fail bind to {}:{}", config.getHost(), config.getPort());
               config.getParentEventLoopGroup().shutdownGracefully();
               config.getChildEventLoopGroup().shutdownGracefully();
               throw new RuntimeException("Server start fail!", future.cause());
           }
        });
        try {
            channelFuture.await(5000, TimeUnit.MILLISECONDS);
            if(channelFuture.isSuccess()) {
                flag = true;
            }
        }catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        return flag;
    }

    @Override
    public void end() {
        log.error("Shutdown the JOY server transport now");
        config.getParentEventLoopGroup().shutdownGracefully();
        config.getChildEventLoopGroup().shutdownGracefully();
    }
}
