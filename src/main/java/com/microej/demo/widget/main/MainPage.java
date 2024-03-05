/*
 * Copyright 2020-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.main;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Navigation;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.common.Pages;
import com.microej.demo.widget.common.scroll.Scroll;
import com.microej.demo.widget.common.scroll.ScrollableList;
import com.microej.demo.widget.common.scroll.Scrollbar;
import com.microej.demo.widget.main.style.GoToBackground;
import com.microej.demo.widget.main.widget.MenuItem;

import ej.microui.display.Colors;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.NoBackground;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.dimension.FixedDimension;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.OddChildSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.OnClickListener;
import ej.widget.container.LayoutOrientation;

/**
 * Page that allows to navigate to other pages.
 */
public class MainPage implements Page {

	private static final int LIST_ITEM = 896560;

	private static final int GRAY = 0xe5e9eb;

	private static final int LIST_ITEM_PADDING_TOP = 6;
	private static final int LIST_ITEM_PADDING_BOTTOM = 5;
	private static final int LIST_ITEM_PADDING_LEFT = 24;

	@Override
	public String getName() {
		return "Main"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(Scrollbar.class));
		style.setDimension(new FixedDimension(2, Widget.NO_CONSTRAINT));
		style.setPadding(new UniformOutline(1));
		style.setColor(GRAY);
		style.setBackground(new RectangularBackground(DemoColors.EMPTY_SPACE));

		style = stylesheet.getSelectorStyle(new ClassSelector(LIST_ITEM));
		style.setColor(DemoColors.ALTERNATE_BACKGROUND);
		style.setPadding(
				new FlexibleOutline(LIST_ITEM_PADDING_TOP, 0, LIST_ITEM_PADDING_BOTTOM, LIST_ITEM_PADDING_LEFT));
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setBackground(new GoToBackground(Colors.WHITE));

		style = stylesheet.getSelectorStyle(new TypeSelector(Scroll.class));
		style.setBackground(new RectangularBackground(Colors.WHITE));
		style = stylesheet.getSelectorStyle(new TypeSelector(ScrollableList.class));
		style.setBackground(NoBackground.NO_BACKGROUND);

		style = stylesheet
				.getSelectorStyle(new AndCombinator(new ClassSelector(LIST_ITEM), OddChildSelector.ODD_CHILD_SELECTOR));
		style.setBackground(new GoToBackground(GRAY));
	}

	@Override
	public Widget getContentWidget() {
		ScrollableList list = new ScrollableList(LayoutOrientation.VERTICAL, false);
		int numPages = Pages.getNumPages();
		for (int i = 0; i < numPages; i++) {
			final Page page = Pages.getPage(i);
			MenuItem goToPage = new MenuItem(page.getName());
			goToPage.addClassSelector(LIST_ITEM);
			list.addChild(goToPage);
			goToPage.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick() {
					Navigation.showPage(page);
				}
			});
		}

		Scroll scroll = new Scroll(LayoutOrientation.VERTICAL);
		scroll.setScrollbarBeforeContent(true);
		scroll.setChild(list);
		return scroll;
	}

}
