/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.list;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;

import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.RectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.util.Alignment;
import ej.widget.basic.Label;
import ej.widget.container.List;
import ej.widget.container.util.LayoutOrientation;

/**
 * Page showing lists.
 */
public class ListPage implements Page {

	private static final int LABEL = 900;

	@Override
	public String getName() {
		return "List"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(LABEL));
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setVerticalAlignment(Alignment.TOP);
		style.setFont(Fonts.getBoldFont());
		style.setMargin(new UniformOutline(6));
		style.setPadding(new FlexibleOutline(2, 7, 2, 7));
		style.setBorder(new RectangularBorder(DemoColors.DEFAULT_FOREGROUND, 1));
	}

	@Override
	public Widget getContentWidget() {
		List list = new List(LayoutOrientation.HORIZONTAL);
		list.addChild(createLabel(1));
		list.addChild(createLabel(2));
		list.addChild(createLabel(3));
		return list;
	}

	private static Label createLabel(int id) {
		Label label = new Label(Integer.toString(id));
		label.addClassSelector(LABEL);
		return label;
	}
}
