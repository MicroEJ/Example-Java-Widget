/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.style;

import ej.microui.display.Colors;
import ej.mwt.Widget;
import ej.mwt.style.Selector;
import ej.mwt.style.Stylesheet;
import ej.mwt.style.background.NoBackground;
import ej.mwt.style.background.PlainRectangularBackground;
import ej.mwt.style.background.UniformPlainRoundedBackground;
import ej.mwt.style.border.FlexibleRectangularBorder;
import ej.mwt.style.border.UniformRectangularBorder;
import ej.mwt.style.border.UniformRoundedBorder;
import ej.mwt.style.cascading.CascadingStylesheet;
import ej.mwt.style.util.EditableStyle;
import ej.mwt.style.container.Alignment;
import ej.mwt.style.dimension.FixedDimension;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.selector.ClassSelector;
import ej.mwt.style.selector.OddChildSelector;
import ej.mwt.style.selector.StateSelector;
import ej.mwt.style.selector.TypeSelector;
import ej.mwt.style.selector.combinator.AndCombinator;
import ej.widget.basic.Label;
import ej.widget.basic.drawing.CheckBox;
import ej.widget.basic.drawing.CircularProgressBar;
import ej.widget.basic.drawing.ProgressBar;
import ej.widget.basic.drawing.RadioBox;
import ej.widget.basic.drawing.Scrollbar;
import ej.widget.basic.drawing.Slider;
import ej.widget.basic.drawing.SwitchBox;
import ej.widget.basic.image.ImageCheck;
import ej.widget.basic.image.ImageRadio;
import ej.widget.basic.image.ImageSlider;
import ej.widget.basic.image.ImageSwitch;
import ej.widget.basic.picto.PictoCheck;
import ej.widget.basic.picto.PictoProgress;
import ej.widget.basic.picto.PictoRadio;
import ej.widget.basic.picto.PictoSlider;
import ej.widget.basic.picto.PictoSwitch;
import ej.widget.chart.BasicChart;
import ej.widget.font.StrictFontLoader;
import ej.widget.keyboard.Key;
import ej.widget.keyboard.Keyboard;
import ej.widget.keyboard.KeyboardText;
import ej.widget.style.text.LineWrapTextStyle;
import ej.widget.util.States;
import ej.widget.wheel.Wheel;

/**
 * Class responsible for initializing the demo styles.
 */
public class StylesheetPopulator {

	private static final int FOREGROUND = MicroEJColors.CONCRETE_BLACK_25;
	private static final int BACKGROUND = MicroEJColors.WHITE;
	private static final int TITLE_FOREGROUND = MicroEJColors.CONCRETE_BLACK_50;
	private static final int LIST_ODD_BACKGROUND = MicroEJColors.CONCRETE_WHITE_75;
	private static final int TITLE_BORDER = MicroEJColors.CONCRETE_WHITE_50;
	private static final int CHECKED_FOREGROUND = MicroEJColors.CORAL;
	private static final int ACTIVE_FOREGROUND = MicroEJColors.POMEGRANATE;

	private static final int KEYBOARD_BACKGROUND_COLOR = MicroEJColors.CONCRETE_WHITE_75;
	private static final int KEYBOARD_KEY_COLOR = MicroEJColors.CONCRETE;
	private static final int KEYBOARD_HIGHLIGHT_COLOR = MicroEJColors.CORAL;
	private static final int TEXT_PLACEHOLDER_COLOR = MicroEJColors.CONCRETE_WHITE_25;
	private static final int TEXT_SELECTION_COLOR = MicroEJColors.CONCRETE_WHITE_50;
	private static final int WHEEL_LINE_COLOR = MicroEJColors.CORAL;

	private static final int KEY_CORNER_RADIUS = 10;
	private static final int BUTTON_CORNER_RADIUS = 14;

	// Prevents initialization.
	private StylesheetPopulator() {
	}

