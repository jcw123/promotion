package com.im.sky.netty.test.test2.protocol;

import com.im.sky.netty.test.test2.codec.Codec;
import com.im.sky.netty.test.test2.codec.CodecFactory;
import com.im.sky.netty.test.test2.util.Constants;
import io.netty.buffer.ByteBuf;

/**
 * @author jiangchangwei
 * @date 2020-6-18 下午 4:24
 *
 * 自定义新的结构如下：
 * MAGICCODE(2B)    Protocol Identity AFCE
 * ================= Protocol Header Begin ================
 * FULLLENGTH(4B): length of(header + body), not include MAGICCODE
 * HEADERLENGTH(2B):length of(ProtocolType + ... header tail), not include FULLLENGTH and HEADERLENGTH
 * PROTOCOLTYPE(1B)
 * CODECTYPE(1B) : serialize/deserialize type
 * MESSAGETYPE(1B) : request / response / and so on
 * COMPRESSTYPE(1B) : compress type
 * MSGID(4B) : message id
 * [OPT]ATTRMAP:
 * MAP_SIZE(1B) size of attr map
 * {
 *     ATTR_KEY(1B) key for attr
 *     ATTR_TYPE(1B) 1:int;2:string;3:byte;4:short
 *     ATTR_VAL(?B) int(4B);string:length(2B)+data;byte(1B);short(2B)
 * }
 * ================== Protocol Header End ==========================
 * ================== Protocol Body Begin ================================
 * String className
 * String alias
 * String methodName
 * String[] argsType
 * Object[] args
 * Map<String, String> attachments
 * ====================Protocol Body End ================================
 *
 **/
public class JoyProtocol implements Protocol {

    private final Codec codec;

    private static final short MAGIC = (short)0xafce;

    private static final byte MAGIC_HIGH = short2bytes(MAGIC)[0];

    private static final byte MAGIC_LOW = short2bytes(MAGIC)[1];

    public static final byte[] MAGIC_CODE_BYTE = new byte[]{MAGIC_HIGH, MAGIC_LOW};

    public static final byte RESPONSE_WITH_EXCEPTION = 0;

    public static final byte RESPONSE_VALUE = 1;

    public static final byte RESPONSE_NULL_VALUE = 2;

    public JoyProtocol(Constants.CodecType codeType) {
        this.codec = CodecFactory.getInstance(codeType);
    }

    @Override
    public Object decode(ByteBuf data, Class clazz) {
        byte[] dataArray = new byte[data.readableBytes()];
        data.readBytes(dataArray);
        return codec.decode(dataArray, clazz);
    }

    @Override
    public Object decode(ByteBuf data, String classTypeName) {
        byte[] dataArray = new byte[data.readableBytes()];
        data.readBytes(dataArray);
        return codec.decode(dataArray, classTypeName);
    }

    @Override
    public ByteBuf encode(Object obj, ByteBuf buf) {
        byte[] arr = codec.encode(obj);
        buf.writeBytes(arr);
        return buf;
    }

    private static byte[] short2bytes(short v) {
        byte[] ret = {0, 0};
        ret[1] = (byte)v;
        ret[0] = (byte)(v >>> 8);
        return ret;
    }


}
