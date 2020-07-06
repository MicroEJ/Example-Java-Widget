/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.listener;

/**
 * Defines an object which listens to text change events.
 */
public interface OnTextChangeListener {

	/**
	 * Invoked when the target of the listener has changed its text.
	 *
	 * @param newText
	 *            the new text of the listened object.
	 */
	void onTextChange(String newText);
}
