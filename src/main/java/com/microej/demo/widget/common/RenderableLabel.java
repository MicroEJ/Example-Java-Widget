/*
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.common;

import ej.annotation.Nullable;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.display.RenderableString;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;

/**
 * A menu item is a widget that displays a text and reacts to click events.
 */
public class RenderableLabel extends Widget {

	private String text;

	private @Nullable RenderableString renderableText;

	/**
	 * Creates a label with an empty text.
	 */
	public RenderableLabel() {
		this(""); //$NON-NLS-1$
	}

	/**
	 * Creates a label with the given text to display.
	 *
	 * @param text
	 *            the text to display.
	 */
	public RenderableLabel(String text) {
		this.text = text;
	}

	/**
	 * Creates a label with the given text to display and its enabled state.
	 *
	 * @param text
	 *            the text to display.
	 * @param enabled
	 *            <code>true</code> if this label is to be enabled, <code>false</code> otherwise.
	 */
	protected RenderableLabel(String text, boolean enabled) {
		super(enabled);
		this.text = text;
	}

	/**
	 * Gets the text displayed on this label.
	 *
	 * @return the text displayed on this label.
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Sets the text to display on this label.
	 *
	 * @param text
	 *            the text to display on this label.
	 */
	public void setText(String text) {
		this.text = text;
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		g.setColor(style.getColor());
		RenderableString renderableText = this.renderableText;
		assert renderableText != null;
		int stringX = Alignment.computeLeftX(renderableText.getWidth(), 0, contentWidth,
				style.getHorizontalAlignment());
		int stringY = Alignment.computeTopY(renderableText.getHeight(), 0, contentHeight, style.getVerticalAlignment());
		Painter.drawRenderableString(g, renderableText, stringX, stringY);

	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Font font = getStyle().getFont();
		RenderableString renderableText = new RenderableString(this.text, font);
		this.renderableText = renderableText;
		int width = renderableText.getWidth();
		int height = renderableText.getHeight();
		size.setSize(width, height);
	}

}