	/**
	 * Creates the stylesheet.
	 *
	 * @param fontLoader
	 *            the font loader.
	 * @return the stylesheet.
	 */
	public static Stylesheet createStylesheet(StrictFontLoader fontLoader) {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		// Sets the default style.
		EditableStyle defaultStyle = stylesheet.getDefaultStyle();
		defaultStyle.setColor(FOREGROUND);
		defaultStyle.setBackground(new PlainRectangularBackground(BACKGROUND));
		defaultStyle.setFont(fontLoader.getFont(FontFamilies.SOURCE_SANS_PRO, WidgetsFontLoader.MEDIUM));
		defaultStyle.setAlignment(Alignment.LEFT | Alignment.VCENTER);

		TypeSelector labelTypeSelector = new TypeSelector(Label.class);

		// Sets the label style.
		EditableStyle style = stylesheet.getRuleStyle(labelTypeSelector);
		style.setBackground(NoBackground.NO_BACKGROUND);

		// Sets the scroll style
		style = stylesheet.getRuleStyle(new TypeSelector(Scrollbar.class));
		style.setBackground(NoBackground.NO_BACKGROUND);

		// Sets the optimal size style.
		style = stylesheet.getRuleStyle(new ClassSelector(ClassSelectors.OPTIMAL_SIZE));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);

		// Sets the top bar style.
		style = stylesheet.getRuleStyle(new ClassSelector(ClassSelectors.TOP_BAR));
		style.setPadding(new UniformOutline(7));
		style.setFont(fontLoader.getFont(FontFamilies.SOURCE_SANS_PRO, WidgetsFontLoader.LARGE));
		style.setColor(TITLE_FOREGROUND);
		style.setBorder(new FlexibleRectangularBorder(TITLE_BORDER, 0, 0, 2, 0));
		style.setDimension(new FixedDimension(Widget.NO_CONSTRAINT, 32));

		// Sets the title style.
		style = stylesheet.getRuleStyle(new ClassSelector(ClassSelectors.TITLE));
		style.setMargin(new FlexibleOutline(0, 0, 0, 15));

		// Sets the centered style.
		style = stylesheet.getRuleStyle(new ClassSelector(ClassSelectors.CENTERED));
		style.setAlignment(Alignment.HCENTER_VCENTER);

		// Sets the store image style.
		style = stylesheet.getRuleStyle(new ClassSelector(ClassSelectors.STORE_IMAGE));
		style.setPadding(new FlexibleOutline(0, 0, 0, 5)); // Align with back button size.

		// Sets the illustrated button style.
		Selector illustratedButtonSelector = new ClassSelector(ClassSelectors.ILLUSTRATED_BUTTON);
		style = stylesheet.getRuleStyle(illustratedButtonSelector);
		style.setPadding(new FlexibleOutline(BUTTON_CORNER_RADIUS / 2, BUTTON_CORNER_RADIUS * 2,
				BUTTON_CORNER_RADIUS / 2, BUTTON_CORNER_RADIUS * 2));
		style.setColor(MicroEJColors.WHITE);
		UniformPlainRoundedBackground illustratedButtonBackground = new UniformPlainRoundedBackground(
				MicroEJColors.CORAL, BUTTON_CORNER_RADIUS);
		style.setBackground(illustratedButtonBackground);
		style.setBorder(new UniformRoundedBorder(MicroEJColors.CORAL, BUTTON_CORNER_RADIUS - 1, 1));

		// Sets the illustrated active button style.
		style = stylesheet.getRuleStyle(new AndCombinator(illustratedButtonSelector, new StateSelector(States.ACTIVE)));
		style.setBackground(new UniformPlainRoundedBackground(ACTIVE_FOREGROUND, BUTTON_CORNER_RADIUS));
		style.setBorder(new UniformRoundedBorder(ACTIVE_FOREGROUND, BUTTON_CORNER_RADIUS - 1, 1));

		// Sets the text scroll style.
		style = stylesheet.getRuleStyle(new ClassSelector(ClassSelectors.TEXT_SCROLL));
		style.setPadding(new UniformOutline(12));

