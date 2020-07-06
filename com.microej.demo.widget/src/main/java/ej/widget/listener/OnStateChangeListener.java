/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.listener;

/**
 * Defines an object which listens to state change events.
 */
public interface OnStateChangeListener {

	/**
	 * Invoked when the target of the listener has changed its state.
	 *
	 * @param newState
	 *            the new state of the listened object.
	 */
	void onStateChange(boolean newState);
}
