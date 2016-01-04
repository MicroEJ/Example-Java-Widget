/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.demo.widgets.style;

import java.util.ArrayList;
import java.util.List;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.mwt.Desktop;
import ej.style.State;
import ej.style.Stylesheet;
import ej.style.background.PlainBackground;
import ej.style.border.ComplexRectangularBorder;
import ej.style.font.FontProfile;
import ej.style.font.FontProfile.FontSize;
import ej.style.outline.ComplexOutline;
import ej.style.outline.EmptyOutline;
import ej.style.outline.SimpleOutline;
import ej.style.text.ComplexTextManager;
import ej.style.util.SimpleStyle;
import ej.widget.basic.Check;
import ej.widget.basic.CircularProgressBar;
import ej.widget.basic.Image;
import ej.widget.basic.Label;
import ej.widget.basic.ProgressBar;
import ej.widget.basic.Radio;
import ej.widget.basic.Slider;
import ej.widget.basic.Switch;
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
 * Stylesheet utilities.
 */
public class StylesheetHelper {

	// Prevents initialization.
	private StylesheetHelper() {
	}

	/**
	 * Populates the stylesheet.
	 *
	 * @param desktop
	 *            the desktop used by the application.
	 */
	public static void initialize(Desktop desktop) {
		Stylesheet stylesheet = ServiceLoaderFactory.getServiceLoader().getService(Stylesheet.class);

		// Sets the default style.
		SimpleStyle defaultStyle = new SimpleStyle();
		defaultStyle.setForegroundColor(Colors.WHITE);
		FontProfile defaultFontProfile = new FontProfile();
		defaultFontProfile.setFamily(FontFamilies.ROBOTO);
		defaultFontProfile.setSize(FontSize.MEDIUM);
		defaultStyle.setFontProfile(defaultFontProfile);
		EmptyOutline transparentBackground = new EmptyOutline();
		defaultStyle.setBorder(transparentBackground);
		defaultStyle.setAlignment(GraphicsContext.LEFT | GraphicsContext.VCENTER);
		stylesheet.setStyle(defaultStyle);

		SimpleStyle desktopStyle = new SimpleStyle();
		PlainBackground desktopBackground = new PlainBackground();
		desktopBackground.setColor(0x404041);
		desktopStyle.setBorder(desktopBackground);
		stylesheet.setStyle(desktop, desktopStyle);

		// Default margin not added in the default style because it also applies for the composites.
		SimpleOutline defaultMargin = new SimpleOutline();
		defaultMargin.setThickness(6);

		// Sets the label style.
		SimpleStyle labelStyle = new SimpleStyle();
		labelStyle.setMargin(defaultMargin);
		stylesheet.setStyle(Label.class, labelStyle);

		// Sets the large picto style.
		SimpleStyle largePictoStyle = new SimpleStyle();
		FontProfile largePictoFontProfile = new FontProfile();
		largePictoFontProfile.setFamily(FontFamilies.PICTO);
		largePictoFontProfile.setSize(FontSize.LARGE);
		largePictoStyle.setFontProfile(largePictoFontProfile);
		stylesheet.setStyle(ClassSelectors.LARGE_ICON, largePictoStyle);

		// Sets the title style.
		SimpleStyle titleStyle = new SimpleStyle();
		FontProfile titleFontProfile = new FontProfile();
		titleFontProfile.setFamily(FontFamilies.ROBOTO);
		titleFontProfile.setSize(FontSize.LARGE);
		titleStyle.setFontProfile(titleFontProfile);
		ComplexRectangularBorder titleBorder = new ComplexRectangularBorder();
		titleBorder.setBottom(2);
		titleBorder.setColorBottom(Colors.SILVER);
		titleStyle.setBorder(titleBorder);
		stylesheet.setStyle(ClassSelectors.TITLE, titleStyle);

		// Sets the list item style.
		SimpleStyle listItemStyle = new SimpleStyle();
		ComplexRectangularBorder listItemBorder = new ComplexRectangularBorder();
		listItemBorder.setBottom(1);
		listItemBorder.setColorBottom(Colors.GRAY);
		listItemStyle.setBorder(listItemBorder);
		ComplexOutline listItemMargin = new ComplexOutline();
		listItemMargin.setLeft(4);
		listItemMargin.setRight(4);
		listItemStyle.setMargin(listItemMargin);
		stylesheet.setStyle(ClassSelectors.LIST_ITEM, listItemStyle);

		// Sets the image style.
		SimpleStyle imageStyle = new SimpleStyle();
		ComplexOutline imagePadding = new ComplexOutline();
		imagePadding.setLeft(5); // Align with back button size.
		imageStyle.setPadding(imagePadding);
		stylesheet.setStyle(Image.class, imageStyle);

		// Sets the unchecked toggle style.
		SimpleStyle toggleStyle = new SimpleStyle();
		toggleStyle.setForegroundColor(0xbcbec0);
		toggleStyle.setMargin(defaultMargin);
		stylesheet.setStyle(Check.class, toggleStyle);
		stylesheet.setStyle(Radio.class, toggleStyle);
		stylesheet.setStyle(Switch.class, toggleStyle);

		// The font to use for the most of the picto widgets.
		FontProfile widgetPictoFontProfile = new FontProfile();
		widgetPictoFontProfile.setFamily(FontFamilies.PICTO);
		widgetPictoFontProfile.setSize(FontSize.MEDIUM);

		// Sets the unchecked picto toggle style.
		SimpleStyle pictoToggleStyle = new SimpleStyle();
		pictoToggleStyle.setFontProfile(widgetPictoFontProfile);
		pictoToggleStyle.setForegroundColor(0xbcbec0);
		pictoToggleStyle.setMargin(defaultMargin);
		stylesheet.setStyle(PictoCheck.class, pictoToggleStyle);
		stylesheet.setStyle(PictoRadio.class, pictoToggleStyle);
		stylesheet.setStyle(PictoSwitch.class, pictoToggleStyle);

		// Sets the widget and checked toggle style.
		SimpleStyle widgetStyle = new SimpleStyle();
		widgetStyle.setMargin(defaultMargin);
		widgetStyle.setForegroundColor(0x10bdf1);
		stylesheet.setStyle(ProgressBar.class, widgetStyle);
		stylesheet.setStyle(CircularProgressBar.class, widgetStyle);
		stylesheet.setStyle(Slider.class, widgetStyle);
		stylesheet.setStyle(Check.class, State.Checked, widgetStyle);
		stylesheet.setStyle(Radio.class, State.Checked, widgetStyle);
		stylesheet.setStyle(Switch.class, State.Checked, widgetStyle);

		// Sets the image widget style.
		SimpleStyle widgetImageStyle = new SimpleStyle();
		widgetImageStyle.setMargin(defaultMargin);
		stylesheet.setStyle(ImageSlider.class, widgetImageStyle);
		stylesheet.setStyle(ImageRadio.class, widgetImageStyle);
		stylesheet.setStyle(ImageCheck.class, widgetImageStyle);
		stylesheet.setStyle(ImageSwitch.class, widgetImageStyle);

		// Sets the picto widget and checked picto toggle style.
		SimpleStyle widgetPictoStyle = new SimpleStyle();
		widgetPictoStyle.setMargin(defaultMargin);
		widgetPictoStyle.setForegroundColor(0x10bdf1);
		widgetPictoStyle.setFontProfile(widgetPictoFontProfile);
		stylesheet.setStyle(PictoSlider.class, widgetPictoStyle);
		stylesheet.setStyle(PictoCheck.class, State.Checked, widgetPictoStyle);
		stylesheet.setStyle(PictoRadio.class, State.Checked, widgetPictoStyle);
		stylesheet.setStyle(PictoSwitch.class, State.Checked, widgetPictoStyle);
		stylesheet.setStyle(PictoProgress.class, widgetPictoStyle);

		// Sets the illustrated button style.
		SimpleStyle buttonStyle = new SimpleStyle();
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
		stylesheet.setStyle(ClassSelectors.ILLUSTRATED_BUTTON, buttonStyle);

		// Sets the illustrated active button style.
		SimpleStyle activeButtonStyle = new SimpleStyle();
		PlainBackground activeButtonBackground = new PlainBackground();
		activeButtonBackground.setColor(0x1185a8);
		activeButtonStyle.setBorder(activeButtonBackground);
		List<String> buttonSelector = new ArrayList<>();
		buttonSelector.add(ClassSelectors.ILLUSTRATED_BUTTON);
		List<State> buttonStates = new ArrayList<>();
		buttonStates.add(State.Active);
		stylesheet.setStyle(buttonSelector, buttonStates, activeButtonStyle);

		// Sets the text title style.
		SimpleStyle textTitleStyle = new SimpleStyle();
		ComplexRectangularBorder textTitleBorder = new ComplexRectangularBorder();
		textTitleBorder.setBottom(1);
		textTitleBorder.setColorBottom(Colors.SILVER);
		textTitleStyle.setBorder(textTitleBorder);
		stylesheet.setStyle(ClassSelectors.TEXT_TITLE, textTitleStyle);

		// Sets the multiline style.
		SimpleStyle multilineStyle = new SimpleStyle();
		ComplexTextManager complexTextManager = new ComplexTextManager();
		complexTextManager.setLineHeight(40);
		multilineStyle.setTextManager(complexTextManager);
		stylesheet.setStyle(ClassSelectors.MULTILINE, multilineStyle);
	}
}
