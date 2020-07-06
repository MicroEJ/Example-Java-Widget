/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.label;

import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.common.TitleBar;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.mwt.Desktop;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.util.Alignment;
import ej.widget.basic.Label;
import ej.widget.container.Grid;

public class LabelPage implements Page {

	public static void main(String[] args) {
		MicroUI.start();
		Desktop desktop = new LabelPage().getDesktop();
		desktop.requestShow();
	}

	@Override
	public Desktop getDesktop() {
		Desktop desktop = new Desktop();

		CascadingStylesheet stylesheet = new CascadingStylesheet();
		EditableStyle style = stylesheet.getDefaultStyle();
		style.setColor(Colors.MAGENTA);
		style.setBackground(new RectangularBackground(Colors.LIME));
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);
		desktop.setStylesheet(stylesheet);

		TitleBar.addTitleBarStyle(stylesheet);

		Grid grid = new Grid(true, 2);
		for (int i = 0; i < 10; i++) {
			grid.addChild(new Label("Label " + i));
		}

		desktop.setWidget(TitleBar.createPage(grid, true));

		return desktop;
	}

}
