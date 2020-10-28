package com.im.sky.mq;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author jiangchangwei
 * @date 2020-10-10 下午 4:33
 *
 * 发送者发送的消息格式为: topic#&#messageId#&#content，返回的消息格式为OK或者FAIL
 * 消费者获取消息的格式为: topic，发送的消息格式为OK或者FAIL
 *
 * 2bytes:FFEE
 * 2bytes:length(只包含content字段数据长度)
 * 1bytes:command(1:发送的message；2: 表示要消费消息)
 * 变长字段：content
 *
 **/
public class OKMessageBroker {

    private static final int COUNT = 10;

    private static final IdGenerator idGenerator = new IdGenerator();

    private int brokerId;

    public OKMessageBroker() {
        this.brokerId = idGenerator.generate();
    }

    /**
     * 用于注册topic，其中key为topic名称，value为appName，表示是由哪个应用申请注册的
     */
    private ConcurrentMap<String, String> topics = new ConcurrentHashMap<>();

    private ConcurrentMap<String, LinkedBlockingQueue<Message>> queues = new ConcurrentHashMap<>();

    public List<Message> getMessage(String topic) {
        BlockingQueue<Message> queue = queues.get(topic);
        if(queue == null) {
            return null;
        }
        int i = 0;
        List<Message> result = new ArrayList<>();
        while(i++ < COUNT && !queue.isEmpty()) {
            result.add(queue.poll());
        }
        return result;
    }

    public void setMessage(Message message) {
        if(message == null) {
            return;
        }
        String topic = message.getTopic();
        BlockingQueue<Message> queue = queues.computeIfAbsent(topic, t -> new LinkedBlockingQueue<>());
        queue.offer(message);
    }

    public void registerTopic(String topic, String appName) {
        topics.put(topic, appName);
    }

    public void unregisterTopic(String topic) {
        topics.remove(topic);
    }

    public Integer getBrokerId() {
        return brokerId;
    }

    @Override
    public int hashCode() {
        return brokerId;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof OKMessageBroker)) {
            return false;
        }
        OKMessageBroker broker = (OKMessageBroker)obj;
        return this.brokerId == broker.getBrokerId();
    }
}
