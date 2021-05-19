/*
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.linechart.widget;

/**
 * The point/value on a Chart.
 */
public class ChartPoint {

	private final String name;
	private final String fullName;
	private final float value;

	private boolean selected;

	/**
	 * Creates a ChartPoint.
	 *
	 * @param name
	 *            the short name of the ChartPoint.
	 * @param fullName
	 *            the long / full name of the ChartPoint.
	 * @param value
	 *            the value of the ChartPoint.
	 */
	public ChartPoint(String name, String fullName, float value) {
		this.name = name;
		this.fullName = fullName;
		this.value = value;
		this.selected = false;
	}

	/**
	 * Gets the short name.
	 *
	 * @return the short name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the full name.
	 *
	 * @return the full name.
	 */
	public String getFullName() {
		return this.fullName;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value.
	 */
	public float getValue() {
		return this.value;
	}

	/**
	 * Checks if the Point is currently selected.
	 *
	 * @return the current selected state.
	 */
	public boolean isSelected() {
		return this.selected;
	}

	/**
	 * Sets whether the point is selected or not.
	 *
	 * @param selected
	 *            <code>true</code> if the point is selected, <code>false</code> otherwise.
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
