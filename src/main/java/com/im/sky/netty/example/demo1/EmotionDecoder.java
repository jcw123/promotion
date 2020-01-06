package com.im.sky.netty.example.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author jiangchangwei
 * @date 2019-12-7 下午 3:31
 **/
public class EmotionDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 6) {
            return;
        }
        in.markReaderIndex();
        Short high = in.getUnsignedByte(0);
        Short low = in.getUnsignedByte(1);
        if(high.byteValue() != (byte)0xBA || low.byteValue() != (byte)0xBE) {
            throw new RuntimeException("数据格式不合法");
        }
        in.skipBytes(2);
        int msgType = in.readByte();
        int remainLen = in.readInt();
        if(remainLen > in.readableBytes()) {
            in.resetReaderIndex();
            return;
        }
        if(msgType == 1) {
            int type = in.readInt();
            byte[] mBytes = new byte[in.readInt()];
            in.readBytes(mBytes);
            String msg = new String(mBytes);
            EmotionEnums emotionEnums = EmotionEnums.get(type, msg);
            if (emotionEnums != null) {
                out.add(emotionEnums);
            }
        } else if(msgType == 2) {
            byte[] mBytes = new byte[remainLen];
            in.readBytes(mBytes);
            out.add(new String(mBytes));
        } else {
            throw new RuntimeException("数据格式不对");
        }
    }
}
