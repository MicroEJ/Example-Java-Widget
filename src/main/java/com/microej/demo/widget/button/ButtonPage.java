/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.button;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;

import ej.microui.display.Image;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.ImageBackground;
import ej.mwt.style.background.RoundedBackground;
import ej.mwt.style.dimension.FixedDimension;
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
import ej.widget.container.LayoutOrientation;
import ej.widget.container.List;

/**
 * Page showing buttons.
 */
public class ButtonPage implements Page {

	private static final String BUTTON_IMAGE = "/images/button.png"; //$NON-NLS-1$
	private static final String PRESSED_BUTTON_IMAGE = "/images/button-pressed.png"; //$NON-NLS-1$

	private static final int RECT_BUTTON = 700;
	private static final int ROUNDED_BUTTON = 701;
	private static final int IMAGE_BUTTON = 702;

	private static final int BUTTONS_PADDING = 10;
	private static final int BUTTONS_WIDTH = 154;
	private static final int BUTTONS_HEIGHT = 20;

	private static final int ROUNDED_CORNER_RADIUS = 4;
	private static final int ROUNDED_BORDER_THICKNESS = 1;
	private static final int RECTANGULAR_BORDER_THICKNESS = 1;

	@Override
	public String getName() {
		return "Button"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		Selector rectButton = new ClassSelector(RECT_BUTTON);
		Selector roundedButton = new ClassSelector(ROUNDED_BUTTON);
		Selector imageButton = new ClassSelector(IMAGE_BUTTON);
		Selector activeSelector = new StateSelector(Button.ACTIVE);

		// all buttons
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(Button.class));
		style.setDimension(new FixedDimension(BUTTONS_WIDTH, BUTTONS_HEIGHT));
		style.setPadding(new UniformOutline(BUTTONS_PADDING));
		style.setFont(Fonts.getSourceSansPro16px700());

		// rect button
		style = stylesheet.getSelectorStyle(rectButton);
		style.setBorder(new RectangularBorder(DemoColors.DEFAULT_BORDER, RECTANGULAR_BORDER_THICKNESS));

		// active rect button
		style = stylesheet.getSelectorStyle(new AndCombinator(rectButton, activeSelector));
		style.setBorder(new RectangularBorder(DemoColors.POMEGRANATE, RECTANGULAR_BORDER_THICKNESS));

		// rounded button
		style = stylesheet.getSelectorStyle(roundedButton);
		style.setBorder(
				new RoundedBorder(DemoColors.ALTERNATE_BACKGROUND, ROUNDED_CORNER_RADIUS, ROUNDED_BORDER_THICKNESS));
		style.setBackground(new RoundedBackground(DemoColors.ALTERNATE_BACKGROUND, ROUNDED_CORNER_RADIUS,
				ROUNDED_BORDER_THICKNESS));

		// active rounded button
		style = stylesheet.getSelectorStyle(new AndCombinator(roundedButton, activeSelector));
		style.setBorder(new RoundedBorder(DemoColors.POMEGRANATE, ROUNDED_CORNER_RADIUS, ROUNDED_BORDER_THICKNESS));
		style.setBackground(
				new RoundedBackground(DemoColors.POMEGRANATE, ROUNDED_CORNER_RADIUS, ROUNDED_BORDER_THICKNESS));

		// image button
		style = stylesheet.getSelectorStyle(imageButton);
		style.setBackground(new ImageBackground(Image.getImage(BUTTON_IMAGE)));

		// active image button
		style = stylesheet.getSelectorStyle(new AndCombinator(imageButton, activeSelector));
		style.setBackground(new ImageBackground(Image.getImage(PRESSED_BUTTON_IMAGE)));
		style.setColor(DemoColors.CORAL);
	}

	@Override
	public Widget getContentWidget() {
		Button rectButton = new Button("Rectangular button"); //$NON-NLS-1$
		rectButton.addClassSelector(RECT_BUTTON);

		Button roundedButton = new Button("Rounded button"); //$NON-NLS-1$
		roundedButton.addClassSelector(ROUNDED_BUTTON);

		Button imageButton = new Button("Image button"); //$NON-NLS-1$
		imageButton.addClassSelector(IMAGE_BUTTON);

		List list = new List(LayoutOrientation.VERTICAL);
		list.addChild(rectButton);
		list.addChild(roundedButton);
		list.addChild(imageButton);
		return list;
	}
}
