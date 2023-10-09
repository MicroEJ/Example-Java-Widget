/*
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.common;

import ej.drawing.TransformPainter;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;

/**
 * Provides utility methods to draw strings.
 */
public class StringOnCirclePainter {

	/**
	 * Enum for specifying the direction in which a rotation can occur.
	 */
	public enum Direction {
		/**
		 * Specifies that the direction is the same as a clock's hands.
		 */
		CLOCKWISE,
		/**
		 * Specifies that the direction is the opposite as a clock's hands.
		 */
		COUNTER_CLOCKWISE,
	}

	private StringOnCirclePainter() {
		// Prevents instantiation.
	}

	/**
	 * Draws a text along a circle. The circle represents the text baseline.
	 * <p>
	 * This method uses the bilinear algorithm to render the content. This algorithm performs a better rendering than
	 * the nearest neighbor algorithm but it is slower to apply.
	 * <p>
	 * This method uses the color of the graphics context as font color.
	 * <p>
	 * The anchor point is the center of the circle.
	 * <p>
	 * The start angle specifies where the text starts along the circle. When the starting angle is 0, the text starts
	 * at the 3 o'clock position (positive X axis).
	 * <p>
	 * The text winds along the circle in the specified direction (clockwise or counter-clockwise). The radius of the
	 * circle must be positive. If it is less than or equal to 0, nothing is drawn.
	 *
	 * @param g
	 *            the graphics context to draw on
	 * @param string
	 *            the string to draw
	 * @param font
	 *            the font to use
	 * @param x
	 *            the x coordinate of the anchor point (center of the circle)
	 * @param y
	 *            the y coordinate of the anchor point (center of the circle)
	 * @param radius
	 *            the radius of the circle, in pixels
	 * @param startAngle
	 *            the angle at which the text starts along the circle, in degrees
	 * @param direction
	 *            the winding direction of the text along the circle
	 */
	public static void drawStringOnCircleBilinear(GraphicsContext g, String string, Font font, int x, int y, int radius,
			float startAngle, Direction direction) {
		drawStringOnCircleBilinear(g, string, font, x, y, radius, startAngle, direction, GraphicsContext.OPAQUE, 0);
	}

	/**
	 * Draws a text along a circle. The circle represents the text baseline.
	 * <p>
	 * This method uses the bilinear algorithm to render the content. This algorithm performs a better rendering than
	 * the nearest neighbor algorithm but it is slower to apply.
	 * <p>
	 * This method uses the color of the graphics context as font color.
	 * <p>
	 * The anchor point is the center of the circle.
	 * <p>
	 * The start angle specifies where the text starts along the circle. When the starting angle is 0, the text starts
	 * at the 3 o'clock position (positive X axis).
	 * <p>
	 * The text winds along the circle in the specified direction (clockwise or counter-clockwise). The radius of the
	 * circle must be positive. If it is less than or equal to 0, nothing is drawn.
	 *
	 * @param g
	 *            the graphics context to draw on
	 * @param string
	 *            the string to draw
	 * @param font
	 *            the font to use
	 * @param x
	 *            the x coordinate of the anchor point (center of the circle)
	 * @param y
	 *            the y coordinate of the anchor point (center of the circle)
	 * @param radius
	 *            the radius of the circle, in pixels
	 * @param startAngle
	 *            the angle at which the text starts along the circle, in degrees
	 * @param direction
	 *            the winding direction of the text along the circle
	 * @param alpha
	 *            the global opacity rendering value
	 * @param letterSpacing
	 *            the extra letter spacing to use, in pixels
	 */
	public static void drawStringOnCircleBilinear(GraphicsContext g, String string, Font font, int x, int y, int radius,
			float startAngle, Direction direction, int alpha, int letterSpacing) {
		drawStringOnCircleInternal(g, string, font, x, y, radius, startAngle, direction, alpha, letterSpacing, true);
	}

