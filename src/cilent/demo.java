package cilent;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import util.SwtUtils;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;

/**
 * 选择表情包的控件及其事件
 * @author 308866567
 *
 */
public class demo extends Dialog {

	protected Object result;
	protected Shell shell;
	protected String imgPath;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public demo(Shell parent, int style) {
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
		shell.setSize(425,315);
		shell.setText(getText());
		
//		Composite composite = new Composite(shell, SWT.NONE);
//		composite.setVisible(true);
//		composite.addFocusListener(new FocusAdapter() {
//			@Override
//			public void focusLost(FocusEvent e) {
//				composite.setVisible(false);
//			}
//		});
//		composite.setBounds(10, 10, 400, 260);
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				imgPath = "/images/1.jpg";
			}
		});
		lblNewLabel.setImage(SWTResourceManager.getImage(demo.class, "/images/1.jpg"));
		lblNewLabel.setBounds(25, 20, 100, 100);
		SwtUtils.autoImage(lblNewLabel);
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				imgPath = "/images/2.jpg";
			}
		});
		lblNewLabel_1.setImage(SWTResourceManager.getImage(demo.class, "/images/2.jpg"));
		lblNewLabel_1.setBounds(150, 20, 100, 100);
		SwtUtils.autoImage(lblNewLabel_1);
		
		Label lblNewLabel_1_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				imgPath = "/images/3.jpg";
			}
		});
		lblNewLabel_1_1.setImage(SWTResourceManager.getImage(demo.class, "/images/3.jpg"));
		SwtUtils.autoImage(lblNewLabel_1_1);
		lblNewLabel_1_1.setBounds(275, 20, 100, 100);
		
		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				imgPath = "/images/4.jpg";
			}
		});
		lblNewLabel_2.setImage(SWTResourceManager.getImage(demo.class, "/images/4.jpg"));
		lblNewLabel_2.setBounds(25, 140, 100, 100);
		SwtUtils.autoImage(lblNewLabel_2);
		
		Label lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				imgPath = "/images/5.jpg";
			}
		});
		lblNewLabel_3.setImage(SWTResourceManager.getImage(demo.class, "/images/5.jpg"));
		lblNewLabel_3.setBounds(150, 140, 100, 100);
		SwtUtils.autoImage(lblNewLabel_3);
		
		//更多 TODO 鼠标点击从本地选择图片文件上传
		Label lblNewLabel_4 = new Label(shell, SWT.NONE);
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

	}
}
