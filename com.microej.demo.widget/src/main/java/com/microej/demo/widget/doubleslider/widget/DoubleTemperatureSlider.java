/*
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.doubleslider.widget;

import ej.bon.XMath;
import ej.drawing.ShapePainter;
import ej.drawing.ShapePainter.Cap;
import ej.microui.display.Display;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.Event;
import ej.microui.event.EventHandler;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Rectangle;
import ej.mwt.util.Size;
import ej.widget.util.color.GradientHelper;

/**
 * Vertical slider with two knobs to select heat and cool temperatures.
 */
public class DoubleTemperatureSlider extends Widget implements EventHandler {

	private static final int QUARTER_CIRCLE = 90;
	private static final int HALF_CIRCLE = 180;
	private static final int THREE_QUARTER_CIRCLE = 270;

	private static final int HEAT_COLOR = 0xfc7900;
	private static final int COLD_COLOR = 0x4b96fb;

	private static final int GUIDE_SIZE_FACTOR = 3;
	private static final int TEN = 10;
	private static final int THICKNESS = 1;
	private static final int FADE = 1;

	private static final String TEMPERATURE_PATTERN = "00.0°C"; //$NON-NLS-1$

	/**
	 * Slider color.
	 */
	public static final int SLIDER_COLOR_ID = 1;
	/**
	 * Guide color.
	 */
	public static final int GUIDE_COLOR_ID = 2;

	private final float maximum;
	private final float minimum;
	private float heatValue;
	private float coolValue;
	private boolean pressedHeat;
	private boolean pressedCool;

