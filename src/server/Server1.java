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

public class Server1 {

	protected Shell shell;
	private Table ulist;
	private Table chatContent;

	ServerSocket serverSocket;

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
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
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
		Message t = new Message();
		t.txt="消息1";
		addMessage(t, ulist);

		//column只用于定义列的格式
		TableColumn headshot = new TableColumn(ulist, SWT.BORDER | SWT.FULL_SELECTION);
		headshot.setWidth(60);

		TableColumn uname = new TableColumn(ulist, SWT.BORDER | SWT.FULL_SELECTION);
		uname.setWidth(150);
		

		
		//消息框
		TableViewer tableViewer_1 = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		chatContent = tableViewer_1.getTable();
		chatContent.setBounds(241, 10, 620, 621);

		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn username = tableViewerColumn_2.getColumn();
		username.setWidth(150);
		username.setText("New Column");

		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn cstate = tableViewerColumn_3.getColumn();
		cstate.setWidth(40);
		cstate.setText("New Column");

		TableViewerColumn tableViewerColumn_4 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn content = tableViewerColumn_4.getColumn();
		content.setWidth(400);
		content.setText("New Column");

		// 创建服务器套接字
		serverSocket = new ServerSocket(11111);
		// 添加服务器接收消息的线程
		Display.getDefault().asyncExec(new Thread() {
			@Override
			public void run() {
				while (true) {
					try {
						Message msg = serverSocket.receive();
						// 分发
						serverSocket.solve(msg);
						// 在服务器面板上显示接收到的消息 TODO
						switch (msg.flag) {
						// 下线
						case -1:

							break;
						// 上线
						case 1:

							break;
						// 接收到一条群聊
						case 0:
							addMessage(msg, ulist);
							break;
						// 接收到一条私聊
						case 3:

							break;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

	void addMessage(Message msg, Table table) {
		System.out.println(msg.txt);
		// 加一行表格
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(0, msg.txt);
		item.setText(1, msg.txt);
	}

}
