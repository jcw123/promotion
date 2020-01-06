package com.im.sky.netty.zs.codec;

import com.im.sky.netty.zs.codec.hessian.HessianCodec;
import com.im.sky.netty.zs.codec.java.JavaCodec;
import com.im.sky.netty.zs.codec.json.JsonCodec;
import com.im.sky.netty.zs.codec.msgpack.MsgpackCodec;
import com.im.sky.netty.zs.codec.protobuf.ProtobufCodec;

public class CodecFactory {

    public static Codec getInstance(int codecType) {
        CodecType ct = CodecType.valueOf(codecType);
        return getInstance(ct);
    }

    public static Codec getInstance(CodecType codecType) {
        Codec ins;
        switch (codecType) {
            case msgpack:
                ins = new MsgpackCodec();
                break;
            case hessian:
                ins = new HessianCodec();
                break;
            case java:
                ins = new JavaCodec();
                break;
            case json:
                ins = new JsonCodec();
                break;
            case protobuf:
                ins = new ProtobufCodec();
                break;
            default:
                throw new RuntimeException("Unsupported codec type:" + codecType);
        }
        return ins;
    }
}
