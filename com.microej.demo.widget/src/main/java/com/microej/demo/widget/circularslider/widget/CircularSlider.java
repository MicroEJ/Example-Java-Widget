/*
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.circularslider.widget;

import ej.bon.XMath;
import ej.drawing.ShapePainter;
import ej.drawing.ShapePainter.Cap;
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
 * Slider with a round knob and a circular bar that is filled on the left of the knob.
 */
public class CircularSlider extends Widget {

	/**
	 * Thickness.
	 */
	public static final int THICKNESS_ID = 0;
	/**
	 * Slider color.
	 */
	public static final int SLIDER_COLOR_ID = 1;
	/**
	 * Guide thickness.
	 */
	public static final int GUIDE_THICKNESS_ID = 2;
	/**
	 * Guide color.
	 */
	public static final int GUIDE_COLOR_ID = 3;
	/**
	 * Slider diameter.
	 */
	public static final int SLIDER_DIAMETER_ID = 4;
	/**
	 * Slider thickness.
	 */
	public static final int SLIDER_THICKNESS_ID = 5;

	private static final int DEFAULT_THICKNESS = 4;
	private static final int FADE = 1;

	private static final int ARC_START = 225;
	private static final int ARC_ANGLE = -270;
	private static final int QUARTER_CIRCLE = 90;
	private static final int FULL_CIRCLE = 360;

	private final int maximum;
	private final int minimum;
	private int value;
	private boolean pressed;

	/**
	 * Creates a circular slider.
	 * <p>
	 * If the given initial value exceeds the minimum and maximum bounds, it is cropped.
	 *
	 * @param min
	 *            the minimum value of the slider
	 * @param max
	 *            the maximum value of the slider
	 * @param initialValue
	 *            the initial value of the slider
	 * @throws IllegalArgumentException
	 *             if the minimum value is greater than the maximum value
	 */
	public CircularSlider(int min, int max, int initialValue) {
		super(true);
		if (min > max) {
			throw new IllegalArgumentException();
		}
		this.minimum = min;
		this.maximum = max;
		this.value = XMath.limit(initialValue, min, max);
	}

	/**
	 * Sets the current value.
	 * <p>
	 * If the given value exceeds the minimum and maximum bounds, it is cropped.
	 *
	 * @param value
	 *            the value to set
	 */
	public void setValue(int value) {
		if (value != this.value) {
			this.value = XMath.limit(value, this.minimum, this.maximum);
		}
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		int range = this.maximum - this.minimum;
		size.setSize(range, range / 2);
	}

	private int getSliderDiameter(Style style) {
		return style.getExtraInt(SLIDER_DIAMETER_ID, style.getFont().getHeight());
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();

		int color = style.getColor();
		int thickness = style.getExtraInt(THICKNESS_ID, DEFAULT_THICKNESS);
		int backgroundColor = style.getExtraInt(SLIDER_COLOR_ID, color);
		int guideThickness = style.getExtraInt(GUIDE_THICKNESS_ID, DEFAULT_THICKNESS);
		int guideColor = style.getExtraInt(GUIDE_COLOR_ID, color);
		int sliderThickness = style.getExtraInt(SLIDER_THICKNESS_ID, DEFAULT_THICKNESS);

		int sliderDiameter = getSliderDiameter(style);
		int sliderRadius = sliderDiameter / 2;
		int circleWidthFootprint = Math.min(contentWidth, contentHeight);
		int circleHeightFootprint = circleWidthFootprint / 2
				+ (int) ((-Math.sin(Math.toRadians(ARC_START))) * ((float) circleWidthFootprint / 2 - sliderRadius))
				+ sliderRadius;
		int circleDiameter = circleWidthFootprint - sliderDiameter - 2 * FADE;
		int circleRadius = circleDiameter / 2;
		int circleX = Alignment.computeLeftX(circleWidthFootprint, sliderRadius, contentWidth,
				style.getHorizontalAlignment());
		int circleY = Alignment.computeTopY(circleHeightFootprint, sliderRadius, contentHeight,
				style.getVerticalAlignment());
		int centerX = circleX + circleRadius;
		int centerY = circleY + circleRadius;

		double currentAngle = ARC_ANGLE * (double) (this.value - this.minimum) / (this.maximum - this.minimum);
		double angleRadians = Math.toRadians(ARC_START - QUARTER_CIRCLE - currentAngle);
		int sliderX = centerX + (int) (Math.cos(angleRadians) * circleRadius);
		int sliderY = centerY + (int) (Math.sin(angleRadians) * circleRadius);

		g.setColor(guideColor);
		ShapePainter.drawThickFadedCircleArc(g, circleX, circleY, circleDiameter, ARC_START, ARC_ANGLE, guideThickness,
				FADE, Cap.ROUNDED, Cap.ROUNDED);
		g.setColor(color);
		ShapePainter.drawThickFadedCircleArc(g, circleX, circleY, circleDiameter, ARC_START, (int) currentAngle,
				thickness, FADE, Cap.ROUNDED, Cap.NONE);
		g.setColor(backgroundColor);
		ShapePainter.drawThickFadedPoint(g, sliderX, sliderY, sliderDiameter, FADE);
		g.setColor(color);
		g.removeBackgroundColor();
		ShapePainter.drawThickFadedCircle(g, sliderX - sliderRadius, sliderY - sliderRadius, sliderDiameter,
				sliderThickness, FADE);

	}

