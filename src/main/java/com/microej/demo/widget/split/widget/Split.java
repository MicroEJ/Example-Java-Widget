/*
 * Copyright 2015-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.split.widget;

import ej.annotation.Nullable;
import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Size;
import ej.widget.container.LayoutOrientation;

/**
 * Lays out two children vertically or horizontally, by giving each child a portion of the available space.
 * <p>
 * The two children are laid out next to each other.
 * <p>
 * In a horizontal split, both children will have the height of the available space, and their width will be determined
 * according to the split ratio. In a vertical split, both children will have the width of the available space, and
 * their height will be determined according to the split ratio. The split ratio is always respected regardless of the
 * existence and of the optimal size of both children.
 * <p>
 * Horizontal:
 * <p>
 * <img src="doc-files/split_horizontal.png" alt="Horizontal split.">
 * <p>
 * Vertical:
 * <p>
 * <img src="doc-files/split_vertical.png" alt="Vertical split.">
 */
public class Split extends Container {

	/** The extra field ID for the fill ratio of the first widget. */
	public static final int RATIO_FIELD = 0;

	private static final float DEFAULT_RATIO = 0.5f;

	private boolean orientation;
	@Nullable
	private Widget first;
	@Nullable
	private Widget last;

	/**
	 * Creates a split specifying its orientation.
	 *
	 * @param orientation
	 *            the orientation of the split (see {@link LayoutOrientation}).
	 */
	public Split(boolean orientation) {
		this.orientation = orientation;
	}

	/**
	 * Sets the orientation of this split.
	 *
	 * @param orientation
	 *            the orientation to set (see {@link LayoutOrientation}).
	 */
	public void setOrientation(boolean orientation) {
		this.orientation = orientation;
	}

	/**
	 * Gets the orientation of this split.
	 *
	 * @return the orientation of this split (see {@link LayoutOrientation}).
	 */
	public boolean getOrientation() {
		return this.orientation;
	}

	/**
	 * Sets the first widget of this split. If this split is horizontal the first widget is on the left side, and if the
	 * split is vertical it is on the top side.
	 *
	 * @param widget
	 *            the widget to add.
	 * @throws NullPointerException
	 *             if the given widget is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             if the specified widget is already in a hierarchy (already contained in a container or desktop).
	 * @see #addChild(Widget)
	 */
	public void setFirstChild(Widget widget) {
		Widget first = this.first;
		if (first != null) {
			removeChild(first);
		}

		this.first = widget;
		addChild(widget);
	}

	/**
	 * Gets the first child of this split.
	 *
	 * @return the first child of this split, or <code>null</code> is it has not been set.
	 */
	@Nullable
	public Widget getFirstChild() {
		return this.first;
	}

	/**
	 * Sets the last widget of this split. If this split is horizontal the first widget is on the right side, and if the
	 * split is vertical it is on the bottom side.
	 *
	 * @param child
	 *            the widget to add.
	 * @throws NullPointerException
	 *             if the given widget is <code>null</code>.
	 * @throws IllegalArgumentException
	 *             if the specified widget is already in a hierarchy (already contained in a container or desktop).
	 * @see #addChild(Widget)
	 */
	public void setLastChild(Widget child) {
		Widget last = this.last;
		if (last != null) {
			removeChild(last);
		}

		this.last = child;
		addChild(child);
	}

	/**
	 * Gets the last child of this split.
	 *
	 * @return the last child of this split, or <code>null</code> is it has not been set.
	 */
	@Nullable
	public Widget getLastChild() {
		return this.last;
	}

	@Override
	public void removeChild(Widget widget) {
		if (widget == this.first) {
			this.first = null;
		} else if (widget == this.last) {
			this.last = null;
		}
		super.removeChild(widget);
	}

	@Override
	public void removeAllChildren() {
		this.first = null;
		this.last = null;
		super.removeAllChildren();
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		// handle case with no widget
		int childrenCount = getChildrenCount();
		if (childrenCount == 0) {
			size.setSize(0, 0);
			return;
		}

		boolean isHorizontal = (this.orientation == LayoutOrientation.HORIZONTAL);
		float ratio = getRatio(getStyle());

		// compute width hint for both widgets
		int widthHint = size.getWidth();
		int firstWidth;
		int lastWidth;
		if (widthHint != Widget.NO_CONSTRAINT && isHorizontal) {
			firstWidth = (int) (widthHint * ratio);
			lastWidth = widthHint - firstWidth;
		} else {
			firstWidth = widthHint;
			lastWidth = widthHint;
		}

		// compute height hint for both widgets
		int heightHint = size.getHeight();
		int firstHeight;
		int lastHeight;
		if (heightHint != Widget.NO_CONSTRAINT && !isHorizontal) {
			firstHeight = (int) (heightHint * ratio);
			lastHeight = heightHint - firstHeight;
		} else {
			firstHeight = heightHint;
			lastHeight = heightHint;
		}

		// compute optimal size of first widget
		Widget first = this.first;
		int firstOptimalWidth;
		int firstOptimalHeight;
		if (first != null) {
			computeChildOptimalSize(first, firstWidth, firstHeight);
			firstOptimalWidth = first.getWidth();
			firstOptimalHeight = first.getHeight();
		} else {
			firstOptimalWidth = 0;
			firstOptimalHeight = 0;
		}

		// compute optimal size of last widget
		Widget last = this.last;
		int lastOptimalWidth;
		int lastOptimalHeight;
		if (last != null) {
			computeChildOptimalSize(last, lastWidth, lastHeight);
			lastOptimalWidth = last.getWidth();
			lastOptimalHeight = last.getHeight();
		} else {
			lastOptimalWidth = 0;
			lastOptimalHeight = 0;
		}

		// compute container optimal size
		int width;
		int height;
		if (isHorizontal) {
			width = (int) Math.max(firstOptimalWidth / ratio, lastOptimalWidth / (1.0f - ratio));
			height = Math.max(firstOptimalHeight, lastOptimalHeight);
		} else {
			width = Math.max(firstOptimalWidth, lastOptimalWidth);
			height = (int) Math.max(firstOptimalHeight / ratio, lastOptimalHeight / (1.0f - ratio));
		}

		// set container optimal size
		size.setSize(width, height);
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		// handle case with no widget
		int childrenCount = getChildrenCount();
		if (childrenCount == 0) {
			return;
		}

		float ratio = getRatio(getStyle());

		// compute size of both widgets
		int firstWidth;
		int firstHeight;
		int lastWidth;
		int lastHeight;
		if (this.orientation == LayoutOrientation.HORIZONTAL) {
			firstWidth = (int) (contentWidth * ratio);
			firstHeight = contentHeight;
			lastWidth = contentWidth - firstWidth;
			lastHeight = contentHeight;
		} else {
			firstWidth = contentWidth;
			firstHeight = (int) (contentHeight * ratio);
			lastWidth = contentWidth;
			lastHeight = contentHeight - firstHeight;
		}

		// lay out first widget
		Widget first = this.first;
		if (first != null) {
			layOutChild(first, 0, 0, firstWidth, firstHeight);
		}

		// lay out last widget
		Widget last = this.last;
		if (last != null) {
			layOutChild(last, contentWidth - lastWidth, contentHeight - lastHeight, lastWidth, lastHeight);
		}
	}

	private static float getRatio(Style style) {
		return style.getExtraFloat(RATIO_FIELD, DEFAULT_RATIO);
	}
}
