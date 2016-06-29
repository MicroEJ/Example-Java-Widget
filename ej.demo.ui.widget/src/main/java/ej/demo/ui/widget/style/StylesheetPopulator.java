/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.demo.ui.widget.style;

import ej.microui.display.Colors;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.style.State;
import ej.style.Stylesheet;
import ej.style.background.NoBackground;
import ej.style.background.PlainBackground;
import ej.style.border.ComplexRectangularBorder;
import ej.style.font.FontProfile;
import ej.style.font.FontProfile.FontSize;
import ej.style.outline.ComplexOutline;
import ej.style.outline.SimpleOutline;
import ej.style.selector.ClassSelector;
import ej.style.selector.EvenChildSelector;
import ej.style.selector.StateSelector;
import ej.style.selector.TypeSelector;
import ej.style.selector.combinator.AndCombinator;
import ej.style.text.ComplexTextManager;
import ej.style.util.EditableStyle;
import ej.style.util.StyleHelper;
import ej.widget.basic.Image;
import ej.widget.basic.Label;
import ej.widget.basic.drawing.CheckBox;
import ej.widget.basic.drawing.CircularProgressBar;
import ej.widget.basic.drawing.ProgressBar;
import ej.widget.basic.drawing.RadioBox;
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

/**
 * Class responsible for initializing the demo styles.
 */
public class StylesheetPopulator {

	// Prevents initialization.
	private StylesheetPopulator() {
	}

