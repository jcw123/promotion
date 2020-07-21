package com.im.sky.netty.test.test2.protocol;

import com.im.sky.netty.test.test2.error.JoyCodecException;
import com.im.sky.netty.test.test2.error.RpcException;
import com.im.sky.netty.test.test2.msg.BaseMessage;
import com.im.sky.netty.test.test2.msg.MessageHeader;
import com.im.sky.netty.test.test2.msg.RequestMessage;
import com.im.sky.netty.test.test2.msg.ResponseMessage;
import com.im.sky.netty.test.test2.util.CodecUtils;
import com.im.sky.netty.test.test2.util.Constants;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jiangchangwei
 * @date 2020-6-23 下午 4:59
 **/
@Slf4j
public class ProtocolUtil {

    public static ByteBuf encode(Object object, ByteBuf byteBuf) {
        int protocolType;
        int codeType;
        try {
            if(object instanceof BaseMessage){
                BaseMessage msg = (BaseMessage)object;
                protocolType = msg.getProtocolType();
                codeType = msg.getMsgHeader().getCodecType();
            }else{
                throw new JoyCodecException("encode object shout be a instance of BaseMessage.");
            }
            Protocol protocol = ProtocolFactory.getProtocol(protocolType, codeType);
            byteBuf =protocol.encode(object,byteBuf);
        } catch (Exception e) {
            log.warn(e.getMessage(),e);
            throw new RpcException(e.getMessage());
        }
        return byteBuf;
    }

    // 去掉数据压缩逻辑
    public static BaseMessage decode(ByteBuf byteBuf) {
        MessageHeader header = null;
        // magiccode + msg Length
        Integer msgLength = byteBuf.readableBytes() + 6;
        BaseMessage msg;
        try {
            short headerLength = byteBuf.readShort();
            int readerIndex = byteBuf.readInt();
            if(readerIndex > byteBuf.readableBytes()) {
                throw new JoyCodecException("codecError: header length error.");
            }
            header = CodecUtils.decodeHeader(byteBuf, headerLength);
            header.setHeaderLength(headerLength);
            header.setLength(msgLength);
            msg = enclosure(byteBuf, header);
        }catch (Exception e) {
            byteBuf.release();
            throw new RpcException(header, e);
        }
        return msg;
    }

    private static BaseMessage enclosure(ByteBuf byteBuf, MessageHeader header) {
        int msgType = header.getMsgType();
        BaseMessage msg;
        try {
            switch (msgType) {
                case Constants.REQUEST_MSG:
                    RequestMessage tmp = new RequestMessage();
                    tmp.setReceiveTime(System.currentTimeMillis());
                    tmp.setMsgBody(byteBuf.slice(byteBuf.readerIndex(), byteBuf.readableBytes()));
                    msg = tmp;
                    break;
                case Constants.RESPONSE_MSG:
                    ResponseMessage responseMessage = new ResponseMessage();
                    responseMessage.setMsgBody(byteBuf.slice(byteBuf.readerIndex(), byteBuf.readableBytes()));
                    msg = responseMessage;
                    break;
                default:
                    throw new RpcException(header, "Unknown message type in header");
            }
        }catch (Exception e) {
            throw new RpcException(header, e);
        }
        return msg;
    }
}
