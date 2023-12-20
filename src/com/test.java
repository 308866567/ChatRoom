package com;

public class test{
	public static void main(String[] args) {
		UserSocket a =new UserSocket("127.0.0.1",11111);
		ServerSocket s =new ServerSocket("127.0.0.1",11111);
		Message msg= new Message(1,"1");
		msg.txt="我是1";
		a.send(msg);
		Message t=s.receive();
		System.out.println(t.txt);
	}
}