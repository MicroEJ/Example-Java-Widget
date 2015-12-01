/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.microej.demo.widgets.style;

import java.util.WeakHashMap;

import ej.microui.display.Font;
import ej.style.font.CompositeFilter;
import ej.style.font.FontFamilyFilter;
import ej.style.font.FontHelper;
import ej.style.font.FontLoader;
import ej.style.font.FontProfile;
import ej.style.font.FontSizeValueFilter;

/**
 * The font loader used in the application. It maps the given font profile to a font.
 */
public class WidgetsFontLoader implements FontLoader {

	private static final String EMPTY_FONT_FAMILY = ""; //$NON-NLS-1$

	private static final int LARGE_HEIGHT = 50;
	private static final int MEDIUM_HEIGHT = 30;

	private final CompositeFilter<Font> compositeFilter;
	private final FontFamilyFilter fontFamilyFilter;
	private final FontSizeValueFilter fontSizeFilter;

	private final Font[] allFonts;

	private final WeakHashMap<FontProfile, Font> fontsCache;

	/**
	 * Creates the application font loader.
	 */
	public WidgetsFontLoader() {
		this.compositeFilter = new CompositeFilter<Font>();

		this.fontFamilyFilter = new FontFamilyFilter(EMPTY_FONT_FAMILY);
		this.compositeFilter.addFilter(this.fontFamilyFilter);

		this.fontSizeFilter = new FontSizeValueFilter(0);
		this.compositeFilter.addFilter(this.fontSizeFilter);

		this.allFonts = Font.getAllFonts();

		this.fontsCache = new WeakHashMap<>();
	}

	@Override
	public Font getFont(FontProfile fontProfile) {
		Font font = this.fontsCache.get(fontProfile);
		if (font == null) {
			font = getFontInternal(fontProfile);
			this.fontsCache.put(fontProfile, font);
		}
		return font;
	}

	private Font getFontInternal(FontProfile fontProfile) {
		this.fontFamilyFilter.setFontFamily(fontProfile.getFamily());

		int sizeValue = getSizeValue(fontProfile);
		this.fontSizeFilter.setSize(sizeValue);

		Font[] fonts = FontHelper.filter(this.compositeFilter, this.allFonts);
		if (fonts.length >= 1) {
			return fonts[0];
		}

		// No font matches with the font profile.
		return Font.getDefaultFont();
	}

	private int getSizeValue(FontProfile fontProfile) {
		switch (fontProfile.getSize()) {
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
