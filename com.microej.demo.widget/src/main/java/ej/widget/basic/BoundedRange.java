/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic;

import ej.mwt.Widget;
import ej.widget.listener.OnValueChangeListener;
import ej.widget.model.BoundedRangeModel;
import ej.widget.model.DefaultBoundedRangeModel;

/**
 * A bounded range in an abstract widget that encapsulates a bounded range model.
 */
public abstract class BoundedRange extends Widget {

	private BoundedRangeModel model;

	/**
	 * Creates a bounded range with the given model.
	 *
	 * @param model
	 *            the model to use.
	 */
	public BoundedRange(BoundedRangeModel model) {
		super();
		assert model != null;
		this.model = model;
	}

	/**
	 * Creates a bounded range with a default bounded range model as model.
	 *
	 * @param min
	 *            the minimum value of the bounded range.
	 * @param max
	 *            the maximum value of bounded range.
	 * @param initialValue
	 *            the initial value of bounded range.
	 * @see DefaultBoundedRangeModel
	 */
	public BoundedRange(int min, int max, int initialValue) {
		this(new DefaultBoundedRangeModel(min, max, initialValue));
	}

	/**
	 * Gets the percent complete for the bounded range. Note that this number is between 0.0 and 1.0.
	 *
	 * @return the percent complete for this bounded range.
	 */
	public float getPercentComplete() {
		return this.model.getPercentComplete();
	}

	/**
	 * Sets the data model used by the bounded range.
	 *
	 * @param model
	 *            the bounded range model to use. Cannot be <code>null</code>.
	 *
	 * @throws NullPointerException
	 *             if the given model is <code>null</code>.
	 */
	public void setModel(BoundedRangeModel model) {
		assert model != null;
		this.model = model;
		requestRender();
	}

	/**
	 * Facility to set the value of the model to its minimum.
	 */
	public void reset() {
		setValue(this.model.getMinimum());
	}

	/**
	 * Gets the bounded range's maximum value from the model.
	 *
	 * @return the bounded range's maximum value.
	 */
	public int getMaximum() {
		return this.model.getMaximum();
	}

	/**
	 * Gets the bounded range's minimum value from the model.
	 *
	 * @return the bounded range's minimum value.
	 */
	public int getMinimum() {
		return this.model.getMinimum();
	}

	/**
	 * Gets the bounded range's current value from the model. The value is always between the minimum and maximum
	 * values, inclusive.
	 *
	 * @return the current value of the bounded range.
	 */
	public int getValue() {
		return this.model.getValue();
	}

	/**
	 * Sets the bounded range's maximum value to the given value. This method forwards the new maximum to the model. The
	 * data model handles any mathematical issues arising from assigning faulty values. If the maximum value is
	 * different from the previous maximum, the change listener is notified.
	 *
	 * @param maximum
	 *            the new maximum.
	 */
	public void setMaximum(int maximum) {
		this.model.setMaximum(maximum);
		requestRender();
	}

	/**
	 * Sets the bounded range's minimum value to the given value. This method forwards the new minimum to the model. The
	 * data model handles any mathematical issues arising from assigning faulty values. If the minimum value is
	 * different from the previous minimum, the change listener is notified.
	 *
	 * @param minimum
	 *            the new minimum.
	 */
	public void setMinimum(int minimum) {
		this.model.setMinimum(minimum);
		requestRender();
	}

	/**
	 * Sets the bounded range's current value to the given value. This method forwards the new value to the model. The
	 * data model (an instance of BoundedRangeModel) handles any mathematical issues arising from assigning faulty
	 * values. If the new value is different from the previous value, the change listener is notified.
	 *
	 * @param value
	 *            the new value.
	 */
	public void setValue(int value) {
		this.model.setValue(value);
		requestRender();
	}

	/**
	 * Adds a listener on the value change events of the bounded range model.
	 *
	 * @param onValueChangeListener
	 *            the value listener to add.
	 * @throws NullPointerException
	 *             if the given listener is <code>null</code>.
	 */
	public void addOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
		assert onValueChangeListener != null;
		this.model.addOnValueChangeListener(onValueChangeListener);
	}

	/**
	 * Removes a listener on the value change events of the bounded range model.
	 *
	 * @param onValueChangeListener
	 *            the value listener to remove.
	 */
	public void removeOnValueChangeListener(OnValueChangeListener onValueChangeListener) {
		this.model.removeOnValueChangeListener(onValueChangeListener);
	}

}
