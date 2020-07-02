/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.style.text;

import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.RenderableString;
import ej.mwt.util.Size;

/**
 * A text manager is responsible of the layout and the rendering of a text content.
 */
public interface TextStyle {

	/**
	 * Computes a text size for a font, taking into account the given available size. An available width or height equal
	 * to {@link ej.mwt.Widget#NO_CONSTRAINT} means that there is no constraint on this dimension.
	 * <p>
	 * The given size is modified to set the text size.
	 *
	 * @param text
	 *            the text.
	 * @param font
	 *            the font.
	 * @param size
	 *            the available size.
	 */
	void computeContentSize(String text, Font font, Size size);

	/**
	 * Gets the lines used to render a text.
	 *
	 * @param text
	 *            the text.
	 * @param font
	 *            the font used to render the text.
	 * @param areaWidth
	 *            the width of the text area.
	 * @param areaHeight
	 *            the height of the text area.
	 * @return an array containing the lines.
	 */
	RenderableString[] getLines(String text, Font font, int areaWidth, int areaHeight);

	/**
	 * Draws text on a graphics context.
	 * <p>
	 * The given graphics context is translated and clipped according to the given area.
	 *
	 * @param g
	 *            the graphics context to draw the text on.
	 * @param lines
	 *            the lines to render.
	 * @param color
	 *            the color to use.
	 * @param areaWidth
	 *            the width of the text area.
	 * @param areaHeight
	 *            the height of the text area.
	 * @param horizontalAlignment
	 *            the horizontal alignment of the text within the area.
	 * @param verticalAlignment
	 *            the vertical alignment of the text within the area.
	 */
	void drawText(GraphicsContext g, RenderableString[] lines, int color, int areaWidth, int areaHeight,
			int horizontalAlignment, int verticalAlignment);

}
