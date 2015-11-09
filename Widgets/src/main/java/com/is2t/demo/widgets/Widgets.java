/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets;

import com.is2t.demo.widgets.page.MainPage;

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
		System.out.println("Widgets.main()");
		desktop = new HorizontalTransitionDesktop();
		// Panel panel = new Panel();
		//
		// panel.setWidget(new MainPage());
		// panel.setWidget(new CheckboxPage());
		// panel.setWidget(new SwitchPage());
		desktop.show(new MainPage());
		desktop.show();
	}

	// @Deprecated
	// public static void show(Widget pageContent) {
	// Panel panel = new Panel() {
	// @Override
	// public void hide() {
	// System.out.println("Widgets.show(...).new Panel() {...}.hide()");
	// super.hide();
	// setWidget(null);
	// }
	//
	// @Override
	// public void hideNotify() {
	// System.out.println("Widgets.show(...).new Panel() {...}.hideNotify()");
	// super.hideNotify();
	// setWidget(null);
	// }
	// };
	// panel.setWidget(pageContent);
	// show(panel);
	// }

	public static void show(Panel panel) {
		System.out.println("Widgets.show()");
		// panel.show(desktop, true);
		desktop.show(panel);
	}

	public static void back() {
		desktop.back();
	}

}
