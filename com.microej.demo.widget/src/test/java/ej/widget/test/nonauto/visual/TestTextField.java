/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.nonauto.visual;

import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.mwt.Desktop;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.widget.basic.TextField;
import ej.widget.util.States;

/**
 *
 */
public class TestTextField {

	public static void main(String[] args) throws InterruptedException {
		MicroUI.start();

		CascadingStylesheet stylesheet = new CascadingStylesheet();
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(TextField.class));
		UniformOutline padding = new UniformOutline(10);
		style.setPadding(padding);

		EditableStyle emptyStyle = stylesheet.getSelectorStyle(new StateSelector(States.EMPTY));
		emptyStyle.setColor(Colors.SILVER);

		TextField textField = new TextField("", "blabla bla");

		Desktop desktop = new Desktop();
		desktop.setStylesheet(stylesheet);
		desktop.setWidget(textField);
		desktop.requestShow();

		Thread.sleep(2000);

		textField.setText("aaa aa");
		Thread.sleep(2000);
		textField.back();
		Thread.sleep(100);
		textField.back();
		Thread.sleep(100);
		textField.back();
		Thread.sleep(100);
		textField.back();
		Thread.sleep(100);
		textField.back();
		Thread.sleep(100);
		textField.back();
		Thread.sleep(100);
	}
}
