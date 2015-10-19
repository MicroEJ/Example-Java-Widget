/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets;

import com.is2t.demo.widgets.page.MainPage;
import com.is2t.demo.widgets.style.ClassSelector;
import com.is2t.demo.widgets.style.FontFamily;

import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.MicroUI;
import ej.mwt.Desktop;
import ej.mwt.Panel;
import ej.style.Stylesheet;
import ej.style.font.FontProfile;
import ej.style.font.FontProfile.FontSize;
import ej.style.util.SimpleStyle;

/**
 * This demo represents a settings menu you can find on your smartphone. The main page shows all available settings,
 * each one leading to a page illustrating a different widget.
 */
public class Widgets {

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
		createStylesheet();
		Desktop desktop = new Desktop();
		Panel panel = new Panel();

		panel.setWidget(new MainPage());
		// panel.setWidget(new VolumePage());
		// panel.setWidget(new ProfilePage());
		panel.show(desktop, true);
		desktop.show();
	}

	private static void createStylesheet() {
		Stylesheet stylesheet = ServiceLoaderFactory.getServiceLoader().getService(Stylesheet.class);

		SimpleStyle mediumPictoStyle = new SimpleStyle();
		FontProfile mediumPictoFontProfile = new FontProfile();
		mediumPictoFontProfile.setFamily(FontFamily.PICTO);
		mediumPictoFontProfile.setSize(FontSize.MEDIUM);
		mediumPictoStyle.setFontProfile(mediumPictoFontProfile);
		stylesheet.setStyle(ClassSelector.MEDIUM_ICON, mediumPictoStyle);

		SimpleStyle smallPictoStyle = new SimpleStyle();
		FontProfile smallPictoFontProfile = new FontProfile();
		smallPictoFontProfile.setFamily(FontFamily.PICTO);
		smallPictoFontProfile.setSize(FontSize.SMALL);
		smallPictoStyle.setFontProfile(smallPictoFontProfile);
		stylesheet.setStyle(ClassSelector.SMALL_ICON, smallPictoStyle);
	}
}
