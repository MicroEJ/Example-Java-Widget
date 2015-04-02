/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import com.is2t.demo.launcher.Launcher;
import com.is2t.demo.widgets.theme.Pictos;
import com.is2t.mwt.composites.BorderComposite;
import com.is2t.mwt.util.ListenerAdapter;
import com.is2t.mwt.widgets.LookExtension;
import com.is2t.mwt.widgets.Picto;
import com.is2t.mwt.widgets.Picto.PictoSize;
import com.is2t.mwt.widgets.button.PictoButton;
import com.is2t.mwt.widgets.label.LeftIconLabel;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.flow.Page;
import ej.flow.mwt.MWTPage;
import ej.mwt.Composite;
import ej.mwt.MWT;
import ej.mwt.Widget;

public abstract class WidgetsPage extends Composite implements Page<AppPage>,
		MWTPage {

	public static final int TOP_BAR_HEIGHT = 60;

	private final Composite topBar;
	private final Composite mainContent;

	public WidgetsPage() {
		this.topBar = createTopBar();
		add(this.topBar);

		this.mainContent = createMainContent();
		add(this.mainContent);
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

	protected Composite createTopBar() {
		BorderComposite topBarLayout = new BorderComposite();
		final Launcher launcher = ServiceLoaderFactory.getServiceLoader()
				.getService(Launcher.class);

		if (canGoBack()) {
			PictoButton backButton = new PictoButton(new Picto(Pictos.Back,
					PictoSize.Medium));
			backButton.setUnderlined(true);
			backButton.setListener(new ListenerAdapter() {
				@Override
				public void performAction(int value, Object object) {
					TransitionsHelper.backUntil(AppPage.MainPage);
				}
			});
			topBarLayout.addAt(backButton, MWT.WEST);
		} else {
			if (launcher != null) {
				PictoButton backButton = new PictoButton(new Picto(Pictos.Back,
						PictoSize.Medium));
				backButton.setUnderlined(true);
				backButton.setListener(new ListenerAdapter() {
					@Override
					public void performAction(int value, Object object) {
						launcher.start();
					}
				});
				topBarLayout.addAt(backButton, MWT.WEST);
			}
		}

		LeftIconLabel titleLabel = new LeftIconLabel(getTitle(), new Picto(
				getPictoTitle(), PictoSize.Medium));
		titleLabel.setUnderlined(true);
		titleLabel.setFontSize(LookExtension.GET_BIG_FONT_INDEX);
		topBarLayout.addAt(titleLabel, MWT.CENTER);

		if (launcher != null) {
			PictoButton homeButton = new PictoButton(
					new Picto(com.is2t.demo.showroom.common.Pictos.Home,
							PictoSize.XSmall));
			homeButton.setUnderlined(true);
			homeButton.setListener(new ListenerAdapter() {

				@Override
				public void performAction(int value, Object object) {
					launcher.start();
				}
			});
			topBarLayout.addAt(homeButton, MWT.EAST);
		}

		return topBarLayout;
	}

	/**
	 * Gets whether or not we can go back from this page.
	 *
	 * @return whether or not we can go back from this page.
	 */
	protected abstract boolean canGoBack();

	/**
	 * Creates and return the composite representing the content of the page.
	 *
	 * @return the composite representing the content of the page.
	 */
	protected abstract Composite createMainContent();

	@Override
	public void validate(int widthHint, int heightHint) {
		int barHeight = TOP_BAR_HEIGHT;
		this.topBar.validate(widthHint, barHeight);
		this.topBar.setBounds(0, 0, widthHint, barHeight);

		int mainContentHeight = heightHint - barHeight;
		int mainContentY = barHeight;
		this.mainContent.validate(widthHint, mainContentHeight);
		this.mainContent.setBounds(0, mainContentY, widthHint,
				mainContentHeight);

		setPreferredSize(widthHint, heightHint);
	}

	@Override
	public Widget getWidget() {
		return this;
	}

	@Override
	public void showNotify() {
		// Nothing to do.
	}

	@Override
	public void hideNotify() {
		// Nothing to do.
	}
}
