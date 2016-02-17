/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.demo.widgets.style;

import java.io.IOException;

import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.style.State;
import ej.style.Stylesheet;
import ej.style.background.PlainBackground;
import ej.style.border.ComplexRectangularBorder;
import ej.style.font.FontProfile;
import ej.style.font.FontProfile.FontSize;
import ej.style.outline.ComplexOutline;
import ej.style.outline.EmptyOutline;
import ej.style.outline.SimpleOutline;
import ej.style.selector.ClassSelector;
import ej.style.selector.EvenChildSelector;
import ej.style.selector.InstanceSelector;
import ej.style.selector.SelectorHelper;
import ej.style.selector.StateSelector;
import ej.style.selector.TypeSelector;
import ej.style.selector.combinator.AndCombinator;
import ej.style.text.ComplexTextManager;
import ej.style.util.EditableStyle;
import ej.style.util.StyleHelper;
import ej.widget.StyledDesktop;
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
	 *
	 * @param desktop
	 *            the desktop used by the application.
	 * @throws IOException
	 */
	public static void initialize(StyledDesktop desktop) {
		Stylesheet stylesheet = StyleHelper.getStylesheet();

		// Sets the default style.
		EditableStyle defaultStyle = new EditableStyle();
		defaultStyle.setForegroundColor(Colors.WHITE);
		FontProfile defaultFontProfile = new FontProfile();
		defaultFontProfile.setFamily(FontFamilies.ROBOTO);
		defaultFontProfile.setSize(FontSize.MEDIUM);
		defaultStyle.setFontProfile(defaultFontProfile);
		EmptyOutline transparentBackground = new EmptyOutline();
		defaultStyle.setBorder(transparentBackground);
		defaultStyle.setAlignment(GraphicsContext.LEFT | GraphicsContext.VCENTER);
		stylesheet.setDefaultStyle(defaultStyle);

		EditableStyle desktopStyle = new EditableStyle();
		PlainBackground desktopBackground = new PlainBackground(0x404041);
		desktopStyle.setBorder(desktopBackground);
		stylesheet.addRule(new InstanceSelector(desktop), desktopStyle);

		// Default margin not added in the default style because it also applies for the composites.
		SimpleOutline defaultMargin = new SimpleOutline();
		defaultMargin.setThickness(6);

		// Sets the label style.
		EditableStyle labelStyle = new EditableStyle();
		labelStyle.setPadding(defaultMargin);
		stylesheet.addRule(new TypeSelector(Label.class), labelStyle);
		stylesheet.addRule(SelectorHelper.createSelector(Label.class, ClassSelectors.LIST_ITEM), labelStyle);

		// Sets the large picto style.
		EditableStyle largePictoStyle = new EditableStyle();
		FontProfile largePictoFontProfile = new FontProfile();
		largePictoFontProfile.setFamily(FontFamilies.PICTO);
		largePictoFontProfile.setSize(FontSize.LARGE);
		largePictoStyle.setFontProfile(largePictoFontProfile);
		stylesheet.addRule(new ClassSelector(ClassSelectors.LARGE_ICON), largePictoStyle);

		// Sets the title style.
		EditableStyle titleStyle = new EditableStyle();
		FontProfile titleFontProfile = new FontProfile();
		titleFontProfile.setFamily(FontFamilies.ROBOTO);
		titleFontProfile.setSize(FontSize.LARGE);
		titleStyle.setFontProfile(titleFontProfile);
		ComplexRectangularBorder titleBorder = new ComplexRectangularBorder();
		titleBorder.setBottom(2);
		titleBorder.setColorBottom(Colors.SILVER);
		titleStyle.setBorder(titleBorder);
		stylesheet.addRule(new ClassSelector(ClassSelectors.TITLE), titleStyle);

		// Sets the list item style.
		EditableStyle listItemStyle = new EditableStyle();
		// ComplexRectangularBorder listItemBorder = new ComplexRectangularBorder();
		// listItemBorder.setBottom(1);
		// listItemBorder.setColorBottom(Colors.GRAY);
		// listItemStyle.setBorder(listItemBorder);
		ComplexOutline listItemMargin = new ComplexOutline();
		listItemMargin.setLeft(4);
		listItemMargin.setRight(4);
		listItemStyle.setMargin(listItemMargin);
		stylesheet.addRule(new ClassSelector(ClassSelectors.LIST_ITEM), listItemStyle);

		EditableStyle evenListItemStyle = new EditableStyle();
		evenListItemStyle.setBorder(new PlainBackground(0x505051));
		stylesheet.addRule(new AndCombinator(new ClassSelector(ClassSelectors.LIST_ITEM), new EvenChildSelector()),
				evenListItemStyle);

		// Sets the image style.
		EditableStyle imageStyle = new EditableStyle();
		ComplexOutline imagePadding = new ComplexOutline();
		imagePadding.setLeft(5); // Align with back button size.
		imageStyle.setPadding(imagePadding);
		stylesheet.addRule(new TypeSelector(Image.class), imageStyle);

		// Sets the unchecked toggle style.
		EditableStyle toggleStyle = new EditableStyle();
		toggleStyle.setForegroundColor(0xbcbec0);
		toggleStyle.setMargin(defaultMargin);
		stylesheet.addRule(new TypeSelector(CheckBox.class), toggleStyle);
		stylesheet.addRule(new TypeSelector(RadioBox.class), toggleStyle);
		stylesheet.addRule(new TypeSelector(SwitchBox.class), toggleStyle);

		// The font to use for the most of the picto widgets.
		FontProfile widgetPictoFontProfile = new FontProfile();
		widgetPictoFontProfile.setFamily(FontFamilies.PICTO);
		widgetPictoFontProfile.setSize(FontSize.MEDIUM);

		// Sets the unchecked picto toggle style.
		EditableStyle pictoToggleStyle = new EditableStyle();
		pictoToggleStyle.setFontProfile(widgetPictoFontProfile);
		pictoToggleStyle.setForegroundColor(0xbcbec0);
		pictoToggleStyle.setMargin(defaultMargin);
		stylesheet.addRule(new TypeSelector(PictoCheck.class), pictoToggleStyle);
		stylesheet.addRule(new TypeSelector(PictoRadio.class), pictoToggleStyle);
		stylesheet.addRule(new TypeSelector(PictoSwitch.class), pictoToggleStyle);

		// Sets the widget and checked toggle style.
		EditableStyle widgetStyle = new EditableStyle();
		widgetStyle.setMargin(defaultMargin);
		widgetStyle.setForegroundColor(0x10bdf1);
		stylesheet.addRule(new TypeSelector(ProgressBar.class), widgetStyle);
		stylesheet.addRule(new TypeSelector(CircularProgressBar.class), widgetStyle);
		stylesheet.addRule(new TypeSelector(Slider.class), widgetStyle);
		stylesheet.addRule(SelectorHelper.createSelector(CheckBox.class, State.Checked), widgetStyle);
		stylesheet.addRule(SelectorHelper.createSelector(RadioBox.class, State.Checked), widgetStyle);
		stylesheet.addRule(SelectorHelper.createSelector(SwitchBox.class, State.Checked), widgetStyle);

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
		stylesheet.addRule(SelectorHelper.createSelector(PictoCheck.class, State.Checked), widgetPictoStyle);
		stylesheet.addRule(SelectorHelper.createSelector(PictoRadio.class, State.Checked), widgetPictoStyle);
		stylesheet.addRule(SelectorHelper.createSelector(PictoSwitch.class, State.Checked), widgetPictoStyle);
		stylesheet.addRule(new TypeSelector(PictoProgress.class), widgetPictoStyle);

		// Sets the illustrated button style.
		EditableStyle buttonStyle = new EditableStyle();
		PlainBackground buttonBackground = new PlainBackground();
		buttonBackground.setColor(0x10bdf1);
		buttonStyle.setBorder(buttonBackground);
		ComplexOutline buttonMargin = new ComplexOutline();
		buttonMargin.setLeft(60);
		buttonMargin.setRight(60);
		buttonMargin.setTop(12);
		buttonMargin.setBottom(12);
		buttonStyle.setMargin(buttonMargin);
		// The content of the button is centered horizontally and vertically.
		buttonStyle.setAlignment(GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		stylesheet.addRule(new ClassSelector(ClassSelectors.ILLUSTRATED_BUTTON), buttonStyle);

		// Sets the illustrated active button style.
		EditableStyle activeButtonStyle = new EditableStyle();
		PlainBackground activeButtonBackground = new PlainBackground();
		activeButtonBackground.setColor(0x1185a8);
		activeButtonStyle.setBorder(activeButtonBackground);
		AndCombinator activeButtonSelector = new AndCombinator(new ClassSelector(ClassSelectors.ILLUSTRATED_BUTTON),
				new StateSelector(State.Active));
		stylesheet.addRule(activeButtonSelector, activeButtonStyle);

		// Sets the text title style.
		EditableStyle textTitleStyle = new EditableStyle();
		ComplexRectangularBorder textTitleBorder = new ComplexRectangularBorder();
		textTitleBorder.setBottom(1);
		textTitleBorder.setColorBottom(Colors.SILVER);
		textTitleStyle.setBorder(textTitleBorder);
		stylesheet.addRule(new ClassSelector(ClassSelectors.TEXT_TITLE), textTitleStyle);

		// Sets the multiline style.
		EditableStyle multilineStyle = new EditableStyle();
		ComplexTextManager complexTextManager = new ComplexTextManager();
		complexTextManager.setLineHeight(40);
		multilineStyle.setTextManager(complexTextManager);
		stylesheet.addRule(new ClassSelector(ClassSelectors.MULTILINE), multilineStyle);
	}
}
