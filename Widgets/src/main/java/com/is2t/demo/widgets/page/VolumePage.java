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
import ej.widget.basic.Label;
import ej.widget.basic.Slider;

/**
 * Page illustrating the scale widget.
 */
public class VolumePage extends ListPage {

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
		elements.add(newItemWithSlider("Game", Pictos.GAME, INTIAL_GAME_VOLUME));
		elements.add(newItemWithSlider("Alarm", Pictos.ALARM, INITIAL_ALARM_VOLUME));
		elements.add(newItemWithSlider("Ring", Pictos.SECURITY, INITIAL_RING_VOLUME));
		return elements;
	}

	/**
	 * Allows to create a slider widget with a text and a picto.
	 * 
	 * @param text
	 *            the text of the item.
	 * @param picto
	 *            the picto of the item.
	 * @param initial
	 *            the initial value of the slider.
	 * @return the item.
	 */
	private static Widget newItemWithSlider(String name, char picto, int initialValue) {
		SplitComposite splitComposite = new SplitComposite();
		splitComposite.setHorizontal(true);
		splitComposite.setRatio(ITEM_WITH_SCALE_RATIO);

		BorderComposite leftPartItem = new BorderComposite();
		Label icon = new Label(picto + "");
		icon.addClassSelector(ClassSelector.MEDIUM_ICON);
		leftPartItem.add(icon, MWT.LEFT);

		Label label = new Label(name);
		label.addClassSelector(ClassSelector.MEDIUM_LABEL);
		leftPartItem.add(label, MWT.CENTER);
		splitComposite.add(leftPartItem);

		Slider slider = new Slider(0, MAX_VOLUME, initialValue);
		splitComposite.add(slider);
		return splitComposite;
	}
}
