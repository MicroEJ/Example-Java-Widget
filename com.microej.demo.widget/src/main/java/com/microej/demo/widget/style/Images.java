/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.style;

import ej.widget.image.DefaultImageLoader;
import ej.widget.image.ImageLoader;

/**
 * The images used in the application.
 */
public interface Images {

	/**
	 * The image loader to use to load the images of the application.
	 */
	ImageLoader IMAGE_LOADER = new DefaultImageLoader();

	/**
	 * The store icon.
	 */
	String STORE_ICON = "/images/store.png"; //$NON-NLS-1$
}
