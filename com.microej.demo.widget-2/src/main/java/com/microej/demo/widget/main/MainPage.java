/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.main;

import com.microej.demo.widget.common.Navigation;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.common.TitleBar;
import com.microej.demo.widget.label.LabelPage;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.mwt.Desktop;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.util.Alignment;
import ej.widget.basic.Button;
import ej.widget.listener.OnClickListener;

public class MainPage implements Page {

	public static void main(String[] args) {
		MicroUI.start();
		Desktop desktop = new MainPage().getDesktop();
		desktop.requestShow();
	}

	@Override
	public Desktop getDesktop() {
		Desktop desktop = new Desktop();

		CascadingStylesheet stylesheet = new CascadingStylesheet();

		EditableStyle style = stylesheet.getDefaultStyle();
		style.setColor(Colors.WHITE);
		style.setBackground(new RectangularBackground(Colors.BLACK));
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);

		TitleBar.addTitleBarStyle(stylesheet);

		desktop.setStylesheet(stylesheet);

		Button button = new Button("MAIN PAGE");
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick() {
				Navigation.showPage(new LabelPage());
			}
		});

		desktop.setWidget(TitleBar.createPage(button, false));

		return desktop;
	}

}
