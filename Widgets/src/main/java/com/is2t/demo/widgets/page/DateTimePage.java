/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.page;

import java.util.Calendar;

import com.is2t.demo.widgets.widget.theme.Pictos;

import ej.bon.Util;
import ej.mwt.MWT;
import ej.mwt.Widget;
import ej.widgets.composites.BorderComposite;
import ej.widgets.widgets.spinner.MultipleSpinner;
import ej.widgets.widgets.spinner.Value;
import ej.widgets.widgets.spinner.ValueListener;
import ej.widgets.widgets.spinner.int_.EndlessIntegerValue;
import ej.widgets.widgets.spinner.int_.IntegerWheel;

/**
 * Page illustrating the #MultipleSpinner widget.
 */
public class DateTimePage extends WidgetsPage {

	private static final int MAX_DAY_IN_MONTH = 31;
	private static final int MONTHS_IN_YEAR = 12;
	private static final int MIN_YEAR = 1970;
	private static final int MAX_YEAR = 2100;

	@Override
	protected boolean canGoBack() {
		return true;
	}

	@Override
	protected Widget createMainContent() {
		BorderComposite dateLayout = new BorderComposite(MWT.VERTICAL);

		// Creates the date spinner.
		MultipleSpinner dateSpinner = new MultipleSpinner();
		Calendar calendar = Calendar.getInstance();
		IntegerWheel dayWheel = createCalendarWheel(Calendar.DAY_OF_MONTH, 1, MAX_DAY_IN_MONTH,
				calendar.get(Calendar.DAY_OF_MONTH));
		dateSpinner.add(dayWheel);
		IntegerWheel monthWheel = createCalendarWheel(Calendar.MONTH, 1, MONTHS_IN_YEAR,
				calendar.get(Calendar.MONTH) + 1);
		dateSpinner.add(monthWheel);
		IntegerWheel yearWheel = createCalendarWheel(Calendar.YEAR, MIN_YEAR, MAX_YEAR, calendar.get(Calendar.YEAR));
		dateSpinner.add(yearWheel);

		dateLayout.add(dateSpinner);
		return dateLayout;
	}

	/**
	 * Creates a endless wheel for the given calendar component.
	 * 
	 * @param field
	 *            the id of the calendar component (#Calendar.DAY_OF_MONTH, #Calendar.MONTH, #Calendar.YEAR).
	 * @param minValue
	 *            the minimum value of the wheel.
	 * @param maxValue
	 *            the maximum value of the wheel.
	 * @param initialValue
	 *            the initial value of the wheel.
	 * @return the created wheel.
	 */
	private static IntegerWheel createCalendarWheel(final int field, int minValue, int maxValue, int initialValue) {
		Value<Integer> calendarComponent = new EndlessIntegerValue(minValue, maxValue, 1, initialValue, "");
		calendarComponent.addListener(new ValueListener<Integer>() {

			@Override
			public void valueChanged(Integer value) {
				if (field == Calendar.MONTH) {
					value--;
				}
				Calendar time = Calendar.getInstance();
				time.set(field, value);
				Util.setCurrentTimeMillis(time.getTimeInMillis());
			}
		});
		IntegerWheel calendarWheel = new IntegerWheel();
		calendarWheel.setModel(calendarComponent);
		return calendarWheel;
	}

	@Override
	protected String getTitle() {
		return "Date & time";
	}

	@Override
	protected char getPictoTitle() {
		return Pictos.DATE_AND_TIME;
	}
}
