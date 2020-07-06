/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.framework;

import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.Widget;
import ej.mwt.util.Size;

/**
 *
 */
public class Item extends Widget {

	static final int COLORS[] = new int[] { Colors.RED, Colors.GREEN, Colors.BLUE, Colors.YELLOW, Colors.MAGENTA,
			Colors.CYAN };
	static int Count;

	private final int color;
	private final int expectedWidth;
	private final int expectedHeight;
	private boolean isPaint;
	private boolean isShowNotified;
	private boolean isHideNotified;

	public Item(int expectedWidth, int expectedHeight) {
		this(expectedWidth, expectedHeight, true);
	}

	public Item(int expectedWidth, int expectedHeight, boolean random) {
		this.expectedWidth = expectedWidth;
		this.expectedHeight = expectedHeight;
		if (random) {
			this.color = (int) (Math.random() * 0xffffff);
		} else {
			this.color = COLORS[Count++];
			if (Count == COLORS.length) {
				Count = 0;
			}
		}
	}

	/**
	 * Gets the expectedWidth.
	 *
	 * @return the expectedWidth.
	 */
	public int getExpectedWidth() {
		return this.expectedWidth;
	}

	/**
	 * Gets the expectedHeight.
	 *
	 * @return the expectedHeight.
	 */
	public int getExpectedHeight() {
		return this.expectedHeight;
	}

	/**
	 * Gets the color.
	 *
	 * @return the color.
	 */
	public int getColor() {
		return this.color;
	}

	/**
	 * Resets the widget state.
	 */
	public void reset() {
		this.isPaint = false;
		this.isHideNotified = false;
		this.isShowNotified = false;
	}

	/**
	 * Gets the isPaint.
	 *
	 * @return the isPaint.
	 */
	public boolean isPaint() {
		return this.isPaint;
	}

	@Override
	public void render(GraphicsContext g) {
		this.isPaint = true;
		int renderableWidth = getWidth();
		int renderableHeight = getHeight();
		g.setColor(getColor());
		Painter.fillRectangle(g, 0, 0, renderableWidth, renderableHeight);
		g.setColor(Colors.WHITE - getColor());
		Painter.drawLine(g, 0, 0, renderableWidth, renderableHeight);
		Painter.drawLine(g, 0, renderableHeight, renderableWidth, 0);
	}

	@Override
	protected void renderContent(GraphicsContext g, Size size) {
		// Nothing to do.
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		size.setSize(this.expectedWidth, this.expectedHeight);
	}

	@Override
	protected void onShown() {
		super.onShown();
		this.isShowNotified = true;
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		this.isHideNotified = true;
	}

	/**
	 * Gets the isShowNotified.
	 *
	 * @return the isShowNotified.
	 */
	public boolean isShowNotified() {
		return this.isShowNotified;
	}

	/**
	 * Gets the isHideNotified.
	 *
	 * @return the isHideNotified.
	 */
	public boolean isHideNotified() {
		return this.isHideNotified;
	}
}
