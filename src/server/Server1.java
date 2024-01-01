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
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;

public class Server1 {

    protected Shell shell;
    private Table ulist;
    private Table chatContent;

    public LinkedList<Message> msgs = new LinkedList<Message>();
    ServerSocket serverSocket;
    private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            Server1 window = new Server1();
            //
//            ServerSocket serverSocket = new ServerSocket(11111);
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

        shell.open();
        shell.layout();
//        initSocket();
        int i=0;
        while (!shell.isDisposed()) {
        	//TODO 
//        	addMessage("新消息"+(++i),chatContent);
            if (!display.readAndDispatch()) {
                initSocket();
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
        headshot.setWidth(60);

        TableColumn uname = new TableColumn(ulist, SWT.BORDER | SWT.FULL_SELECTION);
        uname.setWidth(150);
        TableItem item = new TableItem(ulist, SWT.NONE);
        item.setText(0, "头像");
        item.setText(1, "用户名");
        ulist.redraw();

        //添加一行
        Message t = new Message();
        t.name = "服务器0";
        t.txt = "欢迎来到服务器";
        t.SrcId = 0;
        addUser(t, ulist);
        removeUser(t,ulist);

        //消息框
        chatContent = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        chatContent.setBounds(241, 10, 620, 621);

        TableColumn username = new TableColumn(chatContent, SWT.BORDER | SWT.FULL_SELECTION);
        username.setWidth(150);

        TableColumn cstate = new TableColumn(chatContent, SWT.BORDER | SWT.FULL_SELECTION);
        cstate.setWidth(50);

        TableColumn content = new TableColumn(chatContent, SWT.BORDER | SWT.FULL_SELECTION);
        content.setWidth(400);
        TableItem item1 = new TableItem(chatContent, SWT.NONE);
        item1.setText(0, "用户名");
        item1.setText(1, "状态");
        item1.setText(2, "内容");
        ulist.redraw();
        addMessage(t, chatContent);

    }

    void initSocket() {
        // 创建服务器套接字
        // 添加服务器接收消息的线程
//        Display.getDefault().asyncExec(
//                new Thread() {
//                    @Override
//                    public void run() {
//                        while (true) {
        if (msgs.isEmpty()) {
            return;
        }
        System.out.println("处理消息");
        Message msg = msgs.getFirst();
        msgs.removeFirst();
        // 在服务器面板上显示接收到的消息 TODO
        switch (msg.flag) {
            // 下线
            case -1:
                addUser(msg, ulist);
                addMessage(msg, chatContent);
                break;
            // 上线
            case 1:
//                removeUser(msg, ulist);

                addMessage(msg, chatContent);
                break;
            // 接收到一条群聊
            case 0:
                addMessage(msg, chatContent);
                break;
            // 接收到一条私聊
            case 3:
                addMessage(msg, chatContent);
                break;
            default:
                break;
        }
//                        }
//                    }
//                }
//        );
    }

    void addUser(Message msg, Table table) {
        // 加一行表格
        TableItem item = new TableItem(table, SWT.NONE);
        // 在第一列中插入 Label 控件 显示头像信息
	      TableEditor editor = new TableEditor(table);
	      Label label = new Label(table, SWT.NONE);
	      Image o = SWTResourceManager.getImage("/images/1.jpg");
	      label.setImage(o);
//	      label.setImage(toImage(msg.image));
	      editor.grabHorizontal = true;
	      editor.setEditor(label, item, 0);
//        item.setText(0, msg.SrcId + "");
        item.setText(1, msg.name);
        item.setData("l", label);
        table.redraw();
//        return label;
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

    //未测试
    void removeUser(Message msg, Table table) {
        System.out.println(msg.txt);
        String name = msg.name;
        // 删除一行表格
        TableItem[] items = table.getItems();
       
        for (TableItem item : items) {
        	
            if (item.getText(1).equals(name)) {
                // 移除这个Item从表格
//                table.remove(table.indexOf(item));
                // 获取 TableItem 的第一列的控件（即Label）
               
                
//                item.setData("l", items);
                Label t =(Label)item.getData("l");
//                Label t =(Label)item.getData();
                t.dispose();
            	item.dispose();
                break;
            }
        }
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
	        // 接收到一条群聊
	        case 0:
	        	item.setText(1,"群聊");
	            break;
	        // 接收到一条私聊
	        case 3:
	        	item.setText(1,"私聊");
	            break;
	        default:
	        	item.setText(1,"未知");
        }
        
        table.redraw();
    }
}
