/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.page;

import com.microej.demo.widgets.WidgetsDemo;
import com.microej.demo.widgets.resources.Images;
import com.microej.demo.widgets.resources.Pictos;
import com.microej.demo.widgets.style.ClassSelector;

import ej.composite.BorderComposite;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.transition.page.Page;
import ej.widget.basic.Image;
import ej.widget.basic.Label;
import ej.widget.basic.image.ImageHelper;
import ej.widget.composed.SimpleButton;
import ej.widget.listener.OnClickListener;

/**
 * Common abstract page implementation for all pages of the application.
 */
public abstract class AbstractDemoPage extends Page {

	private Widget createContent() {
		BorderComposite content = new BorderComposite();
		content.setHorizontal(false);
		content.add(createTopBar(), MWT.NORTH);
		content.add(createMainContent(), MWT.CENTER);
		return content;
	}

	/**
	 * Creates the widget representing the top bar of the page.
	 *
	 * @return the top bar widget.
	 */
	protected Widget createTopBar() {
		// The title of the page.
		BorderComposite title = new BorderComposite();

		if (withMicroEJLogoInTopBar()) {
			Image titleIcon = new Image(ImageHelper.loadImage(Images.MICROEJ_LOGO));
			title.add(titleIcon, MWT.WEST);
		}

		Label titleLabel = new Label(getTitle());
		titleLabel.addClassSelector(ClassSelector.LARGE_LABEL);
		title.add(titleLabel, MWT.CENTER);

		// Add a back button if necessary.
		if (canGoBack()) {
			BorderComposite topBar = new BorderComposite();
			topBar.add(title, MWT.CENTER);

			SimpleButton backButton = new SimpleButton(Pictos.BACK + ""); //$NON-NLS-1$
			backButton.getLabel().addClassSelector(ClassSelector.LARGE_ICON);
			backButton.addOnClickListener(new OnClickListener() {

				@Override
				public void onClick() {
					WidgetsDemo.back();
				}
			});
			topBar.add(backButton, MWT.WEST);
			return topBar;
		} else {
			return title;
		}
	}

	/**
	 * Gets whether or not the top bar must contain the MicroEJ logo.
	 *
	 * @return true if the top must contain the MicroEJ logo otherwise false.
	 */
	protected boolean withMicroEJLogoInTopBar() {
		return false;
	}

	/**
	 * Gets the title of the page.
	 *
	 * @return the title of the page.
	 */
	protected abstract String getTitle();

	/**
	 * Gets whether or not we can go back from this page.
	 *
	 * @return true if we can go back otherwise false.
	 */
	protected abstract boolean canGoBack();

	/**
	 * Creates the widget representing the main content of the page.
	 *
	 * @return the composite representing the content of the page.
	 */
	protected abstract Widget createMainContent();

	@Override
	public void showNotify() {
		super.showNotify();
		// The content of the page is created only when the page is displayed.
		setWidget(createContent());
	}

	@Override
	public void hideNotify() {
		super.hideNotify();
		// The content of the page is destroyed only when the page is not displayed anymore.
		setWidget(null);
	}

	@Override
	public String getCurrentURL() {
		return getClass().getName();
	}
}
