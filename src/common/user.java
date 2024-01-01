package common;

public class user {
	public static void main(String[] args) {
		UserSocket t=new UserSocket("1","127.0.0.1",11111);
		t.send("群聊消息,标志位0",0);
		t.send("私聊消息,标志位3",3);
		t.close();
	}
}