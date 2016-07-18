/*
 * Java
 *
 * Copyright 2014-2016 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package ej.demo.ui.widget;

import ej.demo.ui.widget.page.DirectURLResolver;
import ej.demo.ui.widget.page.MainPage;
import ej.demo.ui.widget.style.StylesheetPopulator;
import ej.microui.MicroUI;
import ej.navigation.page.URLResolver;
import ej.navigation.stack.PagesStack;
import ej.navigation.stack.PagesStackURL;
import ej.navigation.tree.HorizontalTreeNavigationDesktop;
import ej.navigation.tree.HorizontalTreeScreenshotNavigationDesktop;
import ej.navigation.tree.TreeNavigationDesktop;

/**
 * This demo illustrates the widgets library.
 */
public class WidgetsDemo {

	private static final boolean WITH_SCREENSHOT_TRANSITION = System
			.getProperty("com.microej.demo.widgets.transition.screenshot") != null; //$NON-NLS-1$
	private static TreeNavigationDesktop Desktop;

	private static boolean GoingForward;
	private static boolean GoingBackward;

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
		MicroUI.start();
		Desktop = newTransitionDesktop();
		StylesheetPopulator.initialize();
		Desktop.show(MainPage.class.getName());
		Desktop.show();
	}

	private static TreeNavigationDesktop newTransitionDesktop() {
		URLResolver urlResolver = new DirectURLResolver();
		PagesStack pagesStack = new PagesStackURL(urlResolver);
		if (WITH_SCREENSHOT_TRANSITION) {
			return new HorizontalTreeScreenshotNavigationDesktop(urlResolver, pagesStack);
		} else {
			return new HorizontalTreeNavigationDesktop(urlResolver, pagesStack);
		}
	}

	/**
	 * Shows the page corresponding to the given URL.
	 *
	 * @param url
	 *            the URL of the page to show.
	 */
	public static void show(String url) {
		GoingForward = true;
		Desktop.show(url);
		GoingForward = false;
	}

	/**
	 * Shows the previous panel.
	 */
	public static void back() {
		GoingBackward = true;
		Desktop.back();
		GoingBackward = false;
	}

	/**
	 * Checks whether or not it is possible to go back in the navigation history.
	 * <p>
	 * Beware, the result of this method consider that it is called while creating the new page.
	 *
	 * @return <code>true</code> it is possible to go back, <code>false</code> otherwise.
	 */
	public static boolean canGoBack() {
		int historySize = Desktop.getHistorySize();
		return (historySize > 1 || GoingForward) && !(historySize == 1 && GoingBackward);
	}

}
