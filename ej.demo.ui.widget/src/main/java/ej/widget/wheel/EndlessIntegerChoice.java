/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.widget.wheel;

import java.util.ListIterator;

/**
 * Represents an integer choice from a minimum to a maximum. The proposed iterator in endless, that means that when it
 * reaches a bound (minimum or maximum), it jumps to the other bound.
 */
public class EndlessIntegerChoice implements Choice {

	private final int minimum;
	private final int maximum;
	private int value;

	/**
	 * Creates a endless integer choice.
	 *
	 * @param minimum
	 *            the minimum value of the model.
	 * @param maximum
	 *            the maximum value of the model.
	 * @param initialValue
	 *            the initial value of the model.
	 * @throws IllegalArgumentException
	 *             if the constraint <code>minimum &lt;= initialValue &lt;= maximum</code> is not satisfied.
	 */
	public EndlessIntegerChoice(int minimum, int maximum, int initialValue) {
		checkBounds(minimum, maximum, initialValue);
		this.minimum = minimum;
		this.maximum = maximum;
		this.value = initialValue;
	}

	private void checkBounds(int minimum, int maximum, int value) {
		if (minimum > value || value > maximum) {
			// Do not need to test that minimum is lower than maximum as long as it is validated by the other tests.
			throw new IllegalArgumentException("Range constraint not respected"); //$NON-NLS-1$
		}
	}

	@Override
	public void setCurrentIndex(int index) {
		index = checkIndex(index);
		this.value = getValueForIndex(index);
	}

	@Override
	public int getCurrentIndex() {
		return this.value - this.minimum;
	}

	@Override
	public String getValueAsString(int index) {
		index = checkIndex(index);
		return getValueAsStringNoCheck(index);
	}

	private String getValueAsStringNoCheck(int index) {
		int valueForIndex = getValueForIndex(index);
		return String.valueOf(valueForIndex);
	}

	private int checkIndex(int index) {
		int range = this.maximum - this.minimum + 1;
		index = index % range;

		if (index < 0) {
			index += range;
		}

		return index;
	}

	private int maxIndex() {
		return this.maximum - this.minimum;
	}

	private int getValueForIndex(int index) {
		return index + this.minimum;
	}

	@Override
	public ListIterator<String> listIterator(int index) {
		index = checkIndex(index);
		return new InternalListIterator(index);
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
			return getValueAsStringNoCheck(this.currentIndex);
		}

		@Override
		public int nextIndex() {
			int nextIndex = this.currentIndex + 1;
			if (nextIndex > maxIndex()) {
				nextIndex = 0;
			}

			return nextIndex;
		}

		@Override
		public String previous() {
			this.currentIndex = previousIndex();
			return getValueAsStringNoCheck(this.currentIndex);
		}

		@Override
		public int previousIndex() {
			int previousIndex = this.currentIndex - 1;
			if (previousIndex < 0) {
				previousIndex = maxIndex();
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
