/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.widget;

import ej.widgets.widgets.Picto;
import ej.widgets.widgets.tiny.RadioButton;

/**
 * RadioButton with an icon.
 */
public class IconToggleButton extends RadioButton {

	private final Picto icon;

	/**
	 * Creates an IconToggleButton with the given text and the given icon.
	 * 
	 * @param text
	 *            the text of the button.
	 * @param icon
	 *            the icon of the button.
	 */
	public IconToggleButton(String text, Picto icon) {
		super(text);
		this.icon = icon;
	}

	@Override
	public boolean isTransparent() {
		return true;
	}

	/**
	 * Gets the icon.
	 * 
	 * @return the icon.
	 */
	public Picto getIcon() {
		return this.icon;
	}
}
