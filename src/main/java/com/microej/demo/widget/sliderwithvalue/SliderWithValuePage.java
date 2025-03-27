/*
 * Copyright 2021-2025 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.sliderwithvalue;

import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.sliderwithvalue.widget.PlusOrMinusDecorator;
import com.microej.demo.widget.sliderwithvalue.widget.SliderWithValue;

import ej.microui.display.Colors;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.SimpleDock;

/**
 * Page showing a slider with value.
 */
public class SliderWithValuePage implements Page {

	private static final int LEFT_LIMIT = 1204;
	private static final int RIGHT_LIMIT = 1205;
	private static final int CIRCLE_COLOR = 0x97A7AF;
	private static final int CIRCLE_DIAMETER = 39;

	private static final int MAX_VALUE = 100;
	private static final int INITIAL_VALUE = 50;

	@Override
	public String getName() {
		return "Slider with Value"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(SliderWithValue.class));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_Y);
		style.setFont(Fonts.getSourceSansPro16px700());
		style.setColor(Colors.WHITE);
		style.setExtraInt(SliderWithValue.BAR_COLOR_ID, 0x97A7AF);
		style.setExtraInt(SliderWithValue.CURSOR_EDGE_ID, 0xEE502E);
		style.setExtraInt(SliderWithValue.CURSOR_BACKGROUND_ID, Colors.BLACK);
		style.setVerticalAlignment(Alignment.VCENTER);

		style = stylesheet.getSelectorStyle(
				new AndCombinator(new TypeSelector(SliderWithValue.class), new StateSelector(StateSelector.ACTIVE)));
		style.setExtraInt(SliderWithValue.CURSOR_BACKGROUND_ID, 0xAB3A22);

		// left limit
		style = stylesheet.getSelectorStyle(new ClassSelector(LEFT_LIMIT));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		style.setFont(Fonts.getSourceSansPro16px700());
		style.setVerticalAlignment(Alignment.VCENTER);

		// right limit
		style = stylesheet.getSelectorStyle(new ClassSelector(RIGHT_LIMIT));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		style.setFont(Fonts.getSourceSansPro16px700());
		style.setVerticalAlignment(Alignment.VCENTER);
	}

	@Override
	public Widget getContentWidget() {
		// limitSliderDock has the following design [leftLimit][slider][rightLimit]
		SimpleDock sliderDock = new SimpleDock(LayoutOrientation.HORIZONTAL);

		SliderWithValue limitSlider = new SliderWithValue(0, MAX_VALUE, INITIAL_VALUE);

		PlusOrMinusDecorator leftLimit = new PlusOrMinusDecorator(CIRCLE_DIAMETER, true, CIRCLE_COLOR, limitSlider);
		leftLimit.addClassSelector(LEFT_LIMIT);
		sliderDock.setFirstChild(leftLimit);

		PlusOrMinusDecorator rightLimit = new PlusOrMinusDecorator(CIRCLE_DIAMETER, false, CIRCLE_COLOR, limitSlider);
		rightLimit.addClassSelector(RIGHT_LIMIT);
		sliderDock.setLastChild(rightLimit);

		sliderDock.setCenterChild(limitSlider);

		return sliderDock;
	}

}
