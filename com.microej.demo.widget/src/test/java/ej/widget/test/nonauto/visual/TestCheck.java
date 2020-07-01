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
import ej.mwt.style.outline.border.RectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.Label;
import ej.widget.composed.Check;
import ej.widget.util.States;

/**
 *
 */
public class TestCheck {

	public static void main(String[] args) {
		MicroUI.start();

		CascadingStylesheet stylesheet = new CascadingStylesheet();

		EditableStyle labelStyle = stylesheet.getSelectorStyle(new TypeSelector(Label.class));
		labelStyle.setHorizontalAlignment(Alignment.LEFT);
		labelStyle.setVerticalAlignment(Alignment.VCENTER);

		EditableStyle checkStyle = stylesheet.getSelectorStyle(new TypeSelector(Check.class));
		checkStyle.setBorder(new RectangularBorder(Colors.BLACK, 2));
		checkStyle.setPadding(new UniformOutline(2));
		checkStyle.setMargin(new UniformOutline(10));

		EditableStyle checkCheckedStyle = stylesheet
				.getSelectorStyle(new AndCombinator(new TypeSelector(Check.class), new StateSelector(States.ACTIVE)));
		checkCheckedStyle.setBackground(new RectangularBackground(0x6060d0));
		checkCheckedStyle.setColor(0x6060d0);

		Check check = new Check("check");

		Desktop desktop = new Desktop();
		desktop.setStylesheet(stylesheet);
		desktop.setWidget(check);
		desktop.requestShow();
	}

}