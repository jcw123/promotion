package com.im.sky.io.nio.self.v1;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author jiangchangwei
 * @date 2019-12-4 下午 11:28
 **/
public class Server {

    private int flag = 0;

    private final int BLOCK = 2048;

    private ByteBuffer sendBuffer = ByteBuffer.allocate(BLOCK);

    private ByteBuffer receiveBuffer = ByteBuffer.allocate(BLOCK);

    private Selector selector;

    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            try {
                Server server = new Server(8888);
                server.listen();
            }catch (Exception e) {}
        }, "server").start();

    }

    public Server(int port) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 8888));
        serverSocketChannel.configureBlocking(false);
        selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server start...");
    }

    public void listen() throws Exception {
        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                process(selectionKey);
            }
        }
    }

    private void process(SelectionKey selectionKey) throws Exception {
        SocketChannel client = null;
        String sendText;
        if(selectionKey.isAcceptable()) {
            SocketChannel socketChannel1 = ((ServerSocketChannel)selectionKey.channel()).accept();
            socketChannel1.configureBlocking(false);
            socketChannel1.register(selectionKey.selector(), SelectionKey.OP_READ);
        } else if(selectionKey.isWritable()) {
            sendBuffer.clear();
            client = (SocketChannel)selectionKey.channel();
            sendText = "message from server" + flag++;
            sendBuffer.put(sendText.getBytes("UTF-8"));
            sendBuffer.flip();
            client.write(sendBuffer);
            System.out.println("服务端向客户端发送的数据为:" + sendText);
            client.register(selectionKey.selector(), SelectionKey.OP_READ);
        } else if(selectionKey.isReadable()) {
            client = (SocketChannel)selectionKey.channel();
            receiveBuffer.clear();
            StringBuilder sb = new StringBuilder();
            int len;
            while ((len = client.read(receiveBuffer)) > 0) {
                receiveBuffer.flip();
                sb.append(new String(receiveBuffer.array(), 0, len));
                receiveBuffer.clear();
            }
            System.out.println("从客户端接收的数据为:" + sb.toString());
            client.register(selectionKey.selector(), SelectionKey.OP_WRITE);
        }
    }
}
