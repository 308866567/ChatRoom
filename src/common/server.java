package common;

import java.net.SocketException;

public class server {
    //接收线程
    public static void main(String[] args) {
        //服务端,开启一个线程跑套接字
        ServerSocket serverSocket = new ServerSocket(11111);
        Thread rec = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        serverSocket.datagramSocket.setSoTimeout(500);
                    } catch (SocketException e) {
                        throw new RuntimeException(e);
                    }
                    serverSocket.receiveMessage();
                }
            }
        });
        Thread get = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Message t = serverSocket.getMessage();
                    if (t != null) {
                        System.out.println("\n-------------------\n");
                        System.out.println(t);
                        System.out.println("\n-------------------\n");
                    }

                    if (t == null) {
                        try {
                            Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
        rec.start();
        get.start();
//        System.out.println("开启了线程");
        serverSocket.close();
    }
}
