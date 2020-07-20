/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.style.text;

import ej.annotation.Nullable;
import ej.bon.XMath;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.display.RenderableString;
import ej.mwt.Widget;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;
import ej.widget.util.TextHelper;

/**
 * Renders the text on multiple lines.
 * <p>
 * The text is wrapped using {@link TextHelper#wrap(String, Font, int)}.
 * <p>
 * The line height is stored as a <code>char</code> for heap optimization and therefore cannot exceed
 * <code>65535</code>.
 */
public class LineWrapTextStyle implements TextStyle {

	private static final short DEFAULT_LINE_HEIGHT = 0;

	private final char lineHeight;

	/**
	 * Creates a line wrap text style with default line height.
	 * <p>
	 * The default height is the height of the font.
	 */
	public LineWrapTextStyle() {
		this.lineHeight = DEFAULT_LINE_HEIGHT;
	}

	/**
	 * Creates a line wrap text style specifying its line height.
	 * <p>
	 * The given line height is clamped between <code>0</code> and <code>Character.MAX_VALUE</code>.
	 *
	 * @param lineHeight
	 *            the height to set.
	 */
	public LineWrapTextStyle(int lineHeight) {
		this.lineHeight = (char) XMath.limit(lineHeight, 0, Character.MAX_VALUE);
	}

	/**
	 * Gets the line height.
	 *
	 * @return the line height.
	 */
	public int getLineHeight() {
		return this.lineHeight;
	}

	@Override
	public void computeContentSize(String text, Font font, Size size) {
		int lineHeight = getLineHeight(font.getHeight());

		int availableWidth = size.getWidth();
		String[] lines;
		if (availableWidth == Widget.NO_CONSTRAINT) {
			lines = TextHelper.getLines(text);
		} else {
			lines = TextHelper.wrap(text, font, availableWidth);
		}
		int linesCount = lines.length;
		int textHeight = Math.max(linesCount, 1) * lineHeight;

		// Finds the largest line.
		int textWidth = 0;
		for (int i = 0; i < linesCount; i++) {
			String line = lines[i];
			assert line != null;
			textWidth = Math.max(textWidth, font.stringWidth(line));
		}

		size.setSize(textWidth, textHeight);
	}

	@Override
	public RenderableString[] getLines(String text, Font font, int areaWidth, int areaHeight) {
		String[] lines = TextHelper.wrap(text, font, areaWidth);
		int linesCount = lines.length;

		RenderableString[] renderableLines = new RenderableString[linesCount];
		for (int i = 0; i < linesCount; i++) {
			String line = lines[i];
			assert line != null;
			renderableLines[i] = new RenderableString(line, font);
		}

		return renderableLines;
	}

	@Override
	public void drawText(GraphicsContext g, RenderableString[] lines, int color, int areaWidth, int areaHeight,
			int horizontalAlignment, int verticalAlignment) {
		if (lines.length == 0) {
			// Nothing to draw.
			return;
		}
		RenderableString firstLine = lines[0];
		int fontHeight = firstLine.getHeight();
		int lineHeight = getLineHeight(fontHeight);

		g.disableEllipsis();
		g.setColor(color);
		int linesCount = lines.length;
		// Can be computed once.
		int y = computeYTop(fontHeight, lineHeight, linesCount, areaHeight, verticalAlignment);

		// Compute first and last visible lines.
		int clipY = g.getClipY();
		int startLine = Math.max((clipY - y) / lineHeight, 0);
		int clipHeight = g.getClipHeight();
		int endLine = Math.min((clipY + clipHeight - y) / lineHeight + 1, linesCount);
		y += startLine * lineHeight;

		// Draw visible lines.
		for (int i = startLine; i < endLine; i++) {
			RenderableString line = lines[i];
			assert line != null;
			int x = Alignment.computeLeftX(line.getWidth(), 0, areaWidth, horizontalAlignment);
			Painter.drawRenderableString(g, line, x, y);
			y += lineHeight;
		}
	}

	private int getLineHeight(int fontHeight) {
		if (this.lineHeight != DEFAULT_LINE_HEIGHT) {
			return this.lineHeight;
		} else {
			return fontHeight;
		}
	}

	private static int computeYTop(int fontHeight, int lineHeight, int linesCount, int areaHeight, int verticalAlignment) {
		int y = Alignment.computeTopY(lineHeight * linesCount, 0, areaHeight, verticalAlignment);
		return Alignment.computeTopY(fontHeight, y, lineHeight, verticalAlignment);
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (obj instanceof LineWrapTextStyle) {
			LineWrapTextStyle textStyle = (LineWrapTextStyle) obj;
			return this.lineHeight == textStyle.lineHeight;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 17 * this.lineHeight;
	}

}
