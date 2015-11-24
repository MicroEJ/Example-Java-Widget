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
import com.microej.demo.widgets.style.ClassSelector;
import com.microej.demo.widgets.style.FontFamily;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.MicroUI;
import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.mwt.Panel;
import ej.style.State;
import ej.style.Stylesheet;
import ej.style.background.PlainBackground;
import ej.style.font.FontProfile;
import ej.style.font.FontProfile.FontSize;
import ej.style.outline.ComplexOutline;
import ej.style.outline.EmptyOutline;
import ej.style.outline.SimpleOutline;
import ej.style.util.SimpleStyle;
import ej.transition.HorizontalTransitionDesktop;
import ej.transition.TransitionDesktop;
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
 * This demo illustrates the widgets library based on MicroUI-2.0 and MWT2.0.
 */
public class Widgets {

	private static TransitionDesktop desktop;

	// Prevents initialization.
	private Widgets() {
	}

	/**
	 * Application entry point.
	 *
	 * @param args
	 *            useless.
	 */
	public static void main(String[] args) {
		MicroUI.start();
		initializeStylesheet();
		desktop = new HorizontalTransitionDesktop();
		desktop.show(new MainPage());
		desktop.show();
	}

	/**
	 * Shows the given panel.
	 *
	 * @param panel
	 *            the panel to display.
	 */
	public static void show(Panel panel) {
		desktop.show(panel);
	}

	/**
	 * Shows the previous panel.
	 */
	public static void back() {
		desktop.back();
	}

	private static void initializeStylesheet() {
		Stylesheet stylesheet = ServiceLoaderFactory.getServiceLoader().getService(Stylesheet.class);

		SimpleStyle defaultStyle = new SimpleStyle();
		defaultStyle.setForegroundColor(Colors.WHITE);
		FontProfile defaultFontProfile = new FontProfile();
		defaultFontProfile.setFamily(FontFamily.ROBOTO);
		defaultFontProfile.setSize(FontSize.MEDIUM);
		defaultStyle.setFontProfile(defaultFontProfile);
		PlainBackground defaultBackground = new PlainBackground();
		defaultBackground.setColor(0x404041);
		defaultStyle.setBorder(defaultBackground);
		defaultStyle.setAlignment(GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		stylesheet.setStyle(defaultStyle);

		SimpleOutline defaultMargin = new SimpleOutline();
		defaultMargin.setThickness(6);

		SimpleStyle labelStyle = new SimpleStyle();
		EmptyOutline transparentBackground = new EmptyOutline();
		labelStyle.setBorder(transparentBackground);
		stylesheet.setStyle(Label.class, labelStyle);

		SimpleStyle largePictoStyle = new SimpleStyle();
		FontProfile largePictoFontProfile = new FontProfile();
		largePictoFontProfile.setFamily(FontFamily.PICTO);
		largePictoFontProfile.setSize(FontSize.LARGE);
		largePictoStyle.setFontProfile(largePictoFontProfile);
		stylesheet.setStyle(ClassSelector.LARGE_ICON, largePictoStyle);

		SimpleStyle mediumLabelStyle = new SimpleStyle();
		mediumLabelStyle.setMargin(defaultMargin);
		mediumLabelStyle.setAlignment(GraphicsContext.LEFT | GraphicsContext.VCENTER);
		stylesheet.setStyle(ClassSelector.MEDIUM_LABEL, mediumLabelStyle);

		SimpleStyle largeLabelStyle = new SimpleStyle();
		FontProfile largeLabelFontProfile = new FontProfile();
		largeLabelFontProfile.setFamily(FontFamily.ROBOTO);
		largeLabelFontProfile.setSize(FontSize.LARGE);
		largeLabelStyle.setFontProfile(largeLabelFontProfile);
		largeLabelStyle.setMargin(defaultMargin);
		largeLabelStyle.setAlignment(GraphicsContext.LEFT | GraphicsContext.VCENTER);
		stylesheet.setStyle(ClassSelector.LARGE_LABEL, largeLabelStyle);

		SimpleStyle toggleStyle = new SimpleStyle();
		toggleStyle.setForegroundColor(0xbcbec0);
		toggleStyle.setMargin(defaultMargin);
		stylesheet.setStyle(Check.class, toggleStyle);
		stylesheet.setStyle(Radio.class, toggleStyle);
		stylesheet.setStyle(Switch.class, toggleStyle);

		FontProfile widgetPictoFontProfile = new FontProfile();
		widgetPictoFontProfile.setFamily(FontFamily.PICTO);
		widgetPictoFontProfile.setSize(FontSize.MEDIUM);

		SimpleStyle pictoToggleStyle = new SimpleStyle();
		pictoToggleStyle.setFontProfile(widgetPictoFontProfile);
		pictoToggleStyle.setForegroundColor(0xbcbec0);
		pictoToggleStyle.setMargin(defaultMargin);
		stylesheet.setStyle(PictoCheck.class, pictoToggleStyle);
		stylesheet.setStyle(PictoRadio.class, pictoToggleStyle);
		stylesheet.setStyle(PictoSwitch.class, pictoToggleStyle);

		SimpleStyle widgetStyle = new SimpleStyle();
		widgetStyle.setMargin(defaultMargin);
		widgetStyle.setForegroundColor(0x10bdf1);
		stylesheet.setStyle(ProgressBar.class, widgetStyle);
		stylesheet.setStyle(CircularProgressBar.class, widgetStyle);
		stylesheet.setStyle(Slider.class, widgetStyle);
		stylesheet.setStyle(Check.class, State.Active, widgetStyle);
		stylesheet.setStyle(Radio.class, State.Active, widgetStyle);
		stylesheet.setStyle(Switch.class, State.Active, widgetStyle);

		SimpleStyle widgetPictoStyle = new SimpleStyle();
		widgetPictoStyle.setMargin(defaultMargin);
		widgetPictoStyle.setForegroundColor(0x10bdf1);
		widgetPictoStyle.setFontProfile(widgetPictoFontProfile);
		stylesheet.setStyle(PictoSlider.class, widgetPictoStyle);
		stylesheet.setStyle(PictoCheck.class, State.Active, widgetPictoStyle);
		stylesheet.setStyle(PictoRadio.class, State.Active, widgetPictoStyle);
		stylesheet.setStyle(PictoSwitch.class, State.Active, widgetPictoStyle);

		SimpleStyle progressBarPictoStyle = new SimpleStyle();
		progressBarPictoStyle.setMargin(defaultMargin);
		progressBarPictoStyle.setForegroundColor(0x10bdf1);
		progressBarPictoStyle.setFontProfile(largePictoFontProfile);
		stylesheet.setStyle(PictoProgressBar.class, progressBarPictoStyle);

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
		stylesheet.setStyle(ClassSelector.BUTTON, buttonStyle);

		SimpleStyle activeButtonStyle = new SimpleStyle();
		PlainBackground activeButtonBackground = new PlainBackground();
		activeButtonBackground.setColor(0x1185a8);
		activeButtonStyle.setBorder(activeButtonBackground);
		List<String> buttonSelector = new ArrayList<>();
		buttonSelector.add(ClassSelector.BUTTON);
		List<State> buttonStates = new ArrayList<>();
		buttonStates.add(State.Active);
		stylesheet.setStyle(buttonSelector, buttonStates, activeButtonStyle);
	}
}
