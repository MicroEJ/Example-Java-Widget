/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.widget;

import ej.microui.Event;
import ej.microui.io.Pointer;
import ej.widgets.widgets.IScale;
import ej.widgets.widgets.tiny.GenericRange;

/**
 * This scale can be overlined and underlined.
 */
public class LinedScale extends GenericRange implements IScale {

	private static final int PAGE = 10;

	private boolean underlined;
	private boolean overlined;

	/**
	 * Creates a LinedScale with the given minimum value and the given maximum value.
	 * 
	 * @param minValue
	 *            the minimum value of the scale.
	 * @param maxValue
	 *            the maximum value of the scale.
	 */
	public LinedScale(int minValue, int maxValue) {
		super(minValue, maxValue);
	}

	/**
	 * Sets the scale as underlined or not.
	 * 
	 * @param underlined
	 *            the underlined to set
	 */
	public void setUnderlined(boolean underlined) {
		this.underlined = underlined;
	}

	/**
	 * Gets whether or not the scale is underlined.
	 * 
	 * @return {@code true} if underlined, {@code false} otherwise.
	 */
	public boolean isUnderlined() {
		return this.underlined;
	}

	/**
	 * Sets the scale as overlined or not.
	 * 
	 * @param overlined
	 *            the overlined to set
	 */
	public void setOverlined(boolean overlined) {
		this.overlined = overlined;
	}

	/**
	 * Gets whether or not the scale is overlined.
	 * 
	 * @return {@code true} if overlined, {@code false} otherwise.
	 */
	public boolean isOverlined() {
		return this.overlined;
	}

	@Override
	public void increment(boolean forward) {
		updateValue(forward ? 1 : -1);

	}

	@Override
	public void pageIncrement(boolean forward) {
		updateValue(forward ? PAGE : -PAGE);
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);

		if (type == Event.POINTER) {
			int action = Pointer.getAction(event);
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int x = getRelativeX(pointer.getX());

			switch (action) {
			case Pointer.PRESSED:
				break;
			case Pointer.RELEASED: // Fall down.
			case Pointer.DRAGGED:
				computeValue(x);
				break;
			}

			return true;
		}
		return false;
	}

	/**
	 * Computes the value of the scale in function of the abscissa of the pointer.
	 * 
	 * @param pointerX
	 *            the abscissa of the pointer.
	 */
	private void computeValue(int pointerX) {
		float valueFactor = pointerX / (float) getWidth();
		int min = getMinValue();
		int newValue = (int) (min + (getMaxValue() - min) * valueFactor);
		setValue(newValue);
	}
}
