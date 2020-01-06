package com.im.sky.netty.example.factorial;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * @author jiangchangwei
 * @date 2019-12-7 下午 3:06
 **/
public final class FactorialClient {

//    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8322"));
    static final int COUNT = Integer.parseInt(System.getProperty("count", "3"));

    public static void main(String[] args) throws Exception {
        // Configure SSL.
//        final SslContext sslCtx;
//        if (SSL) {
//            sslCtx = SslContextBuilder.forClient()
//                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
//        } else {
//            sslCtx = null;
//        }

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new FactorialClientInitializer());

            // Make a new connection.
            ChannelFuture f = b.connect(HOST, PORT).sync();

            // Get the handler instance to retrieve the answer.
            FactorialClientHandler handler =
                    (FactorialClientHandler) f.channel().pipeline().last();

            // Print out the answer.
            System.err.format("Factorial of %,d is: %,d", COUNT, handler.getFactorial());
        } finally {
            group.shutdownGracefully();
        }
    }
}
