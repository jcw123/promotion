package com.im.sky.netty.zs.msg;

import com.im.sky.netty.zs.util.Constants;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public abstract class BaseMessage {

    private MessageHeader msgHeader;

    private ByteBuf msg;

    private ByteBuf msgBody;

    private transient Channel channel;

    protected BaseMessage(boolean initHeader) {
        msgHeader = new MessageHeader();
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

    public boolean isHeartBeat() {
        int msgType = msgHeader.getMsgType();
        return msgType == Constants.HEARTBEAT_REQUEST_MSG
                || msgType == Constants.HEARTBEAT_RESPONSE_MSG;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseMessage)) return false;

        BaseMessage that = (BaseMessage) o;

        if (!msgHeader.equals(that.msgHeader)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return msgHeader.hashCode();
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "msgHeader=" + msgHeader +
                '}';
    }


}
