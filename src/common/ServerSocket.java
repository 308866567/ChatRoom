package common;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashSet;
import java.util.LinkedList;

import static java.lang.Thread.sleep;

//存储服务端的信息
public class ServerSocket implements Runnable {
    int port;//服务端监听的端口号
    String name = "服务器";//服务端名称
    //临界资源
    DatagramSocket datagramSocket;//UDP套接字
    int messageSize = 0;//消息标志
    HashSet<Integer> users;//用于存储客户端的源地址

    LinkedList<Message> list= new LinkedList<>();//消息队列,临界资源
    int flag_list=0;//0可用

    // 创建服务端,监听本机地址的一个端口
    public ServerSocket(int port) {
        this.port = port;
        users = new HashSet<>();
        try {
            datagramSocket = new DatagramSocket(port);// 服务器绑定到一个本机地址上的指定端口
        } catch (SocketException e) {
            System.out.println("服务端套接字创建失败");
            e.printStackTrace();
        }
    }

    // 给一个网络地址发送消息
    public void send(Message msg, String IP, int port) {
        //检查传入的参数正确性 TODO
        if (msg == null || IP == null) {
            return;
        }
        if (!users.contains(port)) {
            System.out.println("客户端" + port + "不在线");
            return;
        }
        //消息添加发送方的信息
        msg.SrcId = datagramSocket.getLocalPort();
        msg.name = name;
        //消息转为字节流,进行发送
        byte[] t = Message.toByteArray(msg);
        //创建数据包
        DatagramPacket datagramPacket;
        try {
            //指定接收方的IP和端口
            datagramPacket = new DatagramPacket(t, t.length, InetAddress.getByName(IP), port);
            datagramSocket.send(datagramPacket);
        } catch (Exception e) {
            System.out.println("服务器发包失败");
            e.printStackTrace();
        }
    }

    //用于接收的缓冲区,只有receive函数使用
    byte[] buffer = new byte[1024 * 64];
    DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
    // 接受所监听端口的消息,进行处理和分发

    //可能返回空
    public Message receive() {
        //接收的数据包
        try {
            datagramSocket.receive(datagramPacket);
        } catch (IOException e) {
            return null;
        }
        //复制出实际接收到的数据
        int len = datagramPacket.getLength();
        byte[] t = new byte[len];
        System.arraycopy(buffer, 0, t, 0, len);
        //字节数组,反序列化转化为Message
        Message msg = Message.getMessage(t);
        //新接收到的消息放入消息队列等待处理 TODO
        return msg;
    }

    // 处理和分发来自客户端的消息,注意跳过发送方
    public void solve(Message msg) {
        //根据消息的内容来分发消息
        switch (msg.flag) {
            // 下线
            case -1:
                users.remove(msg.DesId);
                System.out.println(msg.txt);
                break;
            // 上线
            case 1:
                users.add(msg.SrcId);
                System.out.println(msg.txt);
                break;
            // 群聊
            case 0:
                for (Integer t : users) {
                    if (t != msg.SrcId)
                        send(msg, "127.0.0.1", t);
                }
                break;
            // 私聊
            case 3:
                send(msg, "127.0.0.1", msg.DesId);
                break;
        }
    }

    //关闭服务端
    public void close() {
        datagramSocket.close();
    }

    //接收线程:循环接收来自客户端的消息,并进行分发
    //接收消息,缓存到消息队列里,等待外界进行接收处理
    @Override
    public void run() {
        while (true) {
            try {
                Message msg = receive();
                System.out.println("接收到消息");
                if (msg == null) {
                    System.out.println("消息丢失");
                    continue;
                }
                //发消息给客户端
//                solve(msg);
                //接收到的消息放到队列里
                flag_list = 1;
                list.push(msg);
                flag_list = 0;
                System.out.println("消息处理完成");
            } catch (Exception e) {
                System.out.println("服务端接收线程出错");
                e.printStackTrace();
            }
        }
    }
}
