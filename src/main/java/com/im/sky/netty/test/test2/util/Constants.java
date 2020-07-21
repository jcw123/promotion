package com.im.sky.netty.test.test2.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author jiangchangwei
 * @date 2020-6-18 下午 3:42
 **/
public class Constants {

    public static final ProtocolType DEFAULT_PROTOCOL_TYPE = ProtocolType.JOY;

    public static final CodecType DEFAULT_CODEC_TYPE = CodecType.java;

    public static final CompressType DEFAULT_COMPRESS_TYPE = CompressType.NONE;

    public static final String THREAD_POOL_TYPE_FIXED = "fixed";

    public static final String THREAD_POOL_TYPE_CACHED = "cached";

    public final static String QUEUE_TYPE_NORMAL = "normal";

    public final static String QUEUE_TYPE_PRIORITY = "priority";

    /**
     * 默认io线程池大小
     */
    public final static int DEFAULT_IO_THREADS = 8;

    public final static Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public static final int REQUEST_MSG = 1;

    public static final int RESPONSE_MSG = 2;

    public enum ProtocolType {
        JOY(0);

        int val;

        public int value() {
            return val;
        }

        public static ProtocolType valueOf(int val) {
            switch (val) {
                case 0:
                    return JOY;
                default:
                    throw new IllegalArgumentException("UNKONW protocol type value:"+ val);
            }
        }

        ProtocolType(int val) {
            this.val = val;
        }
    }

    public enum CodecType {
        java(1);

        private int val;

        CodecType(int val) {
            this.val = val;
        }

        public int value() {
            return this.val;
        }

        public static CodecType valueOf(int value) {
            switch (value) {
                case 1 :
                    return java;
                default:
                    throw new IllegalArgumentException("Unkonwn codec type value:" +value);
            }
        }
    }

    public enum CompressType {
        NONE((byte)0),
        SNAPPY((byte)1);

        private byte val;

        CompressType(byte val) {
            this.val = val;
        }

        public byte value() {
            return this.val;
        }

        public static CompressType valueOf(byte value) {
            switch (value) {
                case 0:
                    return NONE;
                case 1:
                    return SNAPPY;
                default:
                    throw new IllegalArgumentException("UNKONW compress type vlaue:" + value);
            }
        }
    }

    public enum HeadKey {
        timeout((byte)1, Integer.class);

        private byte keyNum;

        private Class type;

        HeadKey(byte b, Class clazz) {
            this.keyNum = b;
            this.type = clazz;
        }

        public byte getKeyNum() {
            return keyNum;
        }

        public Class getType() {
            return type;
        }

        public static HeadKey getKey(byte num) {
            switch (num) {
                case 1:
                    return timeout;
                default:
                    throw new IllegalArgumentException("Unkonw head key value:" + num);
            }
        }
    }
}
