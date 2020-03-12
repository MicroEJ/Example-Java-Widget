/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.wheel;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Represents a choice between a set of items.
 */
public interface Choice {

	/**
	 * Gets the value of an item at an index.
	 *
	 * @param index
	 *            the index of the item.
	 * @return the string representation of the item at the given index.
	 * @throws NoSuchElementException
	 *             if the given index matches no item.
	 */
	String getValueAsString(int index);

	/**
	 * Gets the current item index.
	 *
	 * @return the current item index.
	 */
	int getCurrentIndex();

	/**
	 * Sets the current item index.
	 *
	 * @param index
	 *            the index of the item to select.
	 * @throws NoSuchElementException
	 *             if the given index matches no item.
	 */
	void setCurrentIndex(int index);

	/**
	 * Gets a list iterator of the choices from an index.
	 *
	 * @param index
	 *            the index to start the iteration with.
	 * @return the list iterator from the given index.
	 * @throws NoSuchElementException
	 *             if the given index matches no item.
	 */
	ListIterator<String> listIterator(int index);

}
