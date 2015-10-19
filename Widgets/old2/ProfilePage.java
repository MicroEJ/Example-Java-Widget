/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import com.is2t.demo.widgets.style.Pictos;
import com.is2t.widget.Label;
import com.is2t.widget.RadioButton;
import com.is2t.widget.ToggleButtonGroup;
import com.is2t.widget.composite.BorderComposite;
import com.is2t.widget.composite.ListComposite;

import ej.microui.display.GraphicsContext;
import ej.mwt.MWT;
import ej.mwt.Widget;

/**
 * Page illustrating the #RadioButton widget.
 */
public class ProfilePage extends WidgetsPage {

	@Override
	protected String getTitle() {
		return "Profile";
	}

	@Override
	protected char getPictoTitle() {
		return Pictos.LANGUAGE_AND_INPUT;
	}

	@Override
	protected boolean canGoBack() {
		return true;
	}

	@Override
	protected Widget createMainContent() {
		BorderComposite mainContentLayout = new BorderComposite();

		Label titleLabel = new Label("Set the profile");
		mainContentLayout.add(titleLabel, MWT.NORTH);

		ListComposite radioButtonsLayout = new ListComposite(false);

		RadioButton defaultRadio = new RadioButton("Default", true);
		defaultRadio.setHorizontalAlignment(GraphicsContext.LEFT);
		radioButtonsLayout.add(defaultRadio);

		RadioButton silentRadio = new RadioButton("Silent", false);
		silentRadio.setHorizontalAlignment(GraphicsContext.LEFT);
		radioButtonsLayout.add(silentRadio);

		RadioButton officeRadio = new RadioButton("Office", false);
		officeRadio.setHorizontalAlignment(GraphicsContext.LEFT);
		radioButtonsLayout.add(officeRadio);

		mainContentLayout.add(radioButtonsLayout, MWT.CENTER);

		// Guarantees only on radio button can be selected.
		ToggleButtonGroup buttonGroup = new ToggleButtonGroup();
		buttonGroup.addButton(defaultRadio);
		buttonGroup.addButton(silentRadio);
		buttonGroup.addButton(officeRadio);

		return mainContentLayout;
		// return radioButtonsLayout;
	}
}
