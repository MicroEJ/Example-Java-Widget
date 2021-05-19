/*
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.sliderwithvalue.widget;

import com.microej.demo.widget.common.CirclePainter;

import ej.drawing.ShapePainter;
import ej.microui.display.GraphicsContext;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;

/**
 * Display a minus or plus encircled sign.
 * <p>
 * Sends increase and decrease value events to slider.
 */
public class PlusOrMinusDecorator extends Widget {

	private static final int LINE_THICKNESS = 2;
	private static final int THICKNESS = 1;
	private static final int INCREASE_10_PERCENT = 10;
	private static final int DECREASE_10_PERCENT = -10;
	private final boolean isMinus;
	private final int circleDiameter;
	private final int color;
	private final SliderWithValue slider;

	/**
	 * Creates a plus or minus decorator.
	 *
	 * @param circleDiameter
	 *            symbol diameter
	 * @param isMinus
	 *            minus or plus sign
	 * @param color
	 *            widget color
	 * @param slider
	 *            where to send the change value event
	 */
	public PlusOrMinusDecorator(int circleDiameter, boolean isMinus, int color, SliderWithValue slider) {
		this.isMinus = isMinus;
		this.circleDiameter = circleDiameter;
		this.color = color;
		this.slider = slider;
	}

	@Override
	public void onShown() {
		setEnabled(true);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		int circleSize = this.circleDiameter + THICKNESS;
		int widgetWidth = this.circleDiameter + (THICKNESS << 2);
		size.setSize(widgetWidth, circleSize);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();

		int verticalAlignment = style.getVerticalAlignment();
		// all design depends of the font, as in LimitSlider
		int fontSize = getStyle().getFont().getHeight();

		int yTop = Alignment.computeTopY(fontSize, 0, contentHeight, verticalAlignment);
		int top = yTop - (fontSize >> 1);

		if (this.isMinus) {
			drawMinus(g, top);
		} else {
			drawPlus(g, top);
		}
	}

	/**
	 * Draws the minus sign.
	 *
	 * @param gc
	 *            Graphics Context
	 * @param top
	 *            top of the sign
	 */
	private void drawMinus(GraphicsContext gc, int top) {
		CirclePainter.drawCircle(gc, this.color, THICKNESS + 1, top, this.circleDiameter, THICKNESS);
		int halfDiameter = this.circleDiameter >> 1;
		int signLeft = (halfDiameter >> 1) + (THICKNESS << 1);
		int signRight = signLeft + halfDiameter;
		int signTop = top + halfDiameter;
		// draw "-"
		gc.setColor(this.color);
		ShapePainter.drawThickLine(gc, signLeft, signTop, signRight, signTop, LINE_THICKNESS);
	}

	/**
	 * Draws the plus sign.
	 *
	 * @param gc
	 *            Graphics Context
	 * @param top
	 *            top of the sign
	 */
	private void drawPlus(GraphicsContext gc, int top) {
		CirclePainter.drawCircle(gc, this.color, THICKNESS, top, this.circleDiameter, THICKNESS);
		int halfDiameter = this.circleDiameter >> 1;
		int signHalfSize = (halfDiameter >> 1);
		int signLeft = signHalfSize + (THICKNESS << 1);
		int signRight = signLeft + halfDiameter;
		int signTop = top + signHalfSize + THICKNESS;
		int signBottom = signTop + halfDiameter;
		int signX = halfDiameter + (THICKNESS << 1);
		int signY = top + halfDiameter;
		// draw "+"
		gc.setColor(this.color);
		ShapePainter.drawThickLine(gc, signLeft, signY, signRight, signY, LINE_THICKNESS);
		ShapePainter.drawThickLine(gc, signX, signTop, signX, signBottom, LINE_THICKNESS);
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		if (type == Pointer.EVENT_TYPE) {
			int action = Buttons.getAction(event);
			if (action == Buttons.PRESSED) {
				if (this.isMinus) {
					this.slider.updateValue(DECREASE_10_PERCENT);
				} else {
					this.slider.updateValue(INCREASE_10_PERCENT);
				}
			}
		}
		return true;
	}

}
