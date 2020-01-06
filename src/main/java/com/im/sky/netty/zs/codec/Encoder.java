package com.im.sky.netty.zs.codec;

public interface Encoder {

    byte[] encode(Object obj);

    byte[] encode(Object obj, String clsName);
}
