/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import com.is2t.demo.widgets.theme.Pictos;
import com.is2t.mwt.composites.GridComposite;
import com.is2t.mwt.widgets.LookExtension;
import com.is2t.mwt.widgets.label.TitleLabel;
import com.is2t.mwt.widgets.tiny.ButtonGroup;
import com.is2t.mwt.widgets.tiny.RadioButton;

import ej.mwt.Composite;
import ej.mwt.MWT;

public class LanguagePage extends WidgetsPage {

	@Override
	public AppPage getType() {
		return AppPage.ProfilePage;
	}

	@Override
	protected String getTitle() {
		return "Language";
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
		GridComposite mainContentLayout = new GridComposite(MWT.VERTICAL, 3);

		TitleLabel titleLabel = new TitleLabel("Set the language");
		titleLabel.setFontSize(LookExtension.GET_MEDIUM_FONT_INDEX);
		mainContentLayout.add(titleLabel);

		GridComposite radioButtonsLayout = new GridComposite(3);

		RadioButton englishRadio = new RadioButton("English");
		radioButtonsLayout.add(englishRadio);
		englishRadio.toggleSelection();

		RadioButton germanRadio = new RadioButton("Deutsch");
		radioButtonsLayout.add(germanRadio);

		RadioButton italianRadio = new RadioButton("Français");
		radioButtonsLayout.add(italianRadio);

		mainContentLayout.add(radioButtonsLayout);

		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(englishRadio);
		buttonGroup.add(germanRadio);
		buttonGroup.add(italianRadio);

		englishRadio.toggleSelection();

		return mainContentLayout;
	}
}