	/**
	 * Populates the stylesheet.
	 */
	public static void initialize() {
		Stylesheet stylesheet = StyleHelper.getStylesheet();

		// Sets the default style.
		EditableStyle defaultStyle = new EditableStyle();
		defaultStyle.setForegroundColor(Colors.WHITE);
		defaultStyle.setBackgroundColor(0x404041);
		FontProfile defaultFontProfile = new FontProfile();
		defaultFontProfile.setFamily(FontFamilies.ROBOTO);
		defaultFontProfile.setSize(FontSize.MEDIUM);
		defaultStyle.setFontProfile(defaultFontProfile);
		// defaultStyle.setBackground(NoBackground.NO_BACKGROUND);
		defaultStyle.setAlignment(GraphicsContext.LEFT | GraphicsContext.VCENTER);
		stylesheet.setDefaultStyle(defaultStyle);

		// Default margin not added in the default style because it also applies
		// for the composites.
		SimpleOutline defaultMargin = new SimpleOutline(6);

		TypeSelector labelTypeSelector = new TypeSelector(Label.class);
		TypeSelector checkboxTypeSelector = new TypeSelector(CheckBox.class);
		TypeSelector radioboxTypeSelector = new TypeSelector(RadioBox.class);
		TypeSelector switchboxTypeSelector = new TypeSelector(SwitchBox.class);
		TypeSelector pictocheckTypeSelector = new TypeSelector(PictoCheck.class);
		TypeSelector pictoradioTypeSelector = new TypeSelector(PictoRadio.class);
		TypeSelector pictoswitchTypeSelector = new TypeSelector(PictoSwitch.class);
		StateSelector stateCheckedSelector = new StateSelector(State.Checked);
		ClassSelector listItemSelector = new ClassSelector(ClassSelectors.LIST_ITEM);

		// Sets the label style.
		EditableStyle labelStyle = new EditableStyle();
		labelStyle.setPadding(defaultMargin);
		stylesheet.addRule(new AndCombinator(labelTypeSelector, listItemSelector), labelStyle);
		labelStyle.setBackground(NoBackground.NO_BACKGROUND);
		stylesheet.addRule(labelTypeSelector, labelStyle);

		// Sets the large picto style.
		EditableStyle largePictoStyle = new EditableStyle();
		largePictoStyle.setFontProfile(new FontProfile(FontFamilies.PICTO, FontSize.LARGE, Font.STYLE_PLAIN));
		stylesheet.addRule(new ClassSelector(ClassSelectors.LARGE_ICON), largePictoStyle);

		// Sets the title style.
		EditableStyle titleStyle = new EditableStyle();
		titleStyle.setFontProfile(new FontProfile(FontFamilies.ROBOTO, FontSize.LARGE, Font.STYLE_PLAIN));
		ComplexRectangularBorder titleBorder = new ComplexRectangularBorder();
		titleBorder.setBottom(2);
		titleStyle.setBackgroundColor(Colors.SILVER);
		titleStyle.setBorder(titleBorder);
		stylesheet.addRule(new ClassSelector(ClassSelectors.TITLE), titleStyle);

		// Sets the list item style.
		EditableStyle listItemStyle = new EditableStyle();
		// ComplexRectangularBorder listItemBorder = new
		// ComplexRectangularBorder();
		// listItemBorder.setBottom(1);
		// listItemBorder.setColorBottom(Colors.GRAY);
		// listItemStyle.setBorder(listItemBorder);
		listItemStyle.setMargin(new ComplexOutline(0, 4, 0, 4));
		stylesheet.addRule(listItemSelector, listItemStyle);

		EditableStyle evenListItemStyle = new EditableStyle();
		evenListItemStyle.setBackground(new PlainBackground());
		evenListItemStyle.setBackgroundColor(0x505051);
		stylesheet.addRule(new AndCombinator(listItemSelector, new EvenChildSelector()), evenListItemStyle);

		// Sets the image style.
		EditableStyle imageStyle = new EditableStyle();
		// Align with back button size.
		imageStyle.setPadding(new ComplexOutline(0, 0, 0, 5));
		stylesheet.addRule(new TypeSelector(Image.class), imageStyle);

		// Sets the unchecked toggle style.
		EditableStyle toggleStyle = new EditableStyle();
		toggleStyle.setForegroundColor(0xbcbec0);
		toggleStyle.setMargin(defaultMargin);
		stylesheet.addRule(checkboxTypeSelector, toggleStyle);
		stylesheet.addRule(radioboxTypeSelector, toggleStyle);
		stylesheet.addRule(switchboxTypeSelector, toggleStyle);

		// The font to use for the most of the picto widgets.
		FontProfile widgetPictoFontProfile = new FontProfile(FontFamilies.PICTO, FontSize.MEDIUM, Font.STYLE_PLAIN);

		// Sets the unchecked picto toggle style.
		EditableStyle pictoToggleStyle = new EditableStyle();
		pictoToggleStyle.setFontProfile(widgetPictoFontProfile);
		pictoToggleStyle.setForegroundColor(0xbcbec0);
		pictoToggleStyle.setMargin(defaultMargin);
		stylesheet.addRule(pictocheckTypeSelector, pictoToggleStyle);
		stylesheet.addRule(pictoradioTypeSelector, pictoToggleStyle);
		stylesheet.addRule(pictoswitchTypeSelector, pictoToggleStyle);

		// Sets the widget and checked toggle style.
		EditableStyle widgetStyle = new EditableStyle();
		widgetStyle.setMargin(defaultMargin);
		widgetStyle.setForegroundColor(0x10bdf1);
		stylesheet.addRule(new TypeSelector(ProgressBar.class), widgetStyle);
		stylesheet.addRule(new TypeSelector(CircularProgressBar.class), widgetStyle);
		stylesheet.addRule(new TypeSelector(Slider.class), widgetStyle);
		stylesheet.addRule(new AndCombinator(checkboxTypeSelector, stateCheckedSelector), widgetStyle);
		stylesheet.addRule(new AndCombinator(radioboxTypeSelector, stateCheckedSelector), widgetStyle);
		stylesheet.addRule(new AndCombinator(switchboxTypeSelector, stateCheckedSelector), widgetStyle);

		// Sets the image widget style.
		EditableStyle widgetImageStyle = new EditableStyle();
		widgetImageStyle.setMargin(defaultMargin);
		stylesheet.addRule(new TypeSelector(ImageSlider.class), widgetImageStyle);
		stylesheet.addRule(new TypeSelector(ImageRadio.class), widgetImageStyle);
		stylesheet.addRule(new TypeSelector(ImageCheck.class), widgetImageStyle);
		stylesheet.addRule(new TypeSelector(ImageSwitch.class), widgetImageStyle);

		// Sets the picto widget and checked picto toggle style.
		EditableStyle widgetPictoStyle = new EditableStyle();
		widgetPictoStyle.setMargin(defaultMargin);
		widgetPictoStyle.setForegroundColor(0x10bdf1);
		widgetPictoStyle.setFontProfile(widgetPictoFontProfile);
		stylesheet.addRule(new TypeSelector(PictoSlider.class), widgetPictoStyle);
		stylesheet.addRule(new AndCombinator(pictocheckTypeSelector, stateCheckedSelector), widgetPictoStyle);
		stylesheet.addRule(new AndCombinator(pictoradioTypeSelector, stateCheckedSelector), widgetPictoStyle);
		stylesheet.addRule(new AndCombinator(pictoswitchTypeSelector, stateCheckedSelector), widgetPictoStyle);
		stylesheet.addRule(new TypeSelector(PictoProgress.class), widgetPictoStyle);

		// Sets the illustrated button style.
		ClassSelector illustratedButtonSelector = new ClassSelector(ClassSelectors.ILLUSTRATED_BUTTON);
		EditableStyle buttonStyle = new EditableStyle();
		buttonStyle.setBackgroundColor(0x10bdf1);
		buttonStyle.setMargin(new ComplexOutline(12, 60, 12, 60));
		// The content of the button is centered horizontally and vertically.
		buttonStyle.setAlignment(GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		stylesheet.addRule(illustratedButtonSelector, buttonStyle);

		// Sets the illustrated active button style.
		EditableStyle activeButtonStyle = new EditableStyle();
		activeButtonStyle.setBackgroundColor(0x1185a8);
		stylesheet.addRule(new AndCombinator(illustratedButtonSelector, new StateSelector(State.Active)),
				activeButtonStyle);

		// Sets the text title style.
		EditableStyle textTitleStyle = new EditableStyle();
		ComplexRectangularBorder textTitleBorder = new ComplexRectangularBorder();
		textTitleBorder.setBottom(1);
		textTitleStyle.setBackgroundColor(Colors.SILVER);
		textTitleStyle.setBorder(textTitleBorder);
		stylesheet.addRule(new ClassSelector(ClassSelectors.TEXT_TITLE), textTitleStyle);

		// Sets the multiline style.
		EditableStyle multilineStyle = new EditableStyle();
		ComplexTextManager complexTextManager = new ComplexTextManager(40);
		multilineStyle.setTextManager(complexTextManager);
		stylesheet.addRule(new ClassSelector(ClassSelectors.MULTILINE), multilineStyle);
	}
}
