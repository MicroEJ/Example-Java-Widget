/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import com.is2t.demo.widgets.widget.theme.Pictos;

import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.widgets.composites.BorderComposite;
import ej.widgets.widgets.ListComposite;
import ej.widgets.widgets.LookExtension;
import ej.widgets.widgets.label.TitleLabel;
import ej.widgets.widgets.scroll.ScrollComposite;
import ej.widgets.widgets.tiny.ButtonGroup;
import ej.widgets.widgets.tiny.RadioButton;

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

		TitleLabel titleLabel = new TitleLabel("Set the profile");
		titleLabel.setFontSize(LookExtension.GET_MEDIUM_FONT_INDEX);
		mainContentLayout.addAt(titleLabel, MWT.NORTH);

		ListComposite radioButtonsLayout = new ListComposite(false);

		RadioButton englishRadio = new RadioButton("Default");
		radioButtonsLayout.add(englishRadio);
		englishRadio.toggleSelection();

		RadioButton germanRadio = new RadioButton("Silent");
		radioButtonsLayout.add(germanRadio);

		RadioButton italianRadio = new RadioButton("Office");
		radioButtonsLayout.add(italianRadio);

		mainContentLayout.addAt(new ScrollComposite(radioButtonsLayout, false, false), MWT.CENTER);

		// Guarantees only on radio button can be selected.
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(englishRadio);
		buttonGroup.add(germanRadio);
		buttonGroup.add(italianRadio);

		// Default selection.
		englishRadio.toggleSelection();

		return mainContentLayout;
	}
}
