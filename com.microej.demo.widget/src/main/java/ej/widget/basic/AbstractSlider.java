/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic;

import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.widget.model.BoundedRangeModel;

/**
 * A slider is a widget that lets the user graphically select a value by sliding a knob within a bounded interval. The
 * knob is always positioned at the points that match integer values within the specified interval.
 */
public abstract class AbstractSlider extends BoundedRange {

	/**
	 * Creates a slider with the given model.
	 *
	 * @param model
	 *            the model to use.
	 */
	public AbstractSlider(BoundedRangeModel model) {
		super(model);
		setEnabled(true);
	}

	/**
	 * Creates a slider with a default bounded range model.
	 *
	 * @param min
	 *            the minimum value of the slider.
	 * @param max
	 *            the maximum value of the slider.
	 * @param initialValue
	 *            the initial value of the slider.
	 * @see ej.widget.model.DefaultBoundedRangeModel
	 */
	public AbstractSlider(int min, int max, int initialValue) {
		super(min, max, initialValue);
		setEnabled(true);
	}

	@Override
	public boolean handleEvent(int event) {
		if (Event.getType(event) == Pointer.EVENT_TYPE) {
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int pointerX = pointer.getX();
			int pointerY = pointer.getY();
			int action = Pointer.getAction(event);
			switch (action) {
			case Pointer.RELEASED:
			case Pointer.DRAGGED:
				onPointerDragged(pointerX, pointerY);
				return true;
			}
		}
		return super.handleEvent(event);
	}

	private void onPointerDragged(int pointerX, int pointerY) {
		float percentComplete = computePercentComplete(pointerX, pointerY);
		int minimum = getMinimum();
		int maximum = getMaximum();
		int newValue = (int) (minimum + (maximum - minimum) * percentComplete);
		setValue(newValue);
	}

	/**
	 * Computes the percent complete according to the pointer coordinates.
	 *
	 * @param pointerX
	 *            the x coordinate of the pointer.
	 * @param pointerY
	 *            the y coordinate of the pointer.
	 * @return the percent complete according to the pointer coordinates.
	 */
	protected abstract float computePercentComplete(int pointerX, int pointerY);

}
