/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.widget;

import com.is2t.demo.widgets.widget.theme.Pictos;

import ej.microui.io.DisplayFont;
import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.Widget;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.WidgetRenderer;
import ej.widgets.widgets.LookExtension;

/**
 * Renders a #LinedScale widget.
 */
public class LinedScaleRenderer extends WidgetRenderer {

	private static final int MIN_BAR_WIDTH = 200;
	private static final int INTERNAL_MARGIN = 5;
	private static final int BAR_HEIGHT = 3;
	private static final int FULL_COLOR = 0x4da5ea;

	@Override
	public int getPreferredContentWidth(Widget widget) {
		return getMinWidth() + MIN_BAR_WIDTH;
	}

	private int getMinWidth() {
		return 2 * INTERNAL_MARGIN + getPictoFont().charWidth(Pictos.CIRCLE);
	}

	@Override
	public int getPreferredContentHeight(Widget widget) {
		return getPictoFont().getHeight();
	}

	@Override
	public Class<LinedScale> getManagedType() {
		return LinedScale.class;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		LinedScale scale = (LinedScale) renderable;
		int width = scale.getWidth();
		int height = scale.getHeight();
		int contentWidth = width - getMinWidth();
		int contentHeight = BAR_HEIGHT;

		Look look = getLook();
		DisplayFont pictoFont = getPictoFont();
		int backgroundColor = look.getProperty(Look.GET_BACKGROUND_COLOR_DEFAULT);
		int onColor = FULL_COLOR;
		int offColor = look.getProperty(Look.GET_FOREGROUND_COLOR_DISABLED);

		// Fills the background.
		g.setColor(backgroundColor);
		g.fillRect(0, 0, width, height);

		int x = (width - contentWidth) / 2;
		int y = (height - contentHeight) / 2;

		// Fills the bar corresponding to the scale.
		g.setColor(offColor);
		g.fillRect(x, y, contentWidth, contentHeight);

		// Fills the part of the scale included with the min value and the current value.
		float fullFactor = Math.abs(scale.getValue() / (float) (scale.getMaxValue() - scale.getMinValue()));
		int fullWidth = (int) (fullFactor * contentWidth);
		g.setColor(onColor);
		g.fillRect(x, y, fullWidth, contentHeight);

		// Draws the current value.
		g.setFont(pictoFont);
		g.drawChar(Pictos.CIRCLE, x + fullWidth, height / 2, GraphicsContext.HCENTER | GraphicsContext.VCENTER);

		drawLines(g, scale, width, height, false);
	}

	// FIXME: duplicated in com.is2t.widgetextension.button.renderer.PictoButtonRenderer
	/**
	 * Draws the over line and under line if necessary.
	 * 
	 * @param g
	 *            the graphics context in which to draw.
	 * @param scale
	 *            the scale to under and overline.
	 * @param width
	 *            the width of the line.
	 * @param height
	 *            the position of the under line.
	 * @param all
	 *            use all the width or the (width - padding).
	 */
	protected void drawLines(GraphicsContext g, LinedScale scale, int width, int height, boolean all) {
		g.setColor(getLook().getProperty(Look.GET_BORDER_COLOR_CONTENT));
		if (scale.isOverlined()) {
			g.drawHorizontalLine(0, 0, width - (all ? 0 : getPadding()));
		}
		if (scale.isUnderlined()) {
			g.drawHorizontalLine(0, height - 1, width - (all ? 0 : getPadding()));
		}
	}

	private DisplayFont getPictoFont() {
		Look look = getLook();
		return look.getFonts()[look.getProperty(LookExtension.GET_SMALL_PICTO_FONT_INDEX)];
	}
}
