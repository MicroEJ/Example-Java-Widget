/*
 * Copyright 2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.fixedgrid;

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
import ej.widget.container.FixedGrid;

/**
 * Page showing grids.
 */
public class FixedGridPage implements Page {

	private static final int LABEL = 1200;

	private static final int LABEL_MARGIN = 6;
	private static final int LABEL_PADDING_SIDES = 7;
	private static final int LABEL_PADDING_TOP_BOTTOM = 2;

	private static final int WIDGETS_IN_ROW = 4;
	private static final int WIDGETS_IN_COLUMN = 3;

	@Override
	public String getName() {
		return "Fixed Grid"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(LABEL));
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setVerticalAlignment(Alignment.TOP);
		style.setFont(Fonts.getSourceSansPro16px700());
		style.setMargin(new UniformOutline(LABEL_MARGIN));
		style.setPadding(new FlexibleOutline(LABEL_PADDING_TOP_BOTTOM, LABEL_PADDING_SIDES, LABEL_PADDING_TOP_BOTTOM,
				LABEL_PADDING_SIDES));
		style.setBorder(new RectangularBorder(DemoColors.DEFAULT_FOREGROUND, 1));
	}

	@Override
	public Widget getContentWidget() {
		FixedGrid grid = new FixedGrid(WIDGETS_IN_ROW, WIDGETS_IN_COLUMN);
		grid.addChild(createLabel(0), 3, 2);
		grid.addChild(createLabel(1), 0, 0);
		grid.addChild(createLabel(2), 1, 2);
		grid.addChild(createLabel(3), 2, 1);
		return grid;
	}

	private static Label createLabel(int id) {
		Label label = new Label(Integer.toString(id));
		label.addClassSelector(LABEL);
		return label;
	}
}
