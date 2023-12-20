package util;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;


public  class SwtUtils {
	/**
	 * 设置窗口居中
	 * @param shell
	 */
	public static void centerWin(Shell shell) {
		//获取具有多屏幕情况下的屏幕分辨率 缩放后的屏幕分辨率
		Rectangle bounds =    shell.getDisplay().getPrimaryMonitor().getBounds();
		int width = bounds.width;//1700
		int height = bounds.height;
		shell.setLocation((width - shell.getSize().x) / 2, (height - shell.getSize().y) / 2);
//        System.out.println("Screen pixel area: " + bounds.width + " x " + bounds.height);
	}

	/**
	 * label组件上的图片自适应
	 * 
	 * @param label
	 */
	public static void autoImage(Label label) {

		Image oldPicture = label.getImage();// 获取图片
//		 计算缩放后的图片宽度和高度
		int scaledWidth = label.getSize().x;
		int scaledHeight = label.getSize().y;
		if (scaledWidth == 0 || scaledHeight == 0) {
			System.out.println("SwtUtils autoImage-33出错,组件大小为0");
			return;
		}
//		 缩放图片
		Image newPicture = new Image(Display.getDefault(),
				oldPicture.getImageData().scaledTo(scaledWidth, scaledHeight));
		label.setImage(newPicture);
		label.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				Image oldPicture = label.getImage();// 获取图片
//						 计算缩放后的图片宽度和高度
				int scaledWidth = label.getSize().x;
				int scaledHeight = label.getSize().y;
				if (scaledWidth == 0 || scaledHeight == 0) {
					System.out.println("SwtUtils autoImage2-33出错,组件大小为0");
					return;
				}
//						 缩放图片
				Image newPicture = new Image(Display.getDefault(),
						oldPicture.getImageData().scaledTo(scaledWidth, scaledHeight));
				label.setImage(newPicture);
			}
		});

//		label.addPaintListener(new PaintListener() {
//			Image image = label.getImage();
//			@Override
//			public void paintControl(PaintEvent e) {
//				Point size =label.getSize();
//				//GC 画笔 
//				e.gc.drawImage(image, 0, 0, image.getImageData().width, image.getImageData().height, 0, 0, size.x, size.y);
//			}
//		});
	}

	/**
	 * 设置Composite边框,可以自定义颜色
	 * 
	 * @param composite
	 * @param i
	 */
	public static void drawBorder(Composite composite, int color) {
		GC gc = new GC(composite);

		// 设定绘制参数
		int x = 0;
		int y = 0;
		int width = composite.getSize().x;
		int height = composite.getSize().y;

//		  gc.setLineStyle(SWT.LINE_SOLID);
		// 设置线宽
		gc.setLineWidth(5);
		// 设置前景色
		gc.setForeground(Display.getDefault().getSystemColor(color));
		// 绘制空心矩形
		gc.drawRectangle(x, y, width, height);
	}

	/**
	 * 在组件上选取两点画直线 只绘制一次,窗口刷新就会清除直线   直线所在的图层
	 */
	public static void drawRedLine(Composite parent, Point a, Point b) {
		GC gc = new GC(parent);
		// 颜色
		gc.setForeground(parent.getDisplay().getSystemColor(SWT.COLOR_RED));
		// 粗细
		gc.setLineWidth(5);
		// 位置
		gc.drawLine(a.x, a.y, b.x, b.y);
	}

	/**
	 * 组件上的图片自适应 需要确保组件的大小已经分配好且不为0
	 * 
	 * @param
	 */
	public static void autoImage(Control control, String file) {
		// 文件路径名例子"src/image/t.jpg"
		Image oldPicture =	SWTResourceManager.getImage(control.getShell().getClass(), file);
//		 计算缩放后的图片宽度和高度
		int scaledWidth = control.getSize().x;
		int scaledHeight = control.getSize().y;
		if (scaledWidth == 0 || scaledHeight == 0) {
//			System.out.println("SwtUtils autoImage出错,组件大小为0");
			return;
		}
//		 缩放图片
		Image newPicture = new Image(Display.getDefault(),
				oldPicture.getImageData().scaledTo(scaledWidth, scaledHeight));
		control.setBackgroundImage(newPicture);
	}

	/**
	 * 可随大小变化而自适应 组件上的图片自适应 需要确保组件的大小已经分配好且不为0
	 * 
	 * @param
	 */
	public static void autoImage2(Control control, String file) {
		autoImage(control, file);
		// 设置背景图片
		control.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				Image oldPicture =	SWTResourceManager.getImage(control.getShell().getClass(), file);
//						 计算缩放后的图片宽度和高度
				int scaledWidth = control.getSize().x;
				int scaledHeight = control.getSize().y;
				if (scaledWidth == 0 || scaledHeight == 0) {
//					System.out.println("SwtUtils autoImage2出错,组件大小为0");
					return;
				}
//						 缩放图片
				Image newPicture = new Image(Display.getDefault(),
						oldPicture.getImageData().scaledTo(scaledWidth, scaledHeight));
				control.setBackgroundImage(newPicture);
			}
		});

	}

	/**
	 * 弹窗提示
	 * 
	 * @param shell
	 * @param message
	 * @param text
	 */
	public static void pop_upFrame(Shell shell, String title, String message) {
		MessageBox mBox = new MessageBox(shell);
		mBox.setMessage(message);
		mBox.setText(title);
		mBox.open();
	}

	/**
	 * 清空文本框
	 * 
	 * @param texts
	 */
	public static void clearText(Text... texts) {
		if (null == texts || texts.length <= 0) {
			return;
		}

		for (Text t : texts) {
			t.setText("");
		}
	}
}
