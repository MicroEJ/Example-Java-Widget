/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets;

import java.util.ArrayList;
import java.util.List;

import com.microej.demo.widgets.page.MainPage;
import com.microej.demo.widgets.resources.FontFamilies;
import com.microej.demo.widgets.style.ClassSelector;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.style.State;
import ej.style.Stylesheet;
import ej.style.background.PlainBackground;
import ej.style.font.FontProfile;
import ej.style.font.FontProfile.FontSize;
import ej.style.outline.ComplexOutline;
import ej.style.outline.EmptyOutline;
import ej.style.outline.SimpleOutline;
import ej.style.util.SimpleStyle;
import ej.transition.desktop.HorizontalScreenshotTransitionDesktop;
import ej.transition.desktop.HorizontalTransitionDesktop;
import ej.transition.desktop.TransitionDesktop;
import ej.transition.page.ClassNameURLResolver;
import ej.transition.page.PagesStack;
import ej.transition.page.PagesStackURL;
import ej.transition.page.URLResolver;
import ej.widget.basic.Check;
import ej.widget.basic.CircularProgressBar;
import ej.widget.basic.Label;
import ej.widget.basic.ProgressBar;
import ej.widget.basic.Radio;
import ej.widget.basic.Slider;
import ej.widget.basic.Switch;
import ej.widget.basic.picto.PictoCheck;
import ej.widget.basic.picto.PictoProgressBar;
import ej.widget.basic.picto.PictoRadio;
import ej.widget.basic.picto.PictoSlider;
import ej.widget.basic.picto.PictoSwitch;

/**
 * This demo illustrates the widgets library.
 */
public class WidgetsDemo {

	private static final boolean WITH_SCREENSHOT_TRANSITION = System
			.getProperty("com.microej.demo.widgets.transition.screenshot") != null; //$NON-NLS-1$
	private static TransitionDesktop desktop;

	// Prevents initialization.
	private WidgetsDemo() {
	}

	/**
	 * Application entry point.
	 *
	 * @param args
	 *            not used.
	 */
	public static void main(String[] args) {
		MicroUI.start();
		initializeStylesheet();
		desktop = newTransitionDesktop();
		desktop.show(MainPage.class.getName());
		desktop.show();
	}

	private static TransitionDesktop newTransitionDesktop() {
		URLResolver urlResolver = new ClassNameURLResolver();
		PagesStack pagesStack = new PagesStackURL(urlResolver);
		if (WITH_SCREENSHOT_TRANSITION) {
			return new HorizontalScreenshotTransitionDesktop(urlResolver, pagesStack);
		} else {
			return new HorizontalTransitionDesktop(urlResolver, pagesStack);
		}
	}

	/**
	 * Shows the page corresponding to the given URL.
	 *
	 * @param url
	 *            the URL of the page to show.
	 */
	public static void show(String url) {
		desktop.show(url);
	}

	/**
	 * Shows the previous panel.
	 */
	public static void back() {
		desktop.back();
	}

	private static void initializeStylesheet() {
		Stylesheet stylesheet = ServiceLoaderFactory.getServiceLoader().getService(Stylesheet.class);

		// Sets the default style.
		SimpleStyle defaultStyle = new SimpleStyle();
		defaultStyle.setForegroundColor(Colors.WHITE);
		FontProfile defaultFontProfile = new FontProfile();
		defaultFontProfile.setFamily(FontFamilies.ROBOTO);
		defaultFontProfile.setSize(FontSize.MEDIUM);
		defaultStyle.setFontProfile(defaultFontProfile);
		PlainBackground defaultBackground = new PlainBackground();
		defaultBackground.setColor(0x404041);
		defaultStyle.setBorder(defaultBackground);
		defaultStyle.setAlignment(GraphicsContext.LEFT | GraphicsContext.VCENTER);
		stylesheet.setStyle(defaultStyle);

		// Default margin not added in the default style because it also applies for the composites.
		SimpleOutline defaultMargin = new SimpleOutline();
		defaultMargin.setThickness(6);

		// Sets the label style.
		SimpleStyle labelStyle = new SimpleStyle();
		labelStyle.setMargin(defaultMargin);
		// Especially useful for the buttons with a background.
		EmptyOutline transparentBackground = new EmptyOutline();
		labelStyle.setBorder(transparentBackground);
		stylesheet.setStyle(Label.class, labelStyle);

		// Sets the large picto style.
		SimpleStyle largePictoStyle = new SimpleStyle();
		FontProfile largePictoFontProfile = new FontProfile();
		largePictoFontProfile.setFamily(FontFamilies.PICTO);
		largePictoFontProfile.setSize(FontSize.LARGE);
		largePictoStyle.setFontProfile(largePictoFontProfile);
		stylesheet.setStyle(ClassSelector.LARGE_ICON, largePictoStyle);

		// Sets the large label style.
		SimpleStyle largeLabelStyle = new SimpleStyle();
		FontProfile largeLabelFontProfile = new FontProfile();
		largeLabelFontProfile.setFamily(FontFamilies.ROBOTO);
		largeLabelFontProfile.setSize(FontSize.LARGE);
		largeLabelStyle.setFontProfile(largeLabelFontProfile);
		stylesheet.setStyle(ClassSelector.LARGE_LABEL, largeLabelStyle);

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
		stylesheet.setStyle(Check.class, State.Active, widgetStyle);
		stylesheet.setStyle(Radio.class, State.Active, widgetStyle);
		stylesheet.setStyle(Switch.class, State.Active, widgetStyle);

		// Sets the picto widget and checked picto toggle style.
		SimpleStyle widgetPictoStyle = new SimpleStyle();
		widgetPictoStyle.setMargin(defaultMargin);
		widgetPictoStyle.setForegroundColor(0x10bdf1);
		widgetPictoStyle.setFontProfile(widgetPictoFontProfile);
		stylesheet.setStyle(PictoSlider.class, widgetPictoStyle);
		stylesheet.setStyle(PictoCheck.class, State.Active, widgetPictoStyle);
		stylesheet.setStyle(PictoRadio.class, State.Active, widgetPictoStyle);
		stylesheet.setStyle(PictoSwitch.class, State.Active, widgetPictoStyle);

		// Sets the pictos to use for the picto progress bar.
		SimpleStyle progressBarPictoStyle = new SimpleStyle();
		progressBarPictoStyle.setMargin(defaultMargin);
		progressBarPictoStyle.setForegroundColor(0x10bdf1);
		progressBarPictoStyle.setFontProfile(largePictoFontProfile);
		stylesheet.setStyle(PictoProgressBar.class, progressBarPictoStyle);

		// Sets the illustrated button style.
		SimpleStyle buttonStyle = new SimpleStyle();
		buttonStyle.setMargin(defaultMargin);
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
		stylesheet.setStyle(ClassSelector.ILLUSTRATED_BUTTON, buttonStyle);

		// Sets the illustrated active button style.
		SimpleStyle activeButtonStyle = new SimpleStyle();
		PlainBackground activeButtonBackground = new PlainBackground();
		activeButtonBackground.setColor(0x1185a8);
		activeButtonStyle.setBorder(activeButtonBackground);
		List<String> buttonSelector = new ArrayList<>();
		buttonSelector.add(ClassSelector.ILLUSTRATED_BUTTON);
		List<State> buttonStates = new ArrayList<>();
		buttonStates.add(State.Active);
		stylesheet.setStyle(buttonSelector, buttonStates, activeButtonStyle);
	}
}
