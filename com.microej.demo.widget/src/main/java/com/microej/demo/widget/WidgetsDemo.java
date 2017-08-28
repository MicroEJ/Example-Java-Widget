/*
 * Java
 *
 * Copyright 2014-2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widget;

import com.microej.demo.widget.page.DirectURLResolver;
import com.microej.demo.widget.page.MainPage;
import com.microej.demo.widget.style.StylesheetPopulator;

import ej.microui.MicroUI;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.mwt.Desktop;
import ej.mwt.MWT;
import ej.mwt.Panel;
import ej.widget.container.transition.SlideScreenshotTransitionContainer;
import ej.widget.container.transition.SlideTransitionContainer;
import ej.widget.container.transition.TransitionContainer;
import ej.widget.navigation.page.Page;
import ej.widget.navigation.page.URLResolver;
import ej.widget.navigation.stack.PageStackURL;

/**
 * This demo illustrates the widgets library.
 */
public class WidgetsDemo {

	private static boolean USE_SCREENSHOT_TRANSITION = true;

	private static Desktop Desktop;
	private static Panel Panel;
	private static URLResolver UrlResolver;
	private static PageStackURL PageStack;
	private static TransitionContainer TransitionContainer;

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
		UrlResolver = new DirectURLResolver();
		PageStack = new PageStackURL(UrlResolver);
		TransitionContainer = newTransitionContainer();

		// Show the main page.
		show(MainPage.class.getName());

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
	 * Shows the page corresponding to the given URL.
	 *
	 * @param url
	 *            the URL of the page to show.
	 */
	public static void show(String url) {
		Page page = UrlResolver.resolve(url);
		TransitionContainer.show(page, true);
		PageStack.push(page);
	}

	/**
	 * Shows the previous panel.
	 */
	public static void back() {
		PageStack.pop();
		Page page = PageStack.peek();
		TransitionContainer.show(page, false);
	}

	/**
	 * Checks whether or not it is possible to go back in the navigation history.
	 * <p>
	 * Beware, the result of this method consider that it is called while creating the new page.
	 *
	 * @return <code>true</code> it is possible to go back, <code>false</code> otherwise.
	 */
	public static boolean canGoBack() {
		int historySize = PageStack.size();
		return historySize >= 1;
	}

}
