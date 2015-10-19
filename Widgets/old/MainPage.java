/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import java.util.ArrayList;
import java.util.List;

import com.is2t.demo.widgets.widget.LeftIconLabelButton;
import com.is2t.demo.widgets.widget.LinedToggleButton;
import com.is2t.demo.widgets.widget.theme.Pictos;

import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.widgets.composites.BorderComposite;
import ej.widgets.util.ListenerAdapter;
import ej.widgets.widgets.LookExtension;
import ej.widgets.widgets.Picto;
import ej.widgets.widgets.Picto.PictoSize;
import ej.widgets.widgets.label.HeadlineLabel;
import ej.widgets.widgets.label.LeftIconLabel;
import ej.widgets.widgets.tiny.CheckBox;

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

		widgets.add(createSelectableItem("About IS2T", Pictos.ABOUT, false, new AboutPage()));

		widgets.add(createHeadlineLabel("SYSTEM"));
		widgets.add(createSelectableItem("Date & time", Pictos.DATE_AND_TIME, false, new DateTimePage()));
		widgets.add(createSelectableItem("Volume", Pictos.VOLUME, true, new VolumePage()));
		widgets.add(createSelectableItem("Profile", Pictos.LANGUAGE_AND_INPUT, true, new ProfilePage()));

		widgets.add(createHeadlineLabel("WIRELESS & NETWORKS"));
		widgets.add(createItemWithToggleButton("Wi-Fi", Pictos.WIFI, false, true));
		widgets.add(createItemWithToggleButton("Bluetooth", Pictos.BLUETOOTH, true, false));

		widgets.add(createHeadlineLabel("PERSONAL"));
		widgets.add(createItemWithCheckbox("Location data", Pictos.LOCATION, false, true));
		widgets.add(createSelectableItem("Security", Pictos.SECURITY, true, new SecurityPage()));
		widgets.add(createSelectableItem("Battery profile", Pictos.BATTERY_PROFILE, true, new BatteryProfilePage()));

		return widgets;
	}

	/**
	 * Allows to create a headline widget with a text.
	 * 
	 * @param text
	 *            the text of the item.
	 * 
	 * @return the created item.
	 */
	protected static HeadlineLabel createHeadlineLabel(String text) {
		HeadlineLabel headlineLabel = new HeadlineLabel(text, "", 2);
		headlineLabel.setHeadlineFontSize(LookExtension.GET_MEDIUM_FONT_INDEX);
		headlineLabel.setUnderlined(true);
		return headlineLabel;
	}

	/**
	 * Allows to create a selectable widget with a text.
	 * 
	 * @param text
	 *            the text of the item.
	 * @param picto
	 *            the picto of the item.
	 * @param overlined
	 *            indicates whether or not the item has to be overlined.
	 * 
	 * @param destination
	 *            the destination page after clicking on the item.
	 * @return the created item.
	 */
	protected LeftIconLabelButton createSelectableItem(String text, char picto, boolean overlined,
			final WidgetsPage destination) {
		LeftIconLabelButton item = new LeftIconLabelButton(text, new Picto(picto, PictoSize.Small));
		item.setFontSize(LookExtension.GET_MEDIUM_FONT_INDEX);
		item.setOverlined(overlined);
		item.setListener(new ListenerAdapter() {

			@Override
			public void performAction(int value, Object object) {
				getTransitionManager().goTo(destination);
			}
		});
		return item;
	}

	/**
	 * Allows to create a widget with a text and a toggle button.
	 * 
	 * @param text
	 *            the text of the item.
	 * @param picto
	 *            the picto of the item.
	 * @param overlined
	 *            indicates whether or not the item has to be overlined.
	 * @param initial
	 *            the initial value of the checkbox. True for selected and false for not not selected.
	 * @return the created item.
	 */
	protected static Widget createItemWithToggleButton(String text, char picto, boolean overlined, boolean initial) {
		BorderComposite layout = new BorderComposite();

		LeftIconLabel item = createItem(text, picto, overlined);
		layout.add(item);

		LinedToggleButton toggleButton = new LinedToggleButton();
		toggleButton.setOverlined(overlined);

		if (initial) {
			toggleButton.toggleSelection();
		}

		layout.addAt(toggleButton, MWT.EAST);

		return layout;
	}

	/**
	 * Allows to create a widget with a text and a checkbox.
	 * 
	 * @param text
	 *            the text of the item.
	 * @param picto
	 *            the picto of the item.
	 * @param overlined
	 *            indicates whether or not the item has to be overlined.
	 * @param initial
	 *            the initial value of the checkbox. True for selected and false for not not selected.
	 * @return the created item.
	 */
	protected static Widget createItemWithCheckbox(String text, char picto, boolean overlined, boolean initial) {
		BorderComposite layout = new BorderComposite();

		LeftIconLabel item = createItem(text, picto, overlined);
		layout.add(item);

		CheckBox checkbox = new CheckBox();

		if (initial) {
			checkbox.toggleSelection();
		}

		layout.addAt(checkbox, MWT.EAST);

		return layout;
	}
}
