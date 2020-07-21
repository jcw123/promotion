package com.im.sky.netty.test.test2.codec;

import com.im.sky.netty.test.test2.codec.java.JavaCodec;
import com.im.sky.netty.test.test2.util.Constants;

/**
 * @author jiangchangwei
 * @date 2020-6-18 下午 5:13
 **/
public class CodecFactory {

    public static Codec getInstance(int codecType) {
        Constants.CodecType ct = Constants.CodecType.valueOf(codecType);
        return getInstance(ct);
    }

    public static Codec getInstance(Constants.CodecType codecType) {
        Codec ins;
        switch (codecType) {
            case java:
                ins = new JavaCodec();
                break;
            default:
                throw new IllegalArgumentException("Unsupported code type:" + codecType);
        }
        return ins;
    }
}
