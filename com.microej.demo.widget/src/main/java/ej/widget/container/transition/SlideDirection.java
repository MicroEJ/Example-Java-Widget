/*
 * Java
 *
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.container.transition;

/**
 * Enumerates directions for {@link SlideTransitionContainer} and {@link SlideScreenshotTransitionContainer}.
 */
public class SlideDirection {

	/**
	 * Direction to slide upward.
	 */
	public static final int UP = 1;

	/**
	 * Direction to slide downward.
	 */
	public static final int DOWN = 2;

	/**
	 * Direction to slide toward the left.
	 */
	public static final int LEFT = 3;

	/**
	 * Direction to slide toward the right.
	 */
	public static final int RIGHT = 4;

	private SlideDirection() {
		// private constructor
	}
}
