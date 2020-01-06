package com.im.sky.netty.zs.protocol;

import com.im.sky.netty.zs.codec.CodecType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProtocolFactory {

    private static Map<Integer, Protocol> protocolMap = new ConcurrentHashMap<>();

    static {
        Protocol uqpProtocol = initProtocol(ProtocolType.uqp, CodecType.msgpack);
        protocolMap.put(buildKey(ProtocolType.uqp.value(), CodecType.msgpack.type()), uqpProtocol);
    }

    public static Protocol getProtocol(ProtocolType protocolType, CodecType codecType) {
        int key = buildKey(protocolType.value(), codecType.type());
        Protocol ins;
        ins = protocolMap.get(key);
        if (ins == null) {
            ins = initProtocol(protocolType, codecType);
            protocolMap.put(key, ins);
        }
        return ins;
    }

    public static Protocol getProtocol(int protocolType, int codecType) {
        int key = buildKey(protocolType, codecType);
        Protocol ins;
        ins = protocolMap.get(key);
        if (ins == null) {
            ins = initProtocol(ProtocolType.valueOf(protocolType), CodecType.valueOf(codecType));
            protocolMap.put(key, ins);
        }
        return ins;
    }

    private static int buildKey(int protocolType, int codecType) {
        return protocolType << 8 + codecType;
    }

    private static Protocol initProtocol(ProtocolType protocolType, CodecType codecType) {
        Protocol ins = null;
        switch (protocolType) {
            case uqp:
                ins = new UQPProtocol(codecType);
                break;
            default:
                throw new IllegalArgumentException("Init protocol error by protocolType:" + protocolType
                        + " and codecType:" + codecType);
        }
        return ins;
    }
}
