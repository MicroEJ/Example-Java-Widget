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
import ej.widget.style.text.LineWrapTextStyle;
import ej.widget.style.text.TextStyle;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

public class LineWrapTextStyleTest extends Test {

	private static final int FONT_HEIGHT = 10;
	private static final int CHAR_WIDTH = 10;
	private static final int LINE_HEIGHT = 12;
	private static final int WIDTH = CHAR_WIDTH * 3;
	private static final int HEIGHT = 40;
	private static final String SIMPLE_TEXT = "\u0021";
	private static final String COMPLEX_TEXT = "\u0021\u0021 \u0021";
	private static final int CLEAN_COLOR = Colors.RED;
	private static final int TEXT_COLOR = Colors.MAGENTA;

	public static void main(String[] args) {
		TestHelper.launchTest(new LineWrapTextStyleTest());
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

		LineWrapTextStyle textStyle = new LineWrapTextStyle();
		Size contentSize = new Size(size);
		textStyle.computeContentSize(SIMPLE_TEXT, font, contentSize);
		int textWidth = contentSize.getWidth();
		int textHeight = contentSize.getHeight();
		CheckHelper.check(getClass(), "Simple text width", textWidth, CHAR_WIDTH);
		CheckHelper.check(getClass(), "Simple text height", textHeight, FONT_HEIGHT);

		RenderableString[] lines = textStyle.getLines(COMPLEX_TEXT, font, textWidth, textHeight);
		textStyle.drawText(g, lines, TEXT_COLOR, textWidth, textHeight, Alignment.LEFT, Alignment.TOP);
		checkLine(g, "Simple", 0, CHAR_WIDTH - 1, 0, FONT_HEIGHT - 1);

		clean(g, display);
		textStyle = new LineWrapTextStyle(LINE_HEIGHT);
		contentSize = new Size(size);
		textStyle.computeContentSize(SIMPLE_TEXT, font, contentSize);
		textWidth = contentSize.getWidth();
		textHeight = contentSize.getHeight();
		CheckHelper.check(getClass(), "Simple text width 2", textWidth, CHAR_WIDTH);
		CheckHelper.check(getClass(), "Simple text height 2", textHeight, LINE_HEIGHT);

		lines = textStyle.getLines(SIMPLE_TEXT, font, textWidth, textHeight);
		textStyle.drawText(g, lines, TEXT_COLOR, textWidth, textHeight, Alignment.LEFT, Alignment.TOP);
		checkLine(g, "Simple 2", 0, CHAR_WIDTH - 1, 0, FONT_HEIGHT - 1);
	}

	private void complexFreeSize(GraphicsContext g, Display display) {
		clean(g, display);

		Font font = getFont();
		assert font != null;
		int spaceWidth = font.charWidth(' ');

		Size size = new Size(Widget.NO_CONSTRAINT, Widget.NO_CONSTRAINT);

		LineWrapTextStyle textStyle = new LineWrapTextStyle();
		Size contentSize = new Size(size);
		textStyle.computeContentSize(COMPLEX_TEXT, font, contentSize);
		int textWidth = contentSize.getWidth();
		int textHeight = contentSize.getHeight();
		CheckHelper.check(getClass(), "Complex text width", textWidth, 3 * CHAR_WIDTH + spaceWidth);
		CheckHelper.check(getClass(), "Complex text height", textHeight, FONT_HEIGHT);

		RenderableString[] lines = textStyle.getLines(SIMPLE_TEXT, font, textWidth, textHeight);
		textStyle.drawText(g, lines, TEXT_COLOR, textWidth, textHeight, Alignment.LEFT, Alignment.TOP);
		checkLine(g, "Complex", 0, CHAR_WIDTH - 1, 0, FONT_HEIGHT - 1);

		clean(g, display);
		textStyle = new LineWrapTextStyle(LINE_HEIGHT);
		contentSize = new Size(size);
		textStyle.computeContentSize(COMPLEX_TEXT, font, contentSize);
		textWidth = contentSize.getWidth();
		textHeight = contentSize.getHeight();
		CheckHelper.check(getClass(), "Complex text width 2", textWidth, 3 * CHAR_WIDTH + spaceWidth);
		CheckHelper.check(getClass(), "Complex text height 2", textHeight, LINE_HEIGHT);

		lines = textStyle.getLines(COMPLEX_TEXT, font, textWidth, textHeight);
		textStyle.drawText(g, lines, TEXT_COLOR, textWidth, textHeight, Alignment.LEFT, Alignment.TOP);
		checkLine(g, "Complex 2", 0, 3 * CHAR_WIDTH + spaceWidth - 1, 0, FONT_HEIGHT - 1);
	}

