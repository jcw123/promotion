package com.im.sky.netty.test.test2.codec.joy;

import com.im.sky.netty.test.test2.msg.BaseMessage;
import com.im.sky.netty.test.test2.protocol.JoyProtocol;
import com.im.sky.netty.test.test2.protocol.ProtocolUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author jiangchangwei
 * @date 2020-6-24 上午 10:59
 **/
public class JoyEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        ByteBuf headBody = null;
        if(out == null) {
            out = ctx.alloc().buffer();
        }
        try {
            if(msg instanceof BaseMessage) {
                BaseMessage base = (BaseMessage)msg;
                if(base.getMsg() != null) {
                    write(base.getMsg(), out);
                    base.getMsg().release();
                }else {
                    headBody = ctx.alloc().heapBuffer();
                    ProtocolUtil.encode(msg, headBody);
                    write(headBody, out);
                }
            }

        }finally {
            if(headBody != null) {
                headBody.release();
            }
        }
    }

    private void write(ByteBuf data, ByteBuf out) {
        int totalLength = 2 + 4 + data.readableBytes();
        if(out.capacity() < totalLength) {
            out.capacity(totalLength);
        }
        out.writeBytes(JoyProtocol.MAGIC_CODE_BYTE);
        int length = totalLength - 2;
        out.writeInt(length);
        out.writeBytes(data, data.readerIndex(), data.readableBytes());

    }
}
