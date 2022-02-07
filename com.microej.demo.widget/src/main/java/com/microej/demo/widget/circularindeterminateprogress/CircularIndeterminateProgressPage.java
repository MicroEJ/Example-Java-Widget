/*
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.circularindeterminateprogress;

import com.microej.demo.widget.circularindeterminateprogress.widget.CircularIndeterminateProgress;
import com.microej.demo.widget.common.Page;

import ej.microui.display.Colors;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.util.Alignment;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.SimpleDock;

/**
 * Circular Indeterminate Progress page.
 */
public class CircularIndeterminateProgressPage implements Page {

	private static final int BAR_COLOR = 0xEE502E;
	private static final int CIRCULAR_SIZE = 100;
	private static final int CIRCULAR_THICK = 5;

	@Override
	public String getName() {
		return "Circular Indeterminate Progress"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {

		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(CircularIndeterminateProgress.class));
		style.setColor(BAR_COLOR);
		style.setExtraInt(CircularIndeterminateProgress.BACKGROUND_COLOR, Colors.BLACK);
		style.setExtraInt(CircularIndeterminateProgress.PROGRESS_DIAMETER, CIRCULAR_SIZE);
		style.setExtraInt(CircularIndeterminateProgress.PROGRESS_THICK, CIRCULAR_THICK);
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		style.setHorizontalAlignment(Alignment.HCENTER);

	}

	@Override
	public Widget getContentWidget() {
		SimpleDock circularDock = new SimpleDock(LayoutOrientation.VERTICAL);

		// circular progress bar progress clockwise
		CircularIndeterminateProgress circularProgressBar = new CircularIndeterminateProgress();

		circularDock.setCenterChild(circularProgressBar);

		return circularDock;
	}

}
