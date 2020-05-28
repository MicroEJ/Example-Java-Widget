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
import ej.microui.display.Painter;
import ej.mwt.style.Style;
import ej.mwt.style.container.Alignment;
import ej.mwt.util.Size;
import ej.widget.util.StringPainter;
import ej.widget.util.color.LightHelper;

/**
 * Represents a line chart with several ordered points.
 */
public class LineChart extends BasicChart {

	/**
	 * Values
	 */
	private static final int BAR_THICKNESS = 0;
	private static final int BAR_FADE = 1;
	private static final Cap BAR_CAPS = Cap.ROUNDED;
	private static final int CIRCLE_RADIUS = 2;
	private static final int CIRCLE_THICKNESS = 2;
	private static final int CIRCLE_FADE = 1;

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
	public void renderContent(GraphicsContext g, Size size) {
		Style style = getStyle();
		Font font = style.getFont();
		int fontHeight = font.getHeight();

		int yBarBottom = getBarBottom(fontHeight, size);
		int yBarTop = getBarTop(fontHeight, size);

		this.xStep = (size.getWidth() - LEFT_PADDING - fontHeight / 4.0f) / (getPoints().size() - 1.0f);

		float topValue = getScale().getTopValue();

		// draw selected point value
		renderSelectedPointValue(g, style, size);

		// draw points
		int previousX = -1;
		int previousY = -1;
		boolean previousSelected = false;
		int pointIndex = 0;
		for (ChartPoint chartPoint : getPoints()) {
			int currentX = (int) (LEFT_PADDING + pointIndex * this.xStep);
			float value = chartPoint.getValue();

			g.setColor(chartPoint.isSelected() ? getSelectedColor(style) : style.getColor());

			String name = chartPoint.getName();
			if (name != null) {
				StringPainter.drawStringAtPoint(g, font, name, currentX, size.getHeight(), Alignment.HCENTER_BOTTOM);
			}

			if (value < 0.0f) {
				previousX = -1;
				previousY = -1;
			} else {
				int finalLength = (int) ((yBarBottom - yBarTop) * value / topValue);
				int apparitionLength = (int) (finalLength * getAnimationRatio());
				int yTop = yBarBottom - apparitionLength;
				int currentY = yTop;

				if (previousY != -1) {
					if (this.drawArea && (chartPoint.isSelected() || previousSelected)) {
						float stepY = (float) (currentY - previousY) / (currentX - previousX);
						int midX = (currentX + previousX) / 2;
						int areaColor = LightHelper.lightenColor(style.getColor(), 3);
						if (previousSelected) {
							g.setColor(areaColor);
							for (int x = previousX; x < midX; x++) {
								Painter.drawLine(g, x, (int) (previousY + (x - previousX) * stepY) + 1, x, yBarBottom);
							}
						}
						if (chartPoint.isSelected()) {
							g.setColor(areaColor);
							for (int x = midX; x < currentX; x++) {
								Painter.drawLine(g, x, (int) (previousY + (x - previousX) * stepY) + 1, x, yBarBottom);
							}
						}
					}

					g.setColor(style.getColor());
					ShapePainter.drawThickFadedLine(g, previousX, previousY, currentX, currentY, BAR_THICKNESS,
							BAR_FADE, BAR_CAPS, BAR_CAPS);
				}

				previousX = currentX;
				previousY = currentY;
				previousSelected = chartPoint.isSelected();
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

				int finalLength = (int) ((yBarBottom - yBarTop) * value / topValue);
				int apparitionLength = (int) (finalLength * getAnimationRatio());
				int yTop = yBarBottom - apparitionLength;
				int currentY = yTop;

				int circleX = currentX - CIRCLE_RADIUS;
				int circleY = currentY - CIRCLE_RADIUS;
				int circleD = 2 * CIRCLE_RADIUS + 1;

				g.setColor(chartPoint.isSelected() ? getSelectedColor(style) : style.getColor());
				Painter.fillCircle(g, circleX, circleY, circleD);
				ShapePainter.drawThickFadedCircle(g, circleX, circleY, circleD, CIRCLE_THICKNESS, CIRCLE_FADE);

				pointIndex++;
			}
		}
	}

	@Override
	public int getChartX() {
		return LEFT_PADDING - (int) (this.xStep / 2);
	}

	@Override
	public int getChartWidth() {
		return (int) (getPoints().size() * this.xStep);
	}
}
