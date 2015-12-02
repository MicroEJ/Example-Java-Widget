/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.page;

import com.microej.demo.widgets.style.ClassSelectors;

import ej.composite.ListComposite;
import ej.composite.ScrollComposite;
import ej.mwt.Widget;
import ej.widget.basic.Label;

/**
 * This page illustrates the scrollable list.
 */
public class ScrollableListPage extends AbstractDemoPage {

	private static final int ITEM_COUNT = 100;

	@Override
	protected String getTitle() {
		return "Scrollable list"; //$NON-NLS-1$
	}

	@Override
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
			item.addClassSelector(ClassSelectors.LIST_ITEM);
		}

		return new ScrollComposite(listComposite, true);
	}

}
