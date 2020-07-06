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
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.widget.basic.Label;
import ej.widget.basic.drawing.SwitchBox;
import ej.widget.composed.Switch;

/**
 *
 */
public class TestSwitch {

	public static void main(String[] args) {
		MicroUI.start();

		CascadingStylesheet stylesheet = new CascadingStylesheet();
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(Switch.class));
		style.setMargin(new UniformOutline(10));
		// PlainBackground plainBackground = new PlainBackground(Colors.RED);
		// style.setBorder(plainBackground);
		style.setColor(0x10bdf1);

		style = stylesheet.getSelectorStyle(new TypeSelector(SwitchBox.class));
		style.setBackground(new RectangularBackground(Colors.RED));

		style = stylesheet.getSelectorStyle(new TypeSelector(Label.class));
		style.setBackground(new RectangularBackground(Colors.GREEN));

		Switch _switch = new Switch("switch");

		Desktop desktop = new Desktop();
		desktop.setStylesheet(stylesheet);
		desktop.setWidget(_switch);
		desktop.requestShow();
	}
}
