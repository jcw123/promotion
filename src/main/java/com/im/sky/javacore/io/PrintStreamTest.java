package com.im.sky.javacore.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author jiangchangwei
 * @date 2020-10-15 下午 7:56
 **/
public class PrintStreamTest {

    private static ServerSocket serverSocket;

    public static void main(String[] args) throws Exception {
        serverSocket = new ServerSocket(9999);
        new Thread(() -> {
            try {
                Socket socket = serverSocket.accept();
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                printStream.println("你好你好\n");
            }catch (Exception e) {

            }
        }).start();
        Socket socket = new Socket("127.0.0.1", 9999);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("client:" + br.readLine());
        PrintStream stream = new PrintStream(System.out);
        stream.print(1);
        stream.print(true);
    }
}
