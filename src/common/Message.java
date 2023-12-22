package common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    public int id=-1;
    //源
    public int SrcId = 0;//源端口
    public String name="";//源名称
    //目的
    public int DesId = -1;
    //数据
    public int flag = -1;//标志位
    // 默认为0,表示为广播,-1表示下线，1表示上线，3表示私聊
    public byte[] image;
    public String txt="";


    @Override
    public String toString() {
        return "端口:" + SrcId + "的" + name + "说:" + txt;
    }

    public static byte[] toByteArray(Message m) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(m);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static Message getMessage(byte[] t) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(t);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
            return (Message) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}