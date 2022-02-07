/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.common;

import ej.microui.display.Font;

/**
 * Fonts used by the application.
 */
public class Fonts {

	private static final String SOURCE_12_400 = "/fonts/SourceSansPro_12px-400.ejf"; //$NON-NLS-1$
	private static final String SOURCE_15_600 = "/fonts/SourceSansPro_15px-600.ejf"; //$NON-NLS-1$
	private static final String SOURCE_16_700 = "/fonts/SourceSansPro_16px-700.ejf"; //$NON-NLS-1$
	private static final String SOURCE_19_300 = "/fonts/SourceSansPro_19px-300.ejf"; //$NON-NLS-1$
	private static final String SOURCE_22_400 = "/fonts/SourceSansPro_22px-400.ejf"; //$NON-NLS-1$

	private Fonts() {
		// Prevent instantiation.
	}

	/**
	 * Gets the Source Sans Pro font with a height of 12px and a weight of 400.
	 * <p>
	 * Height (Cap to descender): 12<br>
	 * Font-weight: 400<br>
	 * Height: 19<br>
	 * Baseline: 13<br>
	 * Space size: 4<br>
	 *
	 * @return the font with the settings given above.
	 *
	 */
	public static Font getSourceSansPro12px400() {
		return Font.getFont(SOURCE_12_400);
	}

	/**
	 * Gets the Source Sans Pro font with a height of 15px and a weight of 600.
	 * <p>
	 * Height (Cap to descender): 15<br>
	 * Font-weight: 600<br>
	 * Height: 20<br>
	 * Baseline: 16<br>
	 * Space size: 4<br>
	 *
	 * @return the font with the settings given above.
	 */
	public static Font getSourceSansPro15px600() {
		return Font.getFont(SOURCE_15_600);
	}

	/**
	 * Gets the Source Sans Pro font with a height of 16px and a weight of 700.
	 * <p>
	 * Height (Cap to descender): 16<br>
	 * Font-weight: 700<br>
	 * Height: 21<br>
	 * Baseline: 17<br>
	 * Space size: 5<br>
	 *
	 * @return the font with the settings given above.
	 */
	public static Font getSourceSansPro16px700() {
		return Font.getFont(SOURCE_16_700);
	}

	/**
	 * Gets the Source Sans Pro font with a height of 19px and a weight of 300.
	 * <p>
	 * Height (Cap to descender): 19<br>
	 * Font-weight: 700<br>
	 * Height: 26<br>
	 * Baseline: 21<br>
	 * Space size: 4<br>
	 *
	 * @return the font with the settings given above.
	 */
	public static Font getSourceSansPro19px300() {
		return Font.getFont(SOURCE_19_300);
	}

	/**
	 * Gets the Source Sans Pro font with a height of 22px and a weight of 400.
	 * <p>
	 * Height (Cap to descender): 22<br>
	 * Font-weight: 400<br>
	 * Height: 30<br>
	 * Baseline: 24<br>
	 * Space size: 8<br>
	 *
	 * @return the font with the settings given above.
	 */
	public static Font getSourceSansPro22px400() {
		return Font.getFont(SOURCE_22_400);
	}
}
