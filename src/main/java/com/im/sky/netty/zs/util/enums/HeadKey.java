package com.im.sky.netty.zs.util.enums;

public enum HeadKey {

    TIMEOUT((byte)1, Integer.class), // 请求超时时间
    CALLBACK_INS_ID((byte)2, String.class), //回调函数对应的实例id
    UQP_VERSION((byte)3, Short.class), // 客户端的UQP版本
    SRC_LANGUAGE((byte)4, Byte.class), // 请求的语言
    RESPONSE_CODE((byte)5, Byte.class); // 返回结果（0：成功，1：失败）


    private byte keyNum;
    private Class type;

    HeadKey(byte b, Class cls) {
        this.keyNum = b;
        this.type = cls;
    }

    public byte getNum() {
        return this.keyNum;
    }

    public Class getType() {
        return this.type;
    }

    public static HeadKey getKey(byte num) {
        HeadKey key;
        switch (num) {
            case 1:
                key = TIMEOUT;
                break;
            case 2:
                key = CALLBACK_INS_ID;
                break;
            case 3:
                key = UQP_VERSION;
                break;
            case 4:
                key = SRC_LANGUAGE;
                break;
            case 5:
                key = RESPONSE_CODE;
                break;
            default:
                throw new RuntimeException("Unknown head key value:" + num);
        }
        return key;
    }
}
