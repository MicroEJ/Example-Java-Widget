/*
 * Copyright 2015-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.wheel.widget;

/**
 * Represents a choice between a set of strings.
 */
public class StringChoice implements Choice {

	private final String[] strings;
	private int index;

	/**
	 * Creates a string choice.
	 *
	 * @param strings
	 *            the set of strings.
	 * @param initialIndex
	 *            the initial index.
	 */
	public StringChoice(String[] strings, int initialIndex) {
		checkBounds(0, strings.length, initialIndex);
		this.strings = strings.clone();
		this.index = initialIndex;
	}

	private void checkBounds(int minimum, int maximum, int value) {
		if (minimum > value || value > maximum) {
			// Do not need to test that minimum is lower than maximum as long as it is validated by the other tests.
			throw new IllegalArgumentException("Range constraint not respected"); //$NON-NLS-1$
		}
	}

	@Override
	public String getValueAsString(int index) {
		index = checkIndex(index);
		String valueString = this.strings[index];
		assert valueString != null;
		return valueString;
	}

	@Override
	public int getCurrentIndex() {
		return this.index;
	}

	@Override
	public int setCurrentIndex(int index) {
		this.index = checkIndex(index);
		return this.index;
	}

	@Override
	public int checkIndex(int index) {
		int newIndex = index % this.strings.length;

		if (newIndex < 0) {
			newIndex += this.strings.length;
		}

		return newIndex;
	}

	@Override
	public String getNext(int index, int offset) {
		return getValueFromOffset(index, offset, true);
	}

	@Override
	public String getPrevious(int index, int offset) {
		return getValueFromOffset(index, offset, false);
	}

	private String getValueFromOffset(int index, int offset, boolean isNext) {
		int displacement = isNext ? offset : -offset;
		int desiredIndex = checkIndex(index + displacement);
		return getValueAsString(desiredIndex);
	}

}