	/**
	 * Draws a text along a circle. The circle represents the text baseline.
	 * <p>
	 * This method uses the nearest neighbor algorithm to render the content. This algorithm is faster than the bilinear
	 * algorithm but its rendering is more simple.
	 * <p>
	 * This method uses the color of the graphics context as font color.
	 * <p>
	 * The anchor point is the center of the circle.
	 * <p>
	 * The start angle specifies where the text starts along the circle. When the starting angle is 0, the text starts
	 * at the 3 o'clock position (positive X axis).
	 * <p>
	 * The text winds along the circle in the specified direction (clockwise or counter-clockwise). The radius of the
	 * circle must be positive. If it is less than or equal to 0, nothing is drawn.
	 *
	 * @param g
	 *            the graphics context to draw on
	 * @param string
	 *            the string to draw
	 * @param font
	 *            the font to use
	 * @param x
	 *            the x coordinate of the anchor point (center of the circle)
	 * @param y
	 *            the y coordinate of the anchor point (center of the circle)
	 * @param radius
	 *            the radius of the circle, in pixels
	 * @param startAngle
	 *            the angle at which the text starts along the circle, in degrees
	 * @param direction
	 *            the winding direction of the text along the circle
	 */
	public static void drawStringOnCircleNearestNeighbor(GraphicsContext g, String string, Font font, int x, int y,
			int radius, float startAngle, Direction direction) {
		drawStringOnCircleNearestNeighbor(g, string, font, x, y, radius, startAngle, direction, GraphicsContext.OPAQUE,
				0);
	}

	/**
	 * Draws a text along a circle. The circle represents the text baseline.
	 * <p>
	 * This method uses the nearest neighbor algorithm to render the content. This algorithm is faster than the bilinear
	 * algorithm but its rendering is more simple.
	 * <p>
	 * This method uses the color of the graphics context as font color.
	 * <p>
	 * The anchor point is the center of the circle.
	 * <p>
	 * The start angle specifies where the text starts along the circle. When the starting angle is 0, the text starts
	 * at the 3 o'clock position (positive X axis).
	 * <p>
	 * The text winds along the circle in the specified direction (clockwise or counter-clockwise). The radius of the
	 * circle must be positive. If it is less than or equal to 0, nothing is drawn.
	 *
	 * @param g
	 *            the graphics context to draw on
	 * @param string
	 *            the string to draw
	 * @param font
	 *            the font to use
	 * @param x
	 *            the x coordinate of the anchor point (center of the circle)
	 * @param y
	 *            the y coordinate of the anchor point (center of the circle)
	 * @param radius
	 *            the radius of the circle, in pixels
	 * @param startAngle
	 *            the angle at which the text starts along the circle, in degrees
	 * @param direction
	 *            the winding direction of the text along the circle
	 * @param alpha
	 *            the global opacity rendering value
	 * @param letterSpacing
	 *            the extra letter spacing to use, in pixels
	 */
	public static void drawStringOnCircleNearestNeighbor(GraphicsContext g, String string, Font font, int x, int y,
			int radius, float startAngle, Direction direction, int alpha, int letterSpacing) {
		drawStringOnCircleInternal(g, string, font, x, y, radius, startAngle, direction, alpha, letterSpacing, false);
	}

	private static void drawStringOnCircleInternal(GraphicsContext g, String string, Font font, int x, int y,
			int radius, float startAngle, Direction direction, int alpha, int letterSpacing, boolean bilinear) {
		if (isValidAlpha(alpha) && isValidRadius(radius)) {
			int length = string.length();
			float advanceX = 0;
			int offset = 0;
			float sign = direction != Direction.CLOCKWISE ? 1f : -1f;
			int baselinePosition = font.getBaselinePosition();
			startAngle = (float) Math.toRadians(startAngle);
			float perpendicularAngle = (float) (Math.PI / 2 * sign);

			while (offset < length) {
				char character = string.charAt(offset);
				float charWidth = font.charWidth(character);
				if (!Character.isSpaceChar(character)) {
					float halfCharWidth = charWidth / 2;

					// compute the angle of the current character along the circle
					float windingAngle = startAngle + sign * (advanceX + halfCharWidth) / radius;

					// determine its rotation center and its anchor point
					float rx = (float) (x + radius * Math.cos(windingAngle));
					float ry = (float) (y - radius * Math.sin(windingAngle));
					int anchorX = Math.round(rx - halfCharWidth);
					int anchorY = Math.round(ry - baselinePosition);

					// compute the orientation of the glyph
					float glyphAngle = (float) Math.toDegrees(windingAngle + perpendicularAngle);

					if (bilinear) {
						TransformPainter.drawRotatedCharBilinear(g, font, character, anchorX, anchorY, Math.round(rx),
								Math.round(ry), glyphAngle, alpha);
					} else {
						TransformPainter.drawRotatedCharNearestNeighbor(g, font, character, anchorX, anchorY, (int) rx,
								(int) ry, glyphAngle, alpha);
					}
				}

				offset++;
				advanceX += charWidth + letterSpacing;
			}
		}
	}

	private static boolean isValidAlpha(int alpha) {
		return (alpha >= GraphicsContext.TRANSPARENT && alpha <= GraphicsContext.OPAQUE);
	}

	private static boolean isValidRadius(float radius) {
		return radius > 0;
	}
}
