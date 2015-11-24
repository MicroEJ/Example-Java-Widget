/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.demo.widgets.page;

import ej.composite.ListComposite;
import ej.composite.ScrollComposite;
import ej.mwt.Widget;
import ej.widget.basic.Label;

/**
 * This page illustrates the scrollable list.
 */
public class ScrollableListPage extends WidgetsPage {

	private static final int ITEM_COUNT = 50;

	@Override
	protected String getTitle() {
		return "Scrollable list"; //$NON-NLS-1$
	}

	@Override
	protected boolean canGoBack() {
		return true;
	}

	@Override
	protected Widget createMainContent() {
		// layout:
		// Item 1
		// Item 2
		// ...
		// Item n-1
		// Item n

		ListComposite listComposite = new ListComposite();
		listComposite.setHorizontal(false);

		for (int i = 1; i <= ITEM_COUNT; i++) {
			Label item = new Label("Item " + i); //$NON-NLS-1$
			listComposite.add(item);
		}

		return new ScrollComposite(listComposite, true);
	}

}
