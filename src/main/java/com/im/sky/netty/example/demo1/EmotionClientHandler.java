package com.im.sky.netty.example.demo1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @author jiangchangwei
 * @date 2019-12-7 下午 5:04
 **/
public class EmotionClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active");
        new Thread(() -> {
            EmotionEnums[] emotionEnums = EmotionEnums.values();
            Random random = new Random();
            while (true) {
                try {
                    EmotionEnums emotion = emotionEnums[random.nextInt(emotionEnums.length)];
                    ctx.writeAndFlush(emotion);
                    Thread.sleep(1000);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("来自于心灵鸡汤的反馈信息：" + msg);
    }
}
