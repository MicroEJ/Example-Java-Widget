/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.is2t.demo.widgets.style;

import ej.microui.display.DisplayFont;
import ej.style.font.FontLoader;
import ej.style.font.FontProfile;

/**
 * 
 */
public class WidgetsFontLoader implements FontLoader {

	private static final int PICTO_ID = 81;

	@Override
	public DisplayFont getFont(FontProfile fontProfile) {
		String fontFamily = fontProfile.getFamily();

		if (fontFamily.startsWith(FontFamily.PICTO)) {
			switch (fontProfile.getSize()) {
			case LARGE:
				return DisplayFont.getFont(PICTO_ID, 75, DisplayFont.STYLE_PLAIN);
			case SMALL:
				return DisplayFont.getFont(PICTO_ID, 30, DisplayFont.STYLE_PLAIN);
			default:
				return DisplayFont.getFont(PICTO_ID, 50, DisplayFont.STYLE_PLAIN);
			}
		}

		return DisplayFont.getDefaultFont();
	}

}
