/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
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
		wheelGroup.addChild(monthWheel);

		Choice dayChoice = new EndlessIntegerChoice(1, 31, 25);
		Wheel dayWheel = new Wheel(wheelGroup);
		dayWheel.setModel(dayChoice);
		wheelGroup.addChild(dayWheel);

		Choice yearChoice = new EndlessIntegerChoice(2000, 2025, 2017);
		Wheel yearWheel = new Wheel(wheelGroup);
		yearWheel.setModel(yearChoice);
		wheelGroup.addChild(yearWheel);

		setCenterChild(wheelGroup);
	}

}
