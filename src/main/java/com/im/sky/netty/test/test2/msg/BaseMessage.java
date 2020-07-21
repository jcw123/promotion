package com.im.sky.netty.test.test2.msg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * @author jiangchangwei
 * @date 2020-6-18 下午 5:43
 **/
public abstract class BaseMessage {

    private MessageHeader msgHeader;

    private ByteBuf msg;

    private ByteBuf msgBody;

    private transient Channel channel;

    protected BaseMessage(boolean initHeader) {
        if(initHeader) {
            msgHeader = new MessageHeader();
        }
    }

    public MessageHeader getMsgHeader() {
        return msgHeader;
    }

    public void setMsgHeader(MessageHeader msgHeader) {
        this.msgHeader = msgHeader;
    }

    public ByteBuf getMsg() {
        return msg;
    }

    public void setMsg(ByteBuf msg) {
        this.msg = msg;
    }

    public ByteBuf getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(ByteBuf msgBody) {
        this.msgBody = msgBody;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public int getProtocolType() {
        return msgHeader.getProtocolType();
    }

    public int getRequestId() {
        return msgHeader != null ? msgHeader.getMsgId() : -1;
    }

    public void setRequestId(Integer msgId) {
        msgHeader.setMsgId(msgId);
    }

    @Override
    public int hashCode() {
        return msgHeader.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof BaseMessage)) return false;
        BaseMessage other = (BaseMessage)obj;
        if(!msgHeader.equals(other.msgHeader))return false;
        return true;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "msgHeader=" + msgHeader +
                ", msg=" + msg +
                ", msgBody=" + msgBody +
                ", channel=" + channel +
                '}';
    }
}
