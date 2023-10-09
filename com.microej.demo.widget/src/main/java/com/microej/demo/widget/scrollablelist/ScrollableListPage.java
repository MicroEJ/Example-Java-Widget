/*
 * Copyright 2020-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.scrollablelist;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.common.scroll.Scroll;
import com.microej.demo.widget.common.scroll.ScrollableList;
import com.microej.demo.widget.common.scroll.Scrollbar;

import ej.drawing.ShapePainter.Cap;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.NoBackground;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.dimension.FixedDimension;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.FlexibleRectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.OddChildSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.Label;
import ej.widget.container.LayoutOrientation;

/**
 * Page showing a scrollable list.
 */
public class ScrollableListPage implements Page {

	private static final int NUM_ITEMS = 20;

	private static final int SCROLL = 70898;
	private static final int LIST_ITEM = 70899;

	private static final int SCROLLBAR_WIDTH = 10;
	private static final int SCROLLBAR_PADDING = 1;
	private static final int SCROLL_MARGIN_SIDES = 15;
	private static final int LIST_ITEM_BORDER_THICKNESS = 1;
	private static final int LIST_ITEM_MARGIN_SIDES = 10;
	private static final int LIST_ITEM_MARGIN_TOP_BOTTOM = 5;

	@Override
	public String getName() {
		return "Scrollable List"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(Scrollbar.class));
		style.setBackground(NoBackground.NO_BACKGROUND);
		style.setDimension(new FixedDimension(SCROLLBAR_WIDTH, Widget.NO_CONSTRAINT));
		style.setPadding(new UniformOutline(SCROLLBAR_PADDING));
		style.setColor(DemoColors.DEFAULT_FOREGROUND);

		style = stylesheet.getSelectorStyle(new ClassSelector(SCROLL));
		style.setMargin(new FlexibleOutline(0, SCROLL_MARGIN_SIDES, 0, SCROLL_MARGIN_SIDES));

		style = stylesheet.getSelectorStyle(new ClassSelector(LIST_ITEM));
		style.setBorder(new FlexibleRectangularBorder(DemoColors.DEFAULT_BORDER, LIST_ITEM_BORDER_THICKNESS, 0, 0, 0));
		style.setPadding(new FlexibleOutline(LIST_ITEM_MARGIN_TOP_BOTTOM, LIST_ITEM_MARGIN_SIDES,
				LIST_ITEM_MARGIN_TOP_BOTTOM, LIST_ITEM_MARGIN_SIDES));
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setBackground(new RectangularBackground(DemoColors.DEFAULT_BACKGROUND));

		style = stylesheet
				.getSelectorStyle(new AndCombinator(new ClassSelector(LIST_ITEM), OddChildSelector.ODD_CHILD_SELECTOR));
		style.setBackground(new RectangularBackground(DemoColors.ALTERNATE_BACKGROUND));
	}

	@Override
	public Widget getContentWidget() {
		Scroll scroll = new Scroll(LayoutOrientation.VERTICAL);
		scroll.setScrollBarCaps(Cap.ROUNDED);
		scroll.setScrollbarOverlap(true);
		scroll.addClassSelector(SCROLL);
		ScrollableList list = new ScrollableList(LayoutOrientation.VERTICAL, false);
		scroll.setChild(list);
		for (int i = 0; i < NUM_ITEMS; i++) {
			Label label = new Label("Item " + i); //$NON-NLS-1$
			label.addClassSelector(LIST_ITEM);
			list.addChild(label);
		}
		return scroll;
	}
}
