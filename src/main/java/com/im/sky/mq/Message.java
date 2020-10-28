package com.im.sky.mq;

/**
 * @author jiangchangwei
 * @date 2020-10-10 下午 4:34
 **/
public class Message {

    private String topic;

    private String messageId;

    private String content;

    public Message(String topic, String messageId, String content) {
        this.topic = topic;
        this.messageId = messageId;
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
