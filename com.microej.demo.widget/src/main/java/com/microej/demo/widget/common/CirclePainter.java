/**
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.common;

import ej.drawing.ShapePainter;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;

/**
 * Circle drawing utility.
 */
public class CirclePainter {

	private CirclePainter() {
		// Prevent instantiation.
	}

	/**
	 * Generic filled circle.
	 *
	 * @param gc
	 *            Graphics Context
	 * @param color
	 *            circle edge
	 * @param backgroundColor
	 *            background
	 * @param positionX
	 *            position X
	 * @param positionY
	 *            position Y
	 * @param diameter
	 *            circle external diameter
	 * @param thickness
	 *            circle thickness
	 */
	public static void drawFilledCircle(GraphicsContext gc, int color, int backgroundColor, int positionX,
			int positionY, int diameter, int thickness) {
		gc.setColor(backgroundColor);
		Painter.fillCircle(gc, positionX, positionY, diameter);
		drawExternalCircle(gc, color, positionX, positionY, diameter, thickness);
	}

	/**
	 * Generic filled circle.
	 *
	 * @param gc
	 *            Graphics Context
	 * @param color
	 *            circle edge
	 * @param positionX
	 *            position X
	 * @param positionY
	 *            position Y
	 * @param diameter
	 *            circle external diameter
	 * @param thickness
	 *            circle thickness
	 */
	public static void drawCircle(GraphicsContext gc, int color, int positionX, int positionY, int diameter,
			int thickness) {
		drawExternalCircle(gc, color, positionX, positionY, diameter, thickness);
	}

	private static void drawExternalCircle(GraphicsContext gc, int color, int positionX, int positionY, int diameter,
			int thickness) {
		gc.setColor(color);
		gc.removeBackgroundColor();
		ShapePainter.drawThickFadedCircle(gc, positionX, positionY, diameter, thickness, 1);
	}
}
