/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.checkbox;

import com.microej.demo.widget.checkbox.widget.Checkbox;
import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;

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
		style.setFont(Fonts.getBoldFont());
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setMargin(new UniformOutline(2));
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
