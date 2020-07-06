/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.nonauto.visual;

import ej.microui.MicroUI;
import ej.mwt.Desktop;
import ej.mwt.style.EditableStyle;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.util.Alignment;
import ej.widget.basic.drawing.CheckBox;
import ej.widget.composed.Toggle;

/**
 *
 */
public class TestToggle {

	public static void main(String[] args) {
		MicroUI.start();
		Desktop desktop = new Desktop();

		CascadingStylesheet stylesheet = new CascadingStylesheet();
		EditableStyle defaultStyle = stylesheet.getDefaultStyle();
		defaultStyle.setHorizontalAlignment(Alignment.HCENTER);
		defaultStyle.setVerticalAlignment(Alignment.VCENTER);

		// EditableStyle checkedStyle = new EditableStyle();
		// checkedStyle.setBorder(new PlainBackground(Colors.RED));
		// stylesheet.addRule(new AndCombinator(new TypeSelector(Check.class), new StateSelector(State.Checked)),
		// checkedStyle);

		Toggle toggle = new Toggle(new CheckBox(), "Label");
		desktop.setWidget(toggle);
		desktop.requestShow();
	}
}
