/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import com.is2t.demo.widgets.style.ClassSelector;
import com.is2t.demo.widgets.style.Pictos;

import ej.composite.BorderComposite;
import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.widget.Button;
import ej.widget.Label;
import ej.widget.Picto;
import ej.widget.listener.OnClickListener;

/**
 * Page skeleton of the application.
 */
public abstract class WidgetsPage extends BorderComposite {

	public WidgetsPage() {
		setHorizontal(false);
		add(createTopBar(), MWT.NORTH);
		add(createMainContent(), MWT.CENTER);
	}

	/**
	 * Creates the widget representing the top bar of the page.
	 * 
	 * @return the top bar widget.
	 */
	protected Widget createTopBar() {
		// Add the title of the page.
		BorderComposite title = new BorderComposite();
		Picto titleIcon = new Picto(getPictoTitle());
		titleIcon.addClassSelector(ClassSelector.MEDIUM_ICON);
		title.add(titleIcon, MWT.WEST);
		Label titleLabel = new Label(getTitle());
		title.add(titleLabel, MWT.CENTER);

		// Add a back button if necessary.
		if (canGoBack()) {
			BorderComposite topBar = new BorderComposite();
			topBar.add(title, MWT.CENTER);

			Picto backIcon = new Picto(Pictos.BACK);
			backIcon.addClassSelector(ClassSelector.MEDIUM_ICON);
			Button backButton = new Button();
			backButton.setWidget(backIcon);
			backButton.addOnClickListener(new OnClickListener() {

				@Override
				public void onClick() {
					goTo(new MainPage());
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

	public void showNotify() {
		// Nothing to do.
	}

	public void hideNotify() {
		// Nothing to do.
	}

	@Override
	public void render(GraphicsContext g) {
		g.setColor(Colors.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	protected void goTo(WidgetsPage page) {
		getPanel().setWidget(page);
	}
}
