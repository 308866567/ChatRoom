package server;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import util.SwtUtils;

public class Server1 {

	protected Shell shell;
	private Table user_list;
	private Table chat;
	private Text text;
	private Table ulist;
	private Table chatContent;

	/**
	 * Launch the application.
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
		
		//用户列表 用户信息,用户头像
		TableViewer tableViewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		ulist = tableViewer.getTable();
		ulist.setBounds(10, 10, 225, 621);
		
		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn headshot = tableViewerColumn.getColumn();
		headshot.setWidth(60);
		
		TableViewerColumn tableViewerColumn_1 = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn uname = tableViewerColumn_1.getColumn();
		uname.setWidth(150);
		uname.setText("New Column");
		
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
	}

}
