/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.common;

import ej.microui.display.Colors;

/**
 * Colors used by the application.
 */
public class DemoColors {

	/**
	 * Default background color used in the page content.
	 */
	public static final int DEFAULT_BACKGROUND = 0x262a2c;
	/**
	 * Alternate background color that can be used in some page content.
	 */
	public static final int ALTERNATE_BACKGROUND = 0x4b5357;
	/**
	 * Default foreground color used with both default and alternate backgrounds.
	 */
	public static final int DEFAULT_FOREGROUND = 0xffffff;
	/**
	 * Default border color.
	 */
	public static final int DEFAULT_BORDER = 0x97a7af;
	/**
	 * Color for empty space between main title bar and page content.
	 */
	public static final int EMPTY_SPACE = Colors.BLACK;
	/**
	 * Background color for highlighted keys on the keyboard.
	 */
	public static final int KEYBOARD_HIGHLIGHT_BACKGROUND = 0xd9d5d3;

	/**
	 * MicroEJ Coral color.
	 */
	public static final int CORAL = 0xee502e;
	/**
	 * MicroEJ Absinthe color.
	 */
	public static final int ABSINTHE = 0x6cc24a;
	/**
	 * MicroEJ Pomegranate color.
	 */
	public static final int POMEGRANATE = 0xcf4520;

	private DemoColors() {
		// Prevent instantiation.
	}

}
