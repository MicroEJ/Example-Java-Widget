/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import com.is2t.demo.widgets.Widgets;
import com.is2t.demo.widgets.style.ClassSelector;

import ej.composite.ListComposite;
import ej.mwt.Widget;
import ej.widget.basic.Label;
import ej.widget.composed.Button;
import ej.widget.listener.OnClickListener;

/**
 * Main page of the application. It allows to access all the pages of the application. It also illustrates the #CheckBox
 * widget.
 */
public class MainPage extends WidgetsPage {

	@Override
	protected String getTitle() {
		return "MicroEJ Widgets";
	}

	@Override
	protected boolean canGoBack() {
		return false;
	}

	@Override
	protected Widget createMainContent() {
		ListComposite listComposite = new ListComposite();
		listComposite.setHorizontal(false);
		listComposite.add(newSelectableItem("Basic widgets - Pictos", new PictosPage()));
		listComposite.add(newSelectableItem("Basic widgets - Drawings", new DrawingsPage()));
		listComposite.add(newSelectableItem("Progress bars", new ProgressBarPage()));
		listComposite.add(newSelectableItem("Scrollable list", new ScrollableListPage()));
		return listComposite;
	}

	private Widget newSelectableItem(String name, final WidgetsPage destination) {
		Button button = new Button();
		Label label = new Label(name);
		label.addClassSelector(ClassSelector.MEDIUM_LABEL);
		button.setWidget(label);
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
