/*
 * Copyright 2020-2024 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.autoscrolllabel;

import com.microej.demo.widget.autoscrolllabel.widget.AutoscrollLabel;
import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;

import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.FlexibleRectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.util.Alignment;

/**
 * Page showing an autoscroll label.
 */
public class AutoscrollLabelPage implements Page {

	private static final int AUTOSCROLL_LABEL = 601;

	private static final int MARGIN = 10;

	@Override
	public String getName() {
		return "Autoscroll Label"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(AUTOSCROLL_LABEL));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_Y);
		style.setMargin(new UniformOutline(MARGIN));
		style.setBorder(new FlexibleRectangularBorder(DemoColors.DEFAULT_BORDER, 0, 1, 0, 1));
		style.setFont(Fonts.getSourceSansPro16px700());
		style.setHorizontalAlignment(Alignment.LEFT);
	}

	@Override
	public Widget getContentWidget() {
		AutoscrollLabel label = new AutoscrollLabel(
				"Lorem ipsum dolor sit amet, consectetur adipiscing elit. In tincidunt est nec sagittis tincidunt."); // //$NON-NLS-1$
		label.addClassSelector(AUTOSCROLL_LABEL);
		return label;
	}

}
