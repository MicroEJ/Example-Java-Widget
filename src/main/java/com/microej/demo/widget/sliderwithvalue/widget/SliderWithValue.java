/*
 * Copyright 2021-2025 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.sliderwithvalue.widget;

import com.microej.demo.widget.common.CirclePainter;
import com.microej.demo.widget.common.Fonts;

import ej.bon.XMath;
import ej.drawing.ShapePainter;
import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.microui.event.Event;
import ej.microui.event.EventHandler;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.util.Alignment;
import ej.mwt.util.Rectangle;
import ej.mwt.util.Size;
import ej.widget.render.StringPainter;

/**
 * Slider with a round knob showing the current value and a simple line.
 */
public class SliderWithValue extends Widget implements EventHandler {

	/**
	 * Cursor edge color id.
	 */
	public static final int CURSOR_EDGE_ID = 0;
	/**
	 * Cursor filling color id.
	 */
	public static final int CURSOR_BACKGROUND_ID = 2;
	/**
	 * Bar color id.
	 */
	public static final int BAR_COLOR_ID = 1;

	private static final int DEFAULT_CURSOR_EDGE = 0xEE502E;
	private static final int DEFAULT_BAR_COLOR = 0x97A7AF;
	private static final int THICKNESS = 1;
	private static final int FADE = 1;

	private static final float DIAMETER_MULTIPLIER = 1.87f;

	private static final int INCREMENT_VALUE_MAX = 100;

	/**
	 * Maximum slider value.
	 */
	protected int maximum;
	/**
	 * Minimum slider value.
	 */
	private final int minimum;
	private int value;
	private boolean pressed;

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
	public SliderWithValue(int min, int max, int initialValue) {
		super(true);
		checkBounds(min, max, initialValue);

		this.minimum = min;
		this.maximum = max;
		this.value = initialValue;
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();

		int sliderSize = getDiameter(style);
		int halfSliderSize = (sliderSize >> 1);

		int verticalAlignment = style.getVerticalAlignment();
		int yTop = Alignment.computeTopY(sliderSize, 0, contentHeight, verticalAlignment);
		int barY = yTop + halfSliderSize;
		int barStartX = halfSliderSize;
		int barWidth = contentWidth - sliderSize;
		int sliderWidth = barWidth - sliderSize;

		// Draw the bar (for begin and end symbols see the SliderWithValuePage class).
		drawBar(g, style, contentWidth, barY);

		// Draw the cursor.
		drawCursor(g, style, barStartX, sliderWidth, barY);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		int diameter = getDiameter(getStyle());
		int optimalWidth = diameter + (this.maximum - this.minimum);
		int optimalHeight = diameter + 2 * FADE;
		size.setSize(optimalWidth, optimalHeight);
	}

	/**
	 * Draws the bar.
	 *
	 * @param gc
	 *            Graphics Context
	 * @param style
	 *            style
	 * @param width
	 *            width
	 * @param barY
	 *            start Y
	 */
	private void drawBar(GraphicsContext gc, Style style, int width, int barY) {
		gc.setColor(style.getExtraInt(BAR_COLOR_ID, DEFAULT_BAR_COLOR));
		gc.setBackgroundColor(Colors.BLACK);
		ShapePainter.drawThickLine(gc, 0, barY, width, barY, THICKNESS);
	}

	/**
	 * Draws the cursor depending of the slider percent.
	 *
	 * @param gc
	 *            Graphics Context
	 * @param style
	 *            style
	 * @param barStartX
	 *            bar start X
	 * @param sliderWidth
	 *            slider width
	 * @param cursorY
	 *            cursor Y
	 */
	private void drawCursor(GraphicsContext gc, Style style, int barStartX, int sliderWidth, int cursorY) {
		int diameter = getDiameter(style);
		int halfDiameter = diameter >> 1;
		int top = cursorY - halfDiameter;
		int left = (int) (getPercentComplete() * sliderWidth) + barStartX + THICKNESS;
		int circleColor = style.getExtraInt(CURSOR_EDGE_ID, DEFAULT_CURSOR_EDGE);
		CirclePainter.drawFilledCircle(gc, circleColor, getCursorBackgroundColor(style), left, top, diameter,
				THICKNESS);
		String valueText = Integer.toString(this.value);
		gc.setColor(style.getColor());
		StringPainter.drawStringInArea(gc, valueText, Fonts.getSourceSansPro16px700(), left, top, diameter, diameter,
				Alignment.HCENTER, Alignment.VCENTER);
	}

	private int getSize(Style style) {
		return style.getFont().getHeight();
	}

	@Override
	public boolean handleEvent(int event) {
		int eventType = Event.getType(event);
		if (eventType == Pointer.EVENT_TYPE) {
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int pointerX = pointer.getX();
			int action = Buttons.getAction(event);
			switch (action) {
			case Buttons.RELEASED:
				onPointerReleased();
				return true;
			case Pointer.DRAGGED:
				onPointerDragged(pointerX);
				return true;
			case Buttons.PRESSED:
				onPointerPressed();
				return true;
			default:
				break;
			}
		}
		return super.handleEvent(event);
	}

	private void onPointerDragged(int pointerX) {
		float percentComplete = computePercentComplete(pointerX);
		int newValue = (int) (this.minimum + (this.maximum - this.minimum) * percentComplete);
		setValue(newValue);
	}

	private void onPointerReleased() {
		this.pressed = false;
		updateStyle();
		requestRender();
	}

	private void onPointerPressed() {
		this.pressed = true;
		updateStyle();
		requestRender();
	}

	@Override
	public boolean isInState(final int state) {
		return (state == StateSelector.ACTIVE && this.pressed) || super.isInState(state);
	}

	void updateValue(int increment) {
		int newValue = this.value;
		newValue += increment;
		if (newValue != this.value) {
			newValue = XMath.limit(newValue, 0, INCREMENT_VALUE_MAX);
			setValue(newValue);
			requestRender();
		}
	}

	static void checkBounds(int minimum, int maximum, int initialValue) {
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
	public float getPercentComplete() {
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

	private int getDiameter(Style style) {
		return ((int) (getSize(style) * DIAMETER_MULTIPLIER));
	}

	private int getCursorBackgroundColor(Style style) {
		return style.getExtraInt(CURSOR_BACKGROUND_ID, Colors.BLACK);
	}
}
