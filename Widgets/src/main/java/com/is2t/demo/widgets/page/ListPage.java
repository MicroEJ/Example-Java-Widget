/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import java.util.List;

import ej.composite.ListComposite;
import ej.composite.ScrollComposite;
import ej.mwt.Widget;

/**
 * Page allowing to display a list of widgets.
 */
public abstract class ListPage extends WidgetsPage {

	@Override
	protected Widget createMainContent() {
		// Formed with the elements of the list.
		ListComposite listComposite = new ListComposite();
		listComposite.setHorizontal(false);
		List<Widget> listContent = getListElements();

		for (Widget widget : listContent) {
			listComposite.add(widget);
		}

		return new ScrollComposite(listComposite, true);
	}

	/**
	 * Gets the widgets displayed in the list.
	 * 
	 * @return the widgets displayed in the list..
	 */
	protected abstract List<Widget> getListElements();
}
