/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic.image;

import ej.annotation.Nullable;
import ej.microui.display.Image;
import ej.microui.display.ResourceImage;
import ej.service.ServiceFactory;

/**
 * Image utilities.
 */
class ImageHelper {

	@Nullable
	private static ImageTheme theme;

	// Prevents initialization.
	private ImageHelper() {
	}

	/**
	 * Gets the image theme.
	 *
	 * @return the image theme instance.
	 */
	public static ImageTheme getTheme() {
		if (theme != null) {
			return theme;
		}
		return theme = ServiceFactory.getRequiredService(ImageTheme.class);
	}

	public static Image loadImageFromTheme(String imagePath) {
		return ResourceImage.loadImage(imagePath);
	}
}
