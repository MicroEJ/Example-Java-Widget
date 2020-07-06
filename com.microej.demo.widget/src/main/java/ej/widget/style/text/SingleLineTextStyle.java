/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.style.text;

import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.display.RenderableString;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;

/**
 * Renders the text on a single line.
 */
public class SingleLineTextStyle implements TextStyle {

	/**
	 * Single line text style singleton to avoid creating several ones.
	 */
	public static final SingleLineTextStyle SINGLE_LINE_TEXT_STYLE = new SingleLineTextStyle();
	// equals() and hashCode() from Object are sufficient as long as there is only one instance.

	private SingleLineTextStyle() {
	}

	@Override
	public void computeContentSize(String text, Font font, Size size) {
		int textWidth = font.stringWidth(text);
		int textHeight = font.getHeight();
		size.setSize(textWidth, textHeight);
	}

	@Override
	public RenderableString[] getLines(String text, Font font, int areaWidth, int areaHeight) {
		if (text.isEmpty()) {
			return new RenderableString[0];
		} else {
			RenderableString renderableString = new RenderableString(text, font);
			return new RenderableString[] { renderableString };
		}
	}

	@Override
	public void drawText(GraphicsContext g, RenderableString[] lines, int color, int areaWidth, int areaHeight,
			int horizontalAlignment, int verticalAlignment) {
		if (lines.length == 0) {
			// Nothing to draw.
			return;
		}
		g.resetEllipsis();
		g.setColor(color);
		RenderableString line = lines[0];
		int x = Alignment.computeLeftX(line.getWidth(), 0, areaWidth, horizontalAlignment);
		int y = Alignment.computeTopY(line.getHeight(), 0, areaHeight, verticalAlignment);
		Painter.drawRenderableString(g, line, x, y);
	}

}
