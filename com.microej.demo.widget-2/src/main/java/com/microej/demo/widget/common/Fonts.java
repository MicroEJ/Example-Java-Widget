/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.common;

import ej.microui.display.Font;

/**
 * Fonts used by the application.
 */
public class Fonts {

	private static final String SOURCE_LIGHT_20 = "/fonts/source_l_14px-20px-26h.ejf"; //$NON-NLS-1$

	/**
	 * Gets the default font.
	 *
	 * @return the default font.
	 */
	public static Font getDefaultFont() {
		return Font.getFont(SOURCE_LIGHT_20);
	}

	private Fonts() {
	}

}
