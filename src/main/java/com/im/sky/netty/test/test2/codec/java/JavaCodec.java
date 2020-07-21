package com.im.sky.netty.test.test2.codec.java;

import com.im.sky.netty.test.test2.codec.Codec;
import com.im.sky.netty.test.test2.codec.Decoder;
import com.im.sky.netty.test.test2.codec.Encoder;

/**
 * @author jiangchangwei
 * @date 2020-6-18 下午 5:18
 *
 * 仅仅用来序列化和反序列化body字段，像header之类的是由协议自身添加的；
 **/
public class JavaCodec implements Codec {

    private Encoder encoder;

    private Decoder decoder;

    public JavaCodec() {
        encoder = new JavaEncoder();
        decoder = new JavaDecoder();
    }

    @Override
    public Object decode(byte[] data, Class clazz) {
        return decoder.decode(data, clazz);
    }

    @Override
    public Object decode(byte[] data, String classTypeName) {
        return decoder.decode(data, classTypeName);
    }

    @Override
    public byte[] encode(Object obj) {
        return encoder.encode(obj);
    }

    @Override
    public byte[] encode(Object obj, String classTypeName) {
        return encoder.encode(obj, classTypeName);
    }
}
