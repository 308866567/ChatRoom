package server;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import cilent.demo;
import util.SwtUtils;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Text;

public class ServerStart {

	protected Shell shell;
	
	private Text port;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ServerStart window = new ServerStart();
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
		shell.setSize(500, 400);
		shell.setText("聊天室服务器");
		SwtUtils.centerWin(shell);
		
		Label label = new Label(shell, SWT.NONE);
		label.setAlignment(SWT.CENTER);
		label.setFont(SWTResourceManager.getFont("宋体", 15, SWT.NORMAL));
		label.setBounds(131, 90, 180, 40);
		label.setText("聊天室服务器");
		

		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBounds(125, 171, 90, 24);
		label_1.setText("服务器端口 : ");
		
		port = new Text(shell, SWT.BORDER);
		port.setBounds(225, 168, 62, 30);
		port.setText("11111");

		
		Button button = new Button(shell, SWT.NONE);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
//				SwtUtils.pop_upFrame(shell, "提示", "服务器已打开");
//				Server t = new Server(shell, SWT.APPLICATION_MODAL | SWT.CLOSE);
//				t.open();
				Server1 server1 = new Server1();
				server1.setPort(Integer.parseInt(port.getText()));
				server1.open();
				close();
			}
		});
		
		button.setBounds(173, 218, 110, 34);
		button.setText("开启服务器");


	}
	void close(){
		shell.setVisible(false);
	}
}
