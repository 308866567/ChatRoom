package server;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import common.Message;
import common.ServerSocket;
import util.SwtUtils;

import java.util.LinkedList;

public class Server1 {

    protected Shell shell;
    private Table ulist;
    private Table chatContent;

    public LinkedList<Message> msgs = new LinkedList<Message>();
    ServerSocket serverSocket;

    /**
     * Launch the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            Server1 window = new Server1();
            //
            ServerSocket serverSocket = new ServerSocket(11111);
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
        while (!shell.isDisposed()) {
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
        //添加一行
        Message t = new Message();
        t.name = "服务器0";
        t.txt = "欢迎来到服务器";
        t.SrcId = 0;
        addUser(t, ulist);

        //消息框
        chatContent = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        chatContent.setBounds(241, 10, 620, 621);

        TableColumn username = new TableColumn(chatContent, SWT.BORDER | SWT.FULL_SELECTION);
        username.setWidth(150);

        TableColumn cstate = new TableColumn(chatContent, SWT.BORDER | SWT.FULL_SELECTION);
        cstate.setWidth(40);

        TableColumn content = new TableColumn(chatContent, SWT.BORDER | SWT.FULL_SELECTION);
        content.setWidth(400);
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
                removeUser(msg, ulist);

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
        item.setText(0, msg.SrcId + "");
        item.setText(1, msg.name);
        table.redraw();
    }

    //未测试
    void removeUser(Message msg, Table table) {
        System.out.println(msg.txt);
        String name = msg.name;
        // 删除一行表格
        TableItem[] items = table.getItems();
        for (TableItem item : items) {
            if (item.getText(0).equals(msg.SrcId + "")) {
                // 移除这个Item从表格
                table.remove(table.indexOf(item));
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
        table.redraw();
    }

}
