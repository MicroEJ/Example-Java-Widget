/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.progressbar;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.progressbar.widget.ProgressBar;

import ej.microui.display.Colors;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.RectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;

/**
 * Page showing progress bars.
 */
public class ProgressBarPage implements Page {

	private static final int PROGRESS_BAR = 1300;

	@Override
	public String getName() {
		return "ProgressBar"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(PROGRESS_BAR));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_Y);
		style.setBackground(new RectangularBackground(DemoColors.CORAL));
		style.setBorder(new RectangularBorder(Colors.BLACK, 3));
		style.setColor(DemoColors.POMEGRANATE);
		style.setExtraInt(ProgressBar.STRING_COLOR_FIELD, Colors.WHITE);
		style.setMargin(new UniformOutline(6));
	}

	@Override
	public Widget getContentWidget() {
		ProgressBar progressBar = new ProgressBar();
		progressBar.addClassSelector(PROGRESS_BAR);
		progressBar.setProgress(0.40f);
		return progressBar;
	}
}
