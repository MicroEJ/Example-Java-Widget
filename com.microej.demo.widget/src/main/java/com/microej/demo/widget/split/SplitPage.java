/*
 * Copyright 2020-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.split;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.split.widget.Split;

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

/**
 * Page showing splits.
 */
public class SplitPage implements Page {

	private static final int SPLIT = 1100;
	private static final int LABEL = 1101;

	private static final float SPLIT_RATIO = 0.60f;
	private static final int LABEL_MARGIN = 6;
	private static final int LABEL_PADDING_SIDES = 7;
	private static final int LABEL_PADDING_TOP_BOTTOM = 2;

	@Override
	public String getName() {
		return "Split"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(SPLIT));
		style.setExtraFloat(Split.RATIO_FIELD, SPLIT_RATIO);

		style = stylesheet.getSelectorStyle(new ClassSelector(LABEL));
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setVerticalAlignment(Alignment.TOP);
		style.setFont(Fonts.getBoldFont());
		style.setMargin(new UniformOutline(LABEL_MARGIN));
		style.setPadding(new FlexibleOutline(LABEL_PADDING_TOP_BOTTOM, LABEL_PADDING_SIDES, LABEL_PADDING_TOP_BOTTOM,
				LABEL_PADDING_SIDES));
		style.setBorder(new RectangularBorder(DemoColors.DEFAULT_FOREGROUND, 1));
	}

	@Override
	public Widget getContentWidget() {
		Split split = new Split(LayoutOrientation.HORIZONTAL);
		split.addClassSelector(SPLIT);
		split.setFirstChild(createLabel(1));
		split.setLastChild(createLabel(2));
		return split;
	}

	private static Label createLabel(int id) {
		Label label = new Label(Integer.toString(id));
		label.addClassSelector(LABEL);
		return label;
	}
}
