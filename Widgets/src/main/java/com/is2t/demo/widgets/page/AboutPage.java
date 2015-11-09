/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import com.is2t.demo.widgets.style.ClassSelector;
import com.is2t.demo.widgets.style.Pictos;

import ej.composite.BorderComposite;
import ej.composite.ListComposite;
import ej.composite.ScrollComposite;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.widget.basic.Label;

/**
 * Page illustrating the #MultiLineLabel widget and showing how to display some scrolling text.
 */
public class AboutPage extends WidgetsPage {

	private static final String HEADLINE_TEXT = "Designing embedded software has never been this easy!";
	private static final String TEXT = "IS2T is the editor of the highly-scalable MicroEJ solution to prototype, design, and deploy software applications on various hardware configurations.";
	private static final String VISIT_IS2T_WEBSITE = "Visit us at www.is2t.com";

	@Override
	protected Widget createMainContent() {
		BorderComposite mainContentLayout = new BorderComposite();
		mainContentLayout.setHorizontal(false);

		ListComposite listComposite = new ListComposite();
		listComposite.setHorizontal(false);

		// Headline of the text.
		Label headline = new Label(HEADLINE_TEXT);
		headline.addClassSelector(ClassSelector.MEDIUM_LABEL);
		listComposite.add(headline);
		// The main text.
		Label textLabel = new Label(TEXT);
		textLabel.addClassSelector(ClassSelector.MEDIUM_LABEL);
		listComposite.add(textLabel);

		mainContentLayout.add(new ScrollComposite(listComposite, true), MWT.CENTER);

		// Bottom bar.
		Label visitIs2tLabel = new Label(VISIT_IS2T_WEBSITE);
		visitIs2tLabel.addClassSelector(ClassSelector.LARGE_LABEL);
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
