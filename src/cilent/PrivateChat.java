package cilent;

import common.Message;
import common.UserSocket;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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
 *
 * @author 308866567
 */
public class PrivateChat extends Dialog {

    protected Object result;
    protected Shell shell;
    private Table table;
    private Text text;
    UserSocket userSocket;//必传
    int des;

		//TODO 发送按钮 发送消息模板
//		System.out.println("发送");
//		Message t=userSocket.initMessage();
//		t.txt=text.getText();
//		if(t.txt==null)
//			t.txt="";
//		t.flag=-1;
//		userSocket.addMessage(t);
//		text.setText("");

	/**
     * Create the dialog.
     *
     * @param parent
     * @param style
     */
    public PrivateChat(Shell parent, int style,UserSocket userSocket,int des) {
        super(parent, style);
        setText("私聊");
        this.userSocket = userSocket;
        this.des=des;
    }

    /**
     * Open the dialog.
     *
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
        shell.setSize(735, 521);

        text = new Text(shell, SWT.BORDER | SWT.MULTI);
        text.setBounds(10, 10, 694, 396);

        Button send = new Button(shell, SWT.NONE);
        send.setBounds(590, 412, 114, 34);
        send.setText("发送");

        shell.setText("发送给"+des);


        send.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                //TODO 发送按钮 发送消息
                System.out.println("发送");
                Message t=userSocket.initMessage();
                t.txt=text.getText();
                t.DesId=des;
                if(t.txt==null)
                    t.txt="";
                t.flag=3;
                System.out.println("私聊"+t.flag+" "+t);
                userSocket.addMessage(t);
                close();
//                text.setText("");
            }
        });


//        Button headshot = new Button(shell, SWT.NONE);
//        headshot.setBounds(470, 412, 114, 34);
//        headshot.setText("表情包");

    }
    void close(){
        shell.close();
    }
}
