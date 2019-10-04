/*
 * Java
 *
 * Copyright  2017-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
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
