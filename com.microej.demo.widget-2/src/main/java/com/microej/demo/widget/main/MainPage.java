/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.main;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Navigation;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.common.Pages;
import com.microej.demo.widget.main.style.GoToBackground;
import com.microej.demo.widget.main.widget.MenuItem;
import com.microej.demo.widget.main.widget.Scroll;
import com.microej.demo.widget.main.widget.ScrollableList;
import com.microej.demo.widget.main.widget.Scrollbar;

import ej.microui.display.Colors;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
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
import ej.widget.container.util.LayoutOrientation;
import ej.widget.listener.OnClickListener;

/**
 * Page that allows to navigate to other pages.
 */
public class MainPage implements Page {

	private static final int LIST_ITEM = 70898;

	private static final int GRAY = 0xe5e9eb;

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
		style.setPadding(new FlexibleOutline(6, 0, 5, 24));
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setBackground(new GoToBackground(Colors.WHITE));

		style = stylesheet
				.getSelectorStyle(new AndCombinator(new ClassSelector(LIST_ITEM), OddChildSelector.ODD_CHILD_SELECTOR));
		style.setBackground(new GoToBackground(GRAY));
	}

	@Override
	public Widget getContentWidget() {
		ScrollableList list = new ScrollableList(LayoutOrientation.VERTICAL);
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
		for (int i = numPages; i < 100; i++) {
			MenuItem menuItem = new MenuItem("Stub Page " + i); //$NON-NLS-1$
			menuItem.addClassSelector(LIST_ITEM);
			list.addChild(menuItem);
		}

		Scroll scroll = new Scroll(LayoutOrientation.VERTICAL);
		scroll.setChild(list);
		return scroll;
	}
}
