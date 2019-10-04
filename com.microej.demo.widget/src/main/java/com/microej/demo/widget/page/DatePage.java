/*
 * Java
 *
 * Copyright  2015-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package com.microej.demo.widget.page;

import com.microej.demo.widget.style.ClassSelectors;

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

	/**
	 * Creates a date page.
	 */
	public DatePage() {
		super(false, "Wheel"); //$NON-NLS-1$

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

		setCenter(wheelGroup);
	}

}