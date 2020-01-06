package com.im.sky.netty.zs.codec.hessian;

import com.im.sky.netty.zs.codec.Codec;

public class HessianCodec implements Codec {

    @Override
    public Object decode(byte[] data, Class cls) {
        return null;
    }

    @Override
    public Object decode(byte[] data, String clsTypeName) {
        return null;
    }

    @Override
    public byte[] encode(Object obj) {
        return new byte[0];
    }

    @Override
    public byte[] encode(Object obj, String clsName) {
        return new byte[0];
    }
}
