/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic.image;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.Painter;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Rectangle;
import ej.mwt.util.Size;
import ej.widget.basic.AbstractSlider;
import ej.widget.model.BoundedRangeModel;

/**
 * A slider using some images to render.
 */
public class ImageSlider extends AbstractSlider {

	/**
	 * Creates a horizontal slider with the given model.
	 *
	 * @param model
	 *            the model to use.
	 */
	public ImageSlider(BoundedRangeModel model) {
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
	public ImageSlider(int min, int max, int initialValue) {
		super(min, max, initialValue);
	}

	@Override
	protected void renderContent(GraphicsContext g, Size size) {
		Style style = getStyle();
		int width = size.getWidth();
		int height = size.getHeight();

		g.setColor(style.getColor());

		Image bar = getBarImage();
		int barWidth = bar.getWidth();

		// Draws the bar.
		int barX = Alignment.computeLeftX(barWidth, 0, width, Alignment.HCENTER);
		int barY = Alignment.computeTopY(bar.getHeight(), 0, height, Alignment.VCENTER);
		Painter.drawImage(g, bar, barX, barY);

		// Draws the cursor.
		Image cursorImage = getCursorImage();
		int cursorX = barX + (int) (barWidth * getPercentComplete());
		cursorX = Alignment.computeLeftX(cursorImage.getWidth(), cursorX, Alignment.HCENTER);
		int cursorY = Alignment.computeTopY(cursorImage.getHeight(), 0, height, Alignment.VCENTER);
		Painter.drawImage(g, cursorImage, cursorX, cursorY);
	}

	@Override
	protected float computePercentComplete(int pointerX, int pointerY) {
		Rectangle contentBounds = getContentBounds();
		// The size of the bar consider the size of the cursor (room left on both sides).
		int barWidth = getBarImage().getWidth();
		return (float) (getRelativeX(pointerX) - contentBounds.getX() - ((contentBounds.getWidth() - barWidth) >> 1))
				/ barWidth;
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		int width;
		int height;
		Image cursor = getCursorImage();

		Image bar = getBarImage();
		width = bar.getWidth() + cursor.getWidth();
		height = Math.max(bar.getHeight(), cursor.getHeight());

		size.setSize(width, height);
	}

	private Image getBarImage() {
		return ImageHelper.loadImageFromTheme(ImageHelper.getTheme().getSliderHorizontalBarPath());
	}

	private Image getCursorImage() {
		return ImageHelper.loadImageFromTheme(ImageHelper.getTheme().getSliderCursorPath());
	}

}
