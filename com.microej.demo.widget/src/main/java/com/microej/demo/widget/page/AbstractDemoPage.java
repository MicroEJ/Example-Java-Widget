/*
 * Copyright 2014-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.page;

import com.microej.demo.widget.WidgetsDemo;
import com.microej.demo.widget.style.ClassSelectors;
import com.microej.demo.widget.style.Images;

import ej.widget.basic.ButtonImagePath;
import ej.widget.basic.ImagePath;
import ej.widget.basic.Label;
import ej.widget.container.SimpleDock;
import ej.widget.container.util.LayoutOrientation;
import ej.widget.listener.OnClickListener;

/**
 * Common abstract page implementation for all the application pages.
 */
public abstract class AbstractDemoPage extends SimpleDock {

	/**
	 * Creates a new demo page.
	 *
	 * @param mainPage
	 *            <code>true</code> if it is the main page, <code>false</code> otherwise.
	 * @param title
	 *            the page title.
	 */
	public AbstractDemoPage(boolean mainPage, String title) {
		super(LayoutOrientation.VERTICAL);
		createTopBar(mainPage, title);
	}

	/**
	 * Creates the widget representing the top bar of the page.
	 *
	 * @param mainPage
	 *            <code>true</code> if it is the main page, <code>false</code> otherwise.
	 * @param title
	 *            the page title.
	 */
	private void createTopBar(boolean mainPage, String title) {
		// The title of the page.
		Label titleLabel = new Label(title);
		titleLabel.addClassSelector(ClassSelectors.TITLE);

		SimpleDock topBar = new SimpleDock(LayoutOrientation.HORIZONTAL);
		topBar.addClassSelector(ClassSelectors.TOP_BAR);
		topBar.setCenterChild(titleLabel);

		if (mainPage) {
			ImagePath exitIcon = new ImagePath(Images.STORE_ICON);
			topBar.setFirstChild(exitIcon);
		} else {
			// Add a back button.
			ButtonImagePath backButton = new ButtonImagePath("/images/back.png"); //$NON-NLS-1$
			backButton.addOnClickListener(new OnClickListener() {

				@Override
				public void onClick() {
					WidgetsDemo.showMainPage();
				}
			});
			topBar.setFirstChild(backButton);
		}
		setFirstChild(topBar);
	}

}
