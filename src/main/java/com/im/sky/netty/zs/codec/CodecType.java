package com.im.sky.netty.zs.codec;

public enum  CodecType {

    hessian(1),
    msgpack(2),
    java(3),
    json(4),
    protobuf(5);

    private int type;

    CodecType(int type) {
        this.type = type;
    }

    public int type() {
        return this.type;
    }

    public static CodecType valueOf(int type) {
        CodecType p;
        switch (type) {
            case 1:
                p = hessian;
                break;
            case 2:
                p = msgpack;
                break;
            case 3:
                p = java;
                break;
            case 4:
                p = json;
                break;
            case 5:
                p = protobuf;
                break;
            default:
                throw new RuntimeException("Unknown codec type value:" + type);
        }
        return p;
    }
}
