package common;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;

//存储客户端的用户信息
public class UserSocket {
    int id;// 套接字创建后,随机绑定的端口
    String name;
    DatagramSocket datagramSocket;
    InetSocketAddress address;//服务器地址
    LinkedList<Message> list = new LinkedList<>();//消息队列,临界资源
    int flag_list = 0;//0可用

    public Message initMessage() {
        return new Message(id, name, "");
    }


    //开线程循环调用,接收线程:循环接收来自客户端的消息,并进行分发
    //接收消息,缓存到消息队列里,等待外界进行接收处理
    public void receiveMessage() {
        try {
            Message msg = socketReceive();//阻塞,接收到了才会执行
            if (msg == null) {
                System.out.println("消息为空");
                return;
            }
            System.out.println("接收到消息");
            //接收到的消息放到队列里
            flag_list = 1;
            list.push(msg);
            flag_list = 0;
            System.out.println("消息处理完成");
        } catch (Exception e) {
            System.out.println("服务端接收线程超时或出错");
            e.printStackTrace();
        }
    }

    //查看是否有新消息,开线程循环调用
    public Message getMessage() {
        if (flag_list == 0) {
            flag_list = 1;
            if (!list.isEmpty()) {
                Message t = list.pollLast();
                flag_list = 0;
//                System.out.println(t);
                return t;
            } else {
                System.out.println("没有接收到消息");
            }
            flag_list = 0;
        }
        System.out.println("list正在被使用");
        return null;
    }

    LinkedList<Message> sendList = new LinkedList<>();//要发送消息的等待队列
    int flag_sendList = 0;

    //循环使用
    public boolean sendMessage() {
        if (flag_sendList == 0) {
            flag_sendList = 1;
            if (!sendList.isEmpty()) {
                Message t = sendList.pollLast();
                send(t);
                flag_sendList = 0;
                return true;
//                System.out.println(t);
            } else {
                System.out.println("没有要发送的消息");
            }
            flag_sendList = 0;
        }
        System.out.println("发送,sendList正在被使用");
        flag_sendList = 0;
        return false;
    }
    //单次调用,阻塞添加
    public void addMessage(Message msg) {
        while (flag_sendList!=0) ;
        if (flag_sendList == 0) {
            flag_sendList = 1;
            sendList.push(msg);
            flag_sendList = 0;
        } else {
            System.out.println("添加,sendList正在被使用");
        }
        return;
    }

    //传入服务器地址
    public UserSocket(String name, String ip, int port) {
        this.name = name;
        try {
            datagramSocket = new DatagramSocket();// 客户端随机绑定一个本地主机的可用端口
            id = datagramSocket.getLocalPort();
            address = new InetSocketAddress(ip, port);
        } catch (Exception e) {
            System.out.println("客户端套接字创建失败");
            e.printStackTrace();
        }
        //发送上线通知
        Message t = new Message(id, name, name + "上线");
        t.flag = 1;
        send(t);
        try {
            datagramSocket.setSoTimeout(1000);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    // 给服务端发送Message类的字节流
    // 给一个网络地址发送消息,1对1
    void send(Message msg) {
        //检查传入的参数正确性
        if (msg == null) {
            System.out.println("消息为空");
            return;
        }
        //消息转为字节流,进行发送
        try {
            byte[] t = Message.toByteArray(msg);
            //创建数据包
            DatagramPacket datagramPacket;
            //指定接收方的IP和端口
            datagramPacket = new DatagramPacket(t, t.length, address);
            datagramSocket.send(datagramPacket);
        } catch (Exception e) {
            System.out.println("服务器发包失败");
            e.printStackTrace();
        }
    }

    //	接收缓冲区
    static byte[] buffer = new byte[1024 * 64];


    Message socketReceive() {
        try {
            // 只接收发往套接字本地地址的包，地址只有服务器知道
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length,
                    datagramSocket.getLocalSocketAddress());
            //阻塞接收  TODO
            datagramSocket.receive(datagramPacket);
            //反序列化
            int len = datagramPacket.getLength();
            byte[] t = new byte[len];
            System.arraycopy(buffer, 0, t, 0, len);
            return Message.getMessage(t);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        //发送上线通知
        Message t = new Message(id, name, name + "上线");
        t.flag = 1;
        send(t);
        datagramSocket.close();
    }
}
