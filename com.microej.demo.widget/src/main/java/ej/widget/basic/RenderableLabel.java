/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic;

import ej.annotation.Nullable;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.RenderableString;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Rectangle;
import ej.mwt.util.Size;
import ej.widget.style.text.SingleLineTextStyle;
import ej.widget.style.text.TextStyle;

/**
 * A renderable label is a widget that displays a text.
 * <p>
 * Compared to a regular label, this implementation provides:
 * <ul>
 * <li>the ability to use a {@link TextStyle} in order to configure how the text is laid out,</li>
 * <li>an optimization using {@link RenderableString} in order to decrease the rendering time (at the expense of heap
 * usage).</li>
 * </ul>
 */
public class RenderableLabel extends Widget {

	/** The extra field ID for the text style. */
	public static final int TEXT_STYLE = 0;

	private String text;
	@Nullable
	private RenderableString[] lines;

	/**
	 * Creates a label with an empty text.
	 */
	public RenderableLabel() {
		this(""); //$NON-NLS-1$
	}

	/**
	 * Creates a label with the given text to display.
	 * <p>
	 * The text cannot be <code>null</code>.
	 *
	 * @param text
	 *            the text to display.
	 */
	public RenderableLabel(String text) {
		super();
		this.text = text;
	}

	/**
	 * Sets the text to display for this label.
	 *
	 * @param text
	 *            the text to display, it cannot be <code>null</code>.
	 * @see #updateText(String)
	 */
	public void setText(String text) {
		updateText(text);
		requestRender();
	}

	/**
	 * Updates the text to display for this label without asking for a new render.
	 *
	 * @param text
	 *            the text to display, it cannot be <code>null</code>.
	 * @since 2.3.0
	 */
	public void updateText(String text) {
		assert text != null;
		this.text = text;
		if (isShown()) {
			updateLines();
		}
	}

	/**
	 * Gets the displayed text of this label.
	 *
	 * @return the displayed text of this label.
	 */
	public String getText() {
		return this.text;
	}

	@Override
	protected void onLaidOut() {
		super.onLaidOut();
		updateLines();
	}

	@Override
	public void setStyle(Style newStyle) {
		super.setStyle(newStyle);
		if (isShown()) {
			updateLines();
		}
	}

	private void updateLines() {
		Style style = getStyle();
		Font font = style.getFont();
		TextStyle textStyle = getTextStyle(style);
		Rectangle contentBounds = getContentBounds();
		this.lines = textStyle.getLines(this.text, font, contentBounds.getWidth(), contentBounds.getHeight());
	}

	private static TextStyle getTextStyle(Style style) {
		return style.getExtraObject(TEXT_STYLE, TextStyle.class, SingleLineTextStyle.SINGLE_LINE_TEXT_STYLE);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		final RenderableString[] lines = this.lines;
		assert lines != null;
		getTextStyle(style).drawText(g, lines, style.getColor(), contentWidth, contentHeight,
				style.getHorizontalAlignment(), style.getVerticalAlignment());
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		Font font = style.getFont();
		getTextStyle(style).computeContentSize(this.text, font, size);
	}
}
