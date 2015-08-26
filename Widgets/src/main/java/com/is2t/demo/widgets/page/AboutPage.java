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
import ej.widgets.widgets.label.MultiLineLabel;
import ej.widgets.widgets.label.TitleLabel;
import ej.widgets.widgets.scroll.ScrollComposite;

/**
 * Page illustrating the #MultiLineLabel widget and showing how to display some scrolling text.
 */
public class AboutPage extends WidgetsPage {

	private static final String HEADLINE_TEXT = "Designing embedded\nsoftware has never\nbeen this easy!";
	private static final String TEXT = "IS2T is the editor of the\nhighly-scalable MicroEJ\nsolution to prototype,\ndesign, and deploy\nsoftware applications on\nvarious hardware\nconfigurations.";
	private static final String VISIT_IS2T_WEBSITE = "Visit us at www.is2t.com";

	private ScrollComposite scrollComposite;

	@Override
	protected Widget createMainContent() {
		BorderComposite mainContentLayout = new BorderComposite();

		ListComposite listComposite = new ListComposite(false);
		// Headline of the text.
		MultiLineLabel headline = new MultiLineLabel(HEADLINE_TEXT);
		headline.setFontSize(LookExtension.GET_BOLD_SMALL_FONT_INDEX);
		listComposite.add(headline);
		// The main text.
		MultiLineLabel textLabel = new MultiLineLabel(TEXT);
		textLabel.setFontSize(LookExtension.GET_MEDIUM_FONT_INDEX);
		listComposite.add(textLabel);

		this.scrollComposite = new ScrollComposite(listComposite, false, true);
		mainContentLayout.addAt(this.scrollComposite, MWT.CENTER);

		// Bottom bar.
		TitleLabel visitIs2tLabel = new TitleLabel(VISIT_IS2T_WEBSITE);
		visitIs2tLabel.setFontSize(LookExtension.GET_BIG_FONT_INDEX);
		visitIs2tLabel.setOverlined(true);
		mainContentLayout.addAt(visitIs2tLabel, MWT.BOTTOM);

		return mainContentLayout;
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

	@Override
	protected String getTitle() {
		return "About IS2T";
	}

	@Override
	protected char getPictoTitle() {
		return Pictos.ABOUT;
	}

	@Override
	protected boolean canGoBack() {
		return true;
	}
}
