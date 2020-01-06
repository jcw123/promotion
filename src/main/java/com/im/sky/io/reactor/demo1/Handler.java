package com.im.sky.io.reactor.demo1;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Handler implements Runnable {

    final SocketChannel sc;

    final SelectionKey selectionKey;

    ByteBuffer input = ByteBuffer.allocate(1024);
    ByteBuffer output = ByteBuffer.allocate(1024);
    static final int READING = 0, SENDING = 1;
    int state = READING;

    Handler(Selector selector, SocketChannel sc) throws IOException {
        this.sc = sc;
        sc.configureBlocking(false);
        selectionKey = this.sc.register(selector, 0);
        selectionKey.attach(this);
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    boolean inputIsComplete() {
        return true;
    }

    boolean outputIsComplete() {
        return true;
    }

    void process() {

    }

    @Override
    public void run() {
        try {
            if (state == READING) read();
            else if (state == SENDING) send();
        }catch (Exception e) {

        }
    }

    void read() throws IOException {
        sc.read(input);
        if (inputIsComplete()) {
            process();
            state = SENDING;
            selectionKey.interestOps(SelectionKey.OP_WRITE);
        }
    }
    void send() throws IOException {
        sc.write(output);
        if (outputIsComplete()) selectionKey.cancel();
    }

}
