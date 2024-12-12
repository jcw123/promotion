package com.im.sky.io.nio.self.v2;

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
 * @since 2024/11/22
 */
public class Server {

    public static void main(String[] args) throws Exception {
        run();
    }

    private static void run() throws Exception {
        // 服务端连接通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8888));
        serverSocketChannel.configureBlocking(false);

        // 创建一个选择器
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while(true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if(selectionKey.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }else if(selectionKey.isReadable()) {
                    // 处理读事件
                    SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(256);
                    int bytesRead = clientChannel.read(buffer);
                    if (bytesRead == -1) {
                        clientChannel.close();
                    } else {
                        buffer.flip();
                        clientChannel.write(buffer);
                        buffer.clear();
                    }
                }
                // 移除已处理的键
                iterator.remove();
            }
        }
    }
}
