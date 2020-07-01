/*
 * Copyright 2017-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic;

import ej.mwt.Widget;

/**
 * The paging indicator is a widget that shows the current step of a scrolling element.
 */
public abstract class PagingIndicator extends Widget {

	private int itemsCount;
	private int selectedItem;
	private float percent;
	private boolean horizontal;

	/**
	 * Creates an horizontal paging indicator.
	 */
	public PagingIndicator() {
		this(true);
	}

	/**
	 * Creates a paging indicator.
	 *
	 * @param horizontal
	 *            <code>true</code> if the paging indicator is horizontal, <code>false</code> otherwise.
	 */
	public PagingIndicator(boolean horizontal) {
		this.horizontal = horizontal;
		// this.currentItem = 0; VM_DONE
		this.percent = 1f;
	}

	/**
	 * Sets whether the paging indicator is horizontal or not.
	 *
	 * @param horizontal
	 *            <code>true</code> if the paging indicator is horizontal, <code>false</code> otherwise.
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	/**
	 * Gets whether the paging indicator is horizontal or not.
	 *
	 * @return <code>true</code> if the paging indicator is horizontal, <code>false</code> otherwise.
	 */
	public boolean isHorizontal() {
		return this.horizontal;
	}

	/**
	 * Sets the number of items.
	 *
	 * @param itemsCount
	 *            the items count to set.
	 */
	public void setItemsCount(int itemsCount) {
		this.itemsCount = itemsCount;
	}

	/**
	 * Gets the number of items.
	 *
	 * @return the number of items.
	 */
	public int getItemsCount() {
		return this.itemsCount;
	}

	/**
	 * Sets the selected item.
	 * <p>
	 * A percentage is given along the index to indicate whether the selected item is fully selected or not. A
	 * percentage of <code>1.0f</code> means that the selected item is fully selected, <code>0.0f</code> means that the
	 * next item is fully selected.
	 *
	 * @param item
	 *            the selected item.
	 * @param percent
	 *            the selection level, <code>1.0f</code> means that the item is fully selected.
	 * @throws IllegalArgumentException
	 *             if the given item is not in the range of the items (between <code>0</code> included and
	 *             <code>itemsCount</code> excluded).
	 * @throws IllegalArgumentException
	 *             if the given percent is not between <code>0.0f</code> and <code>1.0f</code> included.
	 * @see #setItemsCount(int)
	 */
	public void setSelectedItem(int item, float percent) {
		if (item < 0 || item >= this.itemsCount || percent < 0.0f || percent > 1.0f) {
			throw new IllegalArgumentException();
		}
		this.selectedItem = item;
		this.percent = percent;
	}

	/**
	 * Gets the selected item.
	 *
	 * @return the selected item.
	 */
	public int getSelectedItem() {
		return this.selectedItem;
	}

	/**
	 * Gets the percent of the selection.
	 *
	 * @return the percent.
	 */
	public float getPercent() {
		return this.percent;
	}

	/**
	 * Shows the scroll indicator.
	 */
	public abstract void show();

	/**
	 * Hides the scroll indicator.
	 */
	public abstract void hide();

}
