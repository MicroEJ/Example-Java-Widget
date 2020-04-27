/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.style;

import ej.microui.display.Colors;
import ej.microui.display.Font;
import ej.microui.display.Image;
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
import ej.mwt.style.cascading.StyleBuilder;
import ej.mwt.style.container.Alignment;
import ej.mwt.style.dimension.FixedDimension;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.font.FontProfile;
import ej.mwt.style.font.FontProfile.FontSize;
import ej.mwt.style.outline.FlexibleOutline;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.selector.ClassSelector;
import ej.mwt.style.selector.OddChildSelector;
import ej.mwt.style.selector.StateSelector;
import ej.mwt.style.selector.TypeSelector;
import ej.mwt.style.selector.combinator.AndCombinator;
import ej.mwt.style.text.LineWrapTextStyle;
import ej.mwt.style.text.SingleLineTextStyle;
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
import ej.widget.chart.ChartPoint;
import ej.widget.keyboard.Key;
import ej.widget.keyboard.Keyboard;
import ej.widget.keyboard.KeyboardText;
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
	 * @return the stylesheet.
	 */
	public static Stylesheet createStylesheet() {
		CascadingStylesheet stylesheet = new CascadingStylesheet();

		// Sets the default style.
		StyleBuilder defaultStyle = new StyleBuilder();
		defaultStyle.setForegroundColor(FOREGROUND);
		defaultStyle.setBackgroundColor(BACKGROUND);
		FontProfile defaultFontProfile = new FontProfile(FontFamilies.SOURCE_SANS_PRO, FontSize.MEDIUM,
				Font.STYLE_PLAIN);
		defaultStyle.setFontProfile(defaultFontProfile);
		defaultStyle.setAlignment(Alignment.LEFT | Alignment.VCENTER);
		stylesheet.setDefaultStyle(defaultStyle);

		TypeSelector labelTypeSelector = new TypeSelector(Label.class);

		// Sets the label style.
		StyleBuilder style = new StyleBuilder();
		style.setBackground(NoBackground.NO_BACKGROUND);
		stylesheet.addRule(labelTypeSelector, style);

		// Sets the scroll style
		style.clear();
		style.setBackground(NoBackground.NO_BACKGROUND);
		stylesheet.addRule(new TypeSelector(Scrollbar.class), style);

		// Sets the optimal size style.
		style.clear();
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		stylesheet.addRule(new ClassSelector(ClassSelectors.OPTIMAL_SIZE), style);

		// Sets the top bar style.
		style.clear();
		style.setPadding(new UniformOutline(7));
		style.setFontProfile(new FontProfile(FontFamilies.SOURCE_SANS_PRO, FontSize.LARGE, Font.STYLE_PLAIN));
		style.setForegroundColor(TITLE_FOREGROUND);
		style.setBorder(new FlexibleRectangularBorder(TITLE_BORDER, 0, 0, 2, 0));
		style.setDimension(new FixedDimension(Widget.NO_CONSTRAINT, 32));
		stylesheet.addRule(new ClassSelector(ClassSelectors.TOP_BAR), style);

		// Sets the title style.
		style.clear();
		style.setMargin(new FlexibleOutline(0, 0, 0, 15));
		stylesheet.addRule(new ClassSelector(ClassSelectors.TITLE), style);

		// Sets the centered style.
		style.clear();
		style.setAlignment(Alignment.HCENTER_VCENTER);
		stylesheet.addRule(new ClassSelector(ClassSelectors.CENTERED), style);

		// Sets the image style.
		style.clear();
		// Align with back button size.
		style.setPadding(new FlexibleOutline(0, 0, 0, 5));
		stylesheet.addRule(new TypeSelector(Image.class), style);

		// Sets the illustrated button style.
		style.clear();
		style.setPadding(new FlexibleOutline(BUTTON_CORNER_RADIUS / 2, BUTTON_CORNER_RADIUS * 2,
				BUTTON_CORNER_RADIUS / 2, BUTTON_CORNER_RADIUS * 2));
		style.setForegroundColor(MicroEJColors.WHITE);
		style.setBackgroundColor(MicroEJColors.CORAL);
		style.setBackground(new UniformPlainRoundedBackground(BUTTON_CORNER_RADIUS));
		style.setBorder(new UniformRoundedBorder(MicroEJColors.CORAL, BUTTON_CORNER_RADIUS - 1, 1));
		Selector illustratedButtonSelector = new ClassSelector(ClassSelectors.ILLUSTRATED_BUTTON);
		stylesheet.addRule(illustratedButtonSelector, style);

		// Sets the illustrated active button style.
		style.clear();
		style.setBackgroundColor(ACTIVE_FOREGROUND);
		style.setBorder(new UniformRoundedBorder(ACTIVE_FOREGROUND, BUTTON_CORNER_RADIUS - 1, 1));
		stylesheet.addRule(new AndCombinator(illustratedButtonSelector, new StateSelector(States.ACTIVE)), style);

		// Sets the text scroll style.
		style.clear();
		style.setPadding(new UniformOutline(12));
		stylesheet.addRule(new ClassSelector(ClassSelectors.TEXT_SCROLL), style);

		// Sets the text title style.
		style.clear();
		FlexibleRectangularBorder textTitleBorder = new FlexibleRectangularBorder(TITLE_BORDER, 0, 0, 1, 0);
		style.setBorder(textTitleBorder);
		stylesheet.addRule(new ClassSelector(ClassSelectors.TEXT_TITLE), style);

		// Sets the multiline style.
		style.clear();
		style.setTextStyle(new LineWrapTextStyle(25));
		style.setPadding(new FlexibleOutline(0, 0, 20, 0));
		stylesheet.addRule(new ClassSelector(ClassSelectors.MULTILINE), style);

		initializeWidgetsStyle(stylesheet);

		initializeListStyle(stylesheet);

		initializeKeyboardStyle(stylesheet);

		initializeEditionStyle(stylesheet);

		initializeChartStyle(stylesheet);

		initializeDateStyle(stylesheet);

		return stylesheet;
	}

	private static void initializeWidgetsStyle(CascadingStylesheet stylesheet) {
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

		// The font to use for the most of the picto widgets.
		FontProfile widgetPictoFontProfile = new FontProfile(FontFamilies.PICTO, FontFamilies.PICTO_SIZE,
				Font.STYLE_PLAIN);

		// Sets the picto style.
		StyleBuilder style = new StyleBuilder();
		style.setFontProfile(widgetPictoFontProfile);
		stylesheet.addRule(pictoProgressTypeSelector, style);
		stylesheet.addRule(pictoSliderTypeSelector, style);
		stylesheet.addRule(pictoCheckTypeSelector, style);
		stylesheet.addRule(pictoRadioTypeSelector, style);
		stylesheet.addRule(pictoSwitchTypeSelector, style);

		// Sets the unchecked toggle style.
		style.clear();
		style.setForegroundColor(FOREGROUND);
		style.setMargin(defaultMargin);
		style.setAlignment(Alignment.HCENTER_VCENTER);
		stylesheet.addRule(checkBoxTypeSelector, style);
		stylesheet.addRule(radioBoxTypeSelector, style);
		stylesheet.addRule(switchBoxTypeSelector, style);

		style.clear();
		style.setBorder(new UniformRectangularBorder(FOREGROUND, 3));
		style.setPadding(new UniformOutline(3));
		stylesheet.addRule(checkBoxTypeSelector, style);

		style.clear();
		style.setBorder(new UniformRoundedBorder(FOREGROUND, 1000, 2));
		style.setPadding(new UniformOutline(4));
		stylesheet.addRule(radioBoxTypeSelector, style);

		style.clear();
		style.setBorder(new UniformRoundedBorder(FOREGROUND, 1000, 2));
		style.setPadding(new FlexibleOutline(4, 20, 4, 4));
		style.setAlignment(Alignment.LEFT | Alignment.VCENTER);
		stylesheet.addRule(switchBoxTypeSelector, style);

		style.clear();
		style.setPadding(new FlexibleOutline(4, 4, 4, 20));
		style.setAlignment(Alignment.RIGHT | Alignment.VCENTER);
		stylesheet.addRule(new AndCombinator(switchBoxTypeSelector, stateCheckedSelector), style);

		style.clear();
		style.setDimension(new FixedDimension(Widget.NO_CONSTRAINT, 10));
		style.setBackground(PlainRectangularBackground.PLAIN_RECTANGULAR_BACKGROUND);
		style.setBackgroundColor(ACTIVE_FOREGROUND);
		stylesheet.addRule(progressBarTypeSelector, style);

		// Sets the image widgets style.
		style.clear();
		style.setMargin(defaultMargin);
		stylesheet.addRule(pictoProgressTypeSelector, style);
		stylesheet.addRule(pictoSliderTypeSelector, style);
		stylesheet.addRule(pictoCheckTypeSelector, style);
		stylesheet.addRule(pictoRadioTypeSelector, style);
		stylesheet.addRule(pictoSwitchTypeSelector, style);
		stylesheet.addRule(progressBarTypeSelector, style);
		stylesheet.addRule(circularProgressBarTypeSelector, style);
		stylesheet.addRule(imageSliderTypeSelector, style);
		stylesheet.addRule(sliderTypeSelector, style);
		stylesheet.addRule(imageRadioTypeSelector, style);
		stylesheet.addRule(imageCheckTypeSelector, style);
		stylesheet.addRule(imageSwitchTypeSelector, style);

		// Sets the checked toggles style.
		style.clear();
		style.setForegroundColor(CHECKED_FOREGROUND);
		stylesheet.addRule(new AndCombinator(pictoCheckTypeSelector, stateCheckedSelector), style);
		stylesheet.addRule(new AndCombinator(pictoRadioTypeSelector, stateCheckedSelector), style);
		stylesheet.addRule(new AndCombinator(pictoSwitchTypeSelector, stateCheckedSelector), style);
		stylesheet.addRule(new AndCombinator(imageRadioTypeSelector, stateCheckedSelector), style);
		stylesheet.addRule(new AndCombinator(imageCheckTypeSelector, stateCheckedSelector), style);
		stylesheet.addRule(new AndCombinator(imageSwitchTypeSelector, stateCheckedSelector), style);
		stylesheet.addRule(pictoProgressTypeSelector, style);
		stylesheet.addRule(pictoSliderTypeSelector, style);
		stylesheet.addRule(progressBarTypeSelector, style);
		stylesheet.addRule(circularProgressBarTypeSelector, style);
		stylesheet.addRule(sliderTypeSelector, style);

		style.clear();
		style.setForegroundColor(CHECKED_FOREGROUND);
		style.setBorder(new UniformRectangularBorder(CHECKED_FOREGROUND, 3));
		stylesheet.addRule(new AndCombinator(checkBoxTypeSelector, stateCheckedSelector), style);
		style.setBorder(new UniformRoundedBorder(CHECKED_FOREGROUND, 1000, 2));
		stylesheet.addRule(new AndCombinator(radioBoxTypeSelector, stateCheckedSelector), style);
		stylesheet.addRule(new AndCombinator(switchBoxTypeSelector, stateCheckedSelector), style);
	}

	private static void initializeListStyle(CascadingStylesheet stylesheet) {
		ClassSelector listItemSelector = new ClassSelector(ClassSelectors.LIST_ITEM);
		// Sets the list item style.
		StyleBuilder style = new StyleBuilder();
		style.setPadding(new UniformOutline(10));
		stylesheet.addRule(listItemSelector, style);

		// Sets the odd list item style.
		style.clear();
		style.setBackground(PlainRectangularBackground.PLAIN_RECTANGULAR_BACKGROUND);
		style.setBackgroundColor(LIST_ODD_BACKGROUND);
		stylesheet.addRule(new AndCombinator(listItemSelector, OddChildSelector.ODD_CHILD_SELECTOR), style);
	}

	private static void initializeKeyboardStyle(CascadingStylesheet stylesheet) {
		StyleBuilder keyboardStyle = new StyleBuilder();
		keyboardStyle.setBackground(PlainRectangularBackground.PLAIN_RECTANGULAR_BACKGROUND);
		keyboardStyle.setBackgroundColor(KEYBOARD_BACKGROUND_COLOR);
		TypeSelector keyboardSelector = new TypeSelector(Keyboard.class);
		stylesheet.addRule(keyboardSelector, keyboardStyle);

		StyleBuilder keyStyle = new StyleBuilder();
		keyStyle.setForegroundColor(KEYBOARD_KEY_COLOR);
		keyStyle.setBackground(NoBackground.NO_BACKGROUND);
		keyStyle.setAlignment(Alignment.HCENTER_VCENTER);
		keyStyle.setMargin(new FlexibleOutline(4, 2, 4, 2));
		Selector keySelector = new TypeSelector(Key.class);
		stylesheet.addRule(keySelector, keyStyle);

		StyleBuilder activeKeyStyle = new StyleBuilder();
		activeKeyStyle.setForegroundColor(Colors.WHITE);
		activeKeyStyle.setBackground(new UniformPlainRoundedBackground(KEY_CORNER_RADIUS));
		activeKeyStyle.setBackgroundColor(KEYBOARD_HIGHLIGHT_COLOR);
		activeKeyStyle.setBorder(new UniformRoundedBorder(KEYBOARD_HIGHLIGHT_COLOR, KEY_CORNER_RADIUS - 1, 1));
		StateSelector activeSelector = new StateSelector(States.ACTIVE);
		AndCombinator activeKeySelector = new AndCombinator(keySelector, activeSelector);
		stylesheet.addRule(activeKeySelector, activeKeyStyle);

		StyleBuilder spaceKeyStyle = new StyleBuilder();
		spaceKeyStyle.setBackground(new UniformPlainRoundedBackground(KEY_CORNER_RADIUS));
		spaceKeyStyle.setBackgroundColor(KEYBOARD_KEY_COLOR);
		spaceKeyStyle.setBorder(new UniformRoundedBorder(KEYBOARD_KEY_COLOR, KEY_CORNER_RADIUS - 1, 1));
		ClassSelector spaceKeySelector = new ClassSelector(ClassSelectors.SPACE_KEY_SELECTOR);
		stylesheet.addRule(spaceKeySelector, spaceKeyStyle);

		StyleBuilder activeShiftKeyStyle = new StyleBuilder();
		activeShiftKeyStyle.setBackground(new UniformPlainRoundedBackground(KEY_CORNER_RADIUS));
		activeShiftKeyStyle.setBackgroundColor(MicroEJColors.CONCRETE_WHITE_50);
		activeShiftKeyStyle
				.setBorder(new UniformRoundedBorder(MicroEJColors.CONCRETE_WHITE_50, KEY_CORNER_RADIUS - 1, 1));
		ClassSelector activeShiftKeySelector = new ClassSelector(ClassSelectors.SHIFT_KEY_ACTIVE_SELECTOR);
		stylesheet.addRule(activeShiftKeySelector, activeShiftKeyStyle);

		StyleBuilder specialKeyStyle = new StyleBuilder();
		specialKeyStyle.setForegroundColor(MicroEJColors.WHITE);
		specialKeyStyle.setBackgroundColor(MicroEJColors.CORAL);
		specialKeyStyle.setBackground(new UniformPlainRoundedBackground(KEY_CORNER_RADIUS));
		specialKeyStyle.setBorder(new UniformRoundedBorder(MicroEJColors.CORAL, KEY_CORNER_RADIUS - 1, 1));
		FontProfile specialKeyFont = new FontProfile(FontFamilies.SOURCE_SANS_PRO, FontSize.MEDIUM, Font.STYLE_PLAIN);
		specialKeyStyle.setFontProfile(specialKeyFont);
		ClassSelector specialKeySelector = new ClassSelector(ClassSelectors.SPECIAL_KEY_SELECTOR);
		stylesheet.addRule(specialKeySelector, specialKeyStyle);

		StyleBuilder activeSpecialKeyStyle = new StyleBuilder();
		activeSpecialKeyStyle.setBackgroundColor(ACTIVE_FOREGROUND);
		specialKeyStyle.setBorder(new UniformRoundedBorder(ACTIVE_FOREGROUND, KEY_CORNER_RADIUS - 1, 1));
		stylesheet.addRule(new AndCombinator(specialKeySelector, new StateSelector(States.ACTIVE)),
				activeSpecialKeyStyle);
	}

	private static void initializeEditionStyle(CascadingStylesheet stylesheet) {
		StyleBuilder textStyle = new StyleBuilder();
		textStyle.setForegroundColor(FOREGROUND);
		textStyle.setBackground(NoBackground.NO_BACKGROUND);
		textStyle.setBorder(new FlexibleRectangularBorder(FOREGROUND, 0, 0, 1, 0));
		textStyle.setAlignment(Alignment.LEFT | Alignment.VCENTER);
		textStyle.setTextStyle(SingleLineTextStyle.SINGLE_LINE_TEXT_STYLE);
		textStyle.setMargin(new UniformOutline(5));
		textStyle.setPadding(new FlexibleOutline(0, 1, 1, 1));
		TypeSelector textSelector = new TypeSelector(KeyboardText.class);
		stylesheet.addRule(textSelector, textStyle);

		StyleBuilder focusedTextStyle = new StyleBuilder();
		focusedTextStyle.setBorder(new FlexibleRectangularBorder(CHECKED_FOREGROUND, 0, 0, 2, 0));
		focusedTextStyle.setPadding(new FlexibleOutline(0, 1, 0, 1));
		StateSelector activeSelector = new StateSelector(States.ACTIVE);
		AndCombinator focusedTextSelector = new AndCombinator(textSelector, activeSelector);
		stylesheet.addRule(focusedTextSelector, focusedTextStyle);

		StyleBuilder placeholderTextStyle = new StyleBuilder();
		placeholderTextStyle.setForegroundColor(TEXT_PLACEHOLDER_COLOR);
		StateSelector emptySelector = new StateSelector(States.EMPTY);
		AndCombinator placeholderTextSelector = new AndCombinator(textSelector, emptySelector);
		stylesheet.addRule(placeholderTextSelector, placeholderTextStyle);

		StyleBuilder selectionStyle = new StyleBuilder();
		selectionStyle.setForegroundColor(TEXT_SELECTION_COLOR);
		stylesheet.addRule(new ClassSelector(ClassSelectors.CLASS_SELECTOR_SELECTION), selectionStyle);

		StyleBuilder clearButtonStyle = new StyleBuilder();
		clearButtonStyle.setAlignment(Alignment.RIGHT | Alignment.VCENTER);
		clearButtonStyle.setForegroundColor(FOREGROUND);
		FontProfile clearButtonFont = new FontProfile(FontFamilies.SOURCE_SANS_PRO, FontSize.LARGE, Font.STYLE_PLAIN);
		clearButtonStyle.setFontProfile(clearButtonFont);
		stylesheet.addRule(new ClassSelector(ClassSelectors.CLASS_SELECTOR_CLEAR_BUTTON), clearButtonStyle);

		StyleBuilder formStyle = new StyleBuilder();
		formStyle.setMargin(new FlexibleOutline(5, 10, 5, 10));
		ClassSelector formSelector = new ClassSelector(ClassSelectors.FORM);
		stylesheet.addRule(formSelector, formStyle);

		StyleBuilder resultLabelStyle = new StyleBuilder();
		resultLabelStyle.setMargin(new UniformOutline(5));
		ClassSelector resultLabelSelector = new ClassSelector(ClassSelectors.RESULT_LABEL);
		stylesheet.addRule(resultLabelSelector, resultLabelStyle);
	}

	private static void initializeChartStyle(CascadingStylesheet stylesheet) {
		// Sets the chart style.
		StyleBuilder chartStyle = new StyleBuilder();
		chartStyle.setForegroundColor(MicroEJColors.CONCRETE_WHITE_25);
		chartStyle.setBackground(NoBackground.NO_BACKGROUND);
		FontProfile chartFont = new FontProfile(FontFamilies.SOURCE_SANS_PRO, FontSize.SMALL, Font.STYLE_PLAIN);
		chartStyle.setFontProfile(chartFont);
		chartStyle.setMargin(new FlexibleOutline(10, 40, 10, 40));
		stylesheet.addRule(new ClassSelector(ClassSelectors.CHART), chartStyle);

		// Sets the chart point style.
		StyleBuilder chartPointStyle = new StyleBuilder();
		chartPointStyle.setForegroundColor(MicroEJColors.CONCRETE_WHITE_25);
		Selector chartPointSelector = new TypeSelector(ChartPoint.class);
		stylesheet.addRule(chartPointSelector, chartPointStyle);

		// Sets the selected chart point style.
		StyleBuilder chartPointSelectedStyle = new StyleBuilder();
		chartPointSelectedStyle.setForegroundColor(MicroEJColors.CORAL);
		Selector chartPointSelectedSelector = new AndCombinator(chartPointSelector, new StateSelector(States.CHECKED));
		stylesheet.addRule(chartPointSelectedSelector, chartPointSelectedStyle);

		// Sets the selected chart point value style.
		StyleBuilder chartPointValueStyle = new StyleBuilder();
		chartPointValueStyle.setForegroundColor(MicroEJColors.CORAL);
		FontProfile chartPointValueFont = new FontProfile(FontFamilies.SOURCE_SANS_PRO, FontSize.SMALL,
				Font.STYLE_PLAIN);
		chartPointValueStyle.setFontProfile(chartPointValueFont);
		Selector chartPointValueSelector = new ClassSelector(ClassSelectors.SELECTED_VALUE);
		stylesheet.addRule(chartPointValueSelector, chartPointValueStyle);

		// Sets the switch button style.
		StyleBuilder switchButtonStyle = new StyleBuilder();
		switchButtonStyle.setMargin(new FlexibleOutline(4, 140, 13, 140));
		switchButtonStyle.setForegroundColor(MicroEJColors.WHITE);
		switchButtonStyle.setBackgroundColor(MicroEJColors.CORAL);
		switchButtonStyle.setBackground(new UniformPlainRoundedBackground(BUTTON_CORNER_RADIUS - 1));
		switchButtonStyle.setBorder(new UniformRoundedBorder(MicroEJColors.CORAL, BUTTON_CORNER_RADIUS, 1));
		FontProfile switchButtonFont = new FontProfile(FontFamilies.SOURCE_SANS_PRO, FontSize.MEDIUM, Font.STYLE_PLAIN);
		switchButtonStyle.setFontProfile(switchButtonFont);
		switchButtonStyle.setAlignment(Alignment.HCENTER_VCENTER);
		Selector switchButtonSelector = new ClassSelector(ClassSelectors.SWITCH_BUTTON);
		stylesheet.addRule(switchButtonSelector, switchButtonStyle);

		// Sets the switch active button style.
		StyleBuilder activeSwitchButtonStyle = new StyleBuilder();
		activeSwitchButtonStyle.setBackgroundColor(ACTIVE_FOREGROUND);
		switchButtonStyle.setBorder(new UniformRoundedBorder(ACTIVE_FOREGROUND, BUTTON_CORNER_RADIUS, 1));
		stylesheet.addRule(new AndCombinator(switchButtonSelector, new StateSelector(States.ACTIVE)),
				activeSwitchButtonStyle);
	}

	private static void initializeDateStyle(CascadingStylesheet stylesheet) {
		StyleBuilder wheelStyle = new StyleBuilder();
		wheelStyle.setForegroundColor(MicroEJColors.CONCRETE_BLACK_50);
		wheelStyle.setBackgroundColor(MicroEJColors.CONCRETE_WHITE_25);
		wheelStyle.setBackground(NoBackground.NO_BACKGROUND);
		// wheelStyle.setBorderColor(WHEEL_LINE_COLOR);
		FontProfile wheelFont = new FontProfile(FontFamilies.SOURCE_SANS_PRO, FontSize.LARGE, Font.STYLE_PLAIN);
		wheelStyle.setFontProfile(wheelFont);
		stylesheet.addRule(new TypeSelector(Wheel.class), wheelStyle);

		StyleBuilder datePickerStyle = new StyleBuilder();
		datePickerStyle.setMargin(new UniformOutline(16));
		stylesheet.addRule(new ClassSelector(ClassSelectors.DATE_PICKER), datePickerStyle);
	}
}
