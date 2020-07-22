/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.button;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;

import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RoundedBackground;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.RectangularBorder;
import ej.mwt.style.outline.border.RoundedBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.Selector;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.widget.basic.Button;
import ej.widget.container.Grid;
import ej.widget.util.States;

/**
 * Page showing buttons.
 */
public class ButtonPage implements Page {

	private static final int RECT_BUTTON = 700;
	private static final int ROUNDED_BUTTON = 701;

	@Override
	public String getName() {
		return "Button"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(Button.class));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		style.setPadding(new UniformOutline(10));
		style.setFont(Fonts.getBoldFont());

		Selector rectButton = new ClassSelector(RECT_BUTTON);
		Selector roundedButton = new ClassSelector(ROUNDED_BUTTON);
		Selector activeSelector = new StateSelector(States.ACTIVE);

		// rect button
		style = stylesheet.getSelectorStyle(rectButton);
		style.setBorder(new RectangularBorder(DemoColors.DEFAULT_BORDER, 1));

		// active rect button
		style = stylesheet.getSelectorStyle(new AndCombinator(rectButton, activeSelector));
		style.setBorder(new RectangularBorder(DemoColors.POMEGRANATE, 1));

		// rounded button
		style = stylesheet.getSelectorStyle(roundedButton);
		style.setBorder(new RoundedBorder(DemoColors.ALTERNATE_BACKGROUND, 4, 1));
		style.setBackground(new RoundedBackground(DemoColors.ALTERNATE_BACKGROUND, 4));

		// active rounded button
		style = stylesheet.getSelectorStyle(new AndCombinator(roundedButton, activeSelector));
		style.setBorder(new RoundedBorder(DemoColors.POMEGRANATE, 4, 1));
		style.setBackground(new RoundedBackground(DemoColors.POMEGRANATE, 4));
	}

	@Override
	public Widget getContentWidget() {
		Grid grid = new Grid(true, 2);
		for (int i = 0; i < 6; i++) {
			Button button = new Button("Button " + i); //$NON-NLS-1$
			button.addClassSelector(i % 3 == 1 ? ROUNDED_BUTTON : RECT_BUTTON);
			grid.addChild(button);
		}
		return grid;
	}
}
