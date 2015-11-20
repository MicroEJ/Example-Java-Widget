/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.is2t.demo.widgets.style;

import java.util.ArrayList;
import java.util.List;

import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.style.State;
import ej.style.background.PlainBackground;
import ej.style.cascading.CascadingStylesheet;
import ej.style.font.FontProfile;
import ej.style.font.FontProfile.FontSize;
import ej.style.outline.ComplexOutline;
import ej.style.outline.EmptyOutline;
import ej.style.outline.SimpleOutline;
import ej.style.util.SimpleStyle;
import ej.widget.basic.Label;
import ej.widget.composed.Button;

/**
 *
 */
public class WidgetsStylesheet extends CascadingStylesheet {
	/**
	 *
	 */
	public WidgetsStylesheet() {
		super();
		populate();
	}

	/**
	 *
	 */
	private void populate() {
		SimpleStyle defaultStyle = new SimpleStyle();
		defaultStyle.setForegroundColor(Colors.WHITE);
		PlainBackground defaultBackground = new PlainBackground();
		defaultBackground.setColor(0x404041);
		defaultStyle.setBorder(defaultBackground);
		defaultStyle.setAlignment(GraphicsContext.LEFT | GraphicsContext.VCENTER);
		setStyle(defaultStyle);

		SimpleOutline defaultMargin = new SimpleOutline();
		defaultMargin.setThickness(6);

		// Transparent label style.
		SimpleStyle transparentStyle = new SimpleStyle();
		EmptyOutline transparentBackground = new EmptyOutline();
		transparentStyle.setBorder(transparentBackground);
		setStyle(Label.class, transparentStyle);

		SimpleStyle mediumPictoStyle = new SimpleStyle();
		FontProfile mediumPictoFontProfile = new FontProfile();
		mediumPictoFontProfile.setFamily(FontFamily.PICTO);
		mediumPictoFontProfile.setSize(FontSize.MEDIUM);
		mediumPictoStyle.setFontProfile(mediumPictoFontProfile);
		mediumPictoStyle.setMargin(defaultMargin);
		setStyle(ClassSelector.MEDIUM_ICON, mediumPictoStyle);

		SimpleStyle largePictoStyle = new SimpleStyle();
		FontProfile largePictoFontProfile = new FontProfile();
		largePictoFontProfile.setFamily(FontFamily.PICTO);
		largePictoFontProfile.setSize(FontSize.LARGE);
		largePictoStyle.setFontProfile(largePictoFontProfile);
		setStyle(ClassSelector.LARGE_ICON, largePictoStyle);

		SimpleStyle mediumLabelStyle = new SimpleStyle();
		FontProfile mediumLabelFontProfile = new FontProfile();
		mediumLabelFontProfile.setFamily(FontFamily.ROBOTO);
		mediumLabelFontProfile.setSize(FontSize.MEDIUM);
		mediumLabelStyle.setFontProfile(mediumLabelFontProfile);
		mediumLabelStyle.setAlignment(GraphicsContext.LEFT | GraphicsContext.VCENTER);
		mediumLabelStyle.setMargin(defaultMargin);
		setStyle(ClassSelector.MEDIUM_LABEL, mediumLabelStyle);

		SimpleStyle largeLabelStyle = new SimpleStyle();
		FontProfile largeLabelFontProfile = new FontProfile();
		largeLabelFontProfile.setFamily(FontFamily.ROBOTO);
		largeLabelFontProfile.setSize(FontSize.LARGE);
		largeLabelStyle.setFontProfile(largeLabelFontProfile);
		largeLabelStyle.setAlignment(GraphicsContext.LEFT | GraphicsContext.VCENTER);
		largeLabelStyle.setMargin(defaultMargin);
		setStyle(ClassSelector.LARGE_LABEL, largeLabelStyle);

		SimpleStyle microejButtonStyle = new SimpleStyle();
		microejButtonStyle.setFontProfile(mediumLabelFontProfile);
		microejButtonStyle.setAlignment(GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		microejButtonStyle.setMargin(defaultMargin);
		PlainBackground microejButtonBackground = new PlainBackground();
		microejButtonBackground.setColor(0x10bdf1);
		microejButtonStyle.setBorder(microejButtonBackground);
		ComplexOutline microejButtonMargin = new ComplexOutline();
		microejButtonMargin.setLeft(60);
		microejButtonMargin.setRight(60);
		microejButtonMargin.setTop(12);
		microejButtonMargin.setBottom(12);
		microejButtonStyle.setMargin(microejButtonMargin);
		setStyle(Button.class, ClassSelector.MICROEJ_BUTTON, microejButtonStyle);

		SimpleStyle microejActiveButtonStyle = new SimpleStyle();
		microejActiveButtonStyle.setFontProfile(mediumLabelFontProfile);
		microejActiveButtonStyle.setAlignment(GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		microejActiveButtonStyle.setMargin(microejButtonMargin);
		PlainBackground microejActiveButtonBackground = new PlainBackground();
		microejActiveButtonBackground.setColor(0x1185a8);
		microejActiveButtonStyle.setBorder(microejActiveButtonBackground);

		List<String> selectors = new ArrayList<>();
		selectors.add(ClassSelector.MICROEJ_BUTTON);
		List<State> states = new ArrayList<>();
		states.add(State.Active);
		setStyle(Button.class, selectors, states, microejActiveButtonStyle);
	}

}
