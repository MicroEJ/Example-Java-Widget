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
 * Represents a bar chart with several ordered points.
 */
public class BarChart extends BasicChart {

	/**
	 * Values
	 */
	private static final int BAR_THICKNESS = 9;

	/**
	 * Attributes
	 */
	private float xStep;

	private final AntiAliasedShapes antiAliasedShapes;

	public BarChart() {
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

		this.xStep = (size.getWidth() - LEFT_PADDING) / (getPoints().size() - 0.5f);

		int yBarBottom = getBarBottom(fontHeight, size) - BAR_THICKNESS / 2 - 1;
		int yBarTop = getBarTop(fontHeight, size) + BAR_THICKNESS / 2;

		float topValue = getScale().getTopValue();

		// draw selected point value
		renderSelectedPointValue(g, style, size);

		// draw scale
		renderScale(g, style, size, topValue);

		// draw points
		AntiAliasedShapes antiAliasedShapes = this.antiAliasedShapes;
		antiAliasedShapes.setThickness(BAR_THICKNESS);

		int pointIndex = 0;
		for (ChartPoint chartPoint : getPoints()) {
			int currentX = (int) (LEFT_PADDING + this.xStep / 4 + pointIndex * this.xStep);
			float value = chartPoint.getValue();

			int foregroundColor = chartPoint.getStyle().getForegroundColor();
			g.setColor(foregroundColor);

			String name = chartPoint.getName();
			if (name != null) {
				drawString(g, font, name, currentX, size.getHeight(), Alignment.HCENTER_BOTTOM);
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
