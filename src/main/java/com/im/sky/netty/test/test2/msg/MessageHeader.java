package com.im.sky.netty.test.test2.msg;

import com.im.sky.netty.test.test2.util.Constants;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jiangchangwei
 * @date 2020-6-18 下午 5:25
 **/
public class MessageHeader implements Cloneable{

    /**
     * include magiccode + header + body
     */
    private Integer length;

    /**
     * so body length = length - magiccode length - headerLength;
     */
    private Short headerLength;

    private int protocolType = Constants.DEFAULT_PROTOCOL_TYPE.value();

    private int codecType = Constants.DEFAULT_CODEC_TYPE.value();

    private int msgType;

    private int msgId;

    private byte compressType = Constants.DEFAULT_COMPRESS_TYPE.value();

    private Map<Byte, Object> keysMap = new ConcurrentHashMap<>();

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Short getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(Short headerLength) {
        this.headerLength = headerLength;
    }

    public int getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(int protocolType) {
        this.protocolType = protocolType;
    }

    public int getCodecType() {
        return codecType;
    }

    public void setCodecType(int codecType) {
        this.codecType = codecType;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public byte getCompressType() {
        return compressType;
    }

    public void setCompressType(byte compressType) {
        this.compressType = compressType;
    }

    public Map<Byte, Object> getKeysMap() {
        return keysMap;
    }

    public void setKeysMap(Map<Byte, Object> keysMap) {
        this.keysMap = keysMap;
    }

    public Object getAttrByKey(Constants.HeadKey key){
        return keysMap.get(key.getKeyNum());

    }

    public void addHeadKey(Constants.HeadKey key, Object value) {
        if (!key.getType().isInstance(value)) { // 检查类型
            throw new IllegalArgumentException("type mismatch of key:" + key.getKeyNum() + ", expect:"
                    + key.getType().getName() + ", actual:" + value.getClass().getName());
        }
        keysMap.put(key.getKeyNum(), value);
    }

    public MessageHeader setValues(int protocolType, int codecType,
                                   int msgType, int compressType, int msgId) {
        this.msgId = msgId;
        this.codecType = codecType;
        this.msgType = msgType;
        this.protocolType = protocolType;
        this.compressType = (byte) compressType;
        return this;
    }

    public Map<Byte,Object> getAttrMap(){
        return this.keysMap;
    }

    public MessageHeader copyHeader(MessageHeader header){
        this.msgId = header.msgId;
        this.codecType = header.codecType;
        this.msgType = header.msgType;
        this.protocolType = header.getProtocolType();
        this.compressType = header.getCompressType();
        this.length = header.getLength();
        this.headerLength = header.getHeaderLength();
        Map<Byte,Object> tempMap = header.getKeysMap();
        for(Map.Entry<Byte,Object> entry:tempMap.entrySet()){
            this.keysMap.put(entry.getKey(),entry.getValue());
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageHeader that = (MessageHeader) o;
        return protocolType == that.protocolType &&
                codecType == that.codecType &&
                msgType == that.msgType &&
                msgId == that.msgId &&
                compressType == that.compressType &&
                Objects.equals(length, that.length) &&
                Objects.equals(headerLength, that.headerLength) &&
                Objects.equals(keysMap, that.keysMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(length, headerLength, protocolType, codecType, msgType, msgId, compressType, keysMap);
    }

    @Override
    public String toString() {
        return "MessageHeader{" +
                "length=" + length +
                ", headerLength=" + headerLength +
                ", protocolType=" + protocolType +
                ", codecType=" + codecType +
                ", msgType=" + msgType +
                ", msgId=" + msgId +
                ", compressType=" + compressType +
                ", keysMap=" + keysMap +
                '}';
    }

    @Override
    protected MessageHeader clone() {
        MessageHeader messageHeader;
        try {
            messageHeader = (MessageHeader)super.clone();
        }catch (CloneNotSupportedException e) {
            messageHeader = new MessageHeader();
            messageHeader.copyHeader(this);
        }
        return messageHeader;
    }
}