	/**
	 * Creates a vertical slider.
	 *
	 * @param min
	 *            the minimum value of the slider range.
	 * @param max
	 *            the maximum value of the slider range.
	 * @param initialHeatValue
	 *            the initial position of the heat slider.
	 * @param initialCoolValue
	 *            the initial position of the cool slider.
	 * @throws IllegalArgumentException
	 *             if the minimum range value exceeds the maximum range value.
	 */
	public DoubleTemperatureSlider(int min, int max, float initialHeatValue, float initialCoolValue) {
		super(true);
		if (max < min) {
			throw new IllegalArgumentException();
		}

		this.minimum = min;
		this.maximum = max;
		this.heatValue = XMath.limit(initialHeatValue, min, max);
		this.coolValue = XMath.limit(initialCoolValue, this.heatValue, max);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		int color = style.getColor();
		int sliderColor = style.getExtraInt(SLIDER_COLOR_ID, color);
		int guideColor = style.getExtraInt(GUIDE_COLOR_ID, color);
		Font font = style.getFont();
		int fontHeight = font.getHeight();
		int horizontalAlignment = style.getHorizontalAlignment();

		int textSpacing = fontHeight / 2;
		int sliderWidth = font.stringWidth(TEMPERATURE_PATTERN) + 2 * textSpacing;
		int sliderHeight = fontHeight;

		int halfSliderHeight = sliderHeight / 2;
		int guideWidth = sliderHeight / GUIDE_SIZE_FACTOR;

		int barHeight = contentHeight - sliderHeight - 2 * FADE;
		float range = this.maximum - this.minimum;
		int coolSliderY = (int) ((this.maximum - this.coolValue) * barHeight / range) + halfSliderHeight;
		int heatSliderY = (int) ((this.maximum - this.heatValue) * barHeight / range) + halfSliderHeight;

		int sliderX = Alignment.computeLeftX(sliderWidth, 0, contentWidth, horizontalAlignment);
		int temperatureX = sliderX + textSpacing;
		int guideX = sliderX + sliderHeight / 2;

		// Draw guides.
		g.setColor(sliderColor);
		ShapePainter.drawThickFadedLine(g, guideX, halfSliderHeight, guideX, barHeight + halfSliderHeight, guideWidth,
				1, Cap.ROUNDED, Cap.ROUNDED);
		fillGradient(g, guideX - guideWidth / 4, halfSliderHeight, guideWidth / 2, coolSliderY, sliderColor,
				COLD_COLOR);
		fillRectangle(g, guideX - guideWidth / 4, coolSliderY, guideWidth / 2, heatSliderY, guideColor);
		fillGradient(g, guideX - guideWidth / 4, heatSliderY, guideWidth / 2, barHeight + halfSliderHeight, HEAT_COLOR,
				sliderColor);

		boolean hasBackgroundColor = g.hasBackgroundColor();
		int backgroundColor = hasBackgroundColor ? g.getBackgroundColor() : sliderColor;
		g.removeBackgroundColor();

		// Draw knobs.
		g.setColor(sliderColor);
		int coolSliderTopY = coolSliderY - halfSliderHeight;
		int sliderRectangleX = sliderX + sliderHeight / 2;
		int sliderRectangleWidth = sliderWidth - sliderHeight;
		int sliderRightCircleX = sliderX + sliderWidth - sliderHeight;
		Painter.fillCircleArc(g, sliderX, coolSliderTopY, sliderHeight, QUARTER_CIRCLE, HALF_CIRCLE);
		Painter.fillRectangle(g, sliderRectangleX, coolSliderTopY, sliderRectangleWidth, sliderHeight);
		Painter.fillCircleArc(g, sliderRightCircleX, coolSliderTopY, sliderHeight, THREE_QUARTER_CIRCLE, HALF_CIRCLE);
		int minSliderTopY = heatSliderY - halfSliderHeight;
		Painter.fillCircleArc(g, sliderX, minSliderTopY, sliderHeight, QUARTER_CIRCLE, HALF_CIRCLE);
		Painter.fillRectangle(g, sliderRectangleX, minSliderTopY, sliderRectangleWidth, sliderHeight);
		Painter.fillCircleArc(g, sliderRightCircleX, minSliderTopY, sliderHeight, THREE_QUARTER_CIRCLE, HALF_CIRCLE);
		g.setColor(COLD_COLOR);
		ShapePainter.drawThickFadedCircleArc(g, sliderX, coolSliderTopY, sliderHeight, QUARTER_CIRCLE, HALF_CIRCLE,
				THICKNESS, FADE, Cap.NONE, Cap.NONE);
		ShapePainter.drawThickFadedLine(g, sliderRectangleX + 1, coolSliderTopY,
				sliderRectangleX + sliderWidth - sliderHeight, coolSliderTopY, THICKNESS, FADE, Cap.NONE, Cap.NONE);
		ShapePainter.drawThickFadedLine(g, sliderRectangleX + 1, coolSliderTopY + sliderHeight,
				sliderRectangleX + sliderWidth - sliderHeight, coolSliderTopY + sliderHeight, THICKNESS, FADE, Cap.NONE,
				Cap.NONE);
		ShapePainter.drawThickFadedCircleArc(g, sliderRightCircleX, coolSliderTopY, sliderHeight, THREE_QUARTER_CIRCLE,
				HALF_CIRCLE, THICKNESS, FADE, Cap.NONE, Cap.NONE);
		g.setColor(HEAT_COLOR);
		ShapePainter.drawThickFadedCircleArc(g, sliderX, minSliderTopY, sliderHeight, QUARTER_CIRCLE, HALF_CIRCLE,
				THICKNESS, FADE, Cap.NONE, Cap.NONE);
		ShapePainter.drawThickFadedLine(g, sliderRectangleX + 1, minSliderTopY,
				sliderRectangleX + sliderWidth - sliderHeight, minSliderTopY, THICKNESS, FADE, Cap.NONE, Cap.NONE);
		ShapePainter.drawThickFadedLine(g, sliderRectangleX + 1, minSliderTopY + sliderHeight,
				sliderRectangleX + sliderWidth - sliderHeight, minSliderTopY + sliderHeight, THICKNESS, FADE, Cap.NONE,
				Cap.NONE);
		ShapePainter.drawThickFadedCircleArc(g, sliderRightCircleX, minSliderTopY, sliderHeight, THREE_QUARTER_CIRCLE,
				HALF_CIRCLE, THICKNESS, FADE, Cap.NONE, Cap.NONE);

		if (hasBackgroundColor) {
			g.setBackgroundColor(backgroundColor);
		}

		// Draw texts.
		g.setColor(color);
		int quarterSliderSize = halfSliderHeight / 2;
		int baselineY = quarterSliderSize;
		int temperatureShiftY = baselineY - font.getBaselinePosition();
		Painter.drawString(g, getValue(this.coolValue), font, temperatureX, coolSliderY + temperatureShiftY);
		Painter.drawString(g, getValue(this.heatValue), font, temperatureX, heatSliderY + temperatureShiftY);
	}

	private static void fillRectangle(GraphicsContext g, int x, int yTop, int width, int yBottom, int color) {
		g.setColor(color);
		Painter.fillRectangle(g, x, yTop, width, yBottom - yTop);
	}

