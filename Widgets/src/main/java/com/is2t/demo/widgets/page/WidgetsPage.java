/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import com.is2t.demo.widgets.Widgets;
import com.is2t.demo.widgets.style.ClassSelector;
import com.is2t.demo.widgets.style.Pictos;

import ej.composite.BorderComposite;
import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.mwt.MWT;
import ej.mwt.Panel;
import ej.mwt.Widget;
import ej.widget.basic.Label;
import ej.widget.composed.Button;
import ej.widget.listener.OnClickListener;

/**
 * Page skeleton of the application.
 */
public abstract class WidgetsPage extends Panel {

	public WidgetsPage() {
		super();
		setWidget(createContent());
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
		// Add the title of the page.
		BorderComposite title = new BorderComposite();
		Label titleIcon = new Label(getPictoTitle() + "");
		titleIcon.addClassSelector(ClassSelector.LARGE_ICON);
		title.add(titleIcon, MWT.WEST);
		Label titleLabel = new Label(getTitle());
		titleLabel.addClassSelector(ClassSelector.LARGE_LABEL);
		title.add(titleLabel, MWT.CENTER);

		// Add a back button if necessary.
		if (canGoBack()) {
			BorderComposite topBar = new BorderComposite();
			topBar.add(title, MWT.CENTER);

			Label backIcon = new Label(Pictos.BACK + "");
			backIcon.addClassSelector(ClassSelector.LARGE_ICON);
			Button backButton = new Button();
			backButton.setWidget(backIcon);
			backButton.addOnClickListener(new OnClickListener() {

				@Override
				public void onClick() {
					Widgets.back();
				}
			});
			topBar.add(backButton, MWT.WEST);
			return topBar;
		} else {
			return title;
		}
	}

	/**
	 * Gets the title of the page.
	 * 
	 * @return the title of the page.
	 */
	protected abstract String getTitle();

	/**
	 * Gets the picto used for the title.
	 * 
	 * @return Gets the picto used for the title.
	 */
	protected abstract char getPictoTitle();

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
	public void render(GraphicsContext g) {
		g.setColor(Colors.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
