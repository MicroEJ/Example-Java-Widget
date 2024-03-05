/*
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.common;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;

/**
 * Rectangle drawing utility.
 */
public class RectanglePainter {

	private RectanglePainter() {
		// Prevent instantiation.
	}

	/**
	 * Draws a rectangle from <code>(startX,startY)</code> to <code>(startX + width,startY + height)</code> using the
	 * {@link GraphicsContext}'s current color.
	 * <p>
	 * The shape's bounding box is <code>(startX, startY) to startX + width, endY + height)</code>.
	 * <p>
	 * If either <code>width</code> or <code>height</code> is negative, zero or smaller than 2*thickness, nothing is
	 * drawn.
	 *
	 * @param gc
	 *            the graphics context where render the drawing.
	 * @param startX
	 *            the x coordinate of the start of the rectangle.
	 * @param startY
	 *            the y coordinate of the start of the rectangle.
	 * @param width
	 *            the width of the rectangle.
	 * @param height
	 *            the height of the rectangle.
	 * @param thickness
	 *            the thickness of the rectangle lines.
	 */
	public static void drawThickRectangle(GraphicsContext gc, int startX, int startY, int width, int height,
			int thickness) {
		for (int i = 0; i < thickness; i++) {
			Painter.drawRectangle(gc, startX + i, startY + i, width - i * 2, height - i * 2);
		}
	}
}
