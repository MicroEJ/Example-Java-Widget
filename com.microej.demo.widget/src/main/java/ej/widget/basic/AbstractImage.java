/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.Painter;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;

/**
 * A widget that displays an image.
 * <p>
 * Users of this widget are responsible of closing the image resource.
 *
 * @see ej.microui.display.Image
 */
public abstract class AbstractImage extends Widget {

	/**
	 * Renders the image considering the style.
	 *
	 * @param g
	 *            the graphics context where to render the content of the renderable.
	 * @param style
	 *            the style to use.
	 * @param size
	 *            the remaining size to render the content.
	 * @param source
	 *            the image to draw.
	 */
	protected void renderImage(GraphicsContext g, Style style, Size size, Image source) {
		int x = Alignment.computeLeftX(source.getWidth(), 0, size.getWidth(), style.getHorizontalAlignment());
		int y = Alignment.computeTopY(source.getHeight(), 0, size.getHeight(), style.getVerticalAlignment());
		g.setColor(style.getColor());
		Painter.drawImage(g, source, x, y);
	}

	/**
	 * Gets the content size of the image.
	 * <p>
	 * The given size is modified to set the image optimal size.
	 *
	 * @param style
	 *            the style to use.
	 * @param size
	 *            the size available for the content. A width or a height equal to <code>Widget#NO_CONSTRAINT</code>
	 *            means that there is no constraint on this dimension.
	 * @param source
	 *            the image to draw.
	 */
	protected void computeImageSize(Style style, Size size, Image source) {
		int width = getSize(size.getWidth(), source.getWidth());
		int height = getSize(size.getHeight(), source.getHeight());
		size.setSize(width, height);
	}

	private int getSize(int available, int optimal) {
		return available == Widget.NO_CONSTRAINT ? optimal : Math.min(available, optimal);
	}

}
