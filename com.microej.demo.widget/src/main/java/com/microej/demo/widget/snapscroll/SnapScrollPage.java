/*
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.snapscroll;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.snapscroll.widget.SnapScroll;

import ej.bon.Immutables;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.OddChildSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.ImageWidget;
import ej.widget.basic.Label;

/**
 * Page showing a snap scroll.
 */
public class SnapScrollPage implements Page {

	private static final String[] IMAGES = (String[]) Immutables.get("AvatarsImages"); //$NON-NLS-1$
	private static final String[] NAMES = (String[]) Immutables.get("AvatarsNames"); //$NON-NLS-1$

	private static final String IMAGE_PATH = "/images/carousel/"; //$NON-NLS-1$

	private static final int ITEM = 37100;

	private static final int SCROLL_MARGIN_SIDES = 15;
	private static final int LIST_ITEM_MARGIN_SIDES = 10;
	private static final int LIST_ITEM_MARGIN_TOP_BOTTOM = 5;

	@Override
	public String getName() {
		return "Snap Scroll"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(SnapScroll.class));
		style.setMargin(new FlexibleOutline(0, SCROLL_MARGIN_SIDES, 0, SCROLL_MARGIN_SIDES));

		style = stylesheet.getSelectorStyle(new ClassSelector(ITEM));
		style.setPadding(new FlexibleOutline(LIST_ITEM_MARGIN_TOP_BOTTOM, LIST_ITEM_MARGIN_SIDES,
				LIST_ITEM_MARGIN_TOP_BOTTOM, LIST_ITEM_MARGIN_SIDES));
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setBackground(new RectangularBackground(DemoColors.DEFAULT_BACKGROUND));

		style = stylesheet
				.getSelectorStyle(new AndCombinator(new ClassSelector(ITEM), OddChildSelector.ODD_CHILD_SELECTOR));
		style.setBackground(new RectangularBackground(DemoColors.ALTERNATE_BACKGROUND));
	}

	@Override
	public Widget getContentWidget() {
		SnapScroll snapScroll = new SnapScroll();
		for (int i = 0; i < IMAGES.length; i++) {
			String name = NAMES[i];
			assert name != null;
			Label label = new Label(name);
			label.addClassSelector(ITEM);
			snapScroll.addChild(label);
			ImageWidget image = new ImageWidget(IMAGE_PATH + IMAGES[i]);
			image.addClassSelector(ITEM);
			snapScroll.addChild(image);
		}
		return snapScroll;
	}
}
