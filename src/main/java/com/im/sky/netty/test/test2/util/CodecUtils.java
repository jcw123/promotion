package com.im.sky.netty.test.test2.util;

import com.im.sky.netty.test.test2.error.JoyCodecException;
import com.im.sky.netty.test.test2.msg.MessageHeader;
import io.netty.buffer.ByteBuf;

import java.util.Map;

/**
 * @author jiangchangwei
 * @date 2020-6-23 下午 5:11
 **/
public class CodecUtils {

    public static MessageHeader decodeHeader(ByteBuf byteBuf, int headerLength){
        byte protocolType = byteBuf.readByte();
        byte codecType = byteBuf.readByte();
        byte msgType = byteBuf.readByte();
        byte compressType = byteBuf.readByte();
        int messageId = byteBuf.readInt();
        MessageHeader header = new MessageHeader();
        header.setValues(protocolType,codecType,msgType,compressType,messageId);
        if (headerLength > 8) {
            bytes2Map(header.getAttrMap(), byteBuf);
        }
        return header;
    }

    protected static void bytes2Map(Map<Byte, Object> dataMap, ByteBuf byteBuf){
        byte size = byteBuf.readByte();
        for (int i = 0; i < size; i++) {
            byte key = byteBuf.readByte();
            byte type = byteBuf.readByte();
            if (type == 1) {
                int value = byteBuf.readInt();
                dataMap.put(key, value);
            } else if (type == 2) {
                int length = byteBuf.readShort();
                byte[] dataArr = new byte[length];
                byteBuf.readBytes(dataArr);
                dataMap.put(key, new String(dataArr, Constants.DEFAULT_CHARSET));
            } else if (type == 3) {
                byte value = byteBuf.readByte();
                dataMap.put(key, value);
            } else if (type == 4) {
                short value = byteBuf.readShort();
                dataMap.put(key, value);
            } else {
                throw new JoyCodecException("Value of attrs in message header must be byte/short/int/string");
            }
        }
    }
}
