/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.chart;

import ej.drawing.ShapePainter;
import ej.drawing.ShapePainter.Cap;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.mwt.style.Style;
import ej.mwt.style.container.Alignment;
import ej.mwt.util.Size;
import ej.widget.util.StringPainter;

/**
 * Represents a bar chart with several ordered points.
 */
public class BarChart extends BasicChart {

	/**
	 * Values
	 */
	private static final int BAR_THICKNESS = 9;
	private static final int BAR_FADE = 1;
	private static final Cap BAR_CAPS = Cap.ROUNDED;

	/**
	 * Attributes
	 */
	private float xStep;

	/**
	 * Render widget
	 */
	@Override
	public void renderContent(GraphicsContext g, Size size) {
		Style style = getStyle();
		Font font = style.getFont();
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
		int pointIndex = 0;
		for (ChartPoint chartPoint : getPoints()) {
			int currentX = (int) (LEFT_PADDING + this.xStep / 4 + pointIndex * this.xStep);
			float value = chartPoint.getValue();

			int foregroundColor = chartPoint.getStyle().getForegroundColor();
			g.setColor(foregroundColor);

			String name = chartPoint.getName();
			if (name != null) {
				StringPainter.drawStringAtPoint(g, font, name, currentX, size.getHeight(), Alignment.HCENTER_BOTTOM);
			}

			if (value >= 0.0f) {
				int finalLength = (int) ((yBarBottom - yBarTop) * value / topValue);
				int apparitionLength = (int) (finalLength * getAnimationRatio());
				int yTop = yBarBottom - apparitionLength;
				ShapePainter.drawThickFadedLine(g, currentX, yTop, currentX, yBarBottom, BAR_THICKNESS, BAR_FADE,
						BAR_CAPS, BAR_CAPS);
			}

			pointIndex++;
		}
	}

	@Override
	public int getChartX() {
		return LEFT_PADDING - (int) (this.xStep / 4);
	}

	@Override
	public int getChartWidth() {
		return (int) (getPoints().size() * this.xStep);
	}
}
