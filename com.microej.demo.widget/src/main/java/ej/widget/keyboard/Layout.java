/*
 * Copyright 2017-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.keyboard;

/**
 * Represents a keyboard layout
 */
public interface Layout {

	/**
	 * Gets the characters of the first row of a keyboard
	 *
	 * @return the string containing each character of the first row
	 */
	public abstract String getFirstRow();

	/**
	 * Gets the characters of the second row of a keyboard
	 *
	 * @return the string containing each character of the second row
	 */
	public abstract String getSecondRow();

	/**
	 * Gets the characters of the third row of a keyboard
	 *
	 * @return the string containing each character of the third row
	 */
	public abstract String getThirdRow();
}
