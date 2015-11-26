/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.style;

import ej.microui.display.Font;
import ej.style.font.CompositeFilter;
import ej.style.font.FontFamilyFilter;
import ej.style.font.FontHelper;
import ej.style.font.FontLoader;
import ej.style.font.FontProfile;
import ej.style.font.FontProfile.FontSize;
import ej.style.font.FontSizeValueFilter;

/**
 * The font loader used in the application. It maps the given font profile to a font.
 */
public class WidgetsFontLoader implements FontLoader {

	private static final int LARGE_HEIGHT = 50;
	private static final int MEDIUM_HEIGHT = 30;

	@Override
	public Font getFont(FontProfile fontProfile) {
		CompositeFilter<Font> compositeFilter = new CompositeFilter<Font>();

		FontFamilyFilter fontFamilyFilter = new FontFamilyFilter(fontProfile.getFamily());
		compositeFilter.addFilter(fontFamilyFilter);

		int sizeValue = getSizeValue(fontProfile, fontProfile.getSize());
		FontSizeValueFilter fontSizeFilter = new FontSizeValueFilter(sizeValue);
		compositeFilter.addFilter(fontSizeFilter);

		Font[] fonts = FontHelper.filter(compositeFilter, Font.getAllFonts());
		if (fonts.length >= 1) {
			return fonts[0];
		}

		return Font.getDefaultFont();
	}

	private int getSizeValue(FontProfile fontProfile, FontSize size) {
		switch (size) {
		case LENGTH:
			return fontProfile.getSizeValue();
		case LARGE:
			return LARGE_HEIGHT;
		case MEDIUM:
		default:
			return MEDIUM_HEIGHT;
		}
	}

}
