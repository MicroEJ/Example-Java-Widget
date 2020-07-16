/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.main.style;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.Painter;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.util.Size;

/**
 * Rectangular background with a image showing an arrow on the right.
 */
public class GoToBackground extends RectangularBackground {

	private static final String IMAGES_IC_ARROW_PNG = "/images/ic-arrow.png"; //$NON-NLS-1$

	private final Image goToImage; // Don't need to close it as long as it is a resource.

	/**
	 * Creates a goto background with no border.
	 *
	 * @param color
	 *            the color.
	 */
	public GoToBackground(int color) {
		super(color);
		this.goToImage = Image.getImage(IMAGES_IC_ARROW_PNG);
	}

	@Override
	public void apply(GraphicsContext g, Size size) {
		super.apply(g, size);
		Painter.drawImage(g, this.goToImage, size.getWidth() - this.goToImage.getWidth(),
				(size.getHeight() - this.goToImage.getHeight()) / 2);
	}

}
