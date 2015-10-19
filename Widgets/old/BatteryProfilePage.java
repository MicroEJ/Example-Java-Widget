/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import com.is2t.demo.widgets.widget.IconToggleButton;
import com.is2t.demo.widgets.widget.theme.Pictos;

import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.widgets.composites.BorderComposite;
import ej.widgets.util.ListenerAdapter;
import ej.widgets.widgets.ListComposite;
import ej.widgets.widgets.LookExtension;
import ej.widgets.widgets.Picto;
import ej.widgets.widgets.Picto.PictoSize;
import ej.widgets.widgets.label.TitleLabel;
import ej.widgets.widgets.scroll.ScrollComposite;
import ej.widgets.widgets.tiny.ButtonGroup;

/**
 * Page illustrating the the horizontal scrollable list.
 */
public class BatteryProfilePage extends WidgetsPage {

	private static final int PROFILE_COUNT = 10;
	private static final char[] AVAILABLE_PICTOS = new char[] { Pictos.DATABASE, Pictos.BOX, Pictos.HOME_GARAGE,
			Pictos.FAN, Pictos.BLACK_LIGHT, Pictos.LIGHTNING };

	private ScrollComposite scrollComposite;

	@Override
	protected String getTitle() {
		return "Battery profile";
	}

	@Override
	protected char getPictoTitle() {
		return Pictos.BATTERY_PROFILE;
	}

	@Override
	protected boolean canGoBack() {
		return true;
	}

	@Override
	protected Widget createMainContent() {
		BorderComposite mainContentLayout = new BorderComposite();

		// The label thats show the selected profile.
		TitleLabel label = new TitleLabel("");
		label.setFontSize(LookExtension.GET_MEDIUM_FONT_INDEX);
		mainContentLayout.addAt(label, MWT.SOUTH);

		// The list of profiles.
		ListComposite profilesList = new ListComposite(true);
		ButtonGroup group = new ButtonGroup();

		int availablePictosCount = AVAILABLE_PICTOS.length;

		for (int profileIndex = 1; profileIndex <= PROFILE_COUNT; profileIndex++) {
			String name = "Profile " + profileIndex;
			int pictoIndex = profileIndex % availablePictosCount;
			char picto = AVAILABLE_PICTOS[pictoIndex];
			IconToggleButton profileButton = createProfile(name, picto, group, label);

			// Default profile.
			if (profileIndex == 1) {
				profileButton.toggleSelection();
			}

			profilesList.add(profileButton);
		}

		this.scrollComposite = new ScrollComposite(profilesList, true, false);
		mainContentLayout.addAt(this.scrollComposite, MWT.CENTER);

		return mainContentLayout;
	}

	/**
	 * Creates a toggle button corresponding to a profile.
	 * 
	 * @param name
	 *            the name of the profile.
	 * @param picto
	 *            the picto of the profile.
	 * @param group
	 *            the button group of the button.
	 * @param label
	 *            the label to update if the profile is selected.
	 * @return the created toggle button.
	 */
	private static IconToggleButton createProfile(String name, char picto, ButtonGroup group, final TitleLabel label) {
		final IconToggleButton profile = new IconToggleButton(name, new Picto(picto, PictoSize.Big));
		profile.setListener(new ListenerAdapter() {
			@Override
			public void performAction(int value, Object object) {
				label.setText(profile.getText());
			}
		});
		group.add(profile);
		return profile;
	}

	@Override
	public void showNotify() {
		// Needs to prepare the scrollcomposite to refresh the view.
		this.scrollComposite.showNotify();
		super.showNotify();
	}

	@Override
	public void hideNotify() {
		// Stops all the behavior of the scrollcomposite.
		this.scrollComposite.hideNotify();
		super.hideNotify();
	}

}
