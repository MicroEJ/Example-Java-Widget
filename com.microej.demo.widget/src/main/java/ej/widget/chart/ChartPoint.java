/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.chart;

import ej.mwt.Container;
import ej.widget.ElementAdapter;
import ej.widget.util.States;

/**
 * Represents a point of a Chart
 */
public class ChartPoint extends ElementAdapter {

	/**
	 * Attributes
	 */
	private String name;
	private String fullName;
	private float value;
	private boolean highlighted;
	private boolean selected;

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            the parent.
	 * @param name
	 *            the name.
	 * @param fullName
	 *            the full name.
	 * @param value
	 *            the value.
	 */
	public ChartPoint(Container parent, String name, String fullName, float value) {
		super(parent);
		this.name = name;
		this.fullName = fullName;
		this.value = value;
		this.highlighted = false;
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

	@Override
	public void updateStyleRecursive() {
		// don't check if this "widget" is shown
		updateStyle();
	}

	/**
	 * Sets the highlight state.
	 *
	 * @param highlighted
	 *            the highlight state to set.
	 */
	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
		this.updateStyleRecursiveAndRepaint();
	}

	/**
	 * Sets the selection state.
	 *
	 * @param selected
	 *            the selection state to set.
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
		this.updateStyleRecursive();
	}

	/**
	 * Checks whether the point is in a given state.
	 *
	 * @param state
	 *            the state.
	 */
	@Override
	public boolean isInState(int state) {
		return (state == States.VISITED && this.highlighted) || (state == States.CHECKED && this.selected)
				|| super.isInState(state);
	}
}
