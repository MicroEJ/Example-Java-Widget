/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.composed;

import ej.annotation.Nullable;
import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.util.Size;

/**
 * Lays out a single child.
 * <p>
 * In a wrapper, the child will have the size of the available space.
 * <p>
 * Only the area used by the child is reactive to the pointer events. That means that if the child does not take all the
 * space which was given by the wrapper, the remaining space will not react to pointer events. If the wrapper does not
 * have any child, its whole area reacts to pointer events.
 */
public class Wrapper extends Container {

	/**
	 * Sets the only child of this container.
	 *
	 * @param child
	 *            the widget to add.
	 */
	public void setChild(Widget child) {
		removeAllChildren();
		addChild(child);
	}

	/**
	 * Gets the only child of this container.
	 *
	 * @return the child of this container, or null if it is not set.
	 */
	@Nullable
	public Widget getChild() {
		if (getChildrenCount() == 1) {
			return getChild(0);
		} else {
			return null;
		}
	}

	/**
	 * Removes the child.
	 */
	public void removeChild() {
		super.removeAllChildren();
	}

	@Override
	public boolean contains(int x, int y) {
		if (getChildrenCount() == 0) {
			return super.contains(x, y);
		}
		int relativeX = x - getX() - getContentX();
		int relativeY = y - getY() - getContentY();
		// browse children recursively
		for (Widget widget : getChildren()) {
			if (widget.contains(relativeX, relativeY)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		int width = 0;
		int height = 0;

		// compute child optimal size
		if (getChildrenCount() == 1) {
			Widget child = getChild(0);
			computeChildOptimalSize(child, size.getWidth(), size.getHeight());
			width = child.getWidth();
			height = child.getHeight();
		}

		// set container optimal size
		size.setSize(width, height);
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		// lay out child
		if (getChildrenCount() == 1) {
			Widget child = getChild(0);
			layOutChild(child, 0, 0, contentWidth, contentHeight);
		}
	}
}
