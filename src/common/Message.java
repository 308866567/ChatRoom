package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    //源
    int SrcId;//源端口号
    String name;//源名称
    //目的
    public int DesId = -1;//目的端口号
    //数据
    public int flag = 0;//标志位
    // 默认为0,表示为广播,-1表示下线，1表示上线，3表示私聊
    public byte[] image;
    public String txt;

    //传入发送方消息来创建
    public Message(int srcId, String name, String txt) {
        SrcId = srcId;
        this.name = name;
        this.txt = txt;
    }

    @Override
    public String toString() {
        return "端口:" + SrcId + "的用户(" + name + ")说:" + txt;
    }


    //序列化成字节数组
    public static byte[] toByteArray(Message m) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(m);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    //可能会返回空,反序列化
    public static Message getMessage(byte[] t) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(t);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (Message) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("字节转消息失败,丢弃");
            return null;
        }
    }
}