	@Override
	public boolean handleEvent(int event) {
		int eventType = Event.getType(event);
		if (eventType == Pointer.EVENT_TYPE) {
			Rectangle contentBounds = getContentBounds();
			int contentWidth = contentBounds.getWidth();
			int contentHeight = contentBounds.getHeight();

			Pointer pointer = (Pointer) Event.getGenerator(event);
			int pointerX = pointer.getX() - getAbsoluteX() - contentBounds.getX();
			int pointerY = pointer.getY() - getAbsoluteY() - contentBounds.getY();
			int action = Buttons.getAction(event);

			Style style = getStyle();
			int thickness = style.getExtraInt(THICKNESS_ID, DEFAULT_THICKNESS);

			int sliderDiameter = getSliderDiameter(style);
			int sliderRadius = sliderDiameter / 2;
			int circleDiameter = Math.min(contentWidth, contentHeight) - thickness;
			int circleRadius = circleDiameter / 2;
			int circleX = Alignment.computeLeftX(circleDiameter, thickness / 2, contentWidth,
					style.getHorizontalAlignment()) + sliderRadius;
			int circleY = Alignment.computeTopY(circleDiameter, thickness / 2, contentHeight,
					style.getVerticalAlignment()) + sliderRadius;
			int centerX = circleX + circleRadius;
			int centerY = circleY + circleRadius;

			if (action == Buttons.PRESSED) {
				double distanceX = (double) pointerX - centerX;
				double distanceY = (double) pointerY - centerY;
				int distance = (int) Math.sqrt(distanceX * distanceX + distanceY * distanceY);
				if (distance > circleRadius - sliderDiameter && distance < circleRadius + sliderDiameter) {
					this.pressed = true;
					onMove(pointerX, pointerY, centerX, centerY);
				}
			} else if (action == Pointer.DRAGGED) {
				if (this.pressed) {
					onMove(pointerX, pointerY, centerX, centerY);
				}
				return true;
			} else if (action == Buttons.RELEASED) {
				this.pressed = false;
			}
		}

		return super.handleEvent(event);
	}

	private void onMove(int pointerX, int pointerY, int centerX, int centerY) {
		double angle = Math.toDegrees(Math.atan2((double) pointerY - centerY, (double) pointerX - centerX));
		angle -= ARC_START - QUARTER_CIRCLE;
		if (angle < 0) {
			angle += FULL_CIRCLE;
		}
		int value;
		if (angle < -ARC_ANGLE) {
			value = (int) (this.minimum + angle * (this.maximum - this.minimum + 1) / -ARC_ANGLE);
			setValue(value);
			requestRender();
		}
	}

}
