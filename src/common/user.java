package common;

import java.net.SocketException;

public class user {
    public static void main(String[] args) {
        //
        UserSocket userSocket = new UserSocket("1", "127.0.0.1", 11111);
        Thread rec = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                        userSocket.receiveMessage();
                }
            }
        });
        Thread get = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Message t = userSocket.getMessage();
                    if(t!=null){
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
        Thread send = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!userSocket.sendMessage()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            userSocket.close();
                            return;
//                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
//        rec.start();
//        get.start();
        send.start();

        //发送测试消息
        Message t=userSocket.initMessage();
        t.txt="空";
//        t.flag=-1;//3表示私聊
//        t.DesId=t.SrcId;
        userSocket.addMessage(t);
//		userSocket.sendAll("群聊消息");
//		userSocket.sendAll("私聊消息,标志位3");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
//        Message t=userSocket.initMessage();
        t.txt="下线";
        t.flag=-1;
        userSocket.addMessage(t);
//        send.interrupt();
//        userSocket.close();
    }
}