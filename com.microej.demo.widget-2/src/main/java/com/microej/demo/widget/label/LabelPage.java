/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.label;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;

import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.util.Alignment;
import ej.widget.basic.Label;
import ej.widget.container.Grid;

/**
 * Page showing labels.
 */
public class LabelPage implements Page {

	@Override
	public String getName() {
		return "Label"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getDefaultStyle();
		style.setColor(DemoColors.DEFAULT_FOREGROUND);
		style.setBackground(new RectangularBackground(DemoColors.DEFAULT_BACKGROUND));
		style.setFont(Fonts.getDefaultFont());
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);
	}

	@Override
	public Widget getContentWidget() {
		Grid grid = new Grid(true, 2);
		for (int i = 0; i < 6; i++) {
			grid.addChild(new Label("Label " + i)); //$NON-NLS-1$
		}
		return grid;
	}
}
