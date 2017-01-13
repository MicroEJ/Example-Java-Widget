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
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
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

	private static Desktop Desktop;
	private static Panel Panel;
	private static HistorizedNavigator HistorizedNavigator;

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
		Panel = new Panel();
		Panel.setBounds(0, 0, WIDTH, HEIGHT);
		Panel.setWidget(HistorizedNavigator);
		Panel.showUsingBounds(Desktop);
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

	private static HistorizedNavigator newNavigator() {
		URLResolver urlResolver = new DirectURLResolver();
		PageStack pageStack = new PageStackURL(urlResolver);
		HistorizedNavigator navigator = new HistorizedNavigator(urlResolver, pageStack) {
			@Override
			public boolean handleEvent(int event) {
				// set panel focus to null when we click on a blank space
				int type = Event.getType(event);
				if (type == Event.POINTER) {
					int action = Pointer.getAction(event);
					if (action == Pointer.RELEASED) {
						getPanel().setFocus(null);
						return true;
					}
				}
				return false;
			}
		};
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
