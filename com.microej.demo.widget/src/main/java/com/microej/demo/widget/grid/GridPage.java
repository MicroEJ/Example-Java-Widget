/*
 * Copyright 2020-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.grid;

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
import ej.widget.container.Grid;
import ej.widget.container.LayoutOrientation;

/**
 * Page showing grids.
 */
public class GridPage implements Page {

	private static final int LABEL = 1200;

	private static final int LABEL_MARGIN = 6;
	private static final int LABEL_PADDING_SIDES = 7;
	private static final int LABEL_PADDING_TOP_BOTTOM = 2;

	private static final int WIDGETS_IN_ROW = 3;
	private static final int WIDGETS_COUNT = 6;

	@Override
	public String getName() {
		return "Grid"; //$NON-NLS-1$
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
		Grid grid = new Grid(LayoutOrientation.HORIZONTAL, WIDGETS_IN_ROW);
		for (int i = 0; i < WIDGETS_COUNT; i++) {
			grid.addChild(createLabel(i + 1));
		}
		return grid;
	}

	private static Label createLabel(int id) {
		Label label = new Label(Integer.toString(id));
		label.addClassSelector(LABEL);
		return label;
	}
}
