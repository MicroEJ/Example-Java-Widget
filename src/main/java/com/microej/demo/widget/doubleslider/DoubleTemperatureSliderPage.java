/*
 * Copyright 2021-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.doubleslider;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.doubleslider.widget.DoubleTemperatureSlider;

import ej.microui.display.Colors;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.util.Alignment;

/**
 * Page showing a double temperature slider.
 */
public class DoubleTemperatureSliderPage implements Page {

	private static final int MIN_TEMPERATURE = 20;
	private static final int MAX_TEMPERATURE = 28;
	private static final float COOL_VALUE = 25f;
	private static final float HEAT_VALUE = 23.5f;

	@Override
	public String getName() {
		return "Double Temperature Slider"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(DoubleTemperatureSlider.class));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_X);
		style.setFont(Fonts.getSourceSansPro16px700());
		style.setColor(DemoColors.DEFAULT_FOREGROUND);
		style.setExtraInt(DoubleTemperatureSlider.SLIDER_COLOR_ID, Colors.BLACK);
		style.setExtraInt(DoubleTemperatureSlider.GUIDE_COLOR_ID, DemoColors.ALTERNATE_BACKGROUND);
		style.setVerticalAlignment(Alignment.VCENTER);
	}

	@Override
	public Widget getContentWidget() {
		return new DoubleTemperatureSlider(MIN_TEMPERATURE, MAX_TEMPERATURE, HEAT_VALUE, COOL_VALUE);
	}

}
