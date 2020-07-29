/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.radiobutton;

import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.radiobutton.widget.RadioButton;
import com.microej.demo.widget.radiobutton.widget.RadioButtonGroup;

import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.util.Alignment;
import ej.widget.container.List;
import ej.widget.container.util.LayoutOrientation;

/**
 * Page showing radio buttons.
 */
public class RadioButtonPage implements Page {

	private static final int LIST = 1000;
	private static final int RADIO_BUTTON = 1001;

	@Override
	public String getName() {
		return "RadioButton"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(LIST));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);

		style = stylesheet.getSelectorStyle(new ClassSelector(RADIO_BUTTON));
		style.setFont(Fonts.getBoldFont());
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setMargin(new UniformOutline(2));
	}

	@Override
	public Widget getContentWidget() {
		RadioButtonGroup group = new RadioButtonGroup();

		RadioButton radioButton1 = new RadioButton("Money", group); //$NON-NLS-1$
		radioButton1.addClassSelector(RADIO_BUTTON);

		RadioButton radioButton2 = new RadioButton("Time", group); //$NON-NLS-1$
		radioButton2.addClassSelector(RADIO_BUTTON);

		RadioButton radioButton3 = new RadioButton("Energy", group); //$NON-NLS-1$
		radioButton3.addClassSelector(RADIO_BUTTON);

		List list = new List(LayoutOrientation.VERTICAL);
		list.addClassSelector(LIST);
		list.addChild(radioButton1);
		list.addChild(radioButton2);
		list.addChild(radioButton3);
		return list;
	}
}
