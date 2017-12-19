/*
 * Java
 *
 * Copyright 2014-2017 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widget;

import com.microej.demo.widget.page.MainPage;
import com.microej.demo.widget.style.StylesheetPopulator;

import ej.microui.MicroUI;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.mwt.Desktop;
import ej.mwt.MWT;
import ej.mwt.Panel;
import ej.mwt.Widget;
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
	private static Panel Panel;
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
		Desktop = new Desktop() {
			@Override
			public boolean handleEvent(int event) {
				// set panel focus to null when we click on a blank space
				int type = Event.getType(event);
				if (type == Event.POINTER) {
					int action = Pointer.getAction(event);
					if (action == Pointer.RELEASED) {
						getPanel().setFocus(null);
					}
				}
				return super.handleEvent(event);
			}
		};
		Panel = new Panel();
		Panel.setWidget(TransitionContainer);
		Panel.showFullScreen(Desktop);
		Desktop.show();
	}

	/**
	 * Gets the desktop
	 *
	 * @return the desktop
	 */
	public static Desktop getDesktop() {
		return Desktop;
	}

	/**
	 * Gets the panel
	 *
	 * @return the panel
	 */
	public static Panel getPanel() {
		return Panel;
	}

	private static TransitionContainer newTransitionContainer() {
		if (USE_SCREENSHOT_TRANSITION) {
			return new SlideScreenshotTransitionContainer(MWT.LEFT, false, false);
		} else {
			return new SlideTransitionContainer(MWT.LEFT, false);
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
