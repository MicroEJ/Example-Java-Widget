/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.chart;

/**
 * Represents a point of a Chart
 */
public class ChartPoint {

	/**
	 * Attributes
	 */
	private String name;
	private String fullName;
	private float value;
	private boolean selected;

	/**
	 * Constructor
	 *
	 * @param name
	 *            the name.
	 * @param fullName
	 *            the full name.
	 * @param value
	 *            the value.
	 */
	public ChartPoint(String name, String fullName, float value) {
		this.name = name;
		this.fullName = fullName;
		this.value = value;
		this.selected = false;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the name to set.
	 */
	public void setName(String name) {
		this.name = name;
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
	 * Sets the full name.
	 *
	 * @param fullName
	 *            the full name to set.
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	 * Sets the value.
	 *
	 * @param value
	 *            the value to set.
	 */
	public void setValue(float value) {
		this.value = value;
	}

	/**
	 * Sets the selection state.
	 *
	 * @param selected
	 *            the selection state to set.
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * Gets the selection state.
	 *
	 * @return the selection state.
	 */
	public boolean isSelected() {
		return this.selected;
	}
}
