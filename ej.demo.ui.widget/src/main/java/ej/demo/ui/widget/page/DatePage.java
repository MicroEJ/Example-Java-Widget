/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package ej.demo.ui.widget.page;

import ej.demo.ui.widget.style.ClassSelectors;
import ej.mwt.Widget;
import ej.widget.container.Grid;
import ej.widget.wheel.Choice;
import ej.widget.wheel.EndlessIntegerChoice;
import ej.widget.wheel.EndlessStringChoice;
import ej.widget.wheel.Wheel;

/**
 * This page illustrates a date picker.
 */
public class DatePage extends AbstractDemoPage {

	private static final int WHEEL_SIDES = 2;

	@SuppressWarnings("nls")
	private static final String[] MONTHS = { "January", "February", "March", "April", "May", "June", "July", "August",
			"September", "October", "November", "December" };

	@Override
	protected String getTitle() {
		return "Wheel"; //$NON-NLS-1$
	}

	@Override
	protected Widget createMainContent() {
		Grid datePicker = new Grid(true, 3);
		datePicker.addClassSelector(ClassSelectors.DATE_PICKER);

		Choice monthChoice = new EndlessStringChoice(MONTHS, 11);
		Wheel monthWheel = new Wheel(WHEEL_SIDES);
		monthWheel.setModel(monthChoice);
		datePicker.add(monthWheel);

		Choice dayChoice = new EndlessIntegerChoice(1, 31, 25);
		Wheel dayWheel = new Wheel(WHEEL_SIDES);
		dayWheel.setModel(dayChoice);
		datePicker.add(dayWheel);

		Choice yearChoice = new EndlessIntegerChoice(2000, 2025, 2017);
		Wheel yearWheel = new Wheel(WHEEL_SIDES);
		yearWheel.setModel(yearChoice);
		datePicker.add(yearWheel);

		return datePicker;
	}
}
