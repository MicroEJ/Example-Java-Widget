/*
 * Copyright 2017-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.listener;

/**
 * Utility class with empty implementation of {@link OnValueChangeListener} methods.
 */
public abstract class OnValueChangeAdapter implements OnValueChangeListener {

	@Override
	public void onValueChange(int newValue) {
		// Do nothing by default.
	}

	@Override
	public void onMaximumValueChange(int newMaximum) {
		// Do nothing by default.
	}

	@Override
	public void onMinimumValueChange(int newMinimum) {
		// Do nothing by default.
	}

}
