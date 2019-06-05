/*
 * Java
 *
 * Copyright 2017-2019 MicroEJ Corp. All rights reserved.
 * For demonstration purpose only.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package ej.widget.listener;

/**
 * Defines an object which listens to focus events.
 */
public interface OnFocusListener {

	/**
	 * Invoked when the target of the listener has gained focus.
	 */
	abstract public void onGainFocus();

	/**
	 * Invoked when the target of the listener has lost focus.
	 */
	abstract public void onLostFocus();
}
