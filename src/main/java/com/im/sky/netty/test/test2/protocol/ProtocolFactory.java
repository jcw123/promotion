package com.im.sky.netty.test.test2.protocol;

import com.im.sky.netty.test.test2.util.Constants;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jiangchangwei
 * @date 2020-6-22 下午 6:36
 **/
public class ProtocolFactory {

    private static Map<Integer, Protocol> protocolMap = new ConcurrentHashMap<>();

    static {
        Protocol protocol = initProtocol(Constants.ProtocolType.JOY, Constants.CodecType.java);
        protocolMap.put(buildKey(Constants.ProtocolType.JOY.value(), Constants.CodecType.java.value()), protocol);
    }

    private static int buildKey(int protocolType, int codecType) {
        return protocolType << 8 + codecType;
    }

    public static Protocol getProtocol(int protocolType, int codecType) {
        int key = buildKey(protocolType, codecType);
        Protocol ins;
        ins = protocolMap.get(key);
        if(ins == null) {
            ins = initProtocol(Constants.ProtocolType.valueOf(protocolType), Constants.CodecType.valueOf(codecType));
            protocolMap.put(key, ins);
        }
        return ins;
    }

    public static Protocol getProtocol(Constants.ProtocolType protocolType, Constants.CodecType codecType) {
        int key = buildKey(protocolType.value(), codecType.value());
        Protocol ins;
        ins = protocolMap.get(key);
        if (ins == null) {
            ins = initProtocol(protocolType, codecType);
            protocolMap.put(key, ins);
        }
        return ins;
    }

    private static Protocol initProtocol(Constants.ProtocolType protocolType, Constants.CodecType codecType) {
        Protocol ins;
        switch (protocolType) {
            case JOY:
                ins = new JoyProtocol(codecType);
                break;
            default:
                throw new RuntimeException("init protocol error by protocolType:" + protocolType + " and codcType:" + codecType);
        }
        return ins;
    }
}
