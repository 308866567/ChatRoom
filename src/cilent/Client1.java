package cilent;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import util.SwtUtils;

public class Client1 {

	protected Shell shell;
	private Table user_list;
	private Table chat;
	private Text text;
	String messageString;
	private String imgPath="";
	
	/**
	 * Launch the application.
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
		
		//用户列表 显示用户头像,用户名信息 
		//TODO 双击用户名或用户头像进入单聊
		TableViewer tableViewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		user_list = tableViewer.getTable();
		user_list.setTouchEnabled(true);
		user_list.setBounds(10, 10, 218, 575);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn headshot = tableViewerColumn.getColumn();
		headshot.setWidth(60);
		headshot.setText("New Column");
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn uname = tableViewerColumn_1.getColumn();
		uname.setWidth(150);
		uname.setText("New Column");
		
		//聊天信息显示框
		TableViewer tableViewer_1 = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		chat = tableViewer_1.getTable();
		chat.setBounds(250, 10, 594, 405);
		
		TableViewerColumn tableViewerColumn_2 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn userName = tableViewerColumn_2.getColumn();
		userName.setWidth(150);
		userName.setText("New Column");
		
		TableViewerColumn tableViewerColumn_3 = new TableViewerColumn(tableViewer_1, SWT.NONE);
		TableColumn tblclmnNewColumn = tableViewerColumn_3.getColumn();
		tblclmnNewColumn.setWidth(420);
		tblclmnNewColumn.setText("New Column");
		
		
		text = new Text(shell, SWT.BORDER | SWT.MULTI);
		text.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				messageString = text.getText();
			}
		});
		text.setBounds(250, 434, 594, 151);
		
		Button send = new Button(shell, SWT.NONE);
		//TODO 发送按钮 添加事件发送表情包或文字信息
		send.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				
			}
		});
		send.setBounds(744, 602, 100, 30);
		send.setText("发送");
		
		Button memes = new Button(shell, SWT.NONE);
		//TODO 添加点击事件显示一个对话框选择要发送的表情包
		memes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				showHeadshots(memes.getLocation().x-200, memes.getLocation().y-170);
			}
		});
		memes.setBounds(636, 602, 100, 30);
		memes.setText("表情包");

	}
	
	//表情包选择框
	private void showHeadshots(int x,int y) {
		Shell box = new Shell(shell, SWT.ON_TOP|SWT.CLOSE);
		box.setSize(400,300);
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
		
		//更多  鼠标点击从本地选择图片文件上传
		Label lblNewLabel_4 = new Label(box, SWT.NONE);
		lblNewLabel_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				FileDialog fileDialog = new FileDialog(shell, SWT.OPEN);
                fileDialog.setFilterExtensions(new String[] { "*.jpg;*.png;*.gif;*.bmp" });
                //获取文件地址
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
}
