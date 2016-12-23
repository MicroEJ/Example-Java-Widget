/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.widget.chart;

import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.shape.AntiAliasedShapes;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.style.util.StyleHelper;

/** IPR start **/

/**
 * Represents a bar chart with several ordered points.
 */
public class BarChart extends BasicChart {

	/**
	 * Attributes
	 */
	private float xStep;

	/**
	 * Render widget
	 */
	@Override
	public void renderContent(GraphicsContext g, Style style, Rectangle bounds) {
		Font font = StyleHelper.getFont(style);
		int fontHeight = font.getHeight();

		this.xStep = (bounds.getWidth() - LEFT_PADDING) / (getPoints().size() - 0.5f);
		int thickness = (int) (this.xStep / 2.5f);

		int yBarBottom = getBarBottom(fontHeight, bounds) - thickness / 2 - 1;
		int yBarTop = getBarTop(fontHeight, bounds) + thickness / 2;

		float topValue = getScale().getTopValue();

		// draw selected point value
		renderSelectedPointValue(g, style, bounds);

		// draw scale
		renderScale(g, style, bounds, topValue);

		// draw points
		g.setFont(font);

		AntiAliasedShapes antiAliasedShapes = AntiAliasedShapes.Singleton;
		antiAliasedShapes.setThickness(thickness);

		int pointIndex = 0;
		for (ChartPoint chartPoint : getPoints()) {
			int currentX = (int) (LEFT_PADDING + this.xStep / 4 + pointIndex * this.xStep);
			float value = chartPoint.getValue();

			int foregroundColor = chartPoint.getStyle().getForegroundColor();
			g.setColor(foregroundColor);

			String name = chartPoint.getName();
			if (name != null) {
				g.drawString(name, currentX, bounds.getHeight(), GraphicsContext.HCENTER | GraphicsContext.BOTTOM);
			}

			if (value >= 0.0f) {
				int finalLength = (int) ((yBarBottom - yBarTop) * value / topValue);
				int apparitionLength = (int) (finalLength * getAnimationRatio());
				int yTop = yBarBottom - apparitionLength;
				antiAliasedShapes.drawLine(g, currentX, yTop, currentX, yBarBottom);
			}

			pointIndex++;
		}
	}

	/**
	 * Gets content X
	 */
	@Override
	public int getContentX() {
		return LEFT_PADDING - (int) (this.xStep / 4);
	}

	/**
	 * Gets content width
	 */
	@Override
	public int getContentWidth() {
		return (int) (getPoints().size() * this.xStep);
	}
}

/** IPR end **/
