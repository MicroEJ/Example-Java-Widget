/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import java.util.List;

import ej.mwt.Composite;
import ej.mwt.Widget;
import ej.widgets.widgets.ListComposite;
import ej.widgets.widgets.LookExtension;
import ej.widgets.widgets.Picto;
import ej.widgets.widgets.Picto.PictoSize;
import ej.widgets.widgets.label.LeftIconLabel;
import ej.widgets.widgets.scroll.ScrollComposite;

/**
 * Page allowing to display a list of widgets.
 */
public abstract class ListSettingsPage extends WidgetsPage {

	private ScrollComposite scrollComposite;

	@Override
	protected Widget createMainContent() {
		// Formed with the elements of the list.
		boolean horizontal = false;
		Composite listComposite = new ListComposite(horizontal);
		List<Widget> listContent = getListElements();

		for (Widget widget : listContent) {
			listComposite.add(widget);
		}

		this.scrollComposite = new ScrollComposite(listComposite, horizontal, false);
		return this.scrollComposite;
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

	/**
	 * Gets the widgets displayed in the list.
	 * 
	 * @return the widgets displayed in the list..
	 */
	protected abstract List<Widget> getListElements();

	/**
	 * Allows to create a widget with a text.
	 * 
	 * @param text
	 *            the text of the item.
	 * @param picto
	 *            the picto of the item.
	 * @param overlined
	 *            indicates whether or not the item has to be overlined.
	 * 
	 * @return the created item.
	 */
	protected static LeftIconLabel createItem(String text, char picto, boolean overlined) {
		LeftIconLabel item = new LeftIconLabel(text, new Picto(picto, PictoSize.Small));
		item.setFontSize(LookExtension.GET_MEDIUM_FONT_INDEX);
		item.setOverlined(overlined);
		return item;
	}
}
