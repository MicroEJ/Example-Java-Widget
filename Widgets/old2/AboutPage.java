/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import com.is2t.demo.widgets.style.Pictos;
import com.is2t.widget.Label;
import com.is2t.widget.MultilineLabel;
import com.is2t.widget.composite.BorderComposite;
import com.is2t.widget.composite.ListComposite;

import ej.mwt.MWT;
import ej.mwt.Widget;

/**
 * Page illustrating the #MultiLineLabel widget and showing how to display some scrolling text.
 */
public class AboutPage extends WidgetsPage {

	private static final String HEADLINE_TEXT = "Designing embedded\nsoftware has never\nbeen this easy!";
	private static final String TEXT = "IS2T is the editor of the\nhighly-scalable MicroEJ\nsolution to prototype,\ndesign, and deploy\nsoftware applications on\nvarious hardware\nconfigurations.";
	private static final String VISIT_IS2T_WEBSITE = "Visit us at www.is2t.com";

	@Override
	protected Widget createMainContent() {
		BorderComposite mainContentLayout = new BorderComposite();

		ListComposite listComposite = new ListComposite(false);
		// Headline of the text.
		MultilineLabel headline = new MultilineLabel(HEADLINE_TEXT);
		listComposite.add(headline);
		// The main text.
		MultilineLabel textLabel = new MultilineLabel(TEXT);
		listComposite.add(textLabel);

		mainContentLayout.add(listComposite, MWT.CENTER);

		// Bottom bar.
		Label visitIs2tLabel = new Label(VISIT_IS2T_WEBSITE);
		mainContentLayout.add(visitIs2tLabel, MWT.BOTTOM);

		return mainContentLayout;
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
