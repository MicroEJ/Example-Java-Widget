/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.checkbox.widget;

import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;

/**
 * A checkbox is a widget which displays a text and a box that can be checked or unchecked.
 */
public class Checkbox extends Widget {

	/** The extra field ID for the color of the checkbox when it is checked. */
	public static final int CHECKED_COLOR_FIELD = 0;

	private static final int INNER_BOX_OFFSET = 4;
	private static final int BOX_SIZE_SPACING = 3;

	private final String text;

	private boolean checked;

	/**
	 * Creates a checkbox with the given text to display.
	 *
	 * @param text
	 *            the text to display.
	 */
	public Checkbox(String text) {
		super(true);
		this.text = text;
		this.checked = false;
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		Font font = style.getFont();
		int horizontalAlignment = style.getHorizontalAlignment();
		int verticalAlignment = style.getVerticalAlignment();

		// draw box
		int boxSize = computeBoxSize(font);
		int boxX = Alignment.computeLeftX(computeWidth(this.text, font), 0, contentWidth, horizontalAlignment);
		int boxY = Alignment.computeTopY(boxSize, 0, contentHeight, verticalAlignment);
		g.setColor(style.getColor());
		Painter.drawRectangle(g, boxX, boxY, boxSize, boxSize);
		Painter.drawRectangle(g, boxX + 1, boxY + 1, boxSize - 2, boxSize - 2);

		// fill box
		if (this.checked) {
			g.setColor(getCheckedColor(style));
			int innerBoxSize = boxSize - INNER_BOX_OFFSET * 2;
			Painter.fillRectangle(g, boxX + INNER_BOX_OFFSET, boxY + INNER_BOX_OFFSET, innerBoxSize, innerBoxSize);
		}

		// draw text
		int textX = boxX + boxSize + computeSpacing(font);
		int textY = Alignment.computeTopY(font.getHeight(), 0, contentHeight, verticalAlignment);
		g.setColor(style.getColor());
		Painter.drawString(g, this.text, font, textX, textY);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Font font = getStyle().getFont();
		size.setSize(computeWidth(this.text, font), font.getHeight());
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		if (type == Pointer.EVENT_TYPE) {
			int action = Buttons.getAction(event);
			if (action == Buttons.RELEASED) {
				this.checked = !this.checked;
				requestRender();
				return true;
			}
		}

		return super.handleEvent(event);
	}

	private static int computeWidth(String text, Font font) {
		return computeBoxSize(font) + computeSpacing(font) + font.stringWidth(text);
	}

	private static int computeBoxSize(Font font) {
		return font.getHeight() - BOX_SIZE_SPACING;
	}

	private static int computeSpacing(Font font) {
		return font.getHeight() / 2;
	}

	private static int getCheckedColor(Style style) {
		return style.getExtraInt(CHECKED_COLOR_FIELD, style.getColor());
	}
}
