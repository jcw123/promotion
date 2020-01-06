package com.im.sky.netty.zs.codec;

public interface Codec extends Encoder, Decoder {

    Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
}
