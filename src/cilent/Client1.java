package cilent;

import java.io.ByteArrayInputStream;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import common.Message;
import common.UserSocket;
import util.SwtUtils;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

public class Client1 {
    private Table ulist;
    private Table chatContent;
	protected Shell shell;
	private Table user_list;
	private Table chat;
	private Text text;
	String messageString;
	private String imgPath = "";
	// 套接字
	UserSocket userSocket;
	String name;
	String IP;
	int port;
	

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Client1 window = new Client1();
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
		shell.setText("群聊");
		
		// 用户列表 用户信息,用户头像
        ulist = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        ulist.setBounds(10, 10, 225, 622);

        //column只用于定义列的格式
        TableColumn headshot = new TableColumn(ulist, SWT.BORDER | SWT.FULL_SELECTION);
        headshot.setWidth(60);

        TableColumn uname = new TableColumn(ulist, SWT.BORDER | SWT.FULL_SELECTION);
        uname.setWidth(150);
        TableItem item = new TableItem(ulist, SWT.NONE);
        item.setText(0, "头像");
        item.setText(1, "用户名");
        ulist.redraw();

		
        chatContent = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
        chatContent.setBounds(250, 10, 593, 403);

        TableColumn username = new TableColumn(chatContent, SWT.BORDER | SWT.FULL_SELECTION);
        username.setWidth(150);

        TableColumn content = new TableColumn(chatContent, SWT.BORDER | SWT.FULL_SELECTION);
        content.setWidth(400);

		
		
		text = new Text(shell, SWT.BORDER | SWT.MULTI);
		text.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				messageString = text.getText();
			}
		});
		text.setBounds(250, 434, 594, 151);
		
		Button send = new Button(shell, SWT.NONE);
		send.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		//TODO 发送按钮 添加事件发送表情包或文字信息
		send.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Message msg =new Message();
//				给msg添加信息
				msg.name=name;
				userSocket.send(msg);
			}
		});
		send.setBounds(744, 602, 100, 30);
		send.setText("发送");
		
		Button memes = new Button(shell, SWT.NONE);
//		//TODO 添加点击事件显示一个对话框选择要发送的表情包
//		memes.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseDown(MouseEvent e) {
//				showHeadshots(memes.getLocation().x-200, memes.getLocation().y-170);
//			}
//		});
//		memes.setBounds(636, 602, 100, 30);
//		memes.setText("表情包");
		//客户端套接字
		userSocket =new UserSocket(name,IP,port);
		Display.getDefault().asyncExec(new Thread() {
			@Override
			public void run() {
				while (true) {
//					sleep(500);
					try {
//						Message msg = userSocket.socketReceive();
						//收到的消息显示在面板上 TODO
						addMessage(msg,chatContent);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
	}

	// 表情包选择框
	private void showHeadshots(int x, int y) {
		Shell box = new Shell(shell, SWT.ON_TOP | SWT.CLOSE);
		box.setSize(400, 300);
		box.setLocation(x, y);

		Label lblNewLabel = new Label(box, SWT.NONE);
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				imgPath = "/images/1.jpg";
			}
		});
		lblNewLabel.setImage(SWTResourceManager.getImage(demo.class, "/images/1.jpg"));
		lblNewLabel.setBounds(25, 20, 100, 100);
		SwtUtils.autoImage(lblNewLabel);

		Label lblNewLabel_1 = new Label(box, SWT.NONE);
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				imgPath = "/images/2.jpg";
			}
		});
		lblNewLabel_1.setImage(SWTResourceManager.getImage(demo.class, "/images/2.jpg"));
		lblNewLabel_1.setBounds(150, 20, 100, 100);
		SwtUtils.autoImage(lblNewLabel_1);

		Label lblNewLabel_1_1 = new Label(box, SWT.NONE);
		lblNewLabel_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				imgPath = "/images/3.jpg";
			}
		});
		lblNewLabel_1_1.setImage(SWTResourceManager.getImage(demo.class, "/images/3.jpg"));
		SwtUtils.autoImage(lblNewLabel_1_1);
		lblNewLabel_1_1.setBounds(275, 20, 100, 100);

		Label lblNewLabel_2 = new Label(box, SWT.NONE);
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				imgPath = "/images/4.jpg";
			}
		});
		lblNewLabel_2.setImage(SWTResourceManager.getImage(demo.class, "/images/4.jpg"));
		lblNewLabel_2.setBounds(25, 140, 100, 100);
		SwtUtils.autoImage(lblNewLabel_2);

		Label lblNewLabel_3 = new Label(box, SWT.NONE);
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				imgPath = "/images/5.jpg";
			}
		});
		lblNewLabel_3.setImage(SWTResourceManager.getImage(demo.class, "/images/5.jpg"));
		lblNewLabel_3.setBounds(150, 140, 100, 100);
		SwtUtils.autoImage(lblNewLabel_3);

		// 更多 鼠标点击从本地选择图片文件上传
		Label lblNewLabel_4 = new Label(box, SWT.NONE);
		lblNewLabel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
				fileDialog.setFilterExtensions(new String[] { "*.jpg;*.png;*.gif;*.bmp" });
				// 获取文件地址
				String filePath = fileDialog.open();
				imgPath = filePath;
			}
		});
		lblNewLabel_4.setToolTipText("双击选择本地图片");
		lblNewLabel_4.setImage(SWTResourceManager.getImage(demo.class, "/images/more.png"));
		lblNewLabel_4.setBounds(275, 140, 100, 100);
		SwtUtils.autoImage(lblNewLabel_4);
		box.open();
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
        item.setText(1, msg.name);
        item.setData("l", label);
        table.redraw();
    }
    
    
    //将massage中的image字节数组转换为image
    Image toImage(byte[] bytes) {
        ImageLoader imageLoader = new ImageLoader();
        imageLoader.load(new ByteArrayInputStream(bytes));

        ImageData[] imageDataArray = imageLoader.data;
        if (imageDataArray.length > 0) {
            return new Image(Display.getDefault(), imageDataArray[0]);
        } else {
            return null;
        }
    }


    void removeUser(Message msg, Table table) {
        System.out.println(msg.txt);
        String name = msg.name;
        // 删除一行表格
        TableItem[] items = table.getItems();
       
        for (TableItem item : items) {
        	
            if (item.getText(1).equals(name)) {
                // 移除这个Item从表格
                Label t =(Label)item.getData("l");
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
            item.setText(1, msg.txt);
        
        table.redraw();
    }
}
