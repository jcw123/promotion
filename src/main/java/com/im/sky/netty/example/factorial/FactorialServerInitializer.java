package com.im.sky.netty.example.factorial;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.ssl.SslContext;

/**
 * @author jiangchangwei
 * @date 2019-12-7 下午 3:00
 **/
public class FactorialServerInitializer extends ChannelInitializer<SocketChannel> {

    private SslContext sslCtx;

    public FactorialServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }
//
    public FactorialServerInitializer() {

    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

//        if (sslCtx != null) {
//            pipeline.addLast(sslCtx.newHandler(ch.alloc()));
//        }

        // Enable stream compression (you can remove these two if unnecessary)
//        pipeline.addLast(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.GZIP));
//        pipeline.addLast(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));

        // Add the number codec first,
        pipeline.addLast(new BigIntegerDecoder());
        pipeline.addLast(new NumberEncoder());

        // and then business logic.
        // Please note we create a handler for every new channel
        // because it has stateful properties.
        pipeline.addLast(new FactorialServerHandler());
    }
}

