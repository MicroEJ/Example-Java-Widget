/*
 * Java
 *
 * Copyright 2017 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
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
