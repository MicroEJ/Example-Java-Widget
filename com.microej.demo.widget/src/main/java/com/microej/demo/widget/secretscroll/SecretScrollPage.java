/*
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.secretscroll;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.secretscroll.widget.SecretScroll;

import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.util.Alignment;
import ej.widget.basic.ImageWidget;
import ej.widget.basic.Label;

/**
 * Page showing a secret scroll.
 */
public class SecretScrollPage implements Page {

	private static final String IMAGE_PATH = "/images/microej_background.png"; //$NON-NLS-1$

	private static final int HIDDEN_ITEM = 71410;

	private static final int SCROLL_MARGIN_SIDES = 15;
	private static final int HIDDEN_ITEM_MARGIN_SIDES = 10;

	@Override
	public String getName() {
		return "Secret Scroll"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(SecretScroll.class));
		style.setMargin(new FlexibleOutline(0, SCROLL_MARGIN_SIDES, 0, SCROLL_MARGIN_SIDES));

		style = stylesheet.getSelectorStyle(new ClassSelector(HIDDEN_ITEM));
		style.setPadding(new UniformOutline(HIDDEN_ITEM_MARGIN_SIDES));
		style.setMargin(new FlexibleOutline(0, 0, HIDDEN_ITEM_MARGIN_SIDES, 0));
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setBackground(new RectangularBackground(DemoColors.ALTERNATE_BACKGROUND));
	}

	@Override
	public Widget getContentWidget() {
		Label hiddenChild = new Label("Hidden Item"); //$NON-NLS-1$
		hiddenChild.addClassSelector(HIDDEN_ITEM);
		ImageWidget imageWidget = new ImageWidget(IMAGE_PATH);
		return new SecretScroll(hiddenChild, imageWidget);
	}
}
