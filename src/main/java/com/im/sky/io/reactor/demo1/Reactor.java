package com.im.sky.io.reactor.demo1;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Reactor implements Runnable {

    final Selector selector;

    final ServerSocketChannel serverSocket;

    Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        serverSocket.configureBlocking(false);
        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor());
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator it = selected.iterator();
                while(it.hasNext()) {
                    dispatch((SelectionKey) it.next());
                }
                selected.clear();
            }
        } catch (Exception e) {}
    }

    void dispatch(SelectionKey k) {
        Runnable r = (Runnable)(k.attachment());
        if(r != null) {
            r.run();
        }
    }

    class Acceptor implements Runnable {
        @Override
        public void run() {
            try {
                SocketChannel sc = serverSocket.accept();
                if(sc != null) {
                    new Handler(selector, sc);
                }
            } catch (IOException e) {

            }
        }
    }
}
