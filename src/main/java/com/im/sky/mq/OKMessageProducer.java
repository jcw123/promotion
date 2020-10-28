package com.im.sky.mq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * @author jiangchangwei
 * @date 2020-10-10 下午 4:32
 **/
public class OKMessageProducer {

    private Integer producerId;

    private static final IdGenerator idGenerator = new IdGenerator();

    public OKMessageProducer() throws IOException {
        this.producerId = idGenerator.generate();
        socket = new Socket("127.0.0.1",9999);
    }

    private Socket socket;

    public static void main(String[] args) throws Exception {
        Message message = new Message("test", "1", "hehe");
        OKMessageProducer producer = new OKMessageProducer();
        producer.produce(message);
    }

    public void produce(Message message) throws IOException {
        String topic = message.getTopic();
        String messageId = message.getMessageId();
        String content = message.getContent();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(Constants.MESSAGE_MARK);
        int len = 0;
        byte[] topicArr = topic.getBytes(StandardCharsets.UTF_8);
        len += topicArr.length;
        byte[] messageIdArr = messageId.getBytes(StandardCharsets.UTF_8);
        len += messageIdArr.length;
        byte[] contentArr = content.getBytes(StandardCharsets.UTF_8);
        len += contentArr.length;
        len += Constants.DELIMITER_BYTES.length * 2;
        bos.write((byte)(len & 0xFF));
        bos.write((byte)((len >> 8) & 0xFF));
        bos.write((byte)((len >> 16) & 0xFF));
        bos.write((byte)((len >> 24) & 0xFF));
        bos.write(1);
        bos.write(topicArr);
        bos.write(Constants.DELIMITER_BYTES);
        bos.write(messageIdArr);
        bos.write(Constants.DELIMITER_BYTES);
        bos.write(contentArr);
        socket.getOutputStream().write(bos.toByteArray());
        socket.shutdownOutput();
        System.out.println("生产者发送数据中");
        InputStream is = socket.getInputStream();
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        byte[] arr = new byte[1024];
        while((len = is.read(arr)) != -1) {
            bao.write(arr, 0, len);
        }
        String response = bao.toString("utf-8");
        if("SUCCESS".equals(response)) {
            System.out.println("发送MQ消息成功");
        }else {
            System.out.println("发送MQ消息失败");
        }
    }
}
