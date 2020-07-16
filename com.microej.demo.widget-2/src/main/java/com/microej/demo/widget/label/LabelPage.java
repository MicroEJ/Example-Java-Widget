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
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.util.Alignment;
import ej.widget.basic.Label;
import ej.widget.container.Grid;
import ej.widget.container.SimpleDock;
import ej.widget.container.util.LayoutOrientation;

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

		style = stylesheet.getSelectorStyle(new ClassSelector(PageHelper.TITLE_CLASSSELECTOR));
		style.setHorizontalAlignment(Alignment.LEFT);

		PageHelper.addCommonStyle(stylesheet);

		return stylesheet;
	}

	private Widget createPageContent() {
		SimpleDock dock = new SimpleDock(LayoutOrientation.VERTICAL);
		dock.addClassSelector(PageHelper.CONTENT_CLASSSELECTOR);
		Label title = new Label("Label"); //$NON-NLS-1$
		title.addClassSelector(PageHelper.TITLE_CLASSSELECTOR);
		dock.setFirstChild(title);
		Grid grid = new Grid(true, 2);
		for (int i = 0; i < 6; i++) {
			grid.addChild(new Label("Label " + i)); //$NON-NLS-1$
		}
		dock.setCenterChild(grid);

		Widget pageContent = PageHelper.createPage(dock, true);
		return pageContent;
	}

}
