/*
 * Java
 *
 * Copyright 2016-2017 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package ej.widget.chart;

import ej.style.Element;
import ej.style.State;
import ej.style.util.ElementAdapter;

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
	private Element parentElement;
	private boolean highlighted;
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
		super();
		this.name = name;
		this.fullName = fullName;
		this.value = value;
		this.parentElement = null;
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

	/**
	 * Gets the parentElement.
	 *
	 * @return parentElement the parentElement.
	 */
	@Override
	public Element getParentElement() {
		return this.parentElement;
	}

	/**
	 * Sets the parentElement.
	 *
	 * @param parentElement
	 *            the parentElement to set.
	 */
	public void setParentElement(Element parentElement) {
		this.parentElement = parentElement;
	}

	/**
	 * Sets the highlight state.
	 *
	 * @param highlighted
	 *            the highlight state to set.
	 */
	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
		this.updateStyle();
	}

	/**
	 * Sets the selection state.
	 *
	 * @param selected
	 *            the selection state to set.
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
		this.updateStyle();
	}

	/**
	 * Checks whether the point is in a given state.
	 *
	 * @param state
	 *            the state.
	 */
	@Override
	public boolean isInState(State state) {
		return (state == State.Visited && this.highlighted) || (state == State.Checked && this.selected)
				|| super.isInState(state);
	}
}
