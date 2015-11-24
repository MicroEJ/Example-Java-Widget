/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.page;

import com.microej.demo.widgets.Widgets;

import ej.composite.ListComposite;
import ej.mwt.Widget;
import ej.widget.composed.Button;
import ej.widget.composed.SimpleButton;
import ej.widget.listener.OnClickListener;

/**
 * Main page of the application. It allows to access all the pages of the application.
 */
public class MainPage extends WidgetsPage {

	@Override
	protected String getTitle() {
		return "MicroEJ Widgets"; //$NON-NLS-1$
	}

	@Override
	protected boolean canGoBack() {
		return false;
	}

	@Override
	protected Widget createMainContent() {
		// layout:
		// Basic widgets - Picto
		// Basic widgets - Drawing
		// Progress bar
		// Scrollable list

		ListComposite listComposite = new ListComposite();
		listComposite.setHorizontal(false);
		listComposite.add(newSelectableItem("Basic widgets - Picto", new PictosPage())); //$NON-NLS-1$
		listComposite.add(newSelectableItem("Basic widgets - Drawing", new DrawingsPage())); //$NON-NLS-1$
		listComposite.add(newSelectableItem("Progress bar", new ProgressBarPage())); //$NON-NLS-1$
		listComposite.add(newSelectableItem("Scrollable list", new ScrollableListPage())); //$NON-NLS-1$
		return listComposite;
	}

	// A button that leads to the given destination.
	private Button newSelectableItem(String name, final WidgetsPage destination) {
		SimpleButton button = new SimpleButton(name);
		button.addOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				Widgets.show(destination);
			}
		});
		return button;
	}

	@Override
	protected boolean withMicroEJLogoInTopBar() {
		return true;
	}
}
