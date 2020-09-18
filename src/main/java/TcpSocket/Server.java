package com.jdbc.TcpSocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(6666);// 监听指定端口：监听的端口
        System.out.println("server is run");
        for (;;) {
            Socket socket = ss.accept();// 处理的端口
            System.out.println("connection from" + socket.getRemoteSocketAddress());
            Thread thread = new Handler(socket);
            thread.start();
        }
    }


}

class Handler extends Thread {
    Socket socket;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();// read、writer
            OutputStream outputStream = socket.getOutputStream();
            handle(inputStream, outputStream);
        } catch (IOException e) {
            try {
                this.socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("client disconnected.");
        }


    }


    private void handle(InputStream inputStream, OutputStream outputStream) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        writer.write("hello\n");
        writer.flush();
        for (;;) {
            String s = reader.readLine();
            if (s.equals("bye")) {
                writer.write("bye\n");
                writer.flush();
                break;
            }
            writer.write("from server response: " + s + "\n");
            writer.flush();
        }
    }

}
