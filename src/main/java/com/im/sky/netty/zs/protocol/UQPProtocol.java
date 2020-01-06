package com.im.sky.netty.zs.protocol;

import com.im.sky.netty.zs.codec.Codec;
import com.im.sky.netty.zs.codec.CodecFactory;
import com.im.sky.netty.zs.codec.CodecType;
import com.im.sky.netty.zs.msg.Invocation;
import com.im.sky.netty.zs.msg.MessageHeader;
import com.im.sky.netty.zs.msg.RequestMessage;
import com.im.sky.netty.zs.msg.ResponseMessage;
import com.im.sky.netty.zs.util.CodecUtils;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义一个协议
 *
 * UQP Protocol
 *MAGIC CODE(2B) Protocol Identity ANFE
 * ==================================
 * FULLLENGTH(4B): length of(header + body), not include MAGIC CODE
 * HEADERLENGTH(2B):length of(PROTOCOLTYPE + .... + header tail), not include FULLLENGTH and HEADERLENGTH
 * PROTOCOLTYPE(1B)
 * CODECTYPE(1B): serialize/deserialize type
 * MESSAGETYPE(1B): request/response/heartbeat/callback?
 * COMPRESSTYPE(1B): compress type NONOE and so on
 * MSGID(4B): message id
 * [OPT]ATTRMAP:
 *  MAP_SIZE(1B) size of attr map
 *  {
 *      AATR_KEY(1B) key of attr
 *      ATTR_TYPE(1B) 1:int, 2:string, 3:byte, 4:short
 *      ATTR_VAL(?B) int:(4B); string:Length(2B)+data; byte(1B); short:(2B)
 *  }
 *  ====================== Protocol Header END ============================
 *  ======================Protocol Body Begin ===============================
 *  String className
 *  String alias
 *  String methodName
 *  String[] argsType
 *  Object[] args
 *  Map<String, String> attachments
 *  ======================Protocol Body End =================================
 */
@Slf4j
public class UQPProtocol implements Protocol {
    private final Codec codec;

    public UQPProtocol(CodecType codecType) {
        this.codec = CodecFactory.getInstance(codecType);
    }

    @Override
    public Object decode(ByteBuf data, Class cls) {
        byte[] dataArr = new byte[data.readableBytes()];
        data.readBytes(dataArr);
        return codec.decode(dataArr, cls);
    }

    @Override
    public Object decode(ByteBuf data, String clsTypeName) {
        byte[] dataArr = new byte[data.readableBytes()];
        data.readBytes(dataArr);
        return codec.decode(dataArr, clsTypeName);
    }

    @Override
    public ByteBuf encode(Object obj, ByteBuf buf) {
        if(obj instanceof RequestMessage) {
            RequestMessage request = (RequestMessage)obj;
            MessageHeader msgHeader = request.getMsgHeader();
            Invocation invocationBody = request.getInvocationBody();
            if(invocationBody != null) {
                byte[] invocationData = codec.encode(invocationBody);
                request.getMsgHeader().setHeaderLength(CodecUtils.encodeHeader(msgHeader, buf));
                buf.writeBytes(invocationData);
                request.getMsgHeader().setLength(buf.readableBytes());
            }else {
                CodecUtils.encodeHeader(msgHeader, buf);
            }
        } else if(obj instanceof ResponseMessage) {
            ResponseMessage response = (ResponseMessage)obj;
            MessageHeader msgHeader = response.getMsgHeader();
            byte[] responseData = codec.encode(obj);
            response.getMsgHeader().setHeaderLength(CodecUtils.encodeHeader(msgHeader, buf));
            buf = buf.writeBytes(responseData);
            response.getMsgHeader().setLength(buf.readableBytes());
        } else {
            throw new RuntimeException("no such kind of message");
        }
        return buf;
    }
}
