package com.im.sky.netty.zs.msg;

import com.alibaba.fastjson.JSON;
import com.im.sky.netty.zs.compress.CompressType;
import com.im.sky.netty.zs.util.Constants;
import com.im.sky.netty.zs.util.enums.HeadKey;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class MessageHeader implements Cloneable{

    private Integer length;

    private Short headerLength;

    private int protocolType = Constants.DEFAULT_PROTOCOL_TYPE.value();

    private int codecType = Constants.DEFAULT_CODEC_TYPE.type();

    private int msgType;

    private int msgId;

    private byte compressType = CompressType.NONE.type();

    private Map<Byte, Object> keysMap = new ConcurrentHashMap<>();

    public MessageHeader setValues(int protocolType, int codecType, int msgType, int compressType, int msgId) {
        this.protocolType = protocolType;
        this.codecType = codecType;
        this.msgType = msgType;
        this.compressType = (byte)compressType;
        this.msgId = msgId;
        return this;

    }

    public MessageHeader copyHeader(MessageHeader header) {
        this.length = header.length;
        this.headerLength = header.headerLength;
        this.protocolType = header.protocolType;
        this.codecType = header.codecType;
        this.msgType = header.msgType;
        this.msgId = header.msgId;
        this.compressType = header.compressType;
        Map<Byte, Object> tempMap = header.getKeysMap();
        for(Map.Entry<Byte, Object> entry : tempMap.entrySet()) {
            this.keysMap.put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public void addHeadKey(HeadKey headKey, Object value) {
        if(!headKey.getType().isInstance(value)) {
            throw new IllegalArgumentException("type mismatch of key:" + headKey.getNum() + ", expect:"
            + headKey.getType().getName() + ", actual:" + value.getClass().getName());
        }
    }

    public Object removeByKey(HeadKey key) {
        return keysMap.remove(key.getNum());
    }

    public int getAttrMapSize() {
        return keysMap.size();
    }

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

    public Object getAttrByKey(HeadKey key){
        return keysMap.get(key.getNum());

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
                Objects.equals(headerLength, that.headerLength);
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
                ", keysMap=" + JSON.toJSONString(keysMap) +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        MessageHeader header;
        try {
            header = (MessageHeader)super.clone();
        } catch (CloneNotSupportedException e) {
            header = new MessageHeader();
            header.copyHeader(this);
        }
        return header;
    }
}
