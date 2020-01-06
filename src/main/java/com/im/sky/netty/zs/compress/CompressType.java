package com.im.sky.netty.zs.compress;

public enum CompressType {

    NONE((byte)1);

    private byte type;

    CompressType(byte type) {
        this.type = type;
    }

    public byte type() {
        return this.type;
    }

    public static CompressType valueOf(byte value) {
        CompressType p;
        switch (value) {
            case 1:
                p = NONE;
                break;
            default:
                throw new RuntimeException("Unknown compresss type value:" + value);
        }
        return p;
    }
}