	private void complexConstraintSize(GraphicsContext g, Display display) {
		clean(g, display);

		Font font = getFont();
		assert font != null;

		Size size = new Size(3 * CHAR_WIDTH, Widget.NO_CONSTRAINT);

		LineWrapTextStyle textStyle = new LineWrapTextStyle();
		Size contentSize = new Size(size);
		textStyle.computeContentSize(COMPLEX_TEXT, font, contentSize);
		int textWidth = contentSize.getWidth();
		int textHeight = contentSize.getHeight();
		CheckHelper.check(getClass(), "Complex constraint text width", textWidth, 2 * CHAR_WIDTH);
		CheckHelper.check(getClass(), "Complex constraint text height", textHeight, 2 * FONT_HEIGHT);

		RenderableString[] lines = textStyle.getLines(COMPLEX_TEXT, font, textWidth, textHeight);
		textStyle.drawText(g, lines, TEXT_COLOR, textWidth, textHeight, Alignment.LEFT, Alignment.TOP);

		int firstLineLeft = 0;
		int firstLineRight = 2 * CHAR_WIDTH - 1;
		int firstLineTop = 0;
		int firstLineBottom = FONT_HEIGHT - 1;
		checkLine(g, "Complex constraint first line", firstLineLeft, firstLineRight, firstLineTop, firstLineBottom);

		int secondLineLeft = 0;
		int secondLineRight = CHAR_WIDTH - 1;
		int secondLineTop = FONT_HEIGHT;
		int secondLineBottom = FONT_HEIGHT + FONT_HEIGHT - 1;
		checkLine(g, "Complex constraint second line", secondLineLeft, secondLineRight, secondLineTop,
				secondLineBottom);

		clean(g, display);
		textStyle = new LineWrapTextStyle(LINE_HEIGHT);
		contentSize = new Size(size);
		textStyle.computeContentSize(COMPLEX_TEXT, font, contentSize);
		textWidth = contentSize.getWidth();
		textHeight = contentSize.getHeight();
		CheckHelper.check(getClass(), "Complex constraint text width 2", textWidth, 2 * CHAR_WIDTH);
		CheckHelper.check(getClass(), "Complex constraint text height 2", textHeight, 2 * LINE_HEIGHT);

		lines = textStyle.getLines(COMPLEX_TEXT, font, textWidth, textHeight);
		textStyle.drawText(g, lines, TEXT_COLOR, textWidth, textHeight, Alignment.LEFT, Alignment.TOP);

		firstLineLeft = 0;
		firstLineRight = 2 * CHAR_WIDTH - 1;
		firstLineTop = 0;
		firstLineBottom = FONT_HEIGHT - 1;
		checkLine(g, "Complex constraint first line 2", firstLineLeft, firstLineRight, firstLineTop, firstLineBottom);

		secondLineLeft = 0;
		secondLineRight = CHAR_WIDTH - 1;
		secondLineTop = LINE_HEIGHT;
		secondLineBottom = LINE_HEIGHT + FONT_HEIGHT - 1;
		checkLine(g, "Complex constraint second line 2", secondLineLeft, secondLineRight, secondLineTop,
				secondLineBottom);
	}