		// Sets the text title style.
		style = stylesheet.getRuleStyle(new ClassSelector(ClassSelectors.TEXT_TITLE));
		FlexibleRectangularBorder textTitleBorder = new FlexibleRectangularBorder(TITLE_BORDER, 0, 0, 1, 0);
		style.setBorder(textTitleBorder);

		// Sets the multiline style.
		style = stylesheet.getRuleStyle(new ClassSelector(ClassSelectors.MULTILINE));
		style.setExtraField(Label.TEXT_STYLE, new LineWrapTextStyle(25));
		style.setPadding(new FlexibleOutline(0, 0, 20, 0));

		initializeWidgetsStyle(stylesheet, fontLoader);

		initializeListStyle(stylesheet);

		initializeKeyboardStyle(stylesheet, fontLoader);

		initializeEditionStyle(stylesheet, fontLoader);

		initializeChartStyle(stylesheet, fontLoader);

		initializeDateStyle(stylesheet, fontLoader);

		return stylesheet;
	}

	private static void initializeWidgetsStyle(CascadingStylesheet stylesheet, StrictFontLoader fontLoader) {
		// Default margin not added in the default style because it also applies for the composites.
		UniformOutline defaultMargin = new UniformOutline(6);

		TypeSelector checkBoxTypeSelector = new TypeSelector(CheckBox.class);
		TypeSelector radioBoxTypeSelector = new TypeSelector(RadioBox.class);
		TypeSelector switchBoxTypeSelector = new TypeSelector(SwitchBox.class);
		TypeSelector pictoProgressTypeSelector = new TypeSelector(PictoProgress.class);
		TypeSelector pictoSliderTypeSelector = new TypeSelector(PictoSlider.class);
		TypeSelector pictoCheckTypeSelector = new TypeSelector(PictoCheck.class);
		TypeSelector pictoRadioTypeSelector = new TypeSelector(PictoRadio.class);
		TypeSelector pictoSwitchTypeSelector = new TypeSelector(PictoSwitch.class);
		TypeSelector progressBarTypeSelector = new TypeSelector(ProgressBar.class);
		TypeSelector imageRadioTypeSelector = new TypeSelector(ImageRadio.class);
		TypeSelector imageCheckTypeSelector = new TypeSelector(ImageCheck.class);
		TypeSelector imageSwitchTypeSelector = new TypeSelector(ImageSwitch.class);
		TypeSelector circularProgressBarTypeSelector = new TypeSelector(CircularProgressBar.class);
		TypeSelector imageSliderTypeSelector = new TypeSelector(ImageSlider.class);
		TypeSelector sliderTypeSelector = new TypeSelector(Slider.class);
		StateSelector stateCheckedSelector = new StateSelector(States.CHECKED);

		// Sets the picto style.
		EditableStyle style = stylesheet.getRuleStyle(pictoProgressTypeSelector);
		style.setFont(fontLoader.getFont(FontFamilies.PICTO, WidgetsFontLoader.PICTO));
		style = stylesheet.getRuleStyle(pictoSliderTypeSelector);
		style.setFont(fontLoader.getFont(FontFamilies.PICTO, WidgetsFontLoader.PICTO));
		style = stylesheet.getRuleStyle(pictoCheckTypeSelector);
		style.setFont(fontLoader.getFont(FontFamilies.PICTO, WidgetsFontLoader.PICTO));
		style = stylesheet.getRuleStyle(pictoRadioTypeSelector);
		style.setFont(fontLoader.getFont(FontFamilies.PICTO, WidgetsFontLoader.PICTO));
		style = stylesheet.getRuleStyle(pictoSwitchTypeSelector);
		style.setFont(fontLoader.getFont(FontFamilies.PICTO, WidgetsFontLoader.PICTO));

		// Sets the unchecked toggle style.
		style = stylesheet.getRuleStyle(checkBoxTypeSelector);
		style.setColor(FOREGROUND);
		style.setMargin(defaultMargin);
		style.setAlignment(Alignment.HCENTER_VCENTER);
		style = stylesheet.getRuleStyle(radioBoxTypeSelector);
		style.setColor(FOREGROUND);
		style.setMargin(defaultMargin);
		style.setAlignment(Alignment.HCENTER_VCENTER);
		style = stylesheet.getRuleStyle(switchBoxTypeSelector);
		style.setColor(FOREGROUND);
		style.setMargin(defaultMargin);
		style.setAlignment(Alignment.HCENTER_VCENTER);

		style = stylesheet.getRuleStyle(checkBoxTypeSelector);
		style.setBorder(new UniformRectangularBorder(FOREGROUND, 3));
		style.setPadding(new UniformOutline(3));

		style = stylesheet.getRuleStyle(radioBoxTypeSelector);
		style.setBorder(new UniformRoundedBorder(FOREGROUND, 1000, 2));
		style.setPadding(new UniformOutline(4));

		style = stylesheet.getRuleStyle(switchBoxTypeSelector);
		style.setBorder(new UniformRoundedBorder(FOREGROUND, 1000, 2));
		style.setPadding(new FlexibleOutline(4, 20, 4, 4));
		style.setAlignment(Alignment.LEFT | Alignment.VCENTER);

		style = stylesheet.getRuleStyle(new AndCombinator(switchBoxTypeSelector, stateCheckedSelector));
		style.setPadding(new FlexibleOutline(4, 4, 4, 20));
		style.setAlignment(Alignment.RIGHT | Alignment.VCENTER);

		style = stylesheet.getRuleStyle(progressBarTypeSelector);
		style.setDimension(new FixedDimension(Widget.NO_CONSTRAINT, 10));
		style.setBackground(new PlainRectangularBackground(ACTIVE_FOREGROUND));

		// Sets the image widgets style.
		style = stylesheet.getRuleStyle(pictoSliderTypeSelector);
		style.setMargin(defaultMargin);
		style = stylesheet.getRuleStyle(pictoProgressTypeSelector);
		style.setMargin(defaultMargin);
		style = stylesheet.getRuleStyle(pictoCheckTypeSelector);
		style.setMargin(defaultMargin);
		style = stylesheet.getRuleStyle(pictoRadioTypeSelector);
		style.setMargin(defaultMargin);
		style = stylesheet.getRuleStyle(pictoSwitchTypeSelector);
		style.setMargin(defaultMargin);
		style = stylesheet.getRuleStyle(progressBarTypeSelector);
		style.setMargin(defaultMargin);
		style = stylesheet.getRuleStyle(circularProgressBarTypeSelector);
		style.setMargin(defaultMargin);
		style = stylesheet.getRuleStyle(imageSliderTypeSelector);
		style.setMargin(defaultMargin);
		style = stylesheet.getRuleStyle(sliderTypeSelector);
		style.setMargin(defaultMargin);
		style = stylesheet.getRuleStyle(imageRadioTypeSelector);
		style.setMargin(defaultMargin);
		style = stylesheet.getRuleStyle(imageCheckTypeSelector);
		style.setMargin(defaultMargin);
		style = stylesheet.getRuleStyle(imageSwitchTypeSelector);
		style.setMargin(defaultMargin);

		// Sets the checked toggles style.
		style = stylesheet.getRuleStyle(new AndCombinator(pictoRadioTypeSelector, stateCheckedSelector));
		style.setColor(CHECKED_FOREGROUND);
		style = stylesheet.getRuleStyle(new AndCombinator(pictoCheckTypeSelector, stateCheckedSelector));
		style.setColor(CHECKED_FOREGROUND);
		style = stylesheet.getRuleStyle(new AndCombinator(pictoSwitchTypeSelector, stateCheckedSelector));
		style.setColor(CHECKED_FOREGROUND);
		style = stylesheet.getRuleStyle(new AndCombinator(imageRadioTypeSelector, stateCheckedSelector));
		style.setColor(CHECKED_FOREGROUND);
		style = stylesheet.getRuleStyle(new AndCombinator(imageCheckTypeSelector, stateCheckedSelector));
		style.setColor(CHECKED_FOREGROUND);
		style = stylesheet.getRuleStyle(new AndCombinator(imageSwitchTypeSelector, stateCheckedSelector));
		style.setColor(CHECKED_FOREGROUND);
		style = stylesheet.getRuleStyle(pictoProgressTypeSelector);
		style.setColor(CHECKED_FOREGROUND);
		style = stylesheet.getRuleStyle(pictoSliderTypeSelector);
		style.setColor(CHECKED_FOREGROUND);
		style = stylesheet.getRuleStyle(progressBarTypeSelector);
		style.setColor(CHECKED_FOREGROUND);
		style = stylesheet.getRuleStyle(circularProgressBarTypeSelector);
		style.setColor(CHECKED_FOREGROUND);
		style = stylesheet.getRuleStyle(sliderTypeSelector);
		style.setColor(CHECKED_FOREGROUND);

		style = stylesheet.getRuleStyle(new AndCombinator(checkBoxTypeSelector, stateCheckedSelector));
		style.setColor(CHECKED_FOREGROUND);
		style.setBorder(new UniformRectangularBorder(CHECKED_FOREGROUND, 3));
		style.setBorder(new UniformRoundedBorder(CHECKED_FOREGROUND, 1000, 2));
		style = stylesheet.getRuleStyle(new AndCombinator(radioBoxTypeSelector, stateCheckedSelector));
		style.setColor(CHECKED_FOREGROUND);
		style.setBorder(new UniformRectangularBorder(CHECKED_FOREGROUND, 3));
		style.setBorder(new UniformRoundedBorder(CHECKED_FOREGROUND, 1000, 2));
		style = stylesheet.getRuleStyle(new AndCombinator(switchBoxTypeSelector, stateCheckedSelector));
		style.setColor(CHECKED_FOREGROUND);
		style.setBorder(new UniformRectangularBorder(CHECKED_FOREGROUND, 3));
		style.setBorder(new UniformRoundedBorder(CHECKED_FOREGROUND, 1000, 2));
	}

	private static void initializeListStyle(CascadingStylesheet stylesheet) {
		ClassSelector listItemSelector = new ClassSelector(ClassSelectors.LIST_ITEM);
		// Sets the list item style.
		EditableStyle style = stylesheet.getRuleStyle(listItemSelector);
		style.setPadding(new UniformOutline(10));

		// Sets the odd list item style.
		style = stylesheet.getRuleStyle(new AndCombinator(listItemSelector, OddChildSelector.ODD_CHILD_SELECTOR));
		style.setBackground(new PlainRectangularBackground(LIST_ODD_BACKGROUND));
	}

	private static void initializeKeyboardStyle(CascadingStylesheet stylesheet, StrictFontLoader fontLoader) {
		TypeSelector keyboardSelector = new TypeSelector(Keyboard.class);
		EditableStyle keyboardStyle = stylesheet.getRuleStyle(keyboardSelector);
		keyboardStyle.setBackground(new PlainRectangularBackground(KEYBOARD_BACKGROUND_COLOR));

		Selector keySelector = new TypeSelector(Key.class);
		EditableStyle keyStyle = stylesheet.getRuleStyle(keySelector);
		keyStyle.setColor(KEYBOARD_KEY_COLOR);
		keyStyle.setBackground(NoBackground.NO_BACKGROUND);
		keyStyle.setAlignment(Alignment.HCENTER_VCENTER);
		keyStyle.setMargin(new FlexibleOutline(4, 2, 4, 2));

		StateSelector activeSelector = new StateSelector(States.ACTIVE);
		AndCombinator activeKeySelector = new AndCombinator(keySelector, activeSelector);
		EditableStyle activeKeyStyle = stylesheet.getRuleStyle(activeKeySelector);
		activeKeyStyle.setColor(Colors.WHITE);
		activeKeyStyle.setBackground(new UniformPlainRoundedBackground(KEYBOARD_HIGHLIGHT_COLOR, KEY_CORNER_RADIUS));
		activeKeyStyle.setBorder(new UniformRoundedBorder(KEYBOARD_HIGHLIGHT_COLOR, KEY_CORNER_RADIUS - 1, 1));

		ClassSelector spaceKeySelector = new ClassSelector(ClassSelectors.SPACE_KEY_SELECTOR);
		EditableStyle spaceKeyStyle = stylesheet.getRuleStyle(spaceKeySelector);
		spaceKeyStyle.setBackground(new UniformPlainRoundedBackground(KEYBOARD_KEY_COLOR, KEY_CORNER_RADIUS));
		spaceKeyStyle.setBorder(new UniformRoundedBorder(KEYBOARD_KEY_COLOR, KEY_CORNER_RADIUS - 1, 1));

		ClassSelector activeShiftKeySelector = new ClassSelector(ClassSelectors.SHIFT_KEY_ACTIVE_SELECTOR);
		EditableStyle activeShiftKeyStyle = stylesheet.getRuleStyle(activeShiftKeySelector);
		activeShiftKeyStyle
				.setBackground(new UniformPlainRoundedBackground(MicroEJColors.CONCRETE_WHITE_50, KEY_CORNER_RADIUS));
		activeShiftKeyStyle
				.setBorder(new UniformRoundedBorder(MicroEJColors.CONCRETE_WHITE_50, KEY_CORNER_RADIUS - 1, 1));

		ClassSelector specialKeySelector = new ClassSelector(ClassSelectors.SPECIAL_KEY_SELECTOR);
		EditableStyle specialKeyStyle = stylesheet.getRuleStyle(specialKeySelector);
		specialKeyStyle.setColor(MicroEJColors.WHITE);
		specialKeyStyle.setBackground(new UniformPlainRoundedBackground(MicroEJColors.CORAL, KEY_CORNER_RADIUS));
		specialKeyStyle.setBorder(new UniformRoundedBorder(MicroEJColors.CORAL, KEY_CORNER_RADIUS - 1, 1));
		specialKeyStyle.setFont(fontLoader.getFont(FontFamilies.SOURCE_SANS_PRO, WidgetsFontLoader.MEDIUM));

		EditableStyle activeSpecialKeyStyle = stylesheet
				.getRuleStyle(new AndCombinator(specialKeySelector, new StateSelector(States.ACTIVE)));
		activeSpecialKeyStyle.setBackground(new UniformPlainRoundedBackground(ACTIVE_FOREGROUND, KEY_CORNER_RADIUS));
		specialKeyStyle.setBorder(new UniformRoundedBorder(ACTIVE_FOREGROUND, KEY_CORNER_RADIUS - 1, 1));
	}

	private static void initializeEditionStyle(CascadingStylesheet stylesheet, StrictFontLoader fontLoader) {
		TypeSelector textSelector = new TypeSelector(KeyboardText.class);
		EditableStyle textStyle = stylesheet.getRuleStyle(textSelector);
		textStyle.setColor(FOREGROUND);
		textStyle.setBackground(NoBackground.NO_BACKGROUND);
		textStyle.setBorder(new FlexibleRectangularBorder(FOREGROUND, 0, 0, 1, 0));
		textStyle.setAlignment(Alignment.LEFT | Alignment.VCENTER);
		textStyle.setMargin(new UniformOutline(5));
		textStyle.setPadding(new FlexibleOutline(0, 1, 1, 1));
		textStyle.setExtraField(KeyboardText.SELECTION_COLOR, TEXT_SELECTION_COLOR);
		textStyle.setExtraField(KeyboardText.CLEAR_BUTTON_FONT,
				fontLoader.getFont(FontFamilies.SOURCE_SANS_PRO, WidgetsFontLoader.LARGE));

		StateSelector activeSelector = new StateSelector(States.ACTIVE);
		AndCombinator focusedTextSelector = new AndCombinator(textSelector, activeSelector);
		EditableStyle focusedTextStyle = stylesheet.getRuleStyle(focusedTextSelector);
		focusedTextStyle.setBorder(new FlexibleRectangularBorder(CHECKED_FOREGROUND, 0, 0, 2, 0));
		focusedTextStyle.setPadding(new FlexibleOutline(0, 1, 0, 1));

		StateSelector emptySelector = new StateSelector(States.EMPTY);
		AndCombinator placeholderTextSelector = new AndCombinator(textSelector, emptySelector);
		EditableStyle placeholderTextStyle = stylesheet.getRuleStyle(placeholderTextSelector);
		placeholderTextStyle.setColor(TEXT_PLACEHOLDER_COLOR);

		ClassSelector formSelector = new ClassSelector(ClassSelectors.FORM);
		EditableStyle formStyle = stylesheet.getRuleStyle(formSelector);
		formStyle.setMargin(new FlexibleOutline(5, 10, 5, 10));

		ClassSelector resultLabelSelector = new ClassSelector(ClassSelectors.RESULT_LABEL);
		EditableStyle resultLabelStyle = stylesheet.getRuleStyle(resultLabelSelector);
		resultLabelStyle.setMargin(new UniformOutline(5));
	}

	private static void initializeChartStyle(CascadingStylesheet stylesheet, StrictFontLoader fontLoader) {
		// Sets the chart style.
		EditableStyle chartStyle = stylesheet.getRuleStyle(new ClassSelector(ClassSelectors.CHART));
		chartStyle.setColor(MicroEJColors.CONCRETE_WHITE_25);
		chartStyle.setBackground(NoBackground.NO_BACKGROUND);
		chartStyle.setFont(fontLoader.getFont(FontFamilies.SOURCE_SANS_PRO, WidgetsFontLoader.SMALL));
		chartStyle.setMargin(new FlexibleOutline(10, 40, 10, 40));
		chartStyle.setExtraField(BasicChart.SELECTED_COLOR, MicroEJColors.CORAL);

		// Sets the switch button style.
		Selector switchButtonSelector = new ClassSelector(ClassSelectors.SWITCH_BUTTON);
		EditableStyle switchButtonStyle = stylesheet.getRuleStyle(switchButtonSelector);
		switchButtonStyle.setMargin(new FlexibleOutline(4, 140, 13, 140));
		switchButtonStyle.setColor(MicroEJColors.WHITE);
		switchButtonStyle
				.setBackground(new UniformPlainRoundedBackground(MicroEJColors.CORAL, BUTTON_CORNER_RADIUS - 1));
		switchButtonStyle.setBorder(new UniformRoundedBorder(MicroEJColors.CORAL, BUTTON_CORNER_RADIUS, 1));
		switchButtonStyle.setFont(fontLoader.getFont(FontFamilies.SOURCE_SANS_PRO, WidgetsFontLoader.MEDIUM));
		switchButtonStyle.setAlignment(Alignment.HCENTER_VCENTER);

		// Sets the switch active button style.
		EditableStyle activeSwitchButtonStyle = stylesheet
				.getRuleStyle(new AndCombinator(switchButtonSelector, new StateSelector(States.ACTIVE)));
		activeSwitchButtonStyle
				.setBackground(new UniformPlainRoundedBackground(ACTIVE_FOREGROUND, BUTTON_CORNER_RADIUS - 1));
		switchButtonStyle.setBorder(new UniformRoundedBorder(ACTIVE_FOREGROUND, BUTTON_CORNER_RADIUS, 1));
	}

	private static void initializeDateStyle(CascadingStylesheet stylesheet, StrictFontLoader fontLoader) {
		EditableStyle wheelStyle = stylesheet.getRuleStyle(new TypeSelector(Wheel.class));
		wheelStyle.setColor(MicroEJColors.CONCRETE_BLACK_50);
		wheelStyle.setBackground(NoBackground.NO_BACKGROUND);
		wheelStyle.setExtraField(Wheel.LINE_COLOR_FIELD, WHEEL_LINE_COLOR);
		wheelStyle.setFont(fontLoader.getFont(FontFamilies.SOURCE_SANS_PRO, WidgetsFontLoader.LARGE));

		EditableStyle datePickerStyle = stylesheet.getRuleStyle(new ClassSelector(ClassSelectors.DATE_PICKER));
		datePickerStyle.setMargin(new UniformOutline(16));
	}
}
