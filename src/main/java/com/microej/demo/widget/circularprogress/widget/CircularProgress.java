/*
 * Copyright 2021-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.circularprogress.widget;

import ej.bon.XMath;
import ej.drawing.ShapePainter;
import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Size;

/**
 * Circular Progress.
 */
public class CircularProgress extends Widget {

	private static final int START_ANGLE = 90;
	private static final int FULL_ANGLE = 360;

	private static final int DEFAULT_DIAMETER = 100;
	private static final int DEFAULT_THICK = 5;

	private static final int FADE = 1;

	/**
	 * Background color ID.
	 */
	public static final int BACKGROUND_COLOR = 0;
	/**
	 * Progress diameter ID.
	 */
	public static final int PROGRESS_DIAMETER = 1;
	/**
	 * Progress thick ID.
	 */
	public static final int PROGRESS_THICK = 2;

	private float progress;

	/**
	 * Sets progress between 0.0 and 1.0.
	 *
	 * @param progress
	 *            bar progress
	 */
	public void setProgress(float progress) {
		this.progress = XMath.limit(progress, 0.0f, 1.0f);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		int diameter = style.getExtraInt(PROGRESS_DIAMETER, DEFAULT_DIAMETER) + FADE * 2;
		size.setSize(diameter, diameter);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		int diameter = style.getExtraInt(PROGRESS_DIAMETER, DEFAULT_DIAMETER);
		int thick = style.getExtraInt(PROGRESS_THICK, DEFAULT_THICK);
		int progressDiameter = diameter - (thick << 2);
		int centerX = contentWidth / 2;
		int centerY = contentHeight / 2;

		// background
		g.setColor(style.getExtraInt(BACKGROUND_COLOR, Colors.BLACK));
		int left = centerX - (diameter >> 1);
		int top = centerY - (diameter >> 1);
		ShapePainter.drawThickFadedCircle(g, left, top, diameter, 0, FADE);
		Painter.fillCircle(g, left, top, diameter);
		// Fills the complete part, from 90° anti-clockwise.
		int startAngle = START_ANGLE;
		int arcAngle = (int) (-FULL_ANGLE * this.progress);

		// progress
		g.setColor(style.getColor());
		left = centerX - (progressDiameter >> 1);
		top = centerY - (progressDiameter >> 1);
		ShapePainter.drawThickFadedCircleArc(g, left, top, progressDiameter, startAngle, arcAngle, thick, FADE,
				ShapePainter.Cap.ROUNDED, ShapePainter.Cap.ROUNDED);
	}

}
