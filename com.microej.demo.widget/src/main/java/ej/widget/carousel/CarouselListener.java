/*
 * Java
 *
 * Copyright 2017 SEB. All rights reserved.
 * This Software has been designed by IS2T S.A.
 * All rights in this Software have been transferred to SEB.
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
