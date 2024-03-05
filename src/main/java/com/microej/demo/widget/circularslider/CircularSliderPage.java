/*
 * Copyright 2021-2024 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.circularslider;

import com.microej.demo.widget.circularslider.widget.CircularSlider;
import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;

import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;

/**
 * Page showing a circular slider.
 */
public class CircularSliderPage implements Page {

	private static final int BAR_COLOR = 0xEE502E;
	private static final int THICKNESS = 5;
	private static final int GUIDE_THICKNESS = 2;
	private static final int SLIDER_SIZE = 20;
	private static final int SLIDER_THICKNESS = 1;

	private static final int MAX_VALUE = 100;
	private static final int INITIAL_VALUE = 0;

	@Override
	public String getName() {
		return "Circular Slider"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(CircularSlider.class));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		style.setFont(Fonts.getSourceSansPro16px700());
		style.setExtraInt(CircularSlider.THICKNESS_ID, THICKNESS);
		style.setExtraInt(CircularSlider.SLIDER_COLOR_ID, DemoColors.DEFAULT_BACKGROUND);
		style.setExtraInt(CircularSlider.GUIDE_THICKNESS_ID, GUIDE_THICKNESS);
		style.setExtraInt(CircularSlider.GUIDE_COLOR_ID, BAR_COLOR);
		style.setExtraInt(CircularSlider.SLIDER_DIAMETER_ID, SLIDER_SIZE);
		style.setExtraInt(CircularSlider.SLIDER_THICKNESS_ID, SLIDER_THICKNESS);
	}

	@Override
	public Widget getContentWidget() {
		return new CircularSlider(0, MAX_VALUE, INITIAL_VALUE);
	}

}
