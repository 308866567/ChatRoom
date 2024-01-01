package common;

public class server {
    //接收线程
    public static void main(String[] args) {
        //服务端,开启一个线程跑套接字
        ServerSocket serverSocket = new ServerSocket(11111);
        Thread Server = new Thread(serverSocket);
        Server.start();
        //另开一个线程去查看是否有新消息

        Thread get = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (serverSocket.flag_list == 0) {
                        if (!serverSocket.list.isEmpty()) {
                            Message t = serverSocket.list.pollLast();
                            System.out.println(t);
                        } else {
                            System.out.println("没有消息");
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
        });
        get.start();

//        System.out.println("开启了线程");
//        serverSocket.close();
    }
}
