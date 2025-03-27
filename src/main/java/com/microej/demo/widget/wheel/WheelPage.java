/*
 * Copyright 2021-2025 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.wheel;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.wheel.widget.Choice;
import com.microej.demo.widget.wheel.widget.IntegerChoice;
import com.microej.demo.widget.wheel.widget.StringChoice;
import com.microej.demo.widget.wheel.widget.Wheel;

import ej.bon.Immutables;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.widget.container.List;

/**
 * Page showing a wheel.
 */
public class WheelPage implements Page {

	private static final String[] MONTHS = (String[]) Immutables.get("Months"); //$NON-NLS-1$

	private static final int WHEEL_SIDES = 2;
	private static final int DATE_PICKER = 1000;

	private static final int DATE_PICKER_MARGIN = 16;

	private static final int DEFAULT_YEAR = 2025;
	private static final int DEFAULT_MONTH = 12;
	private static final int DEFAULT_DAY = 13;

	private static final int MIN_DAY = 1;
	private static final int MAX_DAY = 31;
	private static final int MIN_YEAR = 2010;
	private static final int MAX_YEAR = 2035;

	@Override
	public String getName() {
		return "Wheel"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle wheelStyle = stylesheet.getSelectorStyle(new TypeSelector(Wheel.class));
		wheelStyle.setColor(DemoColors.DEFAULT_FOREGROUND);
		wheelStyle.setBackground(new RectangularBackground(DemoColors.DEFAULT_BACKGROUND));
		wheelStyle.setExtraInt(Wheel.LINE_COLOR_FIELD, DemoColors.CORAL);
		wheelStyle.setFont(Fonts.getSourceSansPro22px400());

		EditableStyle datePickerStyle = stylesheet.getSelectorStyle(new ClassSelector(DATE_PICKER));
		datePickerStyle.setMargin(new UniformOutline(DATE_PICKER_MARGIN));
	}

	@Override
	public Widget getContentWidget() {

		List wheelsList = new List(true);
		wheelsList.addClassSelector(DATE_PICKER);

		Choice monthChoice = new StringChoice(MONTHS, DEFAULT_MONTH);
		Wheel monthWheel = new Wheel(WHEEL_SIDES, monthChoice);
		wheelsList.addChild(monthWheel);

		Choice dayChoice = new IntegerChoice(MIN_DAY, MAX_DAY, DEFAULT_DAY);
		Wheel dayWheel = new Wheel(WHEEL_SIDES, dayChoice);
		wheelsList.addChild(dayWheel);

		Choice yearChoice = new IntegerChoice(MIN_YEAR, MAX_YEAR, DEFAULT_YEAR);
		Wheel yearWheel = new Wheel(WHEEL_SIDES, yearChoice);
		wheelsList.addChild(yearWheel);
		return wheelsList;
	}

}
