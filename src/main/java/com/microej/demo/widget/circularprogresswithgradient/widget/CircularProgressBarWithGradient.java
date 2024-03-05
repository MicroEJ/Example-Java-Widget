/*
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.circularprogresswithgradient.widget;

import ej.bon.XMath;
import ej.drawing.ShapePainter;
import ej.microui.MicroUIException;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.Painter;
import ej.mwt.Widget;
import ej.mwt.util.Size;

/**
 * A circular progress bar with a gradient fill.
 * <p>
 * The gradient fill is done by drawing an image representing the circular bar.
 */
public class CircularProgressBarWithGradient extends Widget {

	private static final int PADDING_ANTIALIASING = 3;
	private static final float START_ANGLE = 90f;
	private static final float FULL_ANGLE = 360f;

	private final Image ringImage;
	private final Image cursorImage;
	private float progress;
	private final int barThickness;

	/**
	 * Creates the progress bar, given the image for the gradient circle and an image for the cursor at the end cap.
	 *
	 * <p>
	 * The specified thickness is the thickness of the circle in the given image.
	 *
	 * <p>
	 * This widget assumes that the images are square (width equals height).
	 *
	 * @param circlegradientImagePath
	 *            the path to the gradient circle image
	 * @param cursorImagePath
	 *            the path to the cursor image
	 * @param thickness
	 *            the thickness of the circle
	 * @throws MicroUIException
	 *             if the specified images could not be loaded
	 */
	public CircularProgressBarWithGradient(String circlegradientImagePath, String cursorImagePath, int thickness) {
		this.ringImage = Image.getImage(circlegradientImagePath);
		this.cursorImage = Image.getImage(cursorImagePath);
		this.barThickness = thickness;
	}

	/**
	 * Sets the bar progress.
	 *
	 * @param progress
	 *            the bar progress (between 0f and 1f)
	 */
	public void setProgress(float progress) {
		this.progress = XMath.limit(progress, 0.0f, 1.0f);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		int optimalSize = this.ringImage.getWidth() + this.cursorImage.getWidth();
		size.setSize(optimalSize, optimalSize);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		int centerX = contentWidth / 2;
		int centerY = contentHeight / 2;
		int diameter = this.ringImage.getWidth();
		float angle = this.progress * FULL_ANGLE;
		int radius = diameter / 2;
		int thickness = this.barThickness;
		int halfThickness = thickness / 2;
		int left = centerX - radius;
		int top = centerY - radius;

		// Draw the gradient circle.
		if (this.progress != 0f) {
			Painter.drawImage(g, this.ringImage, left, top);
		}

		g.setColor(getStyle().getColor());

		// Draw a circle arc to hide the ring image underneath.
		// The thickness of this arc is slightly greater than the thickness of the ring, to take into account the
		// anti-aliasing of the ring.
		ShapePainter.drawThickCircleArc(g, left + halfThickness, top + halfThickness, diameter - thickness, START_ANGLE,
				FULL_ANGLE - angle, thickness + PADDING_ANTIALIASING);

		// Draw the cursor at the end cap.
		double cursorAngle = Math.toRadians(START_ANGLE - angle);
		int cursorRadius = this.cursorImage.getWidth() / 2;
		int radiusMinusThickness = radius - halfThickness;
		int cursorX = (int) Math.round(centerX + Math.cos(cursorAngle) * radiusMinusThickness);
		int cursorY = (int) Math.round(centerY - Math.sin(cursorAngle) * radiusMinusThickness);
		Painter.drawImage(g, this.cursorImage, cursorX - cursorRadius, cursorY - cursorRadius);
	}

}
