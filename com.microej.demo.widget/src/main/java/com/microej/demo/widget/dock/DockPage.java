/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.dock;

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
import ej.widget.container.Dock;

/**
 * Page showing docks.
 */
public class DockPage implements Page {

	private static final int LABEL = 800;

	private static final int LABEL_MARGIN = 6;
	private static final int LABEL_PADDING_SIDES = 7;
	private static final int LABEL_PADDING_TOP_BOTTOM = 2;

	private static final String LABEL_ID_ONE = "1"; //$NON-NLS-1$
	private static final String LABEL_ID_TWO = "2"; //$NON-NLS-1$
	private static final String LABEL_ID_THREE = "3"; //$NON-NLS-1$
	private static final String LABEL_ID_FOUR = "4"; //$NON-NLS-1$
	private static final String LABEL_ID_FIVE = "5"; //$NON-NLS-1$
	private static final String LABEL_ID_SIX = "6"; //$NON-NLS-1$

	@Override
	public String getName() {
		return "Dock"; //$NON-NLS-1$
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
		Dock dock = new Dock();
		dock.addChildOnLeft(createLabel(LABEL_ID_ONE));
		dock.addChildOnTop(createLabel(LABEL_ID_TWO));
		dock.addChildOnRight(createLabel(LABEL_ID_THREE));
		dock.addChildOnBottom(createLabel(LABEL_ID_FOUR));
		dock.addChildOnLeft(createLabel(LABEL_ID_FIVE));
		dock.setCenterChild(createLabel(LABEL_ID_SIX));
		return dock;
	}

	private static Label createLabel(String id) {
		Label label = new Label(id);
		label.addClassSelector(LABEL);
		return label;
	}
}
