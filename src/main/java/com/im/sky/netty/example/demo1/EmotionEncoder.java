package com.im.sky.netty.example.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author jiangchangwei
 * @date 2019-12-7 下午 3:32
 *
 * magic: 0xBABE
 **/
public class EmotionEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if(msg instanceof EmotionEnums) {
            EmotionEnums baseMsg = (EmotionEnums)msg;
            int type = baseMsg.getType();
            String m = baseMsg.getMsg();
            // 先写魔数0xBABE
            out.writeByte((byte)0xBA);
            out.writeByte((byte)0xBE);
            out.writeByte(1);
            out.writeInt(0);
            // 写枚举类型值
            out.writeInt(type);
            byte[] mByte = m.getBytes("utf-8");
            // 写描述值的长度
            out.writeInt(mByte.length);
            // 写描述值的字节数组
            out.writeBytes(mByte);
            out.setInt(3, 4 + mByte.length);
        } else if(msg instanceof String) {
           String baseMsg = (String)msg;
           out.writeBytes(new byte[] {(byte)0xBA, (byte)0xBE});
           out.writeByte(2);
           byte[] mBytes = baseMsg.getBytes("utf-8");
           out.writeInt(mBytes.length);
           out.writeBytes(mBytes);
        } else {
            System.out.println("无效的数据类型");
        }
    }
}
