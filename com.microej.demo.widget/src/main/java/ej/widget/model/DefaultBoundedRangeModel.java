/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.model;

import ej.basictool.ArrayTools;
import ej.widget.listener.OnValueChangeListener;

/**
 * A generic implementation of bounded range.
 */
public class DefaultBoundedRangeModel implements BoundedRangeModel {

	private static final OnValueChangeListener[] EMPTY_LISTENERS = new OnValueChangeListener[0];

	private int maximum;
	private int minimum;
	private int value;
	private OnValueChangeListener[] onValueChangeListeners;

	/**
	 * Creates a default bounded range model with the given minimum value, maximum value and initial value.
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
	public DefaultBoundedRangeModel(int minimum, int maximum, int initialValue) {
		checkBounds(minimum, maximum, initialValue);

		this.onValueChangeListeners = EMPTY_LISTENERS;

		this.minimum = minimum;
		this.maximum = maximum;
		this.value = initialValue;
	}

	static void checkBounds(int minimum, int maximum, int initialValue) {
		if (minimum > initialValue || initialValue > maximum) {
			// Do not need to test that minimum is lower than maximum as long as it is validated by the other tests.
			throw new IllegalArgumentException();
		}
	}

	@Override
	public int getMaximum() {
		return this.maximum;
	}

	@Override
	public int getMinimum() {
		return this.minimum;
	}

	@Override
	public int getValue() {
		return this.value;
	}

	@Override
	public float getPercentComplete() {
		int minimum = getMinimum();
		return (float) (this.getValue() - minimum) / (getMaximum() - minimum);
	}

	@Override
	public void setMaximum(int maximum) {
		if (maximum != this.maximum) {
			int value = this.value;
			if (maximum >= value) {
				this.maximum = maximum;
				notifyMaximumChange(maximum);
			} else {
				maximum = Math.max(maximum, this.minimum);
				this.maximum = maximum;
				this.value = maximum;
				notifyMaximumChange(maximum);
				notifyValueChange(maximum);
			}
		}
	}

	@Override
	public void setMinimum(int minimum) {
		if (minimum != this.minimum) {
			int value = this.value;
			if (minimum <= value) {
				this.minimum = minimum;
				notifyMinimumChange(minimum);
			} else {
				minimum = Math.min(minimum, this.maximum);
				this.minimum = minimum;
				this.value = minimum;
				notifyMinimumChange(minimum);
				notifyValueChange(minimum);
			}
		}
	}

	@Override
	public void setValue(int value) {
		if (value != this.value) {
			int newValue;
			if (value < this.minimum) {
				newValue = this.minimum;
			} else if (value > this.maximum) {
				newValue = this.maximum;
			} else {
				newValue = value;
			}
			this.value = newValue;
			notifyValueChange(newValue);
		}
	}

	@Override
	public void addOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
		this.onValueChangeListeners = ArrayTools.add(this.onValueChangeListeners, onValueChangeListener);
	}

	@Override
	public void removeOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
		this.onValueChangeListeners = ArrayTools.remove(this.onValueChangeListeners, onValueChangeListener);
	}

	private void notifyMaximumChange(int newMaximum) {
		for (OnValueChangeListener listener : this.onValueChangeListeners) {
			listener.onMaximumValueChange(newMaximum);
		}
	}

	private void notifyMinimumChange(int newMinimum) {
		for (OnValueChangeListener listener : this.onValueChangeListeners) {
			listener.onMinimumValueChange(newMinimum);
		}
	}

	private void notifyValueChange(int newValue) {
		for (OnValueChangeListener listener : this.onValueChangeListeners) {
			listener.onValueChange(newValue);
		}
	}

}
