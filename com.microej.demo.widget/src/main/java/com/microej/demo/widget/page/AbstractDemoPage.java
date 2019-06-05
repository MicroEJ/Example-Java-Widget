/*
 * Java
 *
 * Copyright 2014-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.demo.widget.page;

import com.microej.demo.widget.WidgetsDemo;
import com.microej.demo.widget.style.ClassSelectors;
import com.microej.demo.widget.style.Images;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.exit.ExitHandler;
import ej.style.util.StyleHelper;
import ej.widget.basic.ButtonImage;
import ej.widget.basic.Image;
import ej.widget.basic.Label;
import ej.widget.composed.ButtonWrapper;
import ej.widget.container.SimpleDock;
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
		super(false);
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

		SimpleDock topBar = new SimpleDock();
		topBar.addClassSelector(ClassSelectors.TOP_BAR);
		topBar.setCenter(titleLabel);

		if (mainPage) {
			// Add an exit button.
			ButtonWrapper exitButton = new ButtonWrapper();
			exitButton.addOnClickListener(new OnClickListener() {

				@Override
				public void onClick() {
					ExitHandler exitHandler = ServiceLoaderFactory.getServiceLoader().getService(ExitHandler.class);
					if (exitHandler != null) {
						exitHandler.exit();
					}
				}
			});

			Image exitIcon = new Image(StyleHelper.getImage(Images.STORE_ICON));
			exitButton.setWidget(exitIcon);
			topBar.setFirst(exitButton);
		} else {
			// Add a back button.
			ButtonImage backButton = new ButtonImage("/images/back.png"); //$NON-NLS-1$
			backButton.addOnClickListener(new OnClickListener() {

				@Override
				public void onClick() {
					WidgetsDemo.showMainPage();
				}
			});
			topBar.setFirst(backButton);
		}
		setFirst(topBar);
	}

}
