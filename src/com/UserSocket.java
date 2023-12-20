package com;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

//存储客户端的用户信息
public class UserSocket {
	int id;// 为套接字绑定的端口
	String name;
	byte[] image;
	DatagramSocket datagramSocket;
	SocketAddress address;//服务器地址

	//传入服务器地址
	UserSocket(String name,String IP,int port) {
		this.name=name;
		try {
			datagramSocket = new DatagramSocket();// 客户端随机绑定一个本地主机的可用端口
			id = datagramSocket.getLocalPort();
			address=new InetSocketAddress(IP, port);
		} catch (Exception e) {
			System.out.println("客户端套接字创建失败");
			e.printStackTrace();
		}
		send(name+"上线",1);
	}

	//消息加标志位
	public void send(String msg,int flag) {
		Message m= new Message();
		m.txt=msg;
		m.flag=flag;
		send(m);
	}
	
	// 给服务端发送Message类的字节流
	public void send(Message msg) {
		msg.setSrcId(datagramSocket.getLocalPort());
		msg.setName(name);
		byte[] t = Message.toByteArray(msg);
		// 数据包设置服务器地址
		DatagramPacket datagramPacket;
		try {
			datagramPacket = new DatagramPacket(t, t.length,address);
			datagramSocket.send(datagramPacket);
		} catch (Exception e) {
			System.out.println("客户端发包失败");
			e.printStackTrace();
		}
	}

	// 只接收发往套接字本地地址的包，地址只有服务器知道
	public Message receive() {
		byte[] buffer = new byte[1024 * 64];
		// 接收发往套接字本地地址的包
		DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length,
				datagramSocket.getLocalSocketAddress());
		try {
			datagramSocket.receive(datagramPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int len = datagramPacket.getLength();
		byte[] t = new byte[len];
		System.arraycopy(buffer, 0, t, 0, len);
		return Message.getMessage(t);
	}

	public void close() {
		send(name+"下线",-1);
		datagramSocket.close();
	}
}
