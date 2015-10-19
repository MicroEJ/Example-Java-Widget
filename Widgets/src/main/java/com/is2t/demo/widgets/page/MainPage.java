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
import ej.composite.SplitComposite;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.widget.Button;
import ej.widget.Label;
import ej.widget.Picto;
import ej.widget.listener.OnClickListener;

/**
 * Main page of the application. It allows to access all the pages of the application. It also illustrates the #CheckBox
 * widget.
 */
public class MainPage extends ListSettingsPage {

	@Override
	protected String getTitle() {
		return "Settings";
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

		widgets.add(createSelectableItem("About IS2T", Pictos.ABOUT, null));

		widgets.add(new Label("SYSTEM"));
		widgets.add(createSelectableItem("Date & time", Pictos.DATE_AND_TIME, null));
		widgets.add(createSelectableItem("Volume", Pictos.VOLUME, null));
		widgets.add(createSelectableItem("Profile", Pictos.LANGUAGE_AND_INPUT, null));

		widgets.add(new Label("WIRELESS & NETWORKS"));
		widgets.add(createItemWithSwitch("Wi-Fi", Pictos.WIFI, true));
		widgets.add(createItemWithSwitch("Bluetooth", Pictos.BLUETOOTH, true));

		widgets.add(new Label("PERSONAL"));
		widgets.add(createItemWithCheckBox("Location data", Pictos.LOCATION, true));
		widgets.add(createSelectableItem("Security", Pictos.SECURITY, null));
		widgets.add(createSelectableItem("Battery profile", Pictos.BATTERY_PROFILE, null));

		return widgets;
	}

	private static Widget createItemWithCheckBox(String name, char picto, boolean checked) {
		SplitComposite item = new SplitComposite();
		item.setHorizontal(true);
		item.setRatio(0.5f);
		Picto icon = new Picto(picto);
		icon.addClassSelector(ClassSelector.SMALL_ICON);
		Label label = new Label(name);
		item.add(label);
		// CheckBox checkBox = new CheckBox();
		// checkBox.setChecked(checked);
		// item.add(checkBox);
		return item;
	}

	private static Widget createItemWithSwitch(String name, char picto, boolean on) {
		SplitComposite item = new SplitComposite();
		item.setHorizontal(true);
		item.setRatio(0.5f);
		Picto icon = new Picto(picto);
		icon.addClassSelector(ClassSelector.SMALL_ICON);
		Label label = new Label(name);
		item.add(label);
		// Switch sswitch = new Switch();
		// sswitch.setChecked(on);
		// item.add(sswitch);
		return item;
	}

	private Widget createSelectableItem(String name, char picto, final WidgetsPage destination) {
		// Pic icon = createItemIcon(picto);

		Button button = new Button();
		BorderComposite buttonContent = new BorderComposite();
		Picto icon = new Picto(picto);
		icon.addClassSelector(ClassSelector.SMALL_ICON);
		buttonContent.add(icon, MWT.WEST);
		buttonContent.add(new Label(name), MWT.CENTER);
		button.setWidget(buttonContent);
		button.addOnClickListener(new OnClickListener() {

			@Override
			public void onClick() {
				// goTo(destination);
			}
		});
		return button;
	}
}
