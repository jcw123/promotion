package com.im.sky.netty.zs.util;

import com.im.sky.netty.zs.msg.MessageHeader;
import io.netty.buffer.ByteBuf;

import java.util.Map;

public class CodecUtils {

    public static byte[] short2bytes(short num) {
        byte[] result = new byte[2];
        result[0] = (byte) (num >>> 8); //取次低8位放到2下标
        result[1] = (byte) (num);      //取最低8位放到3下标
        return result;
    }

    public static short encodeHeader(MessageHeader header, ByteBuf byteBuf) {
        short headLength = 8; // 没有map 长度是8
        if( byteBuf.capacity() < 8 ) byteBuf.capacity(8);
        int writeIndex = byteBuf.writerIndex();
        byteBuf.writeShort(headLength);
        byteBuf.writeByte(header.getProtocolType());
        byteBuf.writeByte(header.getCodecType());
        byteBuf.writeByte(header.getMsgType());
        byteBuf.writeByte(header.getCompressType());
        byteBuf.writeInt(header.getMsgId());
        if (header.getAttrMapSize() > 0) {
            headLength += map2bytes(header.getKeysMap(), byteBuf);
            byteBuf.setBytes(writeIndex, short2bytes(headLength)); // 替换head长度的两位
        }
        return headLength;
    }

    protected static short map2bytes(Map<Byte, Object> dataMap, ByteBuf byteBuf) {
        byteBuf.writeByte(dataMap.size());
        short s = 1;
        for (Map.Entry<Byte, Object> attr : dataMap.entrySet()) {
            byte key = attr.getKey();
            Object val = attr.getValue();
            if (val instanceof Integer) {
                byteBuf.writeByte(key);
                byteBuf.writeByte((byte) 1);
                byteBuf.writeInt((Integer) val);
                s += 6;
            } else if (val instanceof String) {
                byteBuf.writeByte(key);
                byteBuf.writeByte((byte) 2);
                byte[] bs = ((String) val).getBytes(Constants.DEFAULT_CHARSET);
                byteBuf.writeShort(bs.length);
                byteBuf.writeBytes(bs);
                s += (4 + bs.length);
            } else if (val instanceof Byte) {
                byteBuf.writeByte(key);
                byteBuf.writeByte((byte) 3);
                byteBuf.writeByte((Byte) val);
                s += 3;
            } else if (val instanceof Short) {
                byteBuf.writeByte(key);
                byteBuf.writeByte((byte) 4);
                byteBuf.writeShort((Short) val);
                s += 4;
            } else {
                throw new RuntimeException("Value of attrs in message header must be byte/short/int/string");
            }
        }
        return s;
    }

    public static MessageHeader decodeHeader(ByteBuf byteBuf, int headerLength){
        byte protocolType = byteBuf.readByte();
        byte codecType = byteBuf.readByte();
        byte msgType = byteBuf.readByte();
        byte compressType = byteBuf.readByte();
        int messageId = byteBuf.readInt();
        MessageHeader header = new MessageHeader();
        header.setValues(protocolType,codecType,msgType,compressType,messageId);
        if (headerLength > 8) {
            bytes2Map(header.getKeysMap(), byteBuf);
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
                throw new RuntimeException("Value of attrs in message header must be byte/short/int/string");
            }
        }
    }
}
