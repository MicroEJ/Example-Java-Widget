/*
 * Copyright 2015-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.wheel.widget;

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
	 * @return current modified index
	 * @throws NoSuchElementException
	 *             if the given index matches no item.
	 */
	int setCurrentIndex(int index);

	/**
	 * Gets the next value from index with positive displacement.
	 *
	 * @param index
	 *            start from
	 * @param offset
	 *            positive displacement
	 * @return next value at (index+count) location
	 * @throws NoSuchElementException
	 *             if the index does not exist
	 */
	String getNext(int index, int offset);

	/**
	 * Gets the previous value from index with negative displacement.
	 *
	 * @param index
	 *            start from
	 * @param offset
	 *            negative displacement
	 * @return next value at (index-count) location
	 * @throws NoSuchElementException
	 *             if the index does not exist
	 */
	String getPrevious(int index, int offset);

	/**
	 * Checks an index and returns a corrected index.
	 *
	 * @param index
	 *            proposed index
	 * @return corrected index
	 */
	int checkIndex(int index);
}
