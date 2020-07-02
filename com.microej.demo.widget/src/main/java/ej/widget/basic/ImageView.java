/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Size;
import ej.widget.util.ImagePainter;

/**
 * A widget that displays an image.
 * <p>
 * Users of this widget are responsible of closing the image resource.
 *
 * @see Image
 */
public class ImageView extends Widget {

	private Image source;

	/**
	 * Creates an image with the source to display.
	 * <p>
	 * Be careful that the given image is not closed by this widget.
	 *
	 * @param source
	 *            the source to display.
	 */
	public ImageView(Image source) {
		this.source = source;
	}

	/**
	 * Gets the source of the image widget.
	 *
	 * @return the source of the image widget.
	 */
	public Image getSource() {
		return this.source;
	}

	/**
	 * Sets the source to display for this image.
	 * <p>
	 * Be careful that the given image is not closed by this widget.
	 *
	 * @param source
	 *            the source to display.
	 */
	public void setSource(Image source) {
		this.source = source;
	}

	@Override
	protected void renderContent(GraphicsContext g, Size size) {
		Style style = getStyle();
		ImagePainter.drawImageInArea(g, this.source, 0, 0, size.getWidth(), size.getHeight(),
				style.getHorizontalAlignment(), style.getVerticalAlignment());
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		ImagePainter.computeOptimalSize(this.source, size);
	}
}
