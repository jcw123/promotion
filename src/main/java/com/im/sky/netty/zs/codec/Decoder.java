package com.im.sky.netty.zs.codec;

public interface Decoder {

    Object decode(byte[] data, Class cls);

    Object decode(byte[] data, String clsTypeName);
}