	private void topLeft(GraphicsContext g, Display display) {
		clean(g, display);

		Font font = getFont();
		assert font != null;

		LineWrapTextStyle textStyle = new LineWrapTextStyle();

		// without specifying line height
		RenderableString[] lines = textStyle.getLines(COMPLEX_TEXT, font, WIDTH, HEIGHT);
		textStyle.drawText(g, lines, TEXT_COLOR, WIDTH, HEIGHT, Alignment.LEFT, Alignment.TOP);

		int firstLineLeft = 0;
		int firstLineRight = 2 * CHAR_WIDTH - 1;
		int firstLineTop = 0;
		int firstLineBottom = FONT_HEIGHT - 1;
		checkLine(g, "Top left first line", firstLineLeft, firstLineRight, firstLineTop, firstLineBottom);

		int secondLineLeft = 0;
		int secondLineRight = CHAR_WIDTH - 1;
		int secondLineTop = FONT_HEIGHT;
		int secondLineBottom = FONT_HEIGHT * 2 - 1;
		checkLine(g, "Top left second line", secondLineLeft, secondLineRight, secondLineTop, secondLineBottom);

		// specifying line height
		clean(g, display);
		textStyle = new LineWrapTextStyle(LINE_HEIGHT);
		lines = textStyle.getLines(COMPLEX_TEXT, font, WIDTH, HEIGHT);
		textStyle.drawText(g, lines, TEXT_COLOR, WIDTH, HEIGHT, Alignment.LEFT, Alignment.TOP);

		firstLineLeft = 0;
		firstLineRight = 2 * CHAR_WIDTH - 1;
		firstLineTop = 0;
		firstLineBottom = FONT_HEIGHT - 1;
		checkLine(g, "Top left first line 2", firstLineLeft, firstLineRight, firstLineTop, firstLineBottom);

		secondLineLeft = 0;
		secondLineRight = CHAR_WIDTH - 1;
		secondLineTop = LINE_HEIGHT;
		secondLineBottom = LINE_HEIGHT + FONT_HEIGHT - 1;
		checkLine(g, "Top left second line 2", secondLineLeft, secondLineRight, secondLineTop, secondLineBottom);
	}

	private void bottomRight(GraphicsContext g, Display display) {
		clean(g, display);

		Font font = getFont();
		assert font != null;

		LineWrapTextStyle textStyle = new LineWrapTextStyle();

		// without specifying line height
		RenderableString[] lines = textStyle.getLines(COMPLEX_TEXT, font, WIDTH, HEIGHT);
		textStyle.drawText(g, lines, TEXT_COLOR, WIDTH, HEIGHT, Alignment.RIGHT, Alignment.BOTTOM);

		int firstLineLeft = WIDTH - 2 * CHAR_WIDTH;
		int firstLineRight = WIDTH - 1;
		int firstLineTop = HEIGHT - 2 * FONT_HEIGHT;
		int firstLineBottom = HEIGHT - FONT_HEIGHT - 1;
		checkLine(g, "Bottom right first line", firstLineLeft, firstLineRight, firstLineTop, firstLineBottom);

		int secondLineLeft = WIDTH - CHAR_WIDTH;
		int secondLineRight = firstLineRight;
		int secondLineTop = HEIGHT - FONT_HEIGHT;
		int secondLineBottom = HEIGHT - 1;
		checkLine(g, "Bottom right second line", secondLineLeft, secondLineRight, secondLineTop, secondLineBottom);

		// specifying line height
		clean(g, display);
		textStyle = new LineWrapTextStyle(LINE_HEIGHT);
		lines = textStyle.getLines(COMPLEX_TEXT, font, WIDTH, HEIGHT);
		textStyle.drawText(g, lines, TEXT_COLOR, WIDTH, HEIGHT, Alignment.RIGHT, Alignment.BOTTOM);

		firstLineLeft = WIDTH - 2 * CHAR_WIDTH;
		firstLineRight = WIDTH - 1;
		firstLineTop = HEIGHT - LINE_HEIGHT - FONT_HEIGHT;
		firstLineBottom = HEIGHT - LINE_HEIGHT - 1;
		checkLine(g, "Bottom right first line 2", firstLineLeft, firstLineRight, firstLineTop, firstLineBottom);

		secondLineLeft = WIDTH - CHAR_WIDTH;
		secondLineRight = firstLineRight;
		secondLineTop = HEIGHT - FONT_HEIGHT;
		secondLineBottom = HEIGHT - 1;
		checkLine(g, "Bottom right second line 2", secondLineLeft, secondLineRight, secondLineTop, secondLineBottom);
	}

