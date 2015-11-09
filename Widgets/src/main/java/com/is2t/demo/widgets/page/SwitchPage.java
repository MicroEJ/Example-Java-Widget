/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import java.util.ArrayList;
import java.util.List;

import com.is2t.demo.widgets.style.ClassSelector;
import com.is2t.demo.widgets.style.Pictos;

import ej.composite.BorderComposite;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.widget.basic.Label;
import ej.widget.basic.Switch;

/**
 * Main page of the application. It allows to access all the pages of the application. It also illustrates the #CheckBox
 * widget.
 */
public class SwitchPage extends ListPage {

	@Override
	protected String getTitle() {
		return "Switch";
	}

	@Override
	protected char getPictoTitle() {
		return Pictos.SETTINGS;
	}

	@Override
	protected boolean canGoBack() {
		return true;
	}

	@Override
	protected List<Widget> getListElements() {
		List<Widget> options = new ArrayList<Widget>();

		options.add(newItemWithSwitch("Option 1", false));
		options.add(newItemWithSwitch("Option 2", false));
		options.add(newItemWithSwitch("Option 3", true));

		return options;
	}

	private static Widget newItemWithSwitch(String text, boolean checked) {
		BorderComposite item = new BorderComposite();
		item.setHorizontal(true);
		Label label = new Label(text);
		label.addClassSelector(ClassSelector.MEDIUM_LABEL);
		item.add(label, MWT.CENTER);
		Switch switch_ = new Switch(checked);
		item.add(switch_, MWT.RIGHT);
		return item;
	}
}
