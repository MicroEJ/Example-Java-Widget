/*
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.sliderwithprogress;

import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.sliderwithprogress.widget.SliderWithProgress;

import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.SimpleDock;

/**
 * Contains the widgets used by the slider with progress page.
 */
public class SliderWithProgressPage implements Page {

	private static final int ROUNDED_SLIDER = 1202;
	private static final int BAR_COLOR = 0xEE502E;
	private static final int FILL_COLOR = 0x485357;
	private static final int LATERAL_SPACE = 60;

	private static final int MAX_VALUE = 100;
	private static final int INITIAL_VALUE = 50;

	@Override
	public String getName() {
		return "Slider with progress"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle sliderStyle = stylesheet.getSelectorStyle(new ClassSelector(ROUNDED_SLIDER));
		sliderStyle.setFont(Fonts.getSourceSansPro16px700());
		sliderStyle.setMargin(new FlexibleOutline(0, LATERAL_SPACE, 0, LATERAL_SPACE));
		sliderStyle.setExtraInt(SliderWithProgress.BAR_COLOR_ID, BAR_COLOR);
		sliderStyle.setExtraInt(SliderWithProgress.FILL_COLOR_ID, FILL_COLOR);
	}

	@Override
	public Widget getContentWidget() {
		SimpleDock dock = new SimpleDock(LayoutOrientation.VERTICAL);

		SliderWithProgress roundSlider = new SliderWithProgress(0, MAX_VALUE, INITIAL_VALUE);
		roundSlider.addClassSelector(ROUNDED_SLIDER);

		dock.setCenterChild(roundSlider);

		return dock;
	}

}
