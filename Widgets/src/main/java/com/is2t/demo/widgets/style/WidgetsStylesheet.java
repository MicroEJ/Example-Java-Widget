/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.is2t.demo.widgets.style;

import ej.microui.display.GraphicsContext;
import ej.style.cascading.CascadingStylesheet;
import ej.style.font.FontProfile;
import ej.style.font.FontProfile.FontSize;
import ej.style.util.SimpleStyle;

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
		SimpleStyle mediumPictoStyle = new SimpleStyle();
		FontProfile mediumPictoFontProfile = new FontProfile();
		mediumPictoFontProfile.setFamily(FontFamily.PICTO);
		mediumPictoFontProfile.setSize(FontSize.MEDIUM);
		mediumPictoStyle.setFontProfile(mediumPictoFontProfile);
		setStyle(ClassSelector.MEDIUM_ICON, mediumPictoStyle);

		SimpleStyle largePictoStyle = new SimpleStyle();
		FontProfile largePictoFontProfile = new FontProfile();
		largePictoFontProfile.setFamily(FontFamily.PICTO);
		largePictoFontProfile.setSize(FontSize.LARGE);
		largePictoStyle.setFontProfile(largePictoFontProfile);
		setStyle(ClassSelector.LARGE_ICON, largePictoStyle);

		SimpleStyle mediumLabelStyle = new SimpleStyle();
		FontProfile mediumLabelFontProfile = new FontProfile();
		mediumLabelFontProfile.setFamily(FontFamily.OPEN_SANS);
		mediumLabelFontProfile.setSize(FontSize.MEDIUM);
		mediumLabelStyle.setFontProfile(mediumLabelFontProfile);
		mediumLabelStyle.setAlignment(GraphicsContext.LEFT | GraphicsContext.VCENTER);
		setStyle(ClassSelector.MEDIUM_LABEL, mediumLabelStyle);

		SimpleStyle largeLabelStyle = new SimpleStyle();
		FontProfile largeLabelFontProfile = new FontProfile();
		largeLabelFontProfile.setFamily(FontFamily.OPEN_SANS);
		largeLabelFontProfile.setSize(FontSize.LARGE);
		largeLabelStyle.setFontProfile(largeLabelFontProfile);
		largeLabelStyle.setAlignment(GraphicsContext.LEFT | GraphicsContext.VCENTER);
		setStyle(ClassSelector.LARGE_LABEL, largeLabelStyle);
	}

}
