/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import java.util.ArrayList;
import java.util.List;

import com.is2t.demo.widgets.widget.LinedScale;
import com.is2t.demo.widgets.widget.SplitComposite;
import com.is2t.demo.widgets.widget.theme.Pictos;

import ej.mwt.Widget;
import ej.widgets.widgets.label.LeftIconLabel;

/**
 * Page illustrating the scale widget.
 */
public class VolumePage extends ListSettingsPage {

	private static final float ITEM_WITH_SCALE_RATIO = 0.45f;
	private static final int MAX_VOLUME = 100;
	private static final int INITIAL_RING_VOLUME = 33;
	private static final int INITIAL_ALARM_VOLUME = 15;
	private static final int INTIAL_GAME_VOLUME = 50;

	@Override
	protected String getTitle() {
		return "Volume";
	}

	@Override
	protected char getPictoTitle() {
		return Pictos.VOLUME;
	}

	@Override
	protected boolean canGoBack() {
		return true;
	}

	@Override
	protected List<Widget> getListElements() {
		List<Widget> widgets = new ArrayList<Widget>();

		widgets.add(createItemWithScale("Game", Pictos.GAME, true, INTIAL_GAME_VOLUME));
		widgets.add(createItemWithScale("Alarm", Pictos.ALARM, true, INITIAL_ALARM_VOLUME));
		widgets.add(createItemWithScale("Ring", Pictos.SECURITY, true, INITIAL_RING_VOLUME));

		return widgets;
	}

	/**
	 * Allows to create a scale widget with a text.
	 * 
	 * @param text
	 *            the text of the item.
	 * @param picto
	 *            the picto of the item.
	 * @param overlined
	 *            indicates whether or not the item has to be overlined.
	 * @param initial
	 *            the initial value of the scale.
	 * @return the created item.
	 */
	private static Widget createItemWithScale(String text, char picto, boolean overlined, int initial) {
		SplitComposite layout = new SplitComposite();
		layout.setHorizontal(true);
		layout.setRatio(ITEM_WITH_SCALE_RATIO);

		LeftIconLabel item = createItem(text, picto, overlined);
		layout.add(item);

		LinedScale scale = new LinedScale(0, MAX_VOLUME);
		scale.setValue(initial);
		scale.setOverlined(overlined);
		layout.add(scale);

		return layout;
	}
}
