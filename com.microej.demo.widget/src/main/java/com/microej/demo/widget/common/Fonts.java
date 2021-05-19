/*
 * Copyright 2020-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.common;

import ej.microui.display.Font;

/**
 * Fonts used by the application.
 */
public class Fonts {

	private static final String SOURCE_LIGHT_20 = "/fonts/source_l_14px-20px-26h.ejf"; //$NON-NLS-1$
	private static final String SOURCE_BOLD_16 = "/fonts/source_b_11px-16px-21h.ejf"; //$NON-NLS-1$
	private static final String SOURCE_30 = "/fonts/source_sans_pro_24.ejf"; //$NON-NLS-1$
	private static final String SOURCE_REGULAR = "/fonts/source_r_13-24px.ejf"; //$NON-NLS-1$

	private Fonts() {
	}

	/**
	 * Gets the default font (20px height).
	 *
	 * @return the default font.
	 */
	public static Font getDefaultFont() {
		return Font.getFont(SOURCE_LIGHT_20);
	}

	/**
	 * Gets the bold font with 16px height.
	 *
	 * @return the bold font with 16px height.
	 */
	public static Font getBoldFont() {
		return Font.getFont(SOURCE_BOLD_16);
	}

	/**
	 * Gets the regular font.
	 *
	 * @return the regular font.
	 */
	public static Font getRegularFont() {
		return Font.getFont(SOURCE_REGULAR);
	}

	/**
	 * Gets the font with 30px height.
	 *
	 * @return the font with 30px height.
	 */
	public static Font getFont30px() {
		return Font.getFont(SOURCE_30);
	}
}
