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
import ej.microui.display.Display;
import ej.mwt.Desktop;
import ej.mwt.Panel;
import ej.widget.StyledDesktop;
import ej.widget.navigation.navigator.HistorizedNavigator;
import ej.widget.navigation.page.URLResolver;
import ej.widget.navigation.stack.PageStack;
import ej.widget.navigation.stack.PageStackURL;
import ej.widget.navigation.transition.HorizontalTransitionManager;

/**
 * This demo illustrates the widgets library.
 */
public class WidgetsDemo {

	public static int WIDTH;
	public static int HEIGHT;
	public static int KEYBOARD_HEIGHT = 0;

	private static Desktop Desktop;
	private static HistorizedNavigator HistorizedNavigator;

	private static boolean GoingForward;
	private static boolean GoingBackward;
	public static Panel panel;

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
		HistorizedNavigator = newNavigator();

		// Show the main page.
		HistorizedNavigator.show(MainPage.class.getName());

		WIDTH = Display.getDefaultDisplay().getWidth();
		HEIGHT = Display.getDefaultDisplay().getHeight();

		// Show the navigator.
		Desktop = new StyledDesktop();
		panel = new Panel() {
			@Override
			public void validate(int widthHint, int heightHint) {
				super.validate(WIDTH, HEIGHT - KEYBOARD_HEIGHT);
				setBounds(0, 0, WIDTH, HEIGHT - KEYBOARD_HEIGHT);
			}
		};
		panel.setWidget(HistorizedNavigator);
		panel.show(Desktop, true);
		Desktop.show();
	}

	public static Desktop getDesktop() {
		return Desktop;
	}

	private static HistorizedNavigator newNavigator() {
		URLResolver urlResolver = new DirectURLResolver();
		PageStack pageStack = new PageStackURL(urlResolver);
		HistorizedNavigator navigator = new HistorizedNavigator(urlResolver, pageStack);
		navigator.setTransitionManager(new HorizontalTransitionManager());
		return navigator;
	}

	/**
	 * Shows the page corresponding to the given URL.
	 *
	 * @param url
	 *            the URL of the page to show.
	 */
	public static void show(String url) {
		GoingForward = true;
		HistorizedNavigator.show(url);
		GoingForward = false;
	}

	/**
	 * Shows the previous panel.
	 */
	public static void back() {
		GoingBackward = true;
		HistorizedNavigator.back();
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
		int historySize = HistorizedNavigator.getHistorySize();
		return (historySize > 0 || GoingForward) && !(historySize == 0 && GoingBackward);
	}

}
