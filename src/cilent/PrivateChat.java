package cilent;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;


/**
 * 私聊界面
 * @author 308866567
 *
 */
public class PrivateChat extends Dialog {

	protected Object result;
	protected Shell shell;
	private Table table;
	private Text text;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public PrivateChat(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(740, 524);
		shell.setText("私聊");
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setBounds(10, 10, 694, 253);
		
		TableColumn uname = new TableColumn(table, SWT.NONE);
		uname.setWidth(100);
		uname.setText("New Column");
		
		TableColumn chatMessage = new TableColumn(table, SWT.NONE);
		chatMessage.setWidth(500);
		chatMessage.setText("New Column");
		
		text = new Text(shell, SWT.BORDER | SWT.MULTI);
		text.setBounds(10, 269, 694, 137);
		
		Button send = new Button(shell, SWT.NONE);
		send.setBounds(590, 412, 114, 34);
		send.setText("发送");
		
		Button headshot = new Button(shell, SWT.NONE);
		headshot.setBounds(470, 412, 114, 34);
		headshot.setText("表情包");

	}
}
