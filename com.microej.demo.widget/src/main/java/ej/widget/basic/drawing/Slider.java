/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic.drawing;

import ej.drawing.ShapePainter;
import ej.drawing.ShapePainter.Cap;
import ej.microui.display.GraphicsContext;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Rectangle;
import ej.mwt.util.Size;
import ej.widget.basic.AbstractSlider;
import ej.widget.model.BoundedRangeModel;

/**
 * Implementation of a slider that can be horizontal or vertical.
 * <p>
 * The size of a slider is dependent on the font size.
 * <p>
 * This example shows a simple slider:
 *
 * <pre>
 * Slider slider = new Slider(0, 100, 50);
 * </pre>
 *
 * <img src="../doc-files/slider-simple.png" alt="Simple slider.">
 * <p>
 * This example shows a styled slider:
 *
 * <pre>
 * Slider slider = new Slider(0, 100, 50);
 *
 * CascadingStylesheet stylesheet = new CascadingStylesheet();
 * StyleHelper.setStylesheet(stylesheet);
 *
 * EditableStyle sliderStyle = stylesheet.addRule(new TypeSelector(Slider.class));
 * sliderStyle.setColor(Colors.BLUE);
 * sliderStyle.setBackground(new PlainRectangularBackground(Colors.SILVER));
 * sliderStyle.setPadding(new UniformOutline(1));
 * </pre>
 *
 * <img src="../doc-files/slider-styled.png" alt="Styled slider.">
 */
public class Slider extends AbstractSlider {

	/**
	 * The orientation of the slider.
	 */
	protected boolean horizontal;

	/**
	 * Creates a horizontal slider with the given model.
	 *
	 * @param model
	 *            the model to use.
	 */
	public Slider(BoundedRangeModel model) {
		super(model);
		this.horizontal = true;

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
	public Slider(int min, int max, int initialValue) {
		super(min, max, initialValue);
		this.horizontal = true;
	}

	/**
	 * Sets the slider orientation: horizontal or vertical.
	 *
	 * @param horizontal
	 *            <code>true</code> to set the slider horizontal, <code>false</code> to set the slider vertical.
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	@Override
	protected float computePercentComplete(int pointerX, int pointerY) {
		Rectangle contentBounds = getContentBounds();
		int size = getSize(getStyle());
		// The size of the bar considers the size of the cursor (room left on both sides).
		if (this.horizontal) {
			return (float) (pointerX - getAbsoluteX() - contentBounds.getX() - (size >> 1))
					/ (contentBounds.getWidth() - size);
		} else {
			return (float) (pointerY - getAbsoluteY() - contentBounds.getY() - (size >> 1))
					/ (contentBounds.getHeight() - size);
		}
	}

	@Override
	protected void renderContent(GraphicsContext g, Size size) {
		Style style = getStyle();
		int width = size.getWidth();
		int height = size.getHeight();

		int sliderSize = getSize(style);
		int barSize = Math.max(sliderSize >> 2, 1);

		// The size of the bar considers the size of the cursor (room left on both sides).
		int barStartX;
		int barStartY;
		int barEndX;
		int barEndY;
		int cursorX;
		int cursorY;
		if (this.horizontal) {
			int verticalAlignment = style.getVerticalAlignment();
			int yTop = Alignment.computeTopY(sliderSize, 0, height, verticalAlignment);
			int centerY = yTop + (sliderSize >> 1);
			barStartX = sliderSize >> 1;
			barEndX = width - (sliderSize >> 1);
			barStartY = centerY;
			barEndY = centerY;
			int barWidth = width - sliderSize;
			int completeBarWidth = (int) (getPercentComplete() * barWidth);
			cursorX = barStartX + completeBarWidth;
			cursorY = centerY;
		} else {
			int horizontalAlignment = style.getHorizontalAlignment();
			int xLeft = Alignment.computeLeftX(sliderSize, 0, width, horizontalAlignment);
			int centerX = xLeft + (sliderSize >> 1);
			barStartY = sliderSize >> 1;
			barEndY = height - (sliderSize >> 1);
			barStartX = centerX;
			barEndX = centerX;
			int barHeight = height - sliderSize;
			int completeBarHeight = (int) (getPercentComplete() * barHeight);
			cursorX = centerX;
			cursorY = barStartY + completeBarHeight;
		}

		g.setColor(style.getColor());

		// Draw the bar.
		ShapePainter.drawThickFadedLine(g, barStartX, barStartY, barEndX, barEndY, barSize - 2, 1, Cap.ROUNDED,
				Cap.ROUNDED);

		// Draw the cursor.
		ShapePainter.drawThickFadedPoint(g, cursorX, cursorY, sliderSize - 2, 1);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		int referenceSize = getSize(style);
		int minimum = getMinimum();
		int maximum = getMaximum();
		int optimalWidth;
		int optimalHeight;
		if (this.horizontal) {
			optimalWidth = referenceSize + (maximum - minimum);
			optimalHeight = referenceSize;
		} else {
			optimalWidth = referenceSize;
			optimalHeight = referenceSize + (maximum - minimum);
		}
		size.setSize(optimalWidth, optimalHeight);
	}

	private int getSize(Style style) {
		return style.getFont().getHeight();
	}
}
