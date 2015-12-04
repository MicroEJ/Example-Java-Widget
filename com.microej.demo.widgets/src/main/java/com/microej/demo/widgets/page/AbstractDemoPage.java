/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.page;

import com.microej.demo.widgets.WidgetsDemo;
import com.microej.demo.widgets.style.ClassSelectors;
import com.microej.demo.widgets.style.Images;
import com.microej.demo.widgets.style.Pictos;

import ej.composite.BorderComposite;
import ej.microui.display.GraphicsContext;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.transition.page.Page;
import ej.widget.basic.Image;
import ej.widget.basic.Label;
import ej.widget.basic.image.ImageHelper;
import ej.widget.composed.SimpleButton;
import ej.widget.listener.OnClickListener;

/**
 * Common abstract page implementation for all the application pages.
 */
public abstract class AbstractDemoPage extends Page {

	/**
	 * Creates a new demo page.
	 */
	public AbstractDemoPage() {
		setWidget(createContent());
	}

	@Override
	public void onTransitionStart() {
		super.onTransitionStart();
		hideNotify();
	}

	@Override
	public void onTransitionStop() {
		super.onTransitionStop();
		if (isShown()) {
			showNotify();
		}
	}

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
		Label titleLabel = new Label(getTitle());
		titleLabel.addClassSelector(ClassSelectors.TITLE);

		BorderComposite topBar = new BorderComposite();
		topBar.add(titleLabel, MWT.CENTER);

		if (WidgetsDemo.canGoBack()) {
			// Add a back button.
			SimpleButton backButton = new SimpleButton(Character.toString(Pictos.BACK));
			backButton.getLabel().addClassSelector(ClassSelectors.LARGE_ICON);
			backButton.addOnClickListener(new OnClickListener() {

				@Override
				public void onClick() {
					WidgetsDemo.back();
				}
			});
			topBar.add(backButton, MWT.WEST);
		} else {
			// Add a MicroEJ logo.
			Image titleIcon = new Image(ImageHelper.loadImage(Images.MICROEJ_LOGO));
			topBar.add(titleIcon, MWT.WEST);
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

	@Override
	public boolean isTransparent() {
		return false;
	}

	@Override
	public void render(GraphicsContext g) {
		// FIXME Use style.
		g.setColor(0x404041);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
