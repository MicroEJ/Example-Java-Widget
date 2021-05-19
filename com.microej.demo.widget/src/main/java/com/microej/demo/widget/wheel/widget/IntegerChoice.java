/*
 * Copyright 2015-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.wheel.widget;

/**
 * Represents an integer choice from a minimum to a maximum.
 */
public final class IntegerChoice implements Choice {

	private final int minimum;
	private final int maximum;
	private int index;

	/**
	 * Creates an integer choice.
	 *
	 * @param minimum
	 *            the minimum value of the model.
	 * @param maximum
	 *            the maximum value of the model.
	 * @param initialValue
	 *            the initial value of the model.
	 * @throws IllegalArgumentException
	 *             if the constraint <code>minimum &lt;= initialValue &lt;= maximum</code> is not satisfied.
	 */
	public IntegerChoice(int minimum, int maximum, int initialValue) {
		checkBounds(minimum, maximum, initialValue);
		this.minimum = minimum;
		this.maximum = maximum;
		setCurrentIndex(initialValue - this.minimum);
	}

	private void checkBounds(int minimum, int maximum, int value) {
		if (minimum > value || value > maximum) {
			// Do not need to test that minimum is lower than maximum as long as it is validated by the other tests.
			throw new IllegalArgumentException("Range constraint not respected"); //$NON-NLS-1$
		}
	}

	@Override
	public int setCurrentIndex(int index) {
		this.index = checkIndex(index);
		return this.index;
	}

	@Override
	public int getCurrentIndex() {
		return this.index;
	}

	@Override
	public String getValueAsString(int index) {
		index = checkIndex(index);
		return getValueAsStringNoCheck(index);
	}

	private String getValueAsStringNoCheck(int index) {
		int valueForIndex = getValueForIndex(index);
		return String.valueOf(valueForIndex);
	}

	@Override
	public int checkIndex(int index) {
		int range = this.maximum - this.minimum + 1;
		index = index % range;

		if (index < 0) {
			index += range;
		}

		return index;
	}

	private int getValueForIndex(int index) {
		return index + this.minimum;
	}

	@Override
	public String getNext(int index, int offset) {
		return getStringWithDisplacement(index, offset, true);
	}

	@Override
	public String getPrevious(int index, int offset) {
		return getStringWithDisplacement(index, offset, false);
	}

	private String getStringWithDisplacement(int index, int offset, boolean isNext) {
		int displacement = isNext ? offset : -offset;
		int desiredIndex = checkIndex(index + displacement);
		return getValueAsStringNoCheck(desiredIndex);
	}
}
