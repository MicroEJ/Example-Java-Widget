/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.common;

import com.microej.demo.widget.main.MainPage;

import ej.microui.display.Display;

/**
 * Manages the navigation between the pages.
 */
public class Navigation {

	/**
	 * Shows the main page.
	 */
	public static void showMainPage() {
		showPage(new MainPage(), false);
	}

	/**
	 * Shows a page.
	 *
	 * @param page
	 *            the page to show.
	 */
	public static void showPage(Page page) {
		showPage(page, true);
	}

	private static void showPage(Page page, boolean forward) {
		TransitionDisplayable displayable = new TransitionDisplayable(page.getDesktop(), forward);
		Display.getDisplay().requestShow(displayable);
	}

	private Navigation() {
	}
}
