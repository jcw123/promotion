package com.im.sky.netty.zs.protocol;

public enum ProtocolType {

    uqp(1),
    dubbo(2),
    rest(3),
    http(4);

    private int type;

    ProtocolType(int type) {
        this.type = type;
    }

    public int value() {
        return this.type;
    }

    public static ProtocolType valueOf(int type) {
        ProtocolType p;
        switch (type) {
            case 1 :
                p = uqp;
                break;
            case 2:
                p = dubbo;
                break;
            case 3:
                p = rest;
                break;
            case 4:
                p = http;
                break;
            default:
                throw new RuntimeException("Unknown protocol type value:" + type);
        }
        return p;
    }
}
