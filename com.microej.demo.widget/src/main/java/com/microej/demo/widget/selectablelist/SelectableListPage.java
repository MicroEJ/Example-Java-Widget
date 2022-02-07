/*
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.selectablelist;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.selectablelist.widget.AnimatedChoice;

import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.border.FlexibleRectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.OddChildSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

/**
 * Page showing a scrollable list with selectable items.
 */
public class SelectableListPage implements Page {

	private static final int NUM_ITEMS = 4;

	private static final int LIST = 8010;
	private static final int LIST_ITEM = 8011;

	private static final int LIST_MARGIN_SIDES = 15;
	private static final int LIST_ITEM_BORDER_THICKNESS = 1;
	private static final int LIST_ITEM_PADDING_SIDES = 20;
	private static final int LIST_ITEM_PADDING_TOP_BOTTOM = 10;

	private static final int SELECT_RECTANGLE_THICKNESS = 3;

	@Override
	public String getName() {
		return "Selectable List"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(LIST));
		style.setMargin(new FlexibleOutline(0, LIST_MARGIN_SIDES, 0, LIST_MARGIN_SIDES));

		style = stylesheet.getSelectorStyle(new ClassSelector(LIST_ITEM));
		style.setBorder(new FlexibleRectangularBorder(DemoColors.DEFAULT_BORDER, LIST_ITEM_BORDER_THICKNESS, 0, 0, 0));
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setBackground(new RectangularBackground(DemoColors.DEFAULT_BACKGROUND));
		style.setPadding(new FlexibleOutline(LIST_ITEM_PADDING_TOP_BOTTOM, LIST_ITEM_PADDING_SIDES,
				LIST_ITEM_PADDING_TOP_BOTTOM, LIST_ITEM_PADDING_SIDES));
		style.setExtraInt(AnimatedChoice.START_SELECTED_COLOR, DemoColors.DEFAULT_BACKGROUND);
		style.setExtraInt(AnimatedChoice.SELECTED_COLOR, DemoColors.CORAL);
		style.setExtraInt(AnimatedChoice.SELECTED_TEXT_COLOR, DemoColors.DEFAULT_BACKGROUND);
		style.setExtraInt(AnimatedChoice.RECTANGLE_THICKNESS, SELECT_RECTANGLE_THICKNESS);

		style = stylesheet
				.getSelectorStyle(new AndCombinator(new ClassSelector(LIST_ITEM), OddChildSelector.ODD_CHILD_SELECTOR));
		style.setBackground(new RectangularBackground(DemoColors.ALTERNATE_BACKGROUND));
		style.setExtraInt(AnimatedChoice.START_SELECTED_COLOR, DemoColors.ALTERNATE_BACKGROUND);
	}

	@Override
	public Widget getContentWidget() {
		List list = new List(LayoutOrientation.VERTICAL);
		list.addClassSelector(LIST);
		for (int i = 0; i < NUM_ITEMS; i++) {
			AnimatedChoice listItem = new AnimatedChoice("Item " + i); //$NON-NLS-1$
			listItem.addClassSelector(LIST_ITEM);
			list.addChild(listItem);
		}
		return list;
	}
}
