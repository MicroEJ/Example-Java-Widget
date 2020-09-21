/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.label;

import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;

import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.widget.basic.Label;

/**
 * Page showing labels.
 */
public class LabelPage implements Page {

	private static final int LABEL = 600;

	@Override
	public String getName() {
		return "Label"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(LABEL));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		style.setPadding(new UniformOutline(10));
		style.setFont(Fonts.getBoldFont());
	}

	@Override
	public Widget getContentWidget() {
		Label label = new Label("Hello, world!"); //$NON-NLS-1$
		label.addClassSelector(LABEL);
		return label;
	}
}
