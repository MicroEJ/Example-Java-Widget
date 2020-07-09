/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic.picto;

import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Rectangle;
import ej.mwt.util.Size;
import ej.widget.basic.AbstractSlider;
import ej.widget.model.BoundedRangeModel;

/**
 * A slider using some pictos to render.
 */
public class PictoSlider extends AbstractSlider {

	/**
	 * Creates a horizontal slider with the given model.
	 *
	 * @param model
	 *            the model to use.
	 */
	public PictoSlider(BoundedRangeModel model) {
		super(model);
	}

	/**
	 * Creates a horizontal slider with a default bounded range model.
	 *
	 * @param min
	 *            the minimum value of the slider.
	 * @param max
	 *            the maximum value of the slider.
	 * @param initialValue
	 *            the initial value of the slider.
	 * @see ej.widget.model.DefaultBoundedRangeModel
	 */
	public PictoSlider(int min, int max, int initialValue) {
		super(min, max, initialValue);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();

		g.setColor(style.getColor());

		Font font = style.getFont();
		int fontHeight = font.getHeight();

		char bar = getBar();
		int barWidth = font.charWidth(bar);

		// Draw the bar.
		int barX = Alignment.computeLeftX(barWidth, 0, contentWidth, Alignment.HCENTER);
		int barY = Alignment.computeTopY(fontHeight, 0, contentHeight, Alignment.VCENTER);
		Painter.drawChar(g, font, bar, barX, barY);

		// Draw the cursor.
		char cursor = getCursor();
		int cursorX = barX + (int) (barWidth * getPercentComplete());
		cursorX = Alignment.computeLeftX(font.charWidth(cursor), cursorX, Alignment.HCENTER);
		int cursorY = Alignment.computeTopY(font.getHeight(), 0, contentHeight, Alignment.VCENTER);
		Painter.drawChar(g, font, cursor, cursorX, cursorY);
	}

	@Override
	protected float computePercentComplete(int pointerX, int pointerY) {
		Rectangle contentBounds = getContentBounds();
		Font font = getStyle().getFont();
		int barWidth = font.charWidth(getBar());
		return (float) (pointerX - getAbsoluteX() - contentBounds.getX() - ((contentBounds.getWidth() - barWidth) >> 1))
				/ barWidth;
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		Font font = style.getFont();
		int width = font.charWidth(getBar()) + font.charWidth(getCursor());
		int height = font.getHeight();
		size.setSize(width, height);
	}

	private char getBar() {
		return Pictos.SLIDER_BAR;
	}

	private char getCursor() {
		return Pictos.SLIDER_CURSOR;
	}

}
