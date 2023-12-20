package common;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashSet;

//存储服务端的信息
public class ServerSocket implements Runnable{
	int id;
	String name;
	DatagramSocket datagramSocket;

	// 端口表,set,存储客户端的端口
	HashSet<Integer> users = new HashSet<>();

	// 服务端创建,监听本机地址的一个端口
	public	ServerSocket(int port) {
		try {
			id = port;
			datagramSocket = new DatagramSocket(port);// 服务器绑定到一个本机地址上的指定端口
		} catch (Exception e) {
			System.out.println("服务端套接字创建失败");
			e.printStackTrace();
		}
	}

	// 给客户端发送消息,需传入客户端地址
	public void send(Message msg, String IP, int port) {
		if (!users.contains(port)) {
			System.out.println("客户端" + port + "已经下线");
			return;
		}
		msg.setSrcId(datagramSocket.getLocalPort());
		msg.setName(name);
		byte[] t = Message.toByteArray(msg);
		// 数据包设置服务器地址
		DatagramPacket datagramPacket;
		try {
			datagramPacket = new DatagramPacket(t, t.length, InetAddress.getByName(IP), port);
			datagramSocket.send(datagramPacket);
		} catch (Exception e) {
			System.out.println("服务器发包失败");
			e.printStackTrace();
		}
	}

	// 接受来自客户端的消息,进行处理和分发
	public Message receive() throws Exception {
		byte[] buffer = new byte[1024 * 64];
		DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length,
		datagramSocket.getLocalSocketAddress());
		datagramSocket.receive(datagramPacket);
		int len = datagramPacket.getLength();
		byte[] t = new byte[len];
		System.arraycopy(buffer, 0, t, 0, len);
		return Message.getMessage(t);
	}

	// 处理和分发来自客户端的消息
	public void solve(Message msg) {
//		System.out.println(msg.SrcId+msg.name+"说"+msg.txt);
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
				send(msg, "127.0.0.1", t);
			}
			break;
		// 私聊
		case 3:
			send(msg, "127.0.0.1", msg.DesId);
			break;
		}
	}

	public void close() {
		datagramSocket.close();
	}

	//用线程接收消息
	@Override
	public void run() {
		while (true) {
			try {
				solve(receive());
			} catch (Exception e) {
				System.out.println("服务器接收异常");
				close();
				e.printStackTrace();
			}
		}

	}
}
