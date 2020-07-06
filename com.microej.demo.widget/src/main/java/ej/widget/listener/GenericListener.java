/*
 * Copyright 2017-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.listener;

/**
 * A listener is notified when the model it is associated with changed.
 */
public interface GenericListener {

	/**
	 * Something has changed.
	 */
	void update();

}
