/*
 * Copyright 2015-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.keyboard;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.keyboard.widget.Key;
import com.microej.demo.widget.keyboard.widget.Keyboard;
import com.microej.demo.widget.keyboard.widget.TextField;

import ej.microui.display.Colors;
import ej.microui.display.Font;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.NoBackground;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.background.RoundedBackground;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.border.FlexibleRectangularBorder;
import ej.mwt.style.outline.border.RoundedBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;
import ej.mwt.stylesheet.selector.Selector;
import ej.mwt.stylesheet.selector.StateSelector;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.stylesheet.selector.combinator.AndCombinator;
import ej.mwt.util.Alignment;
import ej.widget.basic.Label;

/**
 * This page illustrates a keyboard.
 */
public class KeyboardPage implements Page {

	/** Form color ID. */
	/* package */ static final int FORM = 9;
	/** Result label ID. */
	/* package */ static final int RESULT_LABEL = 10;
	/** Page content rolor ID. */
	/* package */ static final int CONTENT = 301;

	/** Keyboard background color. */
	private static final int KEYBOARD_BACKGROUND_COLOR = DemoColors.DEFAULT_BACKGROUND;
	/** Keyboard key color. */
	private static final int KEYBOARD_KEY_COLOR = Colors.WHITE;
	/** Keyboard key highlight color. */
	private static final int KEYBOARD_HIGHLIGHT_COLOR = 0xd9d5d3;
	/** Text field placeholder color. */
	private static final int TEXT_PLACEHOLDER_COLOR = DemoColors.ALTERNATE_BACKGROUND;
	/** Text field color. */
	private static final int TEXT_COLOR = 0xf4f4f4;
	/** Selected text background color. */
	private static final int TEXT_SELECTION_BACKGROUND = TEXT_COLOR;
	/** Selected text color. */
	private static final int TEXT_SELECTION_COLOR = TEXT_PLACEHOLDER_COLOR;
	/** Shift key background. */
	private static final int SHIFT_KEY_BACKGROUND = 0xcbd3d7;
	/** Key corner radius. */
	private static final int KEY_CORNER_RADIUS = 13;

