package com.im.sky.netty.test.test2.codec;

/**
 * @author jiangchangwei
 * @date 2020-6-18 下午 4:27
 **/
public interface Codec extends Decoder, Encoder{

    Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
}
