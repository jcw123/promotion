package com.im.sky.netty.example.factorial;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.math.BigInteger;

/**
 * @author jiangchangwei
 * @date 2019-12-7 下午 2:58
 **/
public class NumberEncoder extends MessageToByteEncoder<Number> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Number msg, ByteBuf out) {
        // Convert to a BigInteger first for easier implementation.
        BigInteger v;
        if (msg instanceof BigInteger) {
            v = (BigInteger) msg;
        } else {
            v = new BigInteger(String.valueOf(msg));
        }

        // Convert the number into a byte array.
        byte[] data = v.toByteArray();
        int dataLength = data.length;

        // Write a message.
        out.writeByte((byte) 'F'); // magic number
        out.writeInt(dataLength);  // data length
        out.writeBytes(data);      // data
    }
}