	@Override
	public String getName() {
		return "Keyboard"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		Font textFont = Fonts.getSourceSansPro15px600();
		Font textFieldFont = textFont;
		Font textLargeFont = Fonts.getSourceSansPro22px400();

		// Keyboard
		TypeSelector keyboardSelector = new TypeSelector(Keyboard.class);
		EditableStyle keyboardStyle = stylesheet.getSelectorStyle(keyboardSelector);
		keyboardStyle.setFont(textFont);
		keyboardStyle.setBackground(new RectangularBackground(KEYBOARD_BACKGROUND_COLOR));

		Selector labelSelector = new TypeSelector(Label.class);
		EditableStyle labelStyle = stylesheet.getSelectorStyle(labelSelector);
		labelStyle.setBackground(NoBackground.NO_BACKGROUND);

		Selector keySelector = new TypeSelector(Key.class);
		EditableStyle keyStyle = stylesheet.getSelectorStyle(keySelector);
		keyStyle.setColor(KEYBOARD_KEY_COLOR);
		keyStyle.setBackground(NoBackground.NO_BACKGROUND);
		keyStyle.setHorizontalAlignment(Alignment.HCENTER);
		keyStyle.setVerticalAlignment(Alignment.VCENTER);
		keyStyle.setMargin(new FlexibleOutline(3, 2, 3, 2));

		Selector keyBackground = new ClassSelector(Keyboard.KEY_BACKGROUND);
		EditableStyle keyBackgroundStyle = stylesheet.getSelectorStyle(keyBackground);
		keyBackgroundStyle.setBackground(new RectangularBackground(KEYBOARD_BACKGROUND_COLOR));

		StateSelector activeSelector = new StateSelector(Key.ACTIVE);
		AndCombinator activeKeySelector = new AndCombinator(keySelector, activeSelector);
		EditableStyle activeKeyStyle = stylesheet.getSelectorStyle(activeKeySelector);
		activeKeyStyle.setColor(Colors.BLACK);
		activeKeyStyle.setBackground(new RoundedBackground(KEYBOARD_HIGHLIGHT_COLOR, KEY_CORNER_RADIUS));
		activeKeyStyle.setBorder(new RoundedBorder(KEYBOARD_HIGHLIGHT_COLOR, KEY_CORNER_RADIUS, 2));

		ClassSelector spaceKeySelector = new ClassSelector(Keyboard.SPACE_KEY_SELECTOR);
		EditableStyle spaceKeyStyle = stylesheet.getSelectorStyle(spaceKeySelector);
		spaceKeyStyle.setBackground(new RoundedBackground(KEYBOARD_KEY_COLOR, KEY_CORNER_RADIUS));
		spaceKeyStyle.setBorder(new RoundedBorder(KEYBOARD_KEY_COLOR, KEY_CORNER_RADIUS, 2));

		ClassSelector activeShiftKeySelector = new ClassSelector(Keyboard.SHIFT_KEY_ACTIVE_SELECTOR);
		EditableStyle activeShiftKeyStyle = stylesheet.getSelectorStyle(activeShiftKeySelector);
		activeShiftKeyStyle.setBackground(new RoundedBackground(SHIFT_KEY_BACKGROUND, KEY_CORNER_RADIUS));
		activeShiftKeyStyle.setBorder(new RoundedBorder(SHIFT_KEY_BACKGROUND, KEY_CORNER_RADIUS, 2));

		ClassSelector specialKeySelector = new ClassSelector(Keyboard.SPECIAL_KEY_SELECTOR);
		EditableStyle specialKeyStyle = stylesheet.getSelectorStyle(specialKeySelector);
		specialKeyStyle.setColor(Colors.WHITE);
		specialKeyStyle.setBackground(new RoundedBackground(DemoColors.CORAL, KEY_CORNER_RADIUS));
		specialKeyStyle.setBorder(new RoundedBorder(DemoColors.CORAL, KEY_CORNER_RADIUS, 2));
		specialKeyStyle.setFont(textFont);

		specialKeySelector = new ClassSelector(Keyboard.INITIAL_SPECIAL_KEY_SELECTOR);
		specialKeyStyle = stylesheet.getSelectorStyle(specialKeySelector);
		specialKeyStyle.setColor(Colors.WHITE);
		specialKeyStyle.setFont(textFont);

		// Text fields
		TypeSelector textSelector = new TypeSelector(TextField.class);
		EditableStyle textStyle = stylesheet.getSelectorStyle(textSelector);
		textStyle.setFont(textFieldFont);
		textStyle.setColor(TEXT_COLOR);
		textStyle.setBackground(new RectangularBackground(TEXT_PLACEHOLDER_COLOR));
		textStyle.setHorizontalAlignment(Alignment.LEFT);
		textStyle.setVerticalAlignment(Alignment.VCENTER);
		textStyle.setMargin(new FlexibleOutline(4, 5, 5, 5));
		textStyle.setPadding(new FlexibleOutline(0, 1, 1, 1));
		textStyle.setExtraInt(TextField.SELECTION_BACKGROUND, TEXT_SELECTION_BACKGROUND);
		textStyle.setExtraInt(TextField.SELECTION_COLOR, TEXT_SELECTION_COLOR);
		textStyle.setExtraObject(TextField.CLEAR_BUTTON_FONT, textLargeFont);

		activeSelector = new StateSelector(TextField.ACTIVE);
		AndCombinator focusedTextSelector = new AndCombinator(textSelector, activeSelector);
		EditableStyle focusedTextStyle = stylesheet.getSelectorStyle(focusedTextSelector);
		focusedTextStyle.setBackground(new RectangularBackground(TEXT_PLACEHOLDER_COLOR));

		StateSelector emptySelector = new StateSelector(TextField.EMPTY);
		AndCombinator placeholderTextSelector = new AndCombinator(textSelector, emptySelector);
		EditableStyle placeholderTextStyle = stylesheet.getSelectorStyle(placeholderTextSelector);
		placeholderTextStyle.setBackground(NoBackground.NO_BACKGROUND);
		placeholderTextStyle.setColor(TEXT_PLACEHOLDER_COLOR);
		placeholderTextStyle.setBorder(new FlexibleRectangularBorder(TEXT_PLACEHOLDER_COLOR, 1, 1, 1, 1));

		ClassSelector resultLabelSelector = new ClassSelector(RESULT_LABEL);
		EditableStyle resultLabelStyle = stylesheet.getSelectorStyle(resultLabelSelector);
		resultLabelStyle.setFont(textFieldFont);
		resultLabelStyle.setHorizontalAlignment(Alignment.LEFT);
		resultLabelStyle.setVerticalAlignment(Alignment.VCENTER);
		resultLabelStyle.setColor(Colors.WHITE);
		resultLabelStyle.setBackground(NoBackground.NO_BACKGROUND);
		resultLabelStyle.setMargin(new FlexibleOutline(4, 5, 4, 5));

		ClassSelector formSelector = new ClassSelector(FORM);
		EditableStyle formStyle = stylesheet.getSelectorStyle(formSelector);
		formStyle.setMargin(new FlexibleOutline(5, 10, 5, 10));
		formStyle.setBackground(new RectangularBackground(DemoColors.DEFAULT_BACKGROUND));

		ClassSelector contentSelector = new ClassSelector(CONTENT);
		EditableStyle contentStyle = stylesheet.getSelectorStyle(contentSelector);
		contentStyle.setBackground(new RectangularBackground(DemoColors.DEFAULT_BACKGROUND));
	}

	@Override
	public Widget getContentWidget() {
		return new KeyboardPageContent().getContentWidget();
	}

}