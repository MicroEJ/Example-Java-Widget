/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.style.text;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.display.RenderableString;
import ej.mwt.Widget;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;
import ej.widget.style.text.SingleLineTextStyle;
import ej.widget.style.text.TextStyle;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

public class SingleLineTextStyleTest extends Test {

	private static final int FONT_HEIGHT = 10;
	private static final int CHAR_WIDTH = 10;
	private static final int WIDTH = 30;
	private static final int HEIGHT = 20;
	private static final String SIMPLE_TEXT = "\u0021";
	private static final String COMPLEX_TEXT = "\u0021\u0021 \u0021";
	private static final int CLEAN_COLOR = Colors.RED;
	private static final int TEXT_COLOR = Colors.MAGENTA;

	public static void main(String[] args) {
		TestHelper.launchTest(new SingleLineTextStyleTest());
	}

	@Override
	public void run(Display display) {
		GraphicsContext g = display.getGraphicsContext();

		simpleSize(g, display);
		complexFreeSize(g, display);
		complexConstraintSize(g, display);
		topLeft(g, display);
		bottomRight(g, display);
		center(g, display);
		outOfBounds(g, display);
	}

	private void simpleSize(GraphicsContext g, Display display) {
		clean(g, display);

		Font font = getFont();
		assert font != null;

		Size size = new Size(Widget.NO_CONSTRAINT, Widget.NO_CONSTRAINT);

		SingleLineTextStyle textStyle = SingleLineTextStyle.SINGLE_LINE_TEXT_STYLE;
		Size contentSize = new Size(size);
		textStyle.computeContentSize(SIMPLE_TEXT, font, contentSize);
		int textWidth = contentSize.getWidth();
		int textHeight = contentSize.getHeight();
		CheckHelper.check(getClass(), "Simple text width", textWidth, CHAR_WIDTH);
		CheckHelper.check(getClass(), "Simple text height", textHeight, FONT_HEIGHT);

		RenderableString[] lines = textStyle.getLines(SIMPLE_TEXT, font, textWidth, textHeight);
		textStyle.drawText(g, lines, TEXT_COLOR, textWidth, textHeight, Alignment.LEFT, Alignment.TOP);
		CheckHelper.check(getClass(), "Simple top left", readPixel(g, 0, 0), display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), "Simple bottom left", readPixel(g, 0, FONT_HEIGHT - 1),
				display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), "Simple bottom right", readPixel(g, CHAR_WIDTH - 1, FONT_HEIGHT - 1),
				display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), "Simple top right", readPixel(g, CHAR_WIDTH - 1, 0),
				display.getDisplayColor(TEXT_COLOR));
	}

	private void complexFreeSize(GraphicsContext g, Display display) {
		clean(g, display);

		Font font = getFont();
		assert font != null;
		int spaceWidth = font.charWidth(' ');

		Size size = new Size(Widget.NO_CONSTRAINT, Widget.NO_CONSTRAINT);

		SingleLineTextStyle textStyle = SingleLineTextStyle.SINGLE_LINE_TEXT_STYLE;
		Size contentSize = new Size(size);
		textStyle.computeContentSize(COMPLEX_TEXT, font, contentSize);
		int textWidth = contentSize.getWidth();
		int textHeight = contentSize.getHeight();
		CheckHelper.check(getClass(), "Complex text width", textWidth, 3 * CHAR_WIDTH + spaceWidth);
		CheckHelper.check(getClass(), "Complex text height", textHeight, FONT_HEIGHT);

		RenderableString[] lines = textStyle.getLines(COMPLEX_TEXT, font, textWidth, textHeight);
		textStyle.drawText(g, lines, TEXT_COLOR, textWidth, textHeight, Alignment.LEFT, Alignment.TOP);
		CheckHelper.check(getClass(), "Complex top left", readPixel(g, 0, 0), display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), "Complex bottom left", readPixel(g, 0, FONT_HEIGHT - 1),
				display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), "Complex bottom right",
				readPixel(g, 3 * CHAR_WIDTH + spaceWidth - 1, FONT_HEIGHT - 1), display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), "Complex top right", readPixel(g, 3 * CHAR_WIDTH + spaceWidth - 1, 0),
				display.getDisplayColor(TEXT_COLOR));
	}

	private void complexConstraintSize(GraphicsContext g, Display display) {
		clean(g, display);

		Font font = getFont();
		assert font != null;
		int spaceWidth = font.charWidth(' ');

		Size size = new Size(3 * CHAR_WIDTH, Widget.NO_CONSTRAINT);

		SingleLineTextStyle textStyle = SingleLineTextStyle.SINGLE_LINE_TEXT_STYLE;
		Size contentSize = new Size(size);
		textStyle.computeContentSize(COMPLEX_TEXT, font, contentSize);
		int textWidth = contentSize.getWidth();
		int textHeight = contentSize.getHeight();
		CheckHelper.check(getClass(), "Complex constraint text width", textWidth, 3 * CHAR_WIDTH + spaceWidth);
		CheckHelper.check(getClass(), "Complex constraint text height", textHeight, FONT_HEIGHT);

		g.resetClip(0, 0, size.getWidth(), textHeight);
		RenderableString[] lines = textStyle.getLines(COMPLEX_TEXT, font, textWidth, textHeight);
		textStyle.drawText(g, lines, TEXT_COLOR, textWidth, textHeight, Alignment.LEFT, Alignment.TOP);
		CheckHelper.check(getClass(), "Complex top left", readPixel(g, 0, 0), display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), "Complex bottom left", readPixel(g, 0, FONT_HEIGHT - 1),
				display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), "Complex bottom right",
				readPixel(g, 3 * CHAR_WIDTH + spaceWidth - 1, FONT_HEIGHT - 1), display.getDisplayColor(CLEAN_COLOR));
		CheckHelper.check(getClass(), "Complex top right", readPixel(g, 3 * CHAR_WIDTH + spaceWidth - 1, 0),
				display.getDisplayColor(CLEAN_COLOR));
	}

	private void topLeft(GraphicsContext g, Display display) {
		clean(g, display);

		Font font = getFont();
		assert font != null;

		SingleLineTextStyle textStyle = SingleLineTextStyle.SINGLE_LINE_TEXT_STYLE;

		RenderableString[] lines = textStyle.getLines(SIMPLE_TEXT, font, WIDTH, HEIGHT);
		textStyle.drawText(g, lines, TEXT_COLOR, WIDTH, HEIGHT, Alignment.LEFT, Alignment.TOP);
		CheckHelper.check(getClass(), "Top left top left", readPixel(g, 0, 0), display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), "Top left bottom left", readPixel(g, 0, FONT_HEIGHT - 1),
				display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), "Top left bottom right", readPixel(g, CHAR_WIDTH - 1, FONT_HEIGHT - 1),
				display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), "Top left top right", readPixel(g, CHAR_WIDTH - 1, 0),
				display.getDisplayColor(TEXT_COLOR));
	}

	private void bottomRight(GraphicsContext g, Display display) {
		clean(g, display);

		Font font = getFont();
		assert font != null;

		SingleLineTextStyle textStyle = SingleLineTextStyle.SINGLE_LINE_TEXT_STYLE;

		RenderableString[] lines = textStyle.getLines(SIMPLE_TEXT, font, WIDTH, HEIGHT);
		textStyle.drawText(g, lines, TEXT_COLOR, WIDTH, HEIGHT, Alignment.RIGHT, Alignment.BOTTOM);
		CheckHelper.check(getClass(), "Bottom right top left", readPixel(g, WIDTH - CHAR_WIDTH, HEIGHT - FONT_HEIGHT),
				display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), "Bottom right bottom left", readPixel(g, WIDTH - CHAR_WIDTH, HEIGHT - 1),
				display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), "Bottom right bottom right", readPixel(g, WIDTH - 1, HEIGHT - 1),
				display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), "Bottom right top right", readPixel(g, WIDTH - 1, HEIGHT - FONT_HEIGHT),
				display.getDisplayColor(TEXT_COLOR));
	}

	private void center(GraphicsContext g, Display display) {
		clean(g, display);

		Font font = getFont();
		assert font != null;

		SingleLineTextStyle textStyle = SingleLineTextStyle.SINGLE_LINE_TEXT_STYLE;

		RenderableString[] lines = textStyle.getLines(SIMPLE_TEXT, font, WIDTH, HEIGHT);
		textStyle.drawText(g, lines, TEXT_COLOR, WIDTH, HEIGHT, Alignment.HCENTER, Alignment.VCENTER);
		int left = (WIDTH - CHAR_WIDTH) / 2;
		int top = (HEIGHT - FONT_HEIGHT) / 2;
		CheckHelper.check(getClass(), "Center top left", readPixel(g, left, top), display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), "Center bottom left", readPixel(g, left, top + FONT_HEIGHT - 1),
				display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), "Center bottom right", readPixel(g, left + CHAR_WIDTH - 1, top + FONT_HEIGHT - 1),
				display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), "Center top right", readPixel(g, left + CHAR_WIDTH - 1, top),
				display.getDisplayColor(TEXT_COLOR));
	}

	private void outOfBounds(GraphicsContext g, Display display) {
		clean(g, display);

		Font font = getFont();
		assert font != null;

		TextStyle textStyle = SingleLineTextStyle.SINGLE_LINE_TEXT_STYLE;
		int textWidth = 60;
		int textHeight = 200;

		g.translate(-200, -200);
		RenderableString[] lines = textStyle.getLines("We are checking the bounds", font, textWidth, textHeight);
		textStyle.drawText(g, lines, TEXT_COLOR, textWidth, textHeight, Alignment.HCENTER, Alignment.VCENTER);
		g.translate(200, 200);

		g.translate(400, 400);
		lines = textStyle.getLines("We are checking the bounds", font, textWidth, textHeight);
		textStyle.drawText(g, lines, TEXT_COLOR, textWidth, textHeight, Alignment.HCENTER, Alignment.VCENTER);
		g.translate(-400, -400);
	}

	private Font getFont() {
		Font[] allFonts = Font.getAllFonts();
		for (Font font : allFonts) {
			if (font.isIdentifierSupported(Font.LATIN) && font.getHeight() == FONT_HEIGHT
					&& font.getStyle() == Font.STYLE_PLAIN) {
				return font;
			}
		}
		throw new RuntimeException();
	}

	private void clean(GraphicsContext g, Display display) {
		int width = display.getWidth();
		int height = display.getHeight();
		g.translate(-g.getTranslateX(), -g.getTranslateY());
		g.resetClip(0, 0, width, height);
		g.setColor(CLEAN_COLOR);
		Painter.fillRectangle(g, 0, 0, width, height);
	}

	private int readPixel(GraphicsContext g, int x, int y) {
		int readPixel = g.readPixel(x, y);
		return readPixel & 0xffffff;
	}

}
