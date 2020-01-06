package com.im.sky.netty.example.socket;

import com.sun.xml.internal.ws.util.xml.CDATA;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @author jiangchangwei
 * @date 2019-12-20 下午 5:21
 **/
public class Client {

    private String host;

    private int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new Client("localhost", 3306).run();
    }

    public void run() throws UnknownHostException, IOException {
        Socket socket = new Socket(host, port);
        System.out.println(socket.isConnected());
        OutputStream os = socket.getOutputStream();
        os.write("select 1;".getBytes(Charset.forName("utf-8")));
        os.flush();
        InputStream is = socket.getInputStream();
        byte[] data = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int len;
        if((len = is.read(data)) != -1) {
           baos.write(data, 0, len);
            System.out.println(baos.toString());
        }
    }
}
