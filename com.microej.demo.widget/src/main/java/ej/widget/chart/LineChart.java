/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.chart;

import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.shape.AntiAliasedShapes;
import ej.mwt.style.Style;
import ej.mwt.style.container.Alignment;
import ej.mwt.style.util.StyleHelper;
import ej.mwt.util.Size;

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

	private final AntiAliasedShapes antiAliasedShapes;

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
		this.antiAliasedShapes = new AntiAliasedShapes();
	}

	/**
	 * Render widget
	 */
	@Override
	public void renderContent(GraphicsContext g, Size size) {
		Style style = getStyle();
		Font font = StyleHelper.getFont(style);
		int fontHeight = font.getHeight();

		int yBarBottom = getBarBottom(fontHeight, size);
		int yBarTop = getBarTop(fontHeight, size);

		this.xStep = (size.getWidth() - LEFT_PADDING - fontHeight / 4) / (getPoints().size() - 1.0f);

		float topValue = getScale().getTopValue();

		// draw selected point value
		renderSelectedPointValue(g, style, size);

		// draw points
		AntiAliasedShapes antiAliasedShapes = this.antiAliasedShapes;
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
				drawString(g, font, name, currentX, size.getHeight(), Alignment.HCENTER_BOTTOM);
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
		renderScale(g, style, size, topValue);

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

	private void drawString(GraphicsContext g, Font font, String string, int anchorX, int anchorY, int alignment) {
		int x = Alignment.computeLeftX(font.stringWidth(string), anchorX, alignment);
		int y = Alignment.computeTopY(font.getHeight(), anchorY, alignment);
		font.drawString(g, string, x, y);
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
