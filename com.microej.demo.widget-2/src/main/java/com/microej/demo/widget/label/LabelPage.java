/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.label;

import com.microej.demo.widget.common.Page;

import ej.mwt.Widget;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
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
		// no specific style
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
