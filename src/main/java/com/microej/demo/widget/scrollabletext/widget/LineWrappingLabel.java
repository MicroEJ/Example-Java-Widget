/*
 * Copyright 2021-2024 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.scrollabletext.widget;

import ej.annotation.Nullable;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;
import ej.widget.render.StringPainter;
import ej.widget.render.TextHelper;

/**
 * A label is a widget that displays a text.
 * <p>
 * This label wraps lines depending on the width that is available for the widget. <br>
 * The height depends on the font height and how many lines are needed to display the whole text in the given width.
 */
public class LineWrappingLabel extends Widget {

	private @Nullable String[] textSplit;
	private String text;

	/**
	 * Creates a label with an empty text.
	 */
	public LineWrappingLabel() {
		this(""); //$NON-NLS-1$
	}

	/**
	 * Creates a label with the given text to display.
	 *
	 * @param text
	 *            the text to display.
	 */
	public LineWrappingLabel(String text) {
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
		if (isShown()) {
			this.textSplit = getSplitText();
		}
	}

	@Override
	protected void onLaidOut() {
		this.textSplit = getSplitText();
	}

	@Override
	protected void onDetached() {
		this.textSplit = null;
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		Font font = style.getFont();
		int width = size.getWidth();

		String[] textSplit = TextHelper.wrap(getText(), font, width);
		int height = font.getHeight() * textSplit.length;
		size.setHeight(height);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		g.setColor(style.getColor());
		Font font = style.getFont();
		int lineHeight = font.getHeight();

		String[] textSplit = this.textSplit;
		if (textSplit != null) {
			int horizontalAlignment = style.getHorizontalAlignment();
			int verticalAlignment = style.getVerticalAlignment();

			int totalHeight = textSplit.length * lineHeight;
			int currentY = Alignment.computeTopY(totalHeight, 0, contentHeight, verticalAlignment);
			for (String text : textSplit) {
				assert (text != null);
				StringPainter.drawStringInArea(g, text, font, 0, currentY, contentWidth, lineHeight,
						horizontalAlignment, verticalAlignment);
				currentY += lineHeight;
			}
		}
	}

	/**
	 * Gets the current text split into lines depending on the font & widget width.
	 * <p>
	 * This method should not be called before this widget is laid out, since it depends on the Style, Font & Widget
	 * Width.
	 */
	private String[] getSplitText() {
		Style style = getStyle();
		Font font = style.getFont();
		return TextHelper.wrap(getText(), font, getContentBounds().getWidth());
	}

}
