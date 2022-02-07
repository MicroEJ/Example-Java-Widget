/*
 * Copyright 2015-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.keyboard;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Fonts;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.keyboard.widget.Key;
import com.microej.demo.widget.keyboard.widget.Keyboard;
import com.microej.demo.widget.keyboard.widget.TextField;

import ej.microui.display.Font;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.NoBackground;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.background.RoundedBackground;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.border.RectangularBorder;
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

	private static final int FORM_SELECTOR = 1220;
	private static final int RESULT_LABEL_SELECTOR = 1221;
	private static final int SPACE_KEY_SELECTOR = 1222;
	private static final int SHIFT_KEY_INACTIVE_SELECTOR = 1223;
	private static final int SHIFT_KEY_ACTIVE_SELECTOR = 1224;
	private static final int SWITCH_MAPPING_KEY_SELECTOR = 1225;
	private static final int INITIAL_SPECIAL_KEY_SELECTOR = 1226;
	private static final int SPECIAL_KEY_SELECTOR = 1227;

	private static final int KEY_CORNER_RADIUS = 13;
	private static final int KEY_BORDER = 2;
	private static final int KEY_MARGIN_SIDES = 2;
	private static final int KEY_MARGIN_TOP_BOTTOM = 3;

	private static final int TEXT_FIELD_BORDER = 1;
	private static final int TEXT_FIELD_MARGIN = 5;
	private static final int TEXT_FIELD_MARGIN_TOP = 4;
	private static final int TEXT_FIELD_PADDING = 1;
	private static final int TEXT_FIELD_PADDING_TOP = 0;

	private static final int RESULT_LABEL_MARGIN_SIDES = 5;
	private static final int RESULT_LABEL_MARGIN_TOP_BOTTOM = 4;

	private static final int FORM_MARGIN_SIDES = 10;
	private static final int FORM_MARGIN_TOP_BOTTOM = 5;

	@Override
	public String getName() {
		return "Keyboard"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		Font textFont = Fonts.getSourceSansPro15px600();
		Font textFieldFont = textFont;

		// Keyboard
		int keyColor = DemoColors.DEFAULT_FOREGROUND;
		int keyboardBackgroundColor = DemoColors.DEFAULT_BACKGROUND;
		int keyHighlightColor = keyboardBackgroundColor;
		int keyHighlightBackroundColor = DemoColors.KEYBOARD_HIGHLIGHT_BACKGROUND;

		TypeSelector keyboardSelector = new TypeSelector(Keyboard.class);
		EditableStyle keyboardStyle = stylesheet.getSelectorStyle(keyboardSelector);
		keyboardStyle.setFont(textFont);
		keyboardStyle.setBackground(new RectangularBackground(keyboardBackgroundColor));

		Selector labelSelector = new TypeSelector(Label.class);
		EditableStyle labelStyle = stylesheet.getSelectorStyle(labelSelector);
		labelStyle.setBackground(NoBackground.NO_BACKGROUND);

		Selector keySelector = new TypeSelector(Key.class);
		EditableStyle keyStyle = stylesheet.getSelectorStyle(keySelector);
		keyStyle.setColor(keyColor);
		keyStyle.setBackground(NoBackground.NO_BACKGROUND);
		keyStyle.setHorizontalAlignment(Alignment.HCENTER);
		keyStyle.setVerticalAlignment(Alignment.VCENTER);
		keyStyle.setMargin(
				new FlexibleOutline(KEY_MARGIN_TOP_BOTTOM, KEY_MARGIN_SIDES, KEY_MARGIN_TOP_BOTTOM, KEY_MARGIN_SIDES));

		StateSelector activeSelector = new StateSelector(Key.ACTIVE);
		EditableStyle activeKeyStyle = stylesheet.getSelectorStyle(new AndCombinator(keySelector, activeSelector));
		activeKeyStyle.setColor(keyHighlightColor);
		activeKeyStyle.setBackground(new RoundedBackground(keyHighlightBackroundColor, KEY_CORNER_RADIUS, KEY_BORDER));
		activeKeyStyle.setBorder(new RoundedBorder(keyHighlightBackroundColor, KEY_CORNER_RADIUS, KEY_BORDER));

		ClassSelector spaceKeySelector = new ClassSelector(SPACE_KEY_SELECTOR);
		EditableStyle spaceKeyStyle = stylesheet.getSelectorStyle(spaceKeySelector);
		spaceKeyStyle.setBackground(new RoundedBackground(keyColor, KEY_CORNER_RADIUS, KEY_BORDER));
		spaceKeyStyle.setBorder(new RoundedBorder(keyColor, KEY_CORNER_RADIUS, KEY_BORDER));

		ClassSelector activeShiftKeySelector = new ClassSelector(SHIFT_KEY_ACTIVE_SELECTOR);
		EditableStyle activeShiftKeyStyle = stylesheet.getSelectorStyle(activeShiftKeySelector);
		activeShiftKeyStyle.setColor(keyHighlightColor);
		activeShiftKeyStyle
				.setBackground(new RoundedBackground(keyHighlightBackroundColor, KEY_CORNER_RADIUS, KEY_BORDER));
		activeShiftKeyStyle.setBorder(new RoundedBorder(keyHighlightBackroundColor, KEY_CORNER_RADIUS, KEY_BORDER));

		ClassSelector specialKeySelector = new ClassSelector(SPECIAL_KEY_SELECTOR);
		EditableStyle specialKeyStyle = stylesheet.getSelectorStyle(specialKeySelector);
		specialKeyStyle.setColor(keyColor);
		specialKeyStyle.setBackground(new RoundedBackground(DemoColors.CORAL, KEY_CORNER_RADIUS, KEY_BORDER));
		specialKeyStyle.setBorder(new RoundedBorder(DemoColors.CORAL, KEY_CORNER_RADIUS, KEY_BORDER));
		specialKeyStyle.setFont(textFont);

		specialKeySelector = new ClassSelector(INITIAL_SPECIAL_KEY_SELECTOR);
		specialKeyStyle = stylesheet.getSelectorStyle(specialKeySelector);
		specialKeyStyle.setColor(keyColor);
		specialKeyStyle.setFont(textFont);

		// Text fields
		int textColor = DemoColors.DEFAULT_FOREGROUND;
		int textPlaceHolderColor = DemoColors.ALTERNATE_BACKGROUND;
		int highlightColor = DemoColors.DEFAULT_FOREGROUND;
		int highlightBackgroundColor = DemoColors.DEFAULT_BACKGROUND;

		TypeSelector textSelector = new TypeSelector(TextField.class);
		EditableStyle textStyle = stylesheet.getSelectorStyle(textSelector);
		textStyle.setFont(textFieldFont);
		textStyle.setColor(textColor);
		textStyle.setBackground(new RectangularBackground(textPlaceHolderColor));
		textStyle.setHorizontalAlignment(Alignment.LEFT);
		textStyle.setVerticalAlignment(Alignment.VCENTER);
		textStyle.setMargin(
				new FlexibleOutline(TEXT_FIELD_MARGIN_TOP, TEXT_FIELD_MARGIN, TEXT_FIELD_MARGIN, TEXT_FIELD_MARGIN));
		textStyle.setPadding(new FlexibleOutline(TEXT_FIELD_PADDING_TOP, TEXT_FIELD_PADDING, TEXT_FIELD_PADDING,
				TEXT_FIELD_PADDING));
		textStyle.setExtraInt(TextField.SELECTION_BACKGROUND, highlightColor);
		textStyle.setExtraInt(TextField.SELECTION_COLOR, highlightBackgroundColor);

		activeSelector = new StateSelector(TextField.ACTIVE);
		AndCombinator focusedTextSelector = new AndCombinator(textSelector, activeSelector);
		EditableStyle focusedTextStyle = stylesheet.getSelectorStyle(focusedTextSelector);
		focusedTextStyle.setBackground(new RectangularBackground(textPlaceHolderColor));

		StateSelector emptySelector = new StateSelector(TextField.EMPTY);
		AndCombinator placeholderTextSelector = new AndCombinator(textSelector, emptySelector);
		EditableStyle placeholderTextStyle = stylesheet.getSelectorStyle(placeholderTextSelector);
		placeholderTextStyle.setBackground(NoBackground.NO_BACKGROUND);
		placeholderTextStyle.setColor(textPlaceHolderColor);
		placeholderTextStyle.setBorder(new RectangularBorder(textPlaceHolderColor, TEXT_FIELD_BORDER));

		ClassSelector resultLabelSelector = new ClassSelector(RESULT_LABEL_SELECTOR);
		EditableStyle resultLabelStyle = stylesheet.getSelectorStyle(resultLabelSelector);
		resultLabelStyle.setFont(textFieldFont);
		resultLabelStyle.setHorizontalAlignment(Alignment.LEFT);
		resultLabelStyle.setVerticalAlignment(Alignment.VCENTER);
		resultLabelStyle.setColor(textColor);
		resultLabelStyle.setBackground(NoBackground.NO_BACKGROUND);
		resultLabelStyle.setMargin(new FlexibleOutline(RESULT_LABEL_MARGIN_TOP_BOTTOM, RESULT_LABEL_MARGIN_SIDES,
				RESULT_LABEL_MARGIN_TOP_BOTTOM, RESULT_LABEL_MARGIN_SIDES));

		ClassSelector formSelector = new ClassSelector(FORM_SELECTOR);
		EditableStyle formStyle = stylesheet.getSelectorStyle(formSelector);
		formStyle.setMargin(new FlexibleOutline(FORM_MARGIN_TOP_BOTTOM, FORM_MARGIN_SIDES, FORM_MARGIN_TOP_BOTTOM,
				FORM_MARGIN_SIDES));
	}

	@Override
	public Widget getContentWidget() {
		return new KeyboardPageContent(FORM_SELECTOR, RESULT_LABEL_SELECTOR, SPACE_KEY_SELECTOR,
				SHIFT_KEY_INACTIVE_SELECTOR, SHIFT_KEY_ACTIVE_SELECTOR, SWITCH_MAPPING_KEY_SELECTOR,
				INITIAL_SPECIAL_KEY_SELECTOR, SPECIAL_KEY_SELECTOR).getContentWidget();
	}

}
