/*
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.common;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;

/**
 * Dotted line drawing utility.
 */
public class DottedLinePainter {

	private DottedLinePainter() {
		// Prevent instantiation.
	}

	/**
	 * Dotted horizontal line.
	 *
	 * @param gc
	 *            Graphics Context.
	 * @param color
	 *            color of the line.
	 * @param positionX
	 *            starting position X.
	 * @param positionY
	 *            starting position Y.
	 * @param length
	 *            the length of the line.
	 * @param dotLength
	 *            the length of the dots/dashes of the line.
	 */
	public static void drawHorizontalDottedLine(GraphicsContext gc, int color, int positionX, int positionY, int length,
			int dotLength) {
		gc.setColor(color);
		int xEnd = positionX + length;
		int xPos = positionX;
		while (xPos < xEnd) {
			Painter.drawHorizontalLine(gc, xPos, positionY, dotLength);
			xPos += dotLength * 2;
		}
	}

	/**
	 * Dotted horizontal line with two alternating colors.
	 *
	 * @param gc
	 *            Graphics Context.
	 * @param color
	 *            main color of the line.
	 * @param secondaryColor
	 *            color to fill spaces in between.
	 * @param positionX
	 *            starting position X.
	 * @param positionY
	 *            starting position Y.
	 * @param length
	 *            the length of the line.
	 * @param dotLength
	 *            the length of the dots/dashes of the line.
	 */
	public static void drawHorizontalBiColorDottedLine(GraphicsContext gc, int color, int secondaryColor, int positionX,
			int positionY, int length, int dotLength) {
		drawHorizontalDottedLine(gc, secondaryColor, positionX + dotLength, positionY, length - dotLength, dotLength);
		drawHorizontalDottedLine(gc, color, positionX, positionY, length, dotLength);
	}

	/**
	 * Dotted horizontal line.
	 *
	 * @param gc
	 *            Graphics Context.
	 * @param color
	 *            color of the line.
	 * @param positionX
	 *            starting position X.
	 * @param positionY
	 *            starting position Y.
	 * @param length
	 *            the length of the line.
	 * @param dotLength
	 *            the length of the dots/dashes of the line.
	 */
	public static void drawVerticalDottedLine(GraphicsContext gc, int color, int positionX, int positionY, int length,
			int dotLength) {
		gc.setColor(color);
		int yEnd = positionY + length;
		int yPos = positionY;
		while (yPos < yEnd) {
			Painter.drawVerticalLine(gc, positionX, yPos, dotLength);
			yPos += dotLength * 2;
		}
	}

	/**
	 * Dotted vertical line with two alternating colors.
	 *
	 * @param gc
	 *            Graphics Context.
	 * @param color
	 *            main color of the line.
	 * @param secondaryColor
	 *            color to fill spaces in between.
	 * @param positionX
	 *            starting position X.
	 * @param positionY
	 *            starting position Y.
	 * @param length
	 *            the length of the line.
	 * @param dotLength
	 *            the length of the dots/dashes of the line.
	 */
	public static void drawVerticalBiColorDottedLine(GraphicsContext gc, int color, int secondaryColor, int positionX,
			int positionY, int length, int dotLength) {
		drawVerticalDottedLine(gc, secondaryColor, positionX + dotLength, positionY, length - dotLength, dotLength);
		drawVerticalDottedLine(gc, color, positionX, positionY, length, dotLength);
	}
}
