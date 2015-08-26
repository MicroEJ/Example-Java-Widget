/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import com.is2t.demo.widgets.widget.BorderCompositeWithBackground;
import com.is2t.demo.widgets.widget.theme.Pictos;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.flow.Page;
import ej.flow.PageType;
import ej.flow.mwt.MWTFlowManager;
import ej.flow.mwt.MWTPage;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.widgets.composites.BorderComposite;
import ej.widgets.util.ListenerAdapter;
import ej.widgets.widgets.LookExtension;
import ej.widgets.widgets.Picto;
import ej.widgets.widgets.Picto.PictoSize;
import ej.widgets.widgets.button.PictoButton;
import ej.widgets.widgets.label.LeftIconLabel;

/**
 * Page skeleton of the application.
 */
public abstract class WidgetsPage implements Page<WidgetsPage>, MWTPage, PageType<WidgetsPage> {

	/**
	 * Creates the widget representing the top bar of the page.
	 * 
	 * @return the top bar widget.
	 */
	protected Widget createTopBar() {
		BorderComposite topBarLayout = new BorderComposite();

		// Add a back button if necessary.
		if (canGoBack()) {
			PictoButton backButton = new PictoButton(new Picto(Pictos.BACK, PictoSize.Medium));
			backButton.setUnderlined(true);
			backButton.setListener(new ListenerAdapter() {
				@Override
				public void performAction(int value, Object object) {
					getFlowManager().back();
				}
			});
			topBarLayout.addAt(backButton, MWT.WEST);
		}

		// Add the title of the page.
		LeftIconLabel titleLabel = new LeftIconLabel(getTitle(), new Picto(getPictoTitle(), PictoSize.Medium));
		titleLabel.setUnderlined(true);
		titleLabel.setFontSize(LookExtension.GET_BIG_FONT_INDEX);
		topBarLayout.addAt(titleLabel, MWT.CENTER);

		return topBarLayout;
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
	public Widget getWidget() {
		BorderComposite layout = new BorderCompositeWithBackground();
		layout.addAt(createTopBar(), MWT.NORTH);
		layout.addAt(createMainContent(), MWT.CENTER);
		return layout;
	}

	@Override
	public void showNotify() {
		// Nothing to do.
	}

	@Override
	public void hideNotify() {
		// Nothing to do.
	}

	@Override
	public WidgetsPage createPage() {
		return this;
	}

	@Override
	public WidgetsPage getType() {
		return this;
	}

	/**
	 * Gets the application flow manager used to navigate between pages.
	 * 
	 * @return the application flow manager used to navigate between pages.
	 */
	protected MWTFlowManager<WidgetsPage, WidgetsPage> getFlowManager() {
		MWTFlowManager<WidgetsPage, WidgetsPage> mwtFlowManager = ServiceLoaderFactory.getServiceLoader().getService(
				MWTFlowManager.class);
		return mwtFlowManager;
	}
}
