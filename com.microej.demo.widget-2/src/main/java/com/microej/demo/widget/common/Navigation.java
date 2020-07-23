/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.common;

import com.microej.demo.widget.main.MainPage;

import ej.microui.MicroUI;
import ej.microui.display.Display;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.stylesheet.Stylesheet;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.widget.basic.Label;
import ej.widget.container.SimpleDock;
import ej.widget.container.util.LayoutOrientation;

/**
 * Manages the navigation between the pages.
 */
public class Navigation {

	private Navigation() {
	}

	/**
	 * Shows the main page.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		MicroUI.start();
		Desktop desktop = createDesktop(new MainPage());
		Display.getDisplay().requestShow(desktop);
	}

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
		Desktop desktop = createDesktop(page);
		TransitionDisplayable displayable = new TransitionDisplayable(desktop, forward);
		Display.getDisplay().requestShow(displayable);
	}

	private static Desktop createDesktop(Page page) {
		Stylesheet stylesheet = createStylesheet(page);
		Widget rootWidget = createRootWidget(page);

		Desktop desktop = PageHelper.createDesktop();
		desktop.setStylesheet(stylesheet);
		desktop.setWidget(rootWidget);
		return desktop;
	}

	private static Stylesheet createStylesheet(Page page) {
		CascadingStylesheet stylesheet = new CascadingStylesheet();
		page.populateStylesheet(stylesheet);
		PageHelper.addCommonStyle(stylesheet);
		return stylesheet;
	}

	private static Widget createRootWidget(Page page) {
		Widget contentWidget = page.getContentWidget();
		if (page instanceof MainPage) {
			return PageHelper.createPage(contentWidget, false);
		} else {
			Label title = new Label(page.getName());
			title.addClassSelector(PageHelper.TITLE_CLASSSELECTOR);

			SimpleDock dock = new SimpleDock(LayoutOrientation.VERTICAL);
			dock.addClassSelector(PageHelper.CONTENT_CLASSSELECTOR);
			dock.setFirstChild(title);
			dock.setCenterChild(contentWidget);

			return PageHelper.createPage(dock, true);
		}
	}
}
