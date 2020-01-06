package com.im.sky.netty.zs.protocol;

import com.im.sky.netty.zs.msg.BaseMessage;
import com.im.sky.netty.zs.msg.MessageHeader;
import com.im.sky.netty.zs.msg.RequestMessage;
import com.im.sky.netty.zs.msg.ResponseMessage;
import com.im.sky.netty.zs.util.CodecUtils;
import com.im.sky.netty.zs.util.Constants;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class ProtocolUtil {

    public static ByteBuf encode(Object object, ByteBuf byteBuf ){
        int protocolType;
        int codeType;
        MessageHeader header = null;
        try {
            if(object instanceof BaseMessage){
                BaseMessage msg = (BaseMessage)object;
                protocolType = msg.getProtocolType();
                codeType = msg.getMsgHeader().getCodecType();
            }else{
                throw new IllegalArgumentException("encode object shout be a instance of BaseMessage.");
            }
            Protocol protocol = ProtocolFactory.getProtocol(protocolType, codeType);
            byteBuf =protocol.encode(object,byteBuf);
        } catch (Exception e) {
            log.error("encode error", e);
            throw e;
        }
        return byteBuf;
    }

    public static BaseMessage decode(ByteBuf byteBuf){
        MessageHeader header = null;
        Integer msgLength = byteBuf.readableBytes() + 6;//magiccode + msg length(4 byte)
        BaseMessage msg = null;
        try {
            Short headerLength = byteBuf.readShort();
            int readerIndex = byteBuf.readerIndex();
            if(readerIndex > byteBuf.readableBytes()){
                throw new RuntimeException("codecError:header length error.");
            }
            header = CodecUtils.decodeHeader(byteBuf, headerLength);
            header.setHeaderLength(headerLength);
            header.setLength(msgLength);
            msg = enclosure(byteBuf,header);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            byteBuf.release();//release the byteBuf when decode hit on error
            throw e;
        }
        return msg;
    }

    public static BaseMessage enclosure(ByteBuf byteBuf, MessageHeader header){
        int msgType = header.getMsgType();
        BaseMessage msg = null;
        try {
            switch(msgType){
                case Constants.CALLBACK_REQUEST_MSG:
                case Constants.REQUEST_MSG:
                    RequestMessage tmp = new RequestMessage();
                    tmp.setReceiveTime(new Date().getTime());
                    tmp.setMsgBody(byteBuf.slice(byteBuf.readerIndex(),byteBuf.readableBytes()));
                    msg = tmp;
                    break;
                case Constants.CALLBACK_RESPONSE_MSG:
                    Protocol protocol1 = ProtocolFactory.getProtocol(header.getProtocolType(), header.getCodecType());
                    ResponseMessage response1 =(ResponseMessage)protocol1.decode(byteBuf,ResponseMessage.class.getCanonicalName());
                    msg = response1;
                    byteBuf.release();
                    break;
                case Constants.RESPONSE_MSG:
                    ResponseMessage response = new ResponseMessage();
                    response.setMsgBody(byteBuf.slice(byteBuf.readerIndex(),byteBuf.readableBytes()));
                    msg = response;
                    break;
                case Constants.HEARTBEAT_REQUEST_MSG:
                    msg = new RequestMessage();
                    byteBuf.release();
                    break;
                case Constants.HEARTBEAT_RESPONSE_MSG:
                    msg = new ResponseMessage();
                    byteBuf.release();
                    break;
                case Constants.SHAKEHAND_MSG:
                    RequestMessage shakeHand = new RequestMessage();
                    msg = shakeHand;
                    byteBuf.release();
                    break;
                default:
                    throw new RuntimeException("Unknown message type in header!");
            }
            if (msg != null) {
                msg.setMsgHeader(header);
            }
        } catch (Exception e) {
            throw e;
        }
        return msg;

    }
}
