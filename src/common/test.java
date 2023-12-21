package common;

public class test{
	public static void main(String[] args) {
		UserSocket t=new UserSocket("1","127.0.0.1",11111);
		t.send("群聊",0);
		t.send("私聊",3);
		t.close();
	}
}