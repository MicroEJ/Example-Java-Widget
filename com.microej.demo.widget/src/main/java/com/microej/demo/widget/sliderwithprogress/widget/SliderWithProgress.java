/*
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.sliderwithprogress.widget;

import com.microej.demo.widget.common.CirclePainter;

import ej.bon.XMath;
import ej.drawing.ShapePainter;
import ej.drawing.ShapePainter.Cap;
import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Rectangle;
import ej.mwt.util.Size;

/**
 * Slider with a round knob and a bar that is filled on the left of the knob.
 */
public class SliderWithProgress extends Widget {

	/**
	 * Bar and circle color.
	 */
	public static final int BAR_COLOR_ID = 0;
	/**
	 * Bar background color.
	 */
	public static final int BACKGROUND_COLOR_ID = 1;
	/**
	 * Circle fill color.
	 */
	public static final int FILL_COLOR_ID = 2;

	private static final int BAR_THICKNESS = 5;
	private static final int THICKNESS = 1;

	private static final float DIAMETER_MULTIPLIER = 1.5f;

	/**
	 * Maximum slider value.
	 */
	private final int maximum;
	/**
	 * Minimum slider value.
	 */
	private final int minimum;
	private int value;

	/**
	 * Creates a horizontal slider.
	 *
	 * @param min
	 *            the minimum value of the slider.
	 * @param max
	 *            the maximum value of the slider.
	 * @param initialValue
	 *            the initial value of the slider.
	 */
	public SliderWithProgress(int min, int max, int initialValue) {
		super(true);
		checkBounds(min, max, initialValue);

		this.minimum = min;
		this.maximum = max;
		this.value = initialValue;
	}

	private int getSize(Style style) {
		return style.getFont().getHeight();
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		int referenceSize = getSize(style);

		int optimalWidth = referenceSize + (this.maximum - this.minimum);
		int optimalHeight = referenceSize;
		size.setSize(optimalWidth, optimalHeight);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();

		int sliderSize = getSize(style);
		int margin = (sliderSize >> 1);

		// The size of the bar considers the size of the cursor (room left on both sides).
		int cursorX;
		int cursorY;
		int barStartX;
		int barStartY;
		int barEndX;
		int barEndY;
		int verticalAlignment = style.getVerticalAlignment();
		int yTop = Alignment.computeTopY(sliderSize, 0, contentHeight, verticalAlignment);
		int centerY = yTop + margin;
		barStartX = margin;
		barEndX = contentWidth - (sliderSize >> 1);
		barStartY = centerY;
		barEndY = centerY;
		int barWidth = contentWidth - sliderSize;
		int sliderWidth = barWidth - getDiameter(style);
		int completeBarWidth = (int) (getPercentComplete() * barWidth);
		cursorX = barStartX + completeBarWidth;
		cursorY = centerY;

		// Draws the bar.
		drawBar(g, style, barStartX, barStartY, barEndX, barEndY, cursorX);

		// Draws the cursor.
		drawCursor(g, style, margin, barStartX, cursorY, sliderWidth);
	}

	@Override
	public boolean handleEvent(int event) {
		int eventType = Event.getType(event);
		if (eventType == Pointer.EVENT_TYPE) {
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int pointerX = pointer.getX();
			int action = Buttons.getAction(event);
			if (action == Pointer.DRAGGED) {
				onPointerDragged(pointerX);
				return true;
			}
		}

		return super.handleEvent(event);
	}

	private void onPointerDragged(int pointerX) {
		float percentComplete = computePercentComplete(pointerX);
		int newValue = (int) (this.minimum + (this.maximum - this.minimum) * percentComplete);
		setValue(newValue);
	}

	/**
	 * Sets the current value. The value may be changed if it does not satisfy the model's constraints. Those
	 * constraints are that <code>minimum &lt;= value &lt;= maximum</code>. That means that the value will be bounded to
	 * the range [minimum; maximum]. Notifies the listener if the model changes.
	 *
	 * @param value
	 *            the value to set.
	 */
	public void setValue(int value) {
		if (value != this.value) {
			this.value = XMath.limit(value, this.minimum, this.maximum);
		}
		requestRender();
	}

	/**
	 * Draws slider bar.
	 * <p>
	 * Contains a dynamic bar on the left of the cursor.
	 *
	 * @param g
	 *            Graphics context
	 * @param style
	 *            style
	 * @param barStartX
	 *            bar start X position
	 * @param barStartY
	 *            bar start Y position
	 * @param barEndX
	 *            bar end X position
	 * @param barEndY
	 *            bar end Y position
	 * @param cursorX
	 *            cursor X length
	 */
	private void drawBar(GraphicsContext g, Style style, int barStartX, int barStartY, int barEndX, int barEndY,
			int cursorX) {
		g.setColor(Colors.BLACK);
		ShapePainter.drawThickFadedLine(g, barStartX, barStartY, barEndX, barEndY, BAR_THICKNESS, 1, Cap.ROUNDED,
				Cap.ROUNDED);
		if (cursorX > barStartX) {
			g.setColor(style.getExtraInt(BAR_COLOR_ID, Colors.RED));
			ShapePainter.drawThickLine(g, barStartX, barStartY, cursorX, barEndY, THICKNESS);
		}
	}

	private void drawCursor(GraphicsContext gc, Style style, int margin, int barStartX, int cursorY, int sliderWidth) {
		int halfCursorRadius = getDiameter(style) >> 2;
		int top = cursorY - halfCursorRadius - margin + (BAR_THICKNESS >> 1);
		int left = (int) (getPercentComplete() * sliderWidth) + barStartX;
		CirclePainter.drawFilledCircle(gc, style.getExtraInt(BAR_COLOR_ID, Colors.RED),
				style.getExtraInt(FILL_COLOR_ID, Colors.GRAY), left, top, getDiameter(style), THICKNESS);
	}

	private int getDiameter(Style style) {
		return (int) (getSize(style) * DIAMETER_MULTIPLIER);
	}

	private void checkBounds(int minimum, int maximum, int initialValue) {
		if (minimum > initialValue || initialValue > maximum) {
			// Do not need to test that minimum is lower than maximum as long as it is validated by the other tests.
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Gets the percent complete for the range. Note that this number is between 0.0 and 1.0.
	 *
	 * @return the percent complete.
	 */
	private float getPercentComplete() {
		return (float) (this.value - this.minimum) / (this.maximum - this.minimum);
	}

	/**
	 * Computes the percent complete according to the pointer coordinates.
	 *
	 * @param pointerX
	 *            the x coordinate of the pointer.
	 * @return the percent complete according to the pointer coordinates.
	 */
	private float computePercentComplete(int pointerX) {
		Rectangle contentBounds = getContentBounds();
		int size = getSize(getStyle());
		// The size of the bar considers the size of the cursor (room left on both sides).
		return (float) (pointerX - getAbsoluteX() - contentBounds.getX() - (size >> 1))
				/ (contentBounds.getWidth() - size);
	}
}
