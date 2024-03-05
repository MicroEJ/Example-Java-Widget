/*
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.autoscrolllabeloncircle;

import com.microej.demo.widget.autoscrolllabeloncircle.widget.AutoscrollLabelOnCircle;
import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.common.StringOnCirclePainter.Direction;

import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;

/**
 * Page showing an auto-scrolling label on circle.
 */
public class AutoscrollLabelOnCirclePage implements Page {

	private static final int START_ANGLE = 30;
	private static final int ARC_ANGLE = 120;
	private static final int SCROLL_DURATION = 5_000;

	@Override
	public String getName() {
		return "Autoscroll Label On Circle"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(AutoscrollLabelOnCircle.class));
		style.setColor(DemoColors.DEFAULT_FOREGROUND);
		// Use the same color as the background to hide the text when relevant.
		style.setExtraInt(AutoscrollLabelOnCircle.EXTRA_FIELD_BACKGROUND_COLOR, DemoColors.DEFAULT_BACKGROUND);
	}

	@Override
	public Widget getContentWidget() {
		return new AutoscrollLabelOnCircle("Hello, world!", //$NON-NLS-1$
				START_ANGLE, ARC_ANGLE, Direction.CLOCKWISE, SCROLL_DURATION);
	}
}
