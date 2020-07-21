package com.im.sky.netty.test.test2.codec.joy;

import com.im.sky.netty.test.test2.protocol.ProtocolUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangchangwei
 * @date 2020-6-24 上午 10:59
 **/
@Slf4j
public class JoyDecoder extends LengthFieldBasedFrameDecoder {

    public JoyDecoder(int maxFrameLength) {
        super(maxFrameLength, 2, 4, -4, 6);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        Object result = ProtocolUtil.decode(frame);
        log.trace("decoder result:{}", result);
        return result;
    }
}
