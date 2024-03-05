/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.checkbox;

import com.microej.demo.widget.checkbox.widget.Checkbox;
import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;

import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.util.Alignment;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

/**
 * Page showing checkboxes.
 */
public class CheckboxPage implements Page {

	private static final int LIST = 900;
	private static final int CHECKBOX = 901;

	@Override
	public String getName() {
		return "Checkbox"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(LIST));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);

		style = stylesheet.getSelectorStyle(new ClassSelector(CHECKBOX));
		style.setFont(Fonts.getSourceSansPro16px700());
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setMargin(new UniformOutline(2));
		style.setExtraInt(Checkbox.CHECKED_COLOR_FIELD, DemoColors.CORAL);
	}

	@Override
	public Widget getContentWidget() {
		Checkbox checkbox1 = new Checkbox("Accept our terms and conditions"); //$NON-NLS-1$
		checkbox1.addClassSelector(CHECKBOX);

		Checkbox checkbox2 = new Checkbox("Subscribe to our newsletter"); //$NON-NLS-1$
		checkbox2.addClassSelector(CHECKBOX);

		Checkbox checkbox3 = new Checkbox("Donate $1 to Doctors Without Borders"); //$NON-NLS-1$
		checkbox3.addClassSelector(CHECKBOX);

		List list = new List(LayoutOrientation.VERTICAL);
		list.addClassSelector(LIST);
		list.addChild(checkbox1);
		list.addChild(checkbox2);
		list.addChild(checkbox3);
		return list;
	}
}
