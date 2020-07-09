/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.label;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.common.PageHelper;

import ej.microui.MicroUI;
import ej.mwt.Desktop;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.outline.border.FlexibleRectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.util.Alignment;
import ej.widget.basic.Label;
import ej.widget.container.Grid;

/**
 * Page showing labels.
 */
public class LabelPage implements Page {

	/**
	 * Shows the label page.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		MicroUI.start();
		Desktop desktop = new LabelPage().getDesktop();
		desktop.requestShow();
	}

	@Override
	public Desktop getDesktop() {
		Desktop desktop = PageHelper.createDesktop();

		CascadingStylesheet stylesheet = new CascadingStylesheet();
		desktop.setStylesheet(stylesheet);

		EditableStyle style = stylesheet.getDefaultStyle();
		style.setColor(DemoColors.DEFAULT_FOREGROUND);
		style.setBackground(new RectangularBackground(DemoColors.DEFAULT_BACKGROUND));
		style.setFont(Fonts.getDefaultFont());
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);

		style = stylesheet.getSelectorStyle(new TypeSelector(Grid.class));
		style.setBorder(new FlexibleRectangularBorder(DemoColors.EMPTY_SPACE, 0, 0, 0, PageHelper.LEFT_PADDING));

		PageHelper.addTitleBarStyle(stylesheet);

		Grid grid = new Grid(true, 2);
		for (int i = 0; i < 10; i++) {
			grid.addChild(new Label("Label " + i));
		}

		desktop.setWidget(PageHelper.createPage(grid, true));

		return desktop;
	}

}
