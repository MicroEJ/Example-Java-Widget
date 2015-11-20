/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets;

import com.microej.demo.widgets.page.MainPage;

import ej.microui.MicroUI;
import ej.mwt.Panel;
import ej.transition.HorizontalTransitionDesktop;
import ej.transition.TransitionDesktop;

/**
 * This demo represents a settings menu you can find on your smartphone. The main page shows all available settings,
 * each one leading to a page illustrating a different widget.
 */
public class Widgets {

	private static TransitionDesktop desktop;

	// Prevents initialization.
	private Widgets() {
	}

	/**
	 * Application entry point.
	 *
	 * @param args
	 *            useless.
	 */
	public static void main(String[] args) {
		MicroUI.start();
		// desktop = new HorizontalScreenshotTransitionDesktop();
		desktop = new HorizontalTransitionDesktop();
		desktop.show(new MainPage());
		desktop.show();
	}

	public static void show(Panel panel) {
		desktop.show(panel);
	}

	public static void back() {
		desktop.back();
	}

}
