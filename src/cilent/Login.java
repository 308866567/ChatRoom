package cilent;

import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class Login {

	protected Shell shell;
	private Text uname;
	private Text ip;
	private Text port;
//	String name;
//	String IP;
//	int port;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Login window = new Login();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	Display display = Display.getDefault();
	/**
	 * Open the window.
	 */
	public void open() {
		
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
		shell.setSize(600, 400);
		shell.setText("用户登录");
		
		Label label = new Label(shell, SWT.NONE);
		label.setBounds(125, 91, 90, 24);
		label.setText("用户名 : ");
		
		Label lblIp = new Label(shell, SWT.NONE);
		lblIp.setBounds(125, 131, 90, 24);
		lblIp.setText("服务器IP : ");
		
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(125, 171, 90, 24);
		label_1.setText("服务器端口 : ");
		
		uname = new Text(shell, SWT.BORDER);
		uname.setBounds(225, 91, 207, 30);
		
		ip = new Text(shell, SWT.BORDER);
		ip.setBounds(225, 131, 207, 30);
		ip.setText("127.0.0.1");

		port = new Text(shell, SWT.BORDER);
		port.setBounds(225, 168, 62, 30);
		port.setText("11111");


//		Combo headshot = new Combo(shell, SWT.NONE);
//		headshot.setItems(new String[] {"1"});
//		
//		headshot.setBounds(225, 171, 207, 32);
		
//		CustomCombo customCombo = new CustomCombo(shell, SWT.READ_ONLY);
//
//        // 设置图标数组
//        Image[] images = new Image[3];
//        images[0] = new Image(display, "path/to/image1.png");
//        images[1] = new Image(display, "path/to/image2.png");
//        images[2] = new Image(display, "path/to/image3.png");

//        customCombo.setItemImages(images);
		
        //登录按钮 TODO 点击登录按钮后将用户名,用户IP等信息传递到服务器
		Button login = new Button(shell, SWT.NONE);
		login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Client1 client1 = new Client1();
				client1.init(uname.getText(),ip.getText(), Integer.parseInt(port.getText()));
				//TODO
				client1.open();
			}
		});
		login.setBounds(202, 243, 114, 34);
		login.setText("登录");
	}
	
}



