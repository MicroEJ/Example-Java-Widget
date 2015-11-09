/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import java.util.ArrayList;
import java.util.List;

import com.is2t.demo.widgets.Widgets;
import com.is2t.demo.widgets.style.ClassSelector;
import com.is2t.demo.widgets.style.Pictos;

import ej.composite.BorderComposite;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.widget.basic.Label;
import ej.widget.composed.Button;
import ej.widget.listener.OnClickListener;

/**
 * Main page of the application. It allows to access all the pages of the application. It also illustrates the #CheckBox
 * widget.
 */
public class MainPage extends ListPage {

	@Override
	protected String getTitle() {
		return "Widgets";
	}

	@Override
	protected char getPictoTitle() {
		return Pictos.SETTINGS;
	}

	@Override
	protected boolean canGoBack() {
		return false;
	}

	@Override
	protected List<Widget> getListElements() {
		List<Widget> widgets = new ArrayList<Widget>();

		widgets.add(newHeader("BASIC"));
		widgets.add(newSelectableItem("Checkbox", Pictos.VOLUME, new CheckboxPage()));
		widgets.add(newSelectableItem("Switch", Pictos.VOLUME, null));
		widgets.add(newSelectableItem("Slider", Pictos.VOLUME, null));
		widgets.add(newSelectableItem("Text", Pictos.VOLUME, null));
		widgets.add(newSelectableItem("Image", Pictos.VOLUME, null));
		widgets.add(newSelectableItem("Radio button", Pictos.VOLUME, null));
		widgets.add(newSelectableItem("Progress bar", Pictos.VOLUME, null));

		widgets.add(newHeader("COMPOSED"));
		widgets.add(newSelectableItem("Button", Pictos.VOLUME, null));

		return widgets;
	}

	private static Widget newHeader(String name) {
		Label header = new Label(name);
		header.addClassSelector(ClassSelector.MEDIUM_LABEL);
		return header;
	}

	private static Widget newItem(String name, char picto) {
		BorderComposite item = new BorderComposite();
		item.setHorizontal(true);
		Label icon = new Label(picto + "");
		icon.addClassSelector(ClassSelector.MEDIUM_ICON);
		item.add(icon, MWT.LEFT);

		Label label = new Label(name);
		item.add(label, MWT.CENTER);
		label.addClassSelector(ClassSelector.MEDIUM_LABEL);
		return item;
	}

	private Widget newSelectableItem(String name, char picto, final WidgetsPage destination) {
		Button button = new Button();
		button.setWidget(newItem(name, picto));
		button.addOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				Widgets.show(destination);
			}
		});
		return button;
	}
}
