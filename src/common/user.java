package common;

public class user {
	public static void main(String[] args) {
		//
		UserSocket userSocket=new UserSocket("1","127.0.0.1",11111);
//		Thread rec = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				while (true){
//					userSocket.receiveMessage();
//				}
//			}
//		});
//		Thread get = new Thread(new Runnable() {
//			@Override
//			public void run() {
//				while (true){
//					Message t = userSocket.getMessage();
//					if (t == null) {
//						try {
//							Thread.sleep(1500);
//						} catch (InterruptedException e) {
//							throw new RuntimeException(e);
//						}
//					}
//				}
//			}
//		});
//		rec.start();
//		get.start();
		userSocket.sendAll("群聊消息");
		userSocket.sendAll("私聊消息,标志位3");
		userSocket.close();
	}
}