package com.im.sky.netty.zs.util;

import com.im.sky.netty.zs.codec.CodecType;
import com.im.sky.netty.zs.protocol.ProtocolType;

import java.nio.charset.Charset;

public class Constants {

    public static final ProtocolType DEFAULT_PROTOCOL_TYPE = ProtocolType.uqp;

    public static final CodecType DEFAULT_CODEC_TYPE = CodecType.msgpack;

    /*---------消息类型开始-----------*/
    public static final int REQUEST_MSG = 1;

    public static final int RESPONSE_MSG = 2;

    public static final int HEARTBEAT_REQUEST_MSG = 10;//connection heartbeat message

    public static final int HEARTBEAT_RESPONSE_MSG = 11;//connection heartbeat message

    public static final int SHAKEHAND_MSG = 6;

    public static final int SHAKEHAND_RESULT_MSG = 7;

    public static final int CALLBACK_REQUEST_MSG = 4;

    public static final int CALLBACK_RESPONSE_MSG = 5;
    /*---------消息类型结束-----------*/

    /**
     * 默认字符集 utf-8
     */
    public final static Charset DEFAULT_CHARSET = Charset.forName("UTF-8");



}
