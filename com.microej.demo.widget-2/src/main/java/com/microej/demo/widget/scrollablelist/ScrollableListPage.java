/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.scrollablelist;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.common.PageHelper;
import com.microej.demo.widget.scrollablelist.widget.Scroll;
import com.microej.demo.widget.scrollablelist.widget.ScrollableList;
import com.microej.demo.widget.scrollablelist.widget.Scrollbar;

import ej.microui.MicroUI;
import ej.mwt.Desktop;
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
import ej.widget.container.SimpleDock;
import ej.widget.container.util.LayoutOrientation;

/**
 * Page showing a scrollable list.
 */
public class ScrollableListPage implements Page {

	private static final int SCROLL = 70898;
	private static final int LIST_ITEM = 70899;

	/**
	 * Shows the scrollable list page.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		MicroUI.start();
		Desktop desktop = new ScrollableListPage().getDesktop();
		desktop.requestShow();
	}

	@Override
	public Desktop getDesktop() {
		Desktop desktop = PageHelper.createDesktop();

		CascadingStylesheet stylesheet = createStylesheet();
		desktop.setStylesheet(stylesheet);

		Widget pageContent = createPageContent();
		desktop.setWidget(pageContent);

		return desktop;
	}

	private CascadingStylesheet createStylesheet() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		EditableStyle style = stylesheet.getDefaultStyle();
		style.setColor(DemoColors.DEFAULT_FOREGROUND);
		style.setBackground(new RectangularBackground(DemoColors.DEFAULT_BACKGROUND));
		style.setFont(Fonts.getDefaultFont());
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);

		style = stylesheet.getSelectorStyle(new TypeSelector(Scrollbar.class));
		style.setBackground(NoBackground.NO_BACKGROUND);
		style.setDimension(new FixedDimension(10, Widget.NO_CONSTRAINT));
		style.setPadding(new UniformOutline(1));
		style.setColor(DemoColors.DEFAULT_FOREGROUND);

		style = stylesheet.getSelectorStyle(new ClassSelector(SCROLL));
		style.setMargin(new FlexibleOutline(0, 15, 0, 15));

		style = stylesheet.getSelectorStyle(new ClassSelector(LIST_ITEM));
		style.setBorder(new FlexibleRectangularBorder(DemoColors.DEFAULT_BORDER, 1, 0, 0, 0));
		style.setPadding(new FlexibleOutline(5, 10, 5, 10));
		style.setHorizontalAlignment(Alignment.LEFT);
		style.setBackground(new RectangularBackground(DemoColors.DEFAULT_BACKGROUND));

		style = stylesheet
				.getSelectorStyle(new AndCombinator(new ClassSelector(LIST_ITEM), OddChildSelector.ODD_CHILD_SELECTOR));
		style.setBackground(new RectangularBackground(DemoColors.ALTERNATE_BACKGROUND));

		PageHelper.addCommonStyle(stylesheet);

		return stylesheet;
	}

	private Widget createPageContent() {
		SimpleDock dock = new SimpleDock(LayoutOrientation.VERTICAL);
		dock.addClassSelector(PageHelper.CONTENT_CLASSSELECTOR);
		Label title = new Label("Scrollable List"); //$NON-NLS-1$
		title.addClassSelector(PageHelper.TITLE_CLASSSELECTOR);
		dock.setFirstChild(title);

		Scroll scroll = new Scroll(LayoutOrientation.VERTICAL);
		scroll.addClassSelector(SCROLL);
		ScrollableList list = new ScrollableList(LayoutOrientation.VERTICAL);
		scroll.setChild(list);
		for (int i = 0; i < 100; i++) {
			Label label = new Label("Item " + i); //$NON-NLS-1$
			label.addClassSelector(LIST_ITEM);
			list.addChild(label);
		}
		dock.setCenterChild(scroll);

		Widget pageContent = PageHelper.createPage(dock, true);
		return pageContent;
	}

}
