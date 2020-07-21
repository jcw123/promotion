package com.im.sky.netty.test.test2.codec;

/**
 * @author jiangchangwei
 * @date 2020-6-18 下午 4:25
 **/
public interface Encoder {

    byte[] encode(Object obj);

    byte[] encode(Object obj, String classTypeName);
}
