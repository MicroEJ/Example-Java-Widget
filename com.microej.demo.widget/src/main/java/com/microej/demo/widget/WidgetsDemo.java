/*
 * Copyright 2014-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget;

import com.microej.demo.widget.page.MainPage;
import com.microej.demo.widget.style.StylesheetPopulator;

import ej.microui.MicroUI;
import ej.microui.display.Display;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.widget.container.transition.SlideDirection;
import ej.widget.container.transition.SlideScreenshotTransitionContainer;
import ej.widget.container.transition.SlideTransitionContainer;
import ej.widget.container.transition.TransitionContainer;
import ej.widget.navigation.page.PageNotFoundException;

/**
 * This demo illustrates the widgets library.
 */
public class WidgetsDemo {

	private static final boolean USE_SCREENSHOT_TRANSITION = true;

	private static Desktop Desktop;
	private static TransitionContainer TransitionContainer;

	private static MainPage mainPage;

	// Prevents initialization.
	private WidgetsDemo() {
	}

	/**
	 * Application entry point.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		start();
	}

	/**
	 * Starts the widgets demo.
	 */
	public static void start() {
		// Start MicroUI framework.
		MicroUI.start();

		// Initialize stylesheet rules.
		StylesheetPopulator.initialize();

		// Create the navigator.
		TransitionContainer = newTransitionContainer();

		mainPage = new MainPage();

		// Show the main page.
		showMainPage();

		// Show the navigator.
		Desktop = new Desktop();
		Desktop.setWidget(TransitionContainer);
		Display.getDisplay().requestShow(Desktop);
	}

	/**
	 * Stops the widgets demo.
	 */
	public static void stop() {
		// Nothing to do.
	}

	/**
	 * Gets the desktop
	 *
	 * @return the desktop
	 */
	public static Desktop getDesktop() {
		return Desktop;
	}

	private static TransitionContainer newTransitionContainer() {
		if (USE_SCREENSHOT_TRANSITION) {
			return new SlideScreenshotTransitionContainer(SlideDirection.LEFT, false, false);
		} else {
			return new SlideTransitionContainer(SlideDirection.LEFT, false);
		}
	}

	/**
	 * Shows the page corresponding to the given class.
	 *
	 * @param clazz
	 *            the class of the page to show.
	 */
	public static void show(Class<? extends Widget> clazz) {
		try {
			Widget page = clazz.newInstance();
			TransitionContainer.show(page, true);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new PageNotFoundException(clazz.getName(), e);
		}
	}

	/**
	 * Shows the main page.
	 */
	public static void showMainPage() {
		TransitionContainer.show(mainPage, false);
	}

}
