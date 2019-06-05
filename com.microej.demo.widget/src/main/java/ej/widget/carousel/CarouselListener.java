/*
 * Java
 *
 * Copyright 2017-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
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
