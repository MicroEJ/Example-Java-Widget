/*
 * Java
 *
 * Copyright  2015-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package ej.widget.wheel;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ej.mwt.Widget;
import ej.widget.container.Grid;

/**
 * Represents a group of wheels
 */
public class WheelGroup extends Grid {

	private final int numSideValues;
	private final int maxActiveWheels;

	private final Map<Wheel, Boolean> activeWheels;

	/**
	 * Constructor
	 *
	 * @param numSideValues
	 *            the number of side values to show on the wheels
	 * @param maxActiveWheels
	 *            The maximum number of active wheels
	 */
	public WheelGroup(int numSideValues, int maxActiveWheels) {
		super(true, 0);
		this.numSideValues = numSideValues;
		this.maxActiveWheels = maxActiveWheels;
		this.activeWheels = new HashMap<>();
	}

	/**
	 * Gets the number of side values to show on the wheels
	 *
	 * @return the number of side values to show on the wheels
	 */
	public int getNumSideValues() {
		return this.numSideValues;
	}

	/**
	 * Gets the maximum number of active wheels
	 *
	 * @return the maximum number of active wheels
	 */
	public int getMaxActiveWheels() {
		return this.maxActiveWheels;
	}

	/**
	 * Gets the state of a wheel
	 *
	 * @param wheel
	 *            the wheel to check
	 * @return true if the wheel is currently active
	 */
	public Boolean getWheelActive(Wheel wheel) {
		return this.activeWheels.get(wheel);
	}

	/**
	 * Sets the state of a wheel
	 *
	 * @param wheel
	 *            the wheel
	 * @param active
	 *            true if the wheel is currently active
	 */
	public void setWheelActive(Wheel wheel, boolean active) {
		if (this.activeWheels.containsKey(wheel)) {
			this.activeWheels.put(wheel, new Boolean(active));
		}
	}

	/**
	 * Gets the current number of active wheels
	 *
	 * @return the current number of active wheels
	 */
	public int getNumActiveWheels() {
		int count = 0;
		for (Entry<Wheel, Boolean> entry : this.activeWheels.entrySet()) {
			if (entry.getValue().booleanValue()) {
				count++;
			}
		}
		return count;
	}

	@Override
	public void add(Widget widget) throws NullPointerException, IllegalArgumentException {
		super.add(widget);

		if (widget instanceof Wheel) {
			setCount(this.activeWheels.size() + 1);
			this.activeWheels.put((Wheel) widget, new Boolean(false));
		}
	}
}
