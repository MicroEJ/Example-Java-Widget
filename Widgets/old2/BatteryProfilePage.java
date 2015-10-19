/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import com.is2t.demo.widgets.style.Pictos;
import com.is2t.widget.Button;
import com.is2t.widget.Icon;
import com.is2t.widget.Label;
import com.is2t.widget.OnClickListener;
import com.is2t.widget.PictoIcon;
import com.is2t.widget.composite.BorderComposite;
import com.is2t.widget.composite.ListComposite;

import ej.microui.display.Colors;
import ej.microui.display.DisplayFont;
import ej.mwt.MWT;
import ej.mwt.Widget;

/**
 * Page illustrating the the horizontal scrollable list.
 */
public class BatteryProfilePage extends WidgetsPage {

	private static final int PROFILE_COUNT = 10;
	private static final char[] AVAILABLE_PICTOS = new char[] { Pictos.DATABASE, Pictos.BOX, Pictos.HOME_GARAGE,
			Pictos.FAN, Pictos.BLACK_LIGHT, Pictos.LIGHTNING };

	// private ScrollComposite scrollComposite;

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
		BorderComposite mainContent = new BorderComposite();

		// The label thats show the selected profile.
		final Label selectedProfilelabel = new Label("This is a label");
		mainContent.add(selectedProfilelabel, MWT.NORTH);

		// The list of profiles.
		ListComposite profilesList = new ListComposite(true);
		mainContent.add(profilesList, MWT.CENTER);

		int availablePictosCount = AVAILABLE_PICTOS.length;

		for (int profileIndex = 1; profileIndex <= PROFILE_COUNT; profileIndex++) {
			final String name = "Profile " + profileIndex;
			int pictoIndex = profileIndex % availablePictosCount;
			char picto = AVAILABLE_PICTOS[pictoIndex];
			Icon icon = createProfileIcon(picto);
			Button profileButton = new Button("b", icon);
			// Button profileButton = new Button(name);
			profileButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(Object source) {
					selectedProfilelabel.setText(name);
				}
			});
			profilesList.add(profileButton);
		}

		// this.scrollComposite = new ScrollComposite(profilesList, true, false);
		// mainContentLayout.addAt(this.scrollComposite, MWT.CENTER);

		return mainContent;
	}

	protected static Icon createProfileIcon(char picto) {
		DisplayFont pictoFont = DisplayFont.getFont(81, 75, DisplayFont.STYLE_PLAIN);
		return new PictoIcon(picto, pictoFont, Colors.BLACK);
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
	// private static IconToggleButton createProfile(String name, char picto, ButtonGroup group, final TitleLabel label)
	// {
	// final IconToggleButton profile = new IconToggleButton(name, new Picto(picto, PictoSize.Big));
	// profile.setListener(new ListenerAdapter() {
	// @Override
	// public void performAction(int value, Object object) {
	// label.setText(profile.getText());
	// }
	// });
	// group.add(profile);
	// return profile;
	// }
	//
	// @Override
	// public void showNotify() {
	// // Needs to prepare the scrollcomposite to refresh the view.
	// this.scrollComposite.showNotify();
	// super.showNotify();
	// }
	//
	// @Override
	// public void hideNotify() {
	// // Stops all the behavior of the scrollcomposite.
	// this.scrollComposite.hideNotify();
	// super.hideNotify();
	// }

}
