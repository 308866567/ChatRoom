package util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class CustomCombo extends Combo {

    private Image[] itemImages;

    public CustomCombo(Composite parent, int style) {
        super(parent, style);
        init();
    }

    private void init() {
        // 在这里添加自定义的初始化逻辑
        addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                int selectedIndex = getSelectionIndex();
                if (selectedIndex >= 0 && selectedIndex < itemImages.length) {
                    Image selectedImage = itemImages[selectedIndex];
                    // 在这里可以使用选中项的图标进行其他操作
                    System.out.println("Selected Item Image: " + selectedImage);
                }
            }
        });
    }

    public void setItemImages(Image[] images) {
        this.itemImages = images;
        // 在设置图标数组后，更新 Combo 中的项
        String[] items = new String[images.length];
        for (int i = 0; i < items.length; i++) {
            items[i] = "Item " + (i + 1);
        }
        setItems(items);
    }

    public static void main(String[] args) {
        Display display = new Display();
        Composite shell = new Composite(new Shell(display), SWT.NONE);
        shell.setLayout(new FillLayout());

        CustomCombo customCombo = new CustomCombo(shell, SWT.READ_ONLY);

        // 设置图标数组
        Image[] images = new Image[3];
        images[0] = new Image(display, "path/to/image1.png");
        images[1] = new Image(display, "path/to/image2.png");
        images[2] = new Image(display, "path/to/image3.png");

        customCombo.setItemImages(images);

        shell.pack();

        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
}
