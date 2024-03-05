/*
 * Copyright 2020-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.autoscrolllabel.widget;

import ej.bon.Util;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.mwt.animation.Animation;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.widget.basic.Label;
import ej.widget.render.StringPainter;

/**
 * An autoscroll label is a widget which displays a text, if the text is too long to be shown on the label, it will
 * automatically scroll so that the user can read the whole text.
 */
public class AutoscrollLabel extends Label implements Animation {

	private static final int START_WAIT_PERIOD = 2000;
	private static final int PIXELS_PER_SECOND = 50;
	private static final int MOVE_RATIO = 1000 / PIXELS_PER_SECOND;
	private static final int SPACING = 150;

	private int textWidth;
	private long startTime;
	private long elapsedTime;

	/**
	 * Creates an autoscroll label.
	 *
	 * @param text
	 *            the text to display.
	 */
	public AutoscrollLabel(String text) {
		super(text);
		this.textWidth = 0;
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		g.setColor(style.getColor());
		Font font = style.getFont();

		String text = getText();

		int textX = 0;
		if (this.elapsedTime > 0) {
			textX -= (this.elapsedTime / MOVE_RATIO);
		}
		if (textX < -this.textWidth - SPACING) {
			// Loop: move text at the end of the content bounds.
			this.startTime += (this.textWidth + SPACING) * MOVE_RATIO + START_WAIT_PERIOD;
		}

		if (textX + this.textWidth < contentWidth + SPACING) {
			StringPainter.drawStringInArea(g, text, font, textX + this.textWidth + SPACING, 0, contentWidth,
					contentHeight, Alignment.LEFT, style.getVerticalAlignment());
		}
		StringPainter.drawStringInArea(g, text, font, textX, 0, contentWidth, contentHeight, Alignment.LEFT,
				style.getVerticalAlignment());
	}

	@Override
	protected void onLaidOut() {
		super.onLaidOut();
		this.textWidth = getStyle().getFont().stringWidth(this.getText());
	}

	@Override
	protected void onShown() {
		super.onShown();
		if (this.textWidth > getContentBounds().getWidth()) {
			this.startTime = Util.platformTimeMillis() + START_WAIT_PERIOD;

			getDesktop().getAnimator().startAnimation(this);
		}
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		getDesktop().getAnimator().stopAnimation(this);
	}

	@Override
	public boolean tick(long platformTimeMillis) {
		this.elapsedTime = platformTimeMillis - this.startTime;
		requestRender();
		return true;
	}
}
