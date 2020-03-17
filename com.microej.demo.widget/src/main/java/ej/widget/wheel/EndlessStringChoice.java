/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.wheel;

import java.util.ListIterator;

/**
 * Represents a choice between a set of strings. The proposed iterator in endless, that means that when it reaches a
 * bound (minimum or maximum), it jumps to the other bound.
 */
public class EndlessStringChoice implements Choice {

	private final String[] strings;
	private int index;

	/**
	 * Constructor
	 *
	 * @param strings
	 *            the set of strings
	 * @param initialIndex
	 *            the initial index
	 */
	public EndlessStringChoice(String[] strings, int initialIndex) {
		checkBounds(0, strings.length, initialIndex);
		this.strings = strings;
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
		return this.strings[index];
	}

	@Override
	public int getCurrentIndex() {
		return this.index;
	}

	@Override
	public void setCurrentIndex(int index) {
		this.index = checkIndex(index);
	}

	@Override
	public ListIterator<String> listIterator(int index) {
		return new InternalListIterator(checkIndex(index));
	}

	private int checkIndex(int index) {
		int newIndex = index % 12;

		if (newIndex < 0) {
			newIndex += 12;
		}

		return newIndex;
	}

	private class InternalListIterator implements ListIterator<String> {

		private int currentIndex;

		public InternalListIterator(int index) {
			this.currentIndex = index;
		}

		@Override
		public void add(String e) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean hasNext() {
			return true;
		}

		@Override
		public boolean hasPrevious() {
			return true;
		}

		@Override
		public String next() {
			this.currentIndex = nextIndex();
			return EndlessStringChoice.this.strings[this.currentIndex];
		}

		@Override
		public int nextIndex() {
			int nextIndex = this.currentIndex + 1;
			if (nextIndex > 11) {
				nextIndex = 0;
			}

			return nextIndex;
		}

		@Override
		public String previous() {
			this.currentIndex = previousIndex();
			return EndlessStringChoice.this.strings[this.currentIndex];
		}

		@Override
		public int previousIndex() {
			int previousIndex = this.currentIndex - 1;
			if (previousIndex < 0) {
				previousIndex = 11;
			}

			return previousIndex;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void set(String e) {
			throw new UnsupportedOperationException();
		}

	}
}
