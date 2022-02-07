/*
 * Copyright 2020-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.radiobutton.widget;

import ej.annotation.Nullable;

/**
 * Represents a group of radio buttons.
 * <p>
 * Only one radio button may be checked at any time.
 */
public class RadioButtonGroup {

	private @Nullable RadioButton checkedRadioButton;

	/**
	 * Creates a radio button group.
	 */
	public RadioButtonGroup() {
		this.checkedRadioButton = null;
	}

	/**
	 * Returns whether the given radio button is currently checked.
	 *
	 * @param radioButton
	 *            the radio button to test.
	 * @return <code>true</code> if the given radio button is currently checked, <code>false</code> otherwise.
	 */
	public boolean isChecked(RadioButton radioButton) {
		return (radioButton == this.checkedRadioButton);
	}

	/**
	 * Checks the given radio button and unchecks the currently checked radio button.
	 * 
	 * @param radioButton
	 *            the radio button to check.
	 */
	public void setChecked(RadioButton radioButton) {
		RadioButton oldCheckedRadioButton = this.checkedRadioButton;
		this.checkedRadioButton = radioButton;

		if (oldCheckedRadioButton != null) {
			oldCheckedRadioButton.requestRender();
		}

		radioButton.requestRender();
	}
}
