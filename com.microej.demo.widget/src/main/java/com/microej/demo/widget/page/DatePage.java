/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widget.page;

import com.microej.demo.widget.style.ClassSelectors;

import ej.mwt.Widget;
import ej.widget.wheel.Choice;
import ej.widget.wheel.EndlessIntegerChoice;
import ej.widget.wheel.EndlessStringChoice;
import ej.widget.wheel.Wheel;
import ej.widget.wheel.WheelGroup;

/**
 * This page illustrates a date picker.
 */
public class DatePage extends AbstractDemoPage {

	private static final int WHEEL_SIDES = 2;
	private static final int MAX_ACTIVE_WHEELS = 2;

	@SuppressWarnings("nls")
	private static final String[] MONTHS = { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };

	@Override
	protected String getTitle() {
		return "Wheel"; //$NON-NLS-1$
	}

	@Override
	protected Widget createMainContent() {
		WheelGroup wheelGroup = new WheelGroup(WHEEL_SIDES, MAX_ACTIVE_WHEELS);
		wheelGroup.addClassSelector(ClassSelectors.DATE_PICKER);

		Choice monthChoice = new EndlessStringChoice(MONTHS, 11);
		Wheel monthWheel = new Wheel(wheelGroup);
		monthWheel.setModel(monthChoice);
		wheelGroup.add(monthWheel);

		Choice dayChoice = new EndlessIntegerChoice(1, 31, 25);
		Wheel dayWheel = new Wheel(wheelGroup);
		dayWheel.setModel(dayChoice);
		wheelGroup.add(dayWheel);

		Choice yearChoice = new EndlessIntegerChoice(2000, 2025, 2017);
		Wheel yearWheel = new Wheel(wheelGroup);
		yearWheel.setModel(yearChoice);
		wheelGroup.add(yearWheel);

		return wheelGroup;
	}
}
