/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import java.util.ArrayList;
import java.util.List;

import com.is2t.demo.widgets.style.Pictos;

import ej.composite.BorderComposite;
import ej.composite.SplitComposite;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.widget.Label;
import ej.widget.Picto;
import ej.widget.Slider;

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
		List<Widget> elements = new ArrayList<>();
		elements.add(createVolumeItem("Game", Pictos.GAME, INTIAL_GAME_VOLUME));
		elements.add(createVolumeItem("Alarm", Pictos.ALARM, INITIAL_ALARM_VOLUME));
		elements.add(createVolumeItem("Ring", Pictos.SECURITY, INITIAL_RING_VOLUME));
		return elements;
	}

	private static Widget createVolumeItem(String name, char picto, int initialVolume) {
		SplitComposite splitComposite = new SplitComposite();
		splitComposite.setHorizontal(true);
		splitComposite.setRatio(ITEM_WITH_SCALE_RATIO);

		Picto icon = new Picto(picto);
		Label volumeLabel = new Label(name);
		BorderComposite label = new BorderComposite();
		label.add(icon, MWT.WEST);
		label.add(volumeLabel, MWT.CENTER);
		splitComposite.add(label);

		Slider volume = new Slider(0, MAX_VOLUME, initialVolume);
		splitComposite.add(volume);
		return splitComposite;
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
	// private static Widget createItemWithScale(String text, char picto, boolean overlined, int initial) {
	// SplitComposite layout = new SplitComposite();
	// layout.setHorizontal(true);
	// layout.setRatio(ITEM_WITH_SCALE_RATIO);
	//
	// LeftIconLabel item = createItem(text, picto, overlined);
	// layout.add(item);
	//
	// LinedScale scale = new LinedScale(0, MAX_VOLUME);
	// scale.setValue(initial);
	// scale.setOverlined(overlined);
	// layout.add(scale);
	//
	// return layout;
	// }
}
