/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.demo.widgets.page;

import com.microej.demo.widgets.style.ClassSelector;

import ej.composite.ListComposite;
import ej.composite.ScrollComposite;
import ej.mwt.Widget;
import ej.widget.basic.Label;

/**
 *
 */
public class ScrollableListPage extends WidgetsPage {

	private static final int ITEM_COUNT = 50;

	@Override
	protected String getTitle() {
		return "Scrollable list";
	}

	@Override
	protected boolean canGoBack() {
		return true;
	}

	@Override
	protected Widget createMainContent() {
		ListComposite listComposite = new ListComposite();
		listComposite.setHorizontal(false);

		for (int i = 1; i <= ITEM_COUNT; i++) {
			Label item = new Label("Item " + i);
			item.addClassSelector(ClassSelector.MEDIUM_LABEL);
			listComposite.add(item);
		}

		return new ScrollComposite(listComposite, true);
	}

}
