/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
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
