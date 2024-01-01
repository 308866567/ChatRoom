package server;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import common.Message;
import common.ServerSocket;
import util.SwtUtils;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;

public class Server1 {

    protected Shell shell;
    private Table ulist;
    private Table chatContent;
    private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

    //套接字
    int port=11111;
    ServerSocket serverSocket = new ServerSocket(port);

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            Server1 window = new Server1();
            window.open();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        Display display = Display.getDefault();
        createContents();
        //TODO 添加接收线程
        Thread rec = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    serverSocket.receiveMessage();
                }
            }
        });
        rec.start();




        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            //TODO 显示消息
            Message t = serverSocket.getMessage();
            if (t != null) {
                System.out.println("\n-------------------\n");
                System.out.println(t);
                System.out.println("\n-------------------\n");
                solveMessage(t);
            }
            //
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }


    /**
     * Create contents of the window.
     */
    protected void createContents() {

        shell = new Shell();
        shell.setSize(900, 700);
        shell.setText("聊天室服务器");
        SwtUtils.centerWin(shell);

        // 用户列表 用户信息,用户头像
        ulist = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        ulist.setBounds(10, 10, 225, 621);

        //column只用于定义列的格式
        TableColumn headshot = new TableColumn(ulist, SWT.BORDER | SWT.FULL_SELECTION);
        headshot.setWidth(110);

        TableColumn uname = new TableColumn(ulist, SWT.BORDER | SWT.FULL_SELECTION);
        uname.setWidth(110);
        TableItem item = new TableItem(ulist, SWT.NONE);
        item.setText(0, "用户端口号");
        item.setText(1, "用户名");
        ulist.redraw();

        //消息框
        chatContent = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        chatContent.setBounds(241, 10, 620, 621);

        TableColumn username = new TableColumn(chatContent, SWT.BORDER | SWT.FULL_SELECTION);
        username.setWidth(100);

        TableColumn cstate = new TableColumn(chatContent, SWT.BORDER | SWT.FULL_SELECTION);
        cstate.setWidth(100);

        TableColumn content = new TableColumn(chatContent, SWT.BORDER | SWT.FULL_SELECTION);
        content.setWidth(400);
        TableItem item1 = new TableItem(chatContent, SWT.NONE);
        item1.setText(0, "用户名");
        item1.setText(1, "消息类型");
        item1.setText(2, "内容");

//        Message msg=new Message(999,"用户1","消息1");
//        msg.flag=1;
//        for (int i=0;i<100;i++)
//         addMessage(msg, chatContent);
//        addUser(msg, ulist);
//        addUser(msg, ulist);
//        removeUser(msg,ulist);
    }

    void solveMessage(  Message msg ) {
        System.out.println("UI处理消息"+msg.flag);
        // 在服务器面板上显示接收到的消息 TODO
        switch (msg.flag) {
            // 下线
            case -1:
                System.out.println("删除");
                removeUser(msg, ulist);
                addMessage(msg, chatContent);
                break;
            // 上线
            case 1:
                addMessage(msg, chatContent);
                addUser(msg, ulist);
                break;
            default:
                addMessage(msg, chatContent);
                break;
        }
    }

    
    
    //将massage中的image字节数组转换为image
    Image toImage(byte[] bytes) {
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.load(new ByteArrayInputStream(bytes));
        ImageData[] imageDataArray = imageLoader.data;
        if (imageDataArray.length > 0) {
//            return new Image(display, imageDataArray[0]);
            return new Image(Display.getDefault(), imageDataArray[0]);
        } else {
            return null;
        }
    }

    HashMap<String,TableItem> userItem=new HashMap<>();


    void addUser(Message msg, Table table) {
        // 加一行表格
        TableItem item = new TableItem(table, SWT.NONE);
        item.setText(0,msg.SrcId+"");
        item.setText(1, msg.name+"");
        table.setData(msg.SrcId+"",item);
//        TableItem item = (TableItem )   table.getData(msg.SrcId+"");
        table.redraw();
        // 在第一列中插入 Label 控件 显示头像信息
//        TableEditor editor = new TableEditor(table);
//        Label label = new Label(table, SWT.NONE);
//        Image o = SWTResourceManager.getImage("/images/1.jpg");
//        label.setImage(o);
////	      label.setImage(toImage(msg.image));
//        editor.grabHorizontal = true;
////        editor.setEditor(label, item, 0);
////        item.setText(0, msg.SrcId + "");
//        item.setData("l", label);
//        item.setData("id", msg.SrcId+"");
//        userItem.put(msg.SrcId+"",item);//放哈希
        table.redraw();
//        return label;
    }


    //未测试
    // 删除一行用户
    void removeUser(Message msg, Table table) {

//        TableItem[] items = table.getItems();
//        for (TableItem item : items) {
//            if ((item.getData("id")+"").equals(msg.SrcId+"")) {
//                Label t =(Label)item.getData("l");
//                t.dispose();
//            	item.dispose();
//                break;
//            }
//        }

//        System.out.println("删除"+msg.SrcId);
//        TableItem item=userItem.get(msg.SrcId+"");
        TableItem item = (TableItem )   table.getData(msg.SrcId+"");
        if(item==null) {
            System.out.println("item空");
            return;
        }
        item.dispose();
        table.redraw();
    }


    void addMessage(Message msg, Table table) {
        System.out.println(msg.txt);
        // 加一行表格
        TableItem item = new TableItem(table, SWT.NONE);
        if (msg.name != null)
            item.setText(0, msg.name);
        if (msg.txt != null)
            item.setText(2, msg.txt);
        switch (msg.flag) {
            case 1:
                item.setText(1,"上线消息");
                break;
            case -1:
                item.setText(1,"下线消息");
                break;
	        // 接收到一条群聊
	        case 0:
	        	item.setText(1,"群聊消息");
	            break;
	        // 接收到一条私聊
	        case 3:
	        	item.setText(1,"私聊消息");
	            break;
	        default:
	        	item.setText(1,"未知消息");
        }
        
        table.redraw();
    }
}
