/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.toggle;

/**
 * Implementation of a toggle that represents a radio button.
 * <p>
 * A radio cannot be unchecked.
 */
public class RadioModel extends ToggleModel {

	/**
	 * Creates an unchecked radio.
	 */
	public RadioModel() {
		super();
	}

	/**
	 * Creates a radio with the given initial state.
	 *
	 * @param checked
	 *            if <code>true</code>, the radio is initially checked; otherwise, the radio is initially unchecked.
	 */
	public RadioModel(boolean checked) {
		super(checked);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * If the radio is checked, it cannot be unchecked.
	 */
	@Override
	public void setChecked(boolean checked) {
		if (!isChecked()) {
			super.setChecked(checked);
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * If the radio is checked, it cannot be unchecked.
	 */
	@Override
	public void toggle() {
		if (!isChecked()) {
			super.toggle();
		}
	}

}
