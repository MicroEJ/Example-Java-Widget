/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microej.demo.widgets.style;

import ej.microui.display.Font;
import ej.style.font.FontLoader;
import ej.style.font.FontProfile;

/**
 * 
 */
public class WidgetsFontLoader implements FontLoader {

	private static final int PICTO_ID = 81;
	private static final int ROBOTO_ID = 82;

	@Override
	public Font getFont(FontProfile fontProfile) {
		String fontFamily = fontProfile.getFamily();

		if (fontFamily.startsWith(FontFamily.PICTO)) {
			switch (fontProfile.getSize()) {
			case MEDIUM:
				return Font.getFont(PICTO_ID, 30, Font.STYLE_PLAIN);
			case LARGE:
				return Font.getFont(PICTO_ID, 50, Font.STYLE_PLAIN);
			default:
				break;
			}
		} else if (fontFamily.startsWith(FontFamily.ROBOTO)) {
			switch (fontProfile.getSize()) {
			case MEDIUM:
				return Font.getFont(ROBOTO_ID, 30, Font.STYLE_PLAIN);
			case LARGE:
				return Font.getFont(ROBOTO_ID, 50, Font.STYLE_PLAIN);
			default:
				break;
			}
		}

		return Font.getDefaultFont();
	}

}
