/*
 * Java
 *
 * Copyright  2016-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package ej.widget.chart;

import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.shape.AntiAliasedShapes;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.style.util.StyleHelper;

/**
 * Represents a line chart with several ordered points.
 */
public class LineChart extends BasicChart {

	/**
	 * Values
	 */
	private static final int CIRCLE_RADIUS = 2;

	/**
	 * Attributes
	 */
	private final boolean drawArea;
	private final boolean drawCircles;
	private float xStep;

	/**
	 * Constructor
	 *
	 * @param drawArea
	 *            draw the area under the line
	 * @param drawCircles
	 *            draw the circles
	 */
	public LineChart(boolean drawArea, boolean drawCircles) {
		this.drawArea = drawArea;
		this.drawCircles = drawCircles;
	}

	/**
	 * Render widget
	 */
	@Override
	public void renderContent(GraphicsContext g, Style style, Rectangle bounds) {
		Font font = StyleHelper.getFont(style);
		int fontHeight = font.getHeight();

		int yBarBottom = getBarBottom(fontHeight, bounds);
		int yBarTop = getBarTop(fontHeight, bounds);

		this.xStep = (bounds.getWidth() - LEFT_PADDING - fontHeight / 4) / (getPoints().size() - 1.0f);

		float topValue = getScale().getTopValue();

		// draw selected point value
		renderSelectedPointValue(g, style, bounds);

		// draw points
		g.setFont(font);

		AntiAliasedShapes antiAliasedShapes = AntiAliasedShapes.Singleton;
		antiAliasedShapes.setThickness(0);
		antiAliasedShapes.setFade(1);

		int previousX = -1;
		int previousY = -1;
		int previousBackgroundColor = -1;
		int pointIndex = 0;
		for (ChartPoint chartPoint : getPoints()) {
			int currentX = (int) (LEFT_PADDING + pointIndex * this.xStep);
			float value = chartPoint.getValue();

			int foregroundColor = chartPoint.getStyle().getForegroundColor();
			g.setColor(foregroundColor);

			String name = chartPoint.getName();
			if (name != null) {
				g.drawString(name, currentX, bounds.getHeight(), GraphicsContext.HCENTER | GraphicsContext.BOTTOM);
			}

			if (value < 0.0f) {
				previousX = -1;
				previousY = -1;
			} else {
				int finalLength = (int) ((yBarBottom - yBarTop) * value / topValue);
				int apparitionLength = (int) (finalLength * getAnimationRatio());
				int yTop = yBarBottom - apparitionLength;
				int currentY = yTop;

				int backgroundColor = chartPoint.getStyle().getBackgroundColor();
				if (previousY != -1) {
					if (this.drawArea) {
						float stepY = (float) (currentY - previousY) / (currentX - previousX);
						int midX = (currentX + previousX) / 2;
						g.setColor(previousBackgroundColor);
						for (int x = previousX; x < midX; x++) {
							g.drawLine(x, (int) (previousY + (x - previousX) * stepY), x, yBarBottom);
						}
						g.setColor(backgroundColor);
						for (int x = midX; x < currentX; x++) {
							g.drawLine(x, (int) (previousY + (x - previousX) * stepY), x, yBarBottom);
						}
					}

					g.setColor(style.getForegroundColor());
					antiAliasedShapes.drawLine(g, previousX, previousY, currentX, currentY);
				}

				previousX = currentX;
				previousY = currentY;
				previousBackgroundColor = backgroundColor;
			}

			pointIndex++;
		}

		// draw scale
		renderScale(g, style, bounds, topValue);

		// draw circles
		if (this.drawCircles) {
			pointIndex = 0;
			for (ChartPoint chartPoint : getPoints()) {
				int currentX = (int) (LEFT_PADDING + pointIndex * this.xStep);
				float value = chartPoint.getValue();
				if (value < 0.0f) {
					pointIndex++;
					continue;
				}

				int foregroundColor = chartPoint.getStyle().getForegroundColor();
				g.setColor(foregroundColor);

				int finalLength = (int) ((yBarBottom - yBarTop) * value / topValue);
				int apparitionLength = (int) (finalLength * getAnimationRatio());
				int yTop = yBarBottom - apparitionLength;
				int currentY = yTop;

				int circleX = currentX - CIRCLE_RADIUS;
				int circleY = currentY - CIRCLE_RADIUS;
				int circleD = 2 * CIRCLE_RADIUS + 1;

				g.setColor(foregroundColor);
				g.fillCircle(circleX, circleY, circleD);
				antiAliasedShapes.setThickness(2);
				antiAliasedShapes.drawCircle(g, circleX, circleY, circleD);

				pointIndex++;
			}
		}
	}

	/**
	 * Gets content X
	 */
	@Override
	public int getContentX() {
		return LEFT_PADDING - (int) (this.xStep / 2);
	}

	/**
	 * Gets content width
	 */
	@Override
	public int getContentWidth() {
		return (int) (getPoints().size() * this.xStep);
	}
}
