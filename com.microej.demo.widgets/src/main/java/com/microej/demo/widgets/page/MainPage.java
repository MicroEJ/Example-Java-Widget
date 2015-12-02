/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.page;

import com.microej.demo.widgets.WidgetsDemo;
import com.microej.demo.widgets.style.ClassSelectors;

import ej.composite.ListComposite;
import ej.composite.ScrollComposite;
import ej.mwt.Widget;
import ej.widget.composed.Button;
import ej.widget.composed.SimpleButton;
import ej.widget.listener.OnClickListener;

/**
 * Main page of the application. It allows to access to all the pages of the application.
 */
public class MainPage extends AbstractDemoPage {

	@Override
	protected String getTitle() {
		return "MicroEJ Widgets"; //$NON-NLS-1$
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
		listComposite.add(newSelectableItem("Basic widgets - Picto", PictosPage.class.getName())); //$NON-NLS-1$
		listComposite.add(newSelectableItem("Basic widgets - Image", ImagesPage.class.getName())); //$NON-NLS-1$
		listComposite.add(newSelectableItem("Basic widgets - Drawing", DrawingsPage.class.getName())); //$NON-NLS-1$
		listComposite.add(newSelectableItem("Progress bar", ProgressBarPage.class.getName())); //$NON-NLS-1$
		listComposite.add(newSelectableItem("Scrollable list", ScrollableListPage.class.getName())); //$NON-NLS-1$
		listComposite.add(newSelectableItem("Scrollable text", ScrollableTextPage.class.getName())); //$NON-NLS-1$
		return new ScrollComposite(listComposite, true);
	}

	// A button that leads to the given page.
	private Button newSelectableItem(String name, final String url) {
		SimpleButton button = new SimpleButton(name);
		button.addClassSelector(ClassSelectors.LIST_ITEM);
		button.addOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				WidgetsDemo.show(url);
			}
		});
		return button;
	}

}
