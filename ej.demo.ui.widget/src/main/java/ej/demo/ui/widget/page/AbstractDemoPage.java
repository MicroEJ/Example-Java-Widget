/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package ej.demo.ui.widget.page;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.demo.ui.widget.WidgetsDemo;
import ej.demo.ui.widget.style.ClassSelectors;
import ej.demo.ui.widget.style.Images;
import ej.exit.ExitHandler;
import ej.mwt.Widget;
import ej.style.util.StyleHelper;
import ej.widget.basic.Image;
import ej.widget.basic.Label;
import ej.widget.composed.ButtonImage;
import ej.widget.composed.ButtonWrapper;
import ej.widget.container.SimpleDock;
import ej.widget.listener.OnClickListener;
import ej.widget.navigation.page.Page;

/**
 * Common abstract page implementation for all the application pages.
 */
public abstract class AbstractDemoPage extends Page {

	private SimpleDock content;

	/**
	 * Creates a new demo page.
	 */
	public AbstractDemoPage() {
		setWidget(createContent());
	}

	// @Override
	// public void onTransitionStart() {
	// super.onTransitionStart();
	// hideNotify();
	// }
	//
	// @Override
	// public void onTransitionStop() {
	// super.onTransitionStop();
	// if (isShown()) {
	// showNotify();
	// }
	// }

	private Widget createContent() {
		this.content = new SimpleDock();
		this.content.setHorizontal(false);
		this.content.setFirst(createTopBar());
		this.content.setCenter(createMainContent());
		return this.content;
	}

	/**
	 * Creates the widget representing the top bar of the page.
	 *
	 * @return the top bar widget.
	 */
	protected Widget createTopBar() {
		// The title of the page.
		Label titleLabel = new Label(getTitle());
		titleLabel.addClassSelector(ClassSelectors.TITLE);

		SimpleDock topBar = new SimpleDock();
		topBar.addClassSelector(ClassSelectors.TOP_BAR);
		topBar.setCenter(titleLabel);

		if (WidgetsDemo.canGoBack()) {
			// Add a back button.
			// Button backButton = new Button(Character.toString(Pictos.BACK));
			// backButton.getLabel().addClassSelector(ClassSelectors.LARGE_ICON);
			ButtonImage backButton = new ButtonImage("/images/back.png"); //$NON-NLS-1$
			backButton.addOnClickListener(new OnClickListener() {

				@Override
				public void onClick() {
					WidgetsDemo.back();
				}
			});
			topBar.setFirst(backButton);
		} else {
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

		}
		return topBar;
	}

	/**
	 * Gets the title of the page.
	 *
	 * @return the title of the page.
	 */
	protected abstract String getTitle();

	/**
	 * Creates the widget representing the main content of the page.
	 *
	 * @return the composite representing the content of the page.
	 */
	protected abstract Widget createMainContent();

	@Override
	public String getCurrentURL() {
		return getClass().getName();
	}

}
