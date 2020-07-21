package com.im.sky.netty.test.test2.msg;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author jiangchangwei
 * @date 2020-6-22 下午 8:11
 **/
public interface ConnectListener {

    void connected(ChannelHandlerContext context);

    void disconnected(ChannelHandlerContext context);
}