	private static void fillGradient(GraphicsContext g, int x, int yTop, int width, int yBottom, int colorTop,
			int colorBottom) {
		int[] gradient = GradientHelper.createGradient(Display.getDisplay(), colorTop, colorBottom);
		int gradientLength = gradient.length;
		int height = yBottom - yTop;
		float stepYF = (float) height / gradientLength;
		int stepY = (int) Math.ceil(stepYF);
		float currentYF = yTop;
		for (int color : gradient) {
			g.setColor(color);
			Painter.fillRectangle(g, x, (int) Math.floor(currentYF), width, stepY);
			currentYF += stepYF;
		}
	}

	private static String getValue(float value) {
		int intPart = (int) value;
		int decimal = (int) ((value - intPart) * TEN);
		return new StringBuilder().append(intPart).append('.').append(decimal).append('°').append('C').toString();
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		Font font = style.getFont();
		int fontHeight = font.getHeight();
		int textSpacing = fontHeight / 2;
		int sliderWidth = font.stringWidth(TEMPERATURE_PATTERN) + 2 * textSpacing;
		int sliderHeight = fontHeight;

		int optimalHeight = sliderHeight + (int) (this.maximum - this.minimum);
		int optimalWidth = sliderWidth;
		size.setSize(optimalWidth, optimalHeight);
	}

	@Override
	public boolean handleEvent(int event) {
		int eventType = Event.getType(event);
		if (eventType == Pointer.EVENT_TYPE) {
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int pointerX = pointer.getX() - getAbsoluteX();
			int pointerY = pointer.getY() - getAbsoluteY();
			int action = Buttons.getAction(event);
			switch (action) {
			case Buttons.PRESSED:
				return onPointerPressed(pointerX, pointerY);
			case Pointer.DRAGGED:
				if (this.pressedCool || this.pressedHeat) {
					onPointerDragged(pointerY);
					return true;
				}
			case Buttons.RELEASED:
				if (this.pressedCool || this.pressedHeat) {
					onPointerReleased();
					return true;
				}
			}
		}
		return super.handleEvent(event);
	}

	private boolean onPointerPressed(int pointerX, int pointerY) {
		Style style = getStyle();
		Font font = style.getFont();
		int fontHeight = font.getHeight();
		int horizontalAlignment = style.getHorizontalAlignment();

		Rectangle contentBounds = getContentBounds();
		int contentWidth = contentBounds.getWidth();
		int contentHeight = contentBounds.getHeight();

		int textSpacing = fontHeight / 2;
		int sliderWidth = font.stringWidth(TEMPERATURE_PATTERN) + 2 * textSpacing;
		int sliderHeight = fontHeight;

		int barHeight = contentHeight - sliderHeight - 2 * FADE;
		float range = this.maximum - this.minimum;
		int coolSliderY = (int) ((this.maximum - this.coolValue) * barHeight / range);
		int heatSliderY = (int) ((this.maximum - this.heatValue) * barHeight / range);

		int sliderX = Alignment.computeLeftX(sliderWidth, 0, contentWidth, horizontalAlignment);

		if (pointerX < sliderX || pointerX > sliderX + sliderWidth) {
			return false;
		}
		if (pointerY >= coolSliderY && pointerY <= coolSliderY + sliderHeight) {
			this.pressedCool = true;
			return true;
		}
		if (pointerY >= heatSliderY && pointerY <= heatSliderY + sliderHeight) {
			this.pressedHeat = true;
			return true;
		}
		return false;
	}

	private void onPointerDragged(int pointerY) {
		Style style = getStyle();
		Font font = style.getFont();
		int fontHeight = font.getHeight();
		int sliderHeight = fontHeight;

		Rectangle contentBounds = getContentBounds();
		int contentHeight = contentBounds.getHeight();

		int barHeight = contentHeight - sliderHeight;
		float range = this.maximum - this.minimum;

		float maxDifference = sliderHeight * range / barHeight;
		int halfSliderHeight = sliderHeight / 2;
		float newValue = -(((pointerY - halfSliderHeight) * range / barHeight) - this.maximum);
		if (this.pressedCool) {
			this.coolValue = XMath.limit(newValue, this.minimum + maxDifference, this.maximum);
			if (this.heatValue > this.coolValue - maxDifference) {
				this.heatValue = this.coolValue - maxDifference;
			}
			requestRender();
		}
		if (this.pressedHeat) {
			this.heatValue = XMath.limit(newValue, this.minimum, this.maximum - maxDifference);
			if (this.coolValue < this.heatValue + maxDifference) {
				this.coolValue = this.heatValue + maxDifference;
			}
			requestRender();
		}
	}

	private void onPointerReleased() {
		this.pressedCool = false;
		this.pressedHeat = false;
		requestRender();
	}

}
