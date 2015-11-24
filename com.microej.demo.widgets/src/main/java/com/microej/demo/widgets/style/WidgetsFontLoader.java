/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.style;

import ej.microui.display.Font;
import ej.style.font.FontLoader;
import ej.style.font.FontProfile;

/**
 * The font loader used in the application. It maps the given font profile to a font.
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
