/*
 * Java
 *
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.flex;

import ej.annotation.Nullable;
import ej.mwt.Widget;

/**
 * Represents a group.
 * <p>
 * Only one item may be checked at any time.
 */
public class Group {

	private @Nullable Widget checkedItem;

	/**
	 * Creates an item group.
	 */
	public Group() {
		this.checkedItem = null;
	}

	/**
	 * Returns whether the given item is currently checked.
	 *
	 * @param item
	 *            the item to test.
	 * @return <code>true</code> if the given item is currently checked, <code>false</code> otherwise.
	 */
	public boolean isChecked(Widget item) {
		return (item == this.checkedItem);
	}

	/**
	 * Checks the given item and unchecks the currently checked item.
	 *
	 * @param item
	 *            the item to check.
	 */
	public void setChecked(@Nullable Widget item) {
		Widget oldCheckedItem = this.checkedItem;
		this.checkedItem = item;

		if (oldCheckedItem != null) {
			oldCheckedItem.updateStyle();
			oldCheckedItem.requestRender();
		}

		if (item != null) {
			item.updateStyle();
			item.requestRender();
		}
	}
}