	private void center(GraphicsContext g, Display display) {
		clean(g, display);

		Font font = getFont();
		assert font != null;

		LineWrapTextStyle textStyle = new LineWrapTextStyle();

		// without specifying line height
		RenderableString[] lines = textStyle.getLines(COMPLEX_TEXT, font, WIDTH, HEIGHT);
		textStyle.drawText(g, lines, TEXT_COLOR, WIDTH, HEIGHT, Alignment.HCENTER, Alignment.VCENTER);

		int centerX = WIDTH / 2;
		int centerY = HEIGHT / 2;

		int firstLineLeft = centerX - CHAR_WIDTH;
		int firstLineRight = centerX + CHAR_WIDTH - 1;
		int firstLineTop = centerY - FONT_HEIGHT;
		int firstLineBottom = centerY - 1;
		checkLine(g, "Center first line", firstLineLeft, firstLineRight, firstLineTop, firstLineBottom);

		int secondLineLeft = centerX - CHAR_WIDTH / 2;
		int secondLineRight = centerX + CHAR_WIDTH / 2 - 1;
		int secondLineTop = centerY;
		int secondLineBottom = centerY + FONT_HEIGHT - 1;
		checkLine(g, "Center second line", secondLineLeft, secondLineRight, secondLineTop, secondLineBottom);

		// specifying line height
		clean(g, display);
		textStyle = new LineWrapTextStyle(LINE_HEIGHT);
		lines = textStyle.getLines(COMPLEX_TEXT, font, WIDTH, HEIGHT);
		textStyle.drawText(g, lines, TEXT_COLOR, WIDTH, HEIGHT, Alignment.HCENTER, Alignment.VCENTER);

		firstLineLeft = centerX - CHAR_WIDTH;
		firstLineRight = centerX + CHAR_WIDTH - 1;
		firstLineTop = centerY - LINE_HEIGHT + (LINE_HEIGHT - FONT_HEIGHT) / 2;
		firstLineBottom = firstLineTop + FONT_HEIGHT - 1;
		checkLine(g, "Center first line 2", firstLineLeft, firstLineRight, firstLineTop, firstLineBottom);

		secondLineLeft = centerX - CHAR_WIDTH / 2;
		secondLineRight = centerX + CHAR_WIDTH / 2 - 1;
		secondLineTop = centerY + (LINE_HEIGHT - FONT_HEIGHT) / 2;
		secondLineBottom = secondLineTop + FONT_HEIGHT - 1;
		checkLine(g, "Center second line 2", secondLineLeft, secondLineRight, secondLineTop, secondLineBottom);
	}

	private void outOfBounds(GraphicsContext g, Display display) {
		clean(g, display);

		Font font = getFont();
		assert font != null;

		TextStyle textStyle = new LineWrapTextStyle();
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

	private void checkLine(GraphicsContext g, String header, int lineLeft, int lineRight, int lineTop, int lineBottom) {
		Display display = Display.getDisplay();
		CheckHelper.check(getClass(), header + " top left", readPixel(g, lineLeft, lineTop),
				display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), header + " bottom left", readPixel(g, lineLeft, lineBottom),
				display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), header + " bottom right", readPixel(g, lineRight, lineBottom),
				display.getDisplayColor(TEXT_COLOR));
		CheckHelper.check(getClass(), header + " top right", readPixel(g, lineRight, lineTop),
				display.getDisplayColor(TEXT_COLOR));
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
