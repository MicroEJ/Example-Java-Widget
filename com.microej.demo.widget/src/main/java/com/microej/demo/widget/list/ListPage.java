/*
 * Copyright 2020-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
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
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

/**
 * Page showing lists.
 */
public class ListPage implements Page {

	private static final int LABEL = 900;

	private static final int LABEL_MARGIN = 6;
	private static final int LABEL_PADDING_SIDES = 7;
	private static final int LABEL_PADDING_TOP_BOTTOM = 2;
	private static final int LABEL_BORDER_THICKNESS = 1;

	private static final int LABEL_COUNT = 3;

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
		style.setMargin(new UniformOutline(LABEL_MARGIN));
		style.setPadding(new FlexibleOutline(LABEL_PADDING_TOP_BOTTOM, LABEL_PADDING_SIDES, LABEL_PADDING_TOP_BOTTOM,
				LABEL_PADDING_SIDES));
		style.setBorder(new RectangularBorder(DemoColors.DEFAULT_FOREGROUND, LABEL_BORDER_THICKNESS));
	}

	@Override
	public Widget getContentWidget() {
		List list = new List(LayoutOrientation.HORIZONTAL);
		for (int i = 0; i < LABEL_COUNT; i++) {
			list.addChild(createLabel(i + 1));
		}
		return list;
	}

	private static Label createLabel(int id) {
		Label label = new Label(Integer.toString(id));
		label.addClassSelector(LABEL);
		return label;
	}
}
