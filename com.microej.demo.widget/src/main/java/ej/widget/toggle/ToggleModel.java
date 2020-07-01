/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.toggle;

import ej.annotation.Nullable;
import ej.basictool.ArrayTools;
import ej.widget.listener.OnStateChangeListener;

/**
 * A toggle is a two states item: checked and unchecked.
 */
public class ToggleModel {

	private static final OnStateChangeListener[] EMPTY_LISTENERS = new OnStateChangeListener[0];

	/**
	 * The state after the last change.
	 */
	protected boolean checked;
	@Nullable
	private ToggleGroup group;
	private OnStateChangeListener[] onChangeListeners;

	/**
	 * Creates an unchecked toggle.
	 */
	public ToggleModel() {
		this(false);
	}

	/**
	 * Creates a toggle with the given initial state.
	 *
	 * @param checked
	 *            if <code>true</code>, the toggle is initially checked, otherwise the toggle is initially unchecked.
	 */
	public ToggleModel(boolean checked) {
		setState(checked);
		this.onChangeListeners = EMPTY_LISTENERS;
	}

	/**
	 * Adds a listener on the state change event of the toggle.
	 *
	 * @param listener
	 *            the listener to add.
	 */
	public void addOnStateChangeListener(OnStateChangeListener listener) {
		this.onChangeListeners = ArrayTools.add(this.onChangeListeners, listener);
	}

	/**
	 * Removes a listener on the state change event of the toggle.
	 *
	 * @param listener
	 *            the listener to add.
	 */
	public void removeOnStateChangeListener(OnStateChangeListener listener) {
		this.onChangeListeners = ArrayTools.remove(this.onChangeListeners, listener);
	}

	/**
	 * Sets the state of the toggle.
	 * <p>
	 * If the toggle is already in the given state, nothing change.
	 *
	 * @param checked
	 *            the new state of the toggle.
	 */
	public void setChecked(boolean checked) {
		if (checked != this.checked) {
			changeState(checked);
		}
	}

	/**
	 * Changes the state of the toggle to the inverse of the current state.
	 */
	public void toggle() {
		changeState(!this.checked);
	}

	/**
	 * Gets whether or not the toggle button is checked.
	 *
	 * @return <code>true</code> if the toggle button is checked otherwise <code>false</code>.
	 */
	public boolean isChecked() {
		return this.checked;
	}

	/**
	 * Internal method that sets the state of the toggle without check and notify the group and notify the listener.
	 *
	 * @param checked
	 *            the new state of the toggle button.
	 */
	protected void changeState(boolean checked) {
		forceState(checked);

		ToggleGroup group = this.group;
		if (group != null) {
			if (checked) {
				group.select(this);
			} else {
				group.select(null);
			}
		}
	}

	/**
	 * Internal method that sets the state of the toggle without check.
	 *
	 * @param checked
	 *            the new state of the toggle button.
	 */
	void forceState(boolean checked) {
		setState(checked);
		notifyOnStateChangeListener(checked);
	}

	private void setState(boolean checked) {
		this.checked = checked;
	}

	/**
	 * Notifies the listeners that a state change event happened.
	 *
	 * @param newState
	 *            the new state of the toggle.
	 */
	protected void notifyOnStateChangeListener(boolean newState) {
		for (OnStateChangeListener listener : this.onChangeListeners) {
			listener.onStateChange(newState);
		}
	}

	/* package */void setGroup(@Nullable ToggleGroup group) {
		ToggleGroup oldGroup = this.group;

		if (group != oldGroup) {
			if (oldGroup != null) {
				this.group = null;
				oldGroup.removeToggle(this);
			} else {
				this.group = group;
			}
		}
	}

	/**
	 * Gets the group which contains the toggle button.
	 *
	 * @return the group of the toggle button or null if the toggle button has no group.
	 */
	@Nullable
	public ToggleGroup getGroup() {
		return this.group;
	}

}
