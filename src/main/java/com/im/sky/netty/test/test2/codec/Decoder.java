package com.im.sky.netty.test.test2.codec;

/**
 * @author jiangchangwei
 * @date 2020-6-18 下午 4:26
 **/
public interface Decoder {

    Object decode(byte[] data, Class clazz);

    Object decode(byte[] data, String classTypeName);
}
