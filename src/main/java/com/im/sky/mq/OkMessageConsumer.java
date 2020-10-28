package com.im.sky.mq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jiangchangwei
 * @date 2020-10-10 下午 4:32
 **/
public class OkMessageConsumer {

    private Integer consumerId;

    private String topic;

    private static final IdGenerator idGenerator = new IdGenerator();

    private Socket socket;

    public OkMessageConsumer() throws IOException {
        this.consumerId = idGenerator.generate();
        socket = new Socket("127.0.0.1", 9999);
    }

    private Set<OKMessageBroker> brokers = new HashSet<>();

    public void registerBrokers(OKMessageBroker... brokers) {
        this.brokers.addAll(Arrays.asList(brokers));
    }

    public void unregisterBrokers(OKMessageBroker... brokers) {
        for(OKMessageBroker broker : brokers) {
            this.brokers.remove(broker);
        }
    }

    public void consume() throws IOException {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bao.write(Constants.MESSAGE_MARK);
        byte[] topicBytes = topic.getBytes("utf-8");
        int len = topicBytes.length;
        bao.write(len & 0xFF);
        bao.write((len >> 8) & 0xFF);
        bao.write((len >> 16) & 0xFF);
        bao.write((len >> 24) & 0xFF);
        bao.write(2);
        bao.write(topicBytes);
        socket.getOutputStream().write(bao.toByteArray());
        InputStream is = socket.getInputStream();
        ByteArrayOutputStream bao2 = new ByteArrayOutputStream();
        byte[] arr = new byte[1024];
        while((len = is.read(arr)) != -1) {
            bao2.write(arr, 0, len);
        }
        String response = bao2.toString("utf-8");
        if("SUCCESS".equals(response)) {
            System.out.println("获取MQ消息成功");
        }else {
            System.out.println("获取MQ消息失败");
        }
    }
}
