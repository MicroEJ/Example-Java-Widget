/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import com.is2t.demo.widgets.theme.Pictos;
import com.is2t.mwt.composites.BorderComposite;
import com.is2t.mwt.widgets.ListComposite;
import com.is2t.mwt.widgets.LookExtension;
import com.is2t.mwt.widgets.label.TitleLabel;
import com.is2t.mwt.widgets.scroll.ScrollComposite;
import com.is2t.mwt.widgets.tiny.ButtonGroup;
import com.is2t.mwt.widgets.tiny.RadioButton;

import ej.mwt.Composite;
import ej.mwt.MWT;

public class ProfilePage extends WidgetsPage {

	@Override
	public AppPage getType() {
		return AppPage.ProfilePage;
	}

	@Override
	protected String getTitle() {
		return "Profile";
	}

	@Override
	protected char getPictoTitle() {
		return Pictos.LanguageAndInput;
	}

	@Override
	protected boolean canGoBack() {
		return true;
	}

	@Override
	protected Composite createMainContent() {
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

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(englishRadio);
		buttonGroup.add(germanRadio);
		buttonGroup.add(italianRadio);

		englishRadio.toggleSelection();

		return mainContentLayout;
	}
}
