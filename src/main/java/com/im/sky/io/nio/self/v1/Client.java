package com.im.sky.io.nio.self.v1;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author jiangchangwei
 * @date 2019-12-5 上午 12:08
 **/
public class Client {

    private static int flag = 0;

    private static int BLOCK = 4096;

    private static ByteBuffer sendBuffer = ByteBuffer.allocate(BLOCK);

    private static ByteBuffer receiveBuffer = ByteBuffer.allocate(BLOCK);

    private final static InetSocketAddress SERVER_ADDRESS = new InetSocketAddress("127.0.0.1", 8888);

    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            try {
                SocketChannel socketChannel = SocketChannel.open();
                socketChannel.configureBlocking(false);
                Selector selector = Selector.open();
                socketChannel.register(selector, SelectionKey.OP_CONNECT);
                socketChannel.connect(SERVER_ADDRESS);
                Set<SelectionKey> selectionKeys;
                Iterator<SelectionKey> iterator;
                SelectionKey selectionKey;
                SocketChannel client;
                String sendText;
                while (true) {
                    selector.select();
                    selectionKeys = selector.selectedKeys();
                    iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        selectionKey = iterator.next();
                        if (selectionKey.isConnectable()) {
                            System.out.println("client connect");
                            client = (SocketChannel) selectionKey.channel();
                            if (client.isConnectionPending()) {
                                client.finishConnect();
                                System.out.println("完成连接");
                                sendBuffer.clear();
                                sendBuffer.put("Hello, Server".getBytes());
                                sendBuffer.flip();
                                client.write(sendBuffer);
                            }
                            client.register(selectionKey.selector(), SelectionKey.OP_READ);
                        } else if (selectionKey.isReadable()) {
                            client = (SocketChannel) selectionKey.channel();
                            receiveBuffer.clear();
                            StringBuilder sb = new StringBuilder();
                            int len;
                            while ((len = client.read(receiveBuffer)) > 0) {
                                sb.append(new String(receiveBuffer.array(), 0, len));
                            }
                            System.out.println("客户端接收服务端的数据为:" + sb.toString());
                            client.register(selectionKey.selector(), SelectionKey.OP_WRITE);
                        } else if (selectionKey.isWritable()) {
                            sendBuffer.clear();
                            client = (SocketChannel) selectionKey.channel();
                            sendText = "message from client--" + flag++;
                            sendBuffer.put(sendText.getBytes());
                            sendBuffer.flip();
                            client.write(sendBuffer);
                            System.out.println("客户端向服务端发送数据:" + sendText);
                            client.register(selectionKey.selector(), SelectionKey.OP_READ);
                        }
                        selectionKeys.clear();
                    }
                }
            }catch (Exception e) {}
        }, "client").start();
    }


}
