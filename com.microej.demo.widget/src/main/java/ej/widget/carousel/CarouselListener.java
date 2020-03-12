/*
 * Copyright 2017-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.carousel;

/**
 * Represents a listener for carousel events
 */
public interface CarouselListener {

	/**
	 * Signals the carousel entries order has changed
	 *
	 * @param recipeIds
	 *            the ordered list of recipe ids
	 */
	public abstract void onCarouselChanged(int[] recipeIds);
}
