/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.widget;

import ej.widgets.widgets.tiny.CheckBox;

/**
 * This checkbox can be overlined and underlined.
 */
public class LinedToggleButton extends CheckBox {

	private boolean underlined;
	private boolean overlined;

	/**
	 * Sets the checkbox as underlined or not.
	 * 
	 * @param underlined
	 *            the underlined to set
	 */
	public void setUnderlined(boolean underlined) {
		this.underlined = underlined;
	}

	/**
	 * Gets whether or not the checkbox is underlined.
	 * 
	 * @return {@code true} if underlined, {@code false} otherwise.
	 */
	public boolean isUnderlined() {
		return this.underlined;
	}

	/**
	 * Sets the checkbox as overlined or not.
	 * 
	 * @param overlined
	 *            the overlined to set
	 */
	public void setOverlined(boolean overlined) {
		this.overlined = overlined;
	}

	/**
	 * Gets whether or not the checkbox is overlined.
	 * 
	 * @return {@code true} if overlined, {@code false} otherwise.
	 */
	public boolean isOverlined() {
		return this.overlined;
	}
}
