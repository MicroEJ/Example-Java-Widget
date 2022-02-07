/*
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.circulardottedprogress.widget;

import ej.drawing.ShapePainter;
import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Size;

/**
 * Circular Dotted Progress.
 */
public class CircularDottedProgress extends Widget {

	/**
	 * Background color ID.
	 */
	public static final int BACKGROUND_COLOR = 0;
	/**
	 * Progress diameter ID.
	 */
	public static final int PROGRESS_DIAMETER = 1;
	/**
	 * Dot diameter ID.
	 */
	public static final int DOT_SIZE = 2;

	private static final int DEFAULT_DIAMETER = 100;
	private static final int DEFAULT_DOT_SIZE = 5;

	private static final int FADING = 1;

	/**
	 * Angle in degrees between two dots.
	 */
	private static final float DOT_INTERVAL = 30.0f;
	private static final float ANGLE_FULL_CIRCLE = 360.0f;
	private static final float ANGLE_TRANSLATE_RIGHT = 270.0f;
	private final float startAngle;

	private final boolean clockwise;
	private float currentAngle;

	/**
	 * Creates a circular dotted progress.
	 *
	 * @param startAngle
	 *            angle where the progress starts, position of 0 degrees is middle right.
	 * @param clockwise
	 *            true for clockwise, false for counter clockwise.
	 */
	public CircularDottedProgress(float startAngle, boolean clockwise) {
		this.startAngle = toPositiveNormalizedAngle(startAngle + ANGLE_TRANSLATE_RIGHT);
		this.clockwise = clockwise;
	}

	/**
	 * Set progress from 0 to 360 degrees.
	 *
	 * @param progress
	 *            bar progress
	 */
	public void setProgress(float progress) {
		this.currentAngle = progress;
		requestRender();
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		int diameter = style.getExtraInt(PROGRESS_DIAMETER, DEFAULT_DIAMETER) + FADING * 2;
		size.setSize(diameter, diameter);

	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		int diameter = style.getExtraInt(PROGRESS_DIAMETER, DEFAULT_DIAMETER);
		int dotSize = style.getExtraInt(DOT_SIZE, DEFAULT_DOT_SIZE);

		float progressRadius = (diameter - (dotSize << 2)) >> 1;
		int centerX = (diameter >> 1) - (dotSize >> 1);
		int centerY = centerX;

		// background
		g.setColor(style.getExtraInt(BACKGROUND_COLOR, Colors.BLACK));
		ShapePainter.drawThickFadedPoint(g, contentWidth / 2, contentHeight / 2, diameter, FADING);
		// progress
		g.setColor(style.getColor());
		displayDots(g, progressRadius, dotSize, centerX, centerY);
	}

	/**
	 * Display progress dots around widget center.
	 *
	 * @param g
	 *            Graphics context
	 * @param progressRadius
	 *            progress distance from widget center
	 * @param dotSize
	 *            dot size
	 * @param centerX
	 *            widget center X
	 * @param centerY
	 *            widget center Y
	 */
	private void displayDots(GraphicsContext g, float progressRadius, int dotSize, int centerX, int centerY) {
		float beginAngle = this.startAngle;
		float angleDifference = this.currentAngle;

		if (angleDifference >= DOT_INTERVAL) {

			int dotsCounter = Math.round(angleDifference / DOT_INTERVAL) + 1;
			int dotNo = 0;
			float dotAngle = beginAngle;
			do {
				double radiansAngle = Math.toRadians(dotAngle);
				int dotX = centerX + (int) Math.round((progressRadius * Math.sin(radiansAngle)));
				int dotY = centerY + (int) Math.round(progressRadius * Math.cos(radiansAngle));
				Painter.fillCircle(g, dotX, dotY, dotSize);

				if (this.clockwise) {
					dotAngle -= DOT_INTERVAL;
				} else {
					dotAngle += DOT_INTERVAL;
				}
				dotNo++;
			} while (dotNo < dotsCounter);
		}
	}

	/**
	 * Transforms the given angle in a positive one.
	 *
	 * @param angle
	 *            original angle
	 * @return positive angle
	 */
	private float toPositiveNormalizedAngle(float angle) {
		if (angle < 0.f) {
			return ANGLE_FULL_CIRCLE - angle;
		} else if (angle > ANGLE_FULL_CIRCLE) {
			return angle - ANGLE_FULL_CIRCLE;
		} else {
			return angle;
		}
	}

}
