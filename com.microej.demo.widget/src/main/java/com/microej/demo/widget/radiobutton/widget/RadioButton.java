/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.radiobutton.widget;

import ej.drawing.ShapePainter;
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
 * A radio button is a widget which displays a text and a box that can be checked or unchecked.
 */
public class RadioButton extends Widget {

	/** The extra field ID for the color of the radio button when it is checked. */
	public static final int CHECKED_COLOR_FIELD = 0;

	private static final int INNER_CIRCLE_OFFSET = 5;
	private static final int BOX_SIZE_OFFSET = 4;

	private final String text;
	private final RadioButtonGroup group;

	/**
	 * Creates a radio button with the given text to display.
	 *
	 * @param text
	 *            the text to display.
	 * @param group
	 *            the group to which the radio button should belong.
	 */
	public RadioButton(String text, RadioButtonGroup group) {
		super(true);
		this.text = text;
		this.group = group;
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
		ShapePainter.drawThickFadedCircle(g, boxX + 1, boxY + 1, boxSize - 2, 1, 1);

		// fill box
		if (this.group.isChecked(this)) {
			g.setColor(getCheckedColor(style));
			int innerCircleSize = boxSize - INNER_CIRCLE_OFFSET * 2;
			ShapePainter.drawThickFadedCircle(g, boxX + INNER_CIRCLE_OFFSET, boxY + INNER_CIRCLE_OFFSET,
					innerCircleSize, 0, 1);
			Painter.fillCircle(g, boxX + INNER_CIRCLE_OFFSET, boxY + INNER_CIRCLE_OFFSET, innerCircleSize);
			Painter.fillCircle(g, boxX + INNER_CIRCLE_OFFSET + 1, boxY + INNER_CIRCLE_OFFSET + 1, innerCircleSize);
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
				this.group.setChecked(this);
				return true;
			}
		}

		return super.handleEvent(event);
	}

	private static int computeWidth(String text, Font font) {
		return computeBoxSize(font) + computeSpacing(font) + font.stringWidth(text);
	}

	private static int computeBoxSize(Font font) {
		return (font.getHeight() - BOX_SIZE_OFFSET);
	}

	private static int computeSpacing(Font font) {
		return font.getHeight() / 2 + 1;
	}

	private static int getCheckedColor(Style style) {
		return style.getExtraInt(CHECKED_COLOR_FIELD, style.getColor());
	}
}
