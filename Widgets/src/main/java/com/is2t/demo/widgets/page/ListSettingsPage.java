/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import java.util.List;

import ej.composite.GridComposite;
import ej.mwt.Widget;

/**
 * Page allowing to display a list of widgets.
 */
public abstract class ListSettingsPage extends WidgetsPage {

	@Override
	protected Widget createMainContent() {
		// Formed with the elements of the list.
		boolean horizontal = false;
		GridComposite listComposite = new GridComposite();
		listComposite.setHorizontal(horizontal);
		List<Widget> listContent = getListElements();
		listComposite.setCount(listContent.size());

		for (Widget widget : listContent) {
			listComposite.add(widget);
		}

		return listComposite;
	}

	/**
	 * Gets the widgets displayed in the list.
	 * 
	 * @return the widgets displayed in the list..
	 */
	protected abstract List<Widget> getListElements();
}
