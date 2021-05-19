/*
 * Copyright 2015-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.progressbar.widget;

import ej.bon.XMath;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.Widget;
import ej.mwt.style.DefaultStyle;
import ej.mwt.style.Style;
import ej.mwt.util.Size;
import ej.widget.util.render.StringPainter;

/**
 * A progress bar is a widget which displays an animated bar indicating that the user should wait for an estimated
 * amount of time.
 */
public class ProgressBar extends Widget {

	/** The extra field ID for the color of the percentage string. */
	public static final int STRING_COLOR_FIELD = 0;

	private static final int DEFAULT_STRING_COLOR = DefaultStyle.COLOR;

	private static final int PROGRESS_MULTIPLIER = 100;

	private float progress;

	/**
	 * Creates a progress bar. The progress value is initialized to 0.
	 */
	public ProgressBar() {
		this.progress = 0.0f;
	}

	/**
	 * Sets the progress value.
	 * <p>
	 * The given progress value is clamped between <code>0.0f</code> and <code>1.0f</code>.
	 *
	 * @param progress
	 *            the progress value to set.
	 */
	public void setProgress(float progress) {
		this.progress = XMath.limit(progress, 0.0f, 1.0f);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();

		// fill rectangle
		int filledWidth = Math.round(contentWidth * this.progress);
		g.setColor(style.getColor());
		Painter.fillRectangle(g, 0, 0, filledWidth, contentHeight);

		// draw percentage string
		int percentage = Math.round(PROGRESS_MULTIPLIER * this.progress);
		String string = Integer.toString(percentage) + "%"; //$NON-NLS-1$
		g.setColor(getStringColor(style));
		StringPainter.drawStringInArea(g, string, style.getFont(), 0, 0, contentWidth, contentHeight,
				style.getHorizontalAlignment(), style.getVerticalAlignment());
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		int fontHeight = style.getFont().getHeight();
		size.setSize(fontHeight, fontHeight);
	}

	private static int getStringColor(Style style) {
		return style.getExtraInt(STRING_COLOR_FIELD, DEFAULT_STRING_COLOR);
	}
}
