package fr.opensagres.mongodb.ide.ui.viewers.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class GradientProgressBar extends Canvas {

	private final IWidthProvider widthProvider;
	private int value;
	private String text;
	private Color cf;
	private Color cg;

	public GradientProgressBar(Composite parent, int style, Color background,
			IWidthProvider widthProvider, int value, String text) {
		super(parent, style);
		this.widthProvider = widthProvider;
		this.value = value;
		this.text = text;
		// Add paint listener to draw the navigation page
		this.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				onPaint(e);
			}
		});
		// cf= new Color(getDisplay(), 196, 232, 251);
		cg = new Color(getDisplay(), 196, 232, 251);
		cf = new Color(getDisplay(), 76, 170, 255);
		super.setBackground(background);
	}

	private void onPaint(PaintEvent e) {

		GC gc = e.gc;

		int x = e.x;
		int y = e.y + 1;
		int height = e.height - 2;

		Color foreground = gc.getForeground();
		Color background = gc.getBackground();
		Display display = super.getDisplay();
		gc.setForeground(cg);
		gc.setBackground(cf);
		int totalWidth = widthProvider.getWidth();
		int width = totalWidth * value / 100;
		gc.fillGradientRectangle(x, y, width, height, true);
		Rectangle rect2 = new Rectangle(x, y, width - 1, height - 1);
		gc.drawRectangle(rect2);
		gc.setForeground(display.getSystemColor(SWT.COLOR_LIST_FOREGROUND));
		Point size = gc.textExtent(text);
		int offset = Math.max(0, (height - size.y) / 2);
		gc.drawText(text, x + 2, y + offset, true);
		gc.setForeground(background);
		gc.setBackground(foreground);
	}

	public double getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
