/*
 * Copyright 2017-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.container;

import ej.microui.MicroUI;
import ej.mwt.Widget;
import ej.mwt.util.Size;
import ej.widget.basic.PagingIndicator;
import ej.widget.container.util.LayoutOrientation;
import ej.widget.util.SwipeEventHandler;

/**
 * A list carousel is a container that contains a list of widget. The widgets are ordered from top to bottom. If the
 * widgets exceed the size of the carousel, they are scrolled.
 *
 * @see AbstractCarousel
 * @since 2.3.0
 */
public class ListCarousel extends AbstractCarousel {

	private boolean needToScroll;
	private int swipeSize;
	private int totalSize;
	private int totalSizeMinusLast;

	private int position;

	/**
	 * Creates a list carousel without cursor.
	 *
	 * @param orientation
	 *            the orientation of the carousel (see {@link LayoutOrientation}).
	 * @param cyclic
	 *            <code>true</code> if the carousel is cyclic, <code>false</code> otherwise.
	 */
	public ListCarousel(boolean orientation, boolean cyclic) {
		super(orientation, cyclic);
	}

	/**
	 * Creates a list carousel with a cursor that can be hidden automatically or not.
	 *
	 * @param orientation
	 *            the orientation of the carousel (see {@link LayoutOrientation}).
	 * @param cyclic
	 *            <code>true</code> if the carousel is cyclic, <code>false</code> otherwise.
	 * @param cursor
	 *            the cursor that indicate the progress in the carousel.
	 * @param autoHideCursor
	 *            <code>true</code> if the cursor hides automatically, <code>false</code> otherwise.
	 */
	public ListCarousel(boolean orientation, boolean cyclic, PagingIndicator cursor, boolean autoHideCursor) {
		super(orientation, cyclic, cursor, autoHideCursor);
	}

	@Override
	protected Size validateChildren(int boundsWidth, int boundsHeight, boolean horizontal) {
		int width;
		int height;
		if (horizontal) {
			width = 0;
			height = boundsHeight;
		} else {
			width = boundsWidth;
			height = 0;
		}
		// Each child takes the full height and the width it needs when horizontal, the opposite when vertical.
		for (Widget widget : getChildren()) {
			assert (widget != null);
			if (widget != this.cursor) {
				if (horizontal) {
					computeChildOptimalSize(widget, Widget.NO_CONSTRAINT, boundsHeight);
					width += widget.getWidth();
					height = Math.max(height, widget.getHeight());
				} else {
					computeChildOptimalSize(widget, boundsWidth, Widget.NO_CONSTRAINT);
					width = Math.max(width, widget.getWidth());
					height += widget.getHeight();
				}
			}
		}
		return new Size(width, height);
	}

	@Override
	protected SwipeEventHandler setBoundsChildren(int boundsX, int boundsY, int boundsWidth, int boundsHeight,
			int widgetsCount) {
		PagingIndicator cursor = this.cursor;
		boolean isHorizontal = (this.orientation == LayoutOrientation.HORIZONTAL);

		int currentX = boundsX;
		int currentY = boundsY;
		int[] sizes = new int[widgetsCount];
		int index = 0;
		// Each child takes the full height and the width it needs when horizontal, the opposite when vertical.
		for (Widget widget : getChildren()) {
			if (widget != cursor) {
				int widgetWidth = widget.getWidth();
				int widgetHeight = widget.getHeight();
				if (isHorizontal) {
					layOutChild(widget, currentX, currentY, widgetWidth, boundsHeight);
					sizes[index++] = widgetWidth;
					currentX += widgetWidth;
				} else {
					layOutChild(widget, currentX, currentY, boundsWidth, widgetHeight);
					sizes[index++] = widgetHeight;
					currentY += widgetHeight;
				}
			}
		}
		// Compute swipe size.
		int excess;
		int size;
		if (isHorizontal) {
			size = currentX - boundsX;
			excess = size - boundsWidth;
		} else {
			size = currentY - boundsY;
			excess = size - boundsHeight;
		}

		this.needToScroll = excess > 0;
		this.swipeSize = excess;
		this.totalSize = size;
		this.totalSizeMinusLast = size - sizes[widgetsCount - 1];
		this.position = 0;

		return new SwipeEventHandler(sizes, this.cyclic, true, isHorizontal, this);
	}

	@Override
	protected void setShownChildren() {
		for (Widget widget : getChildren()) {
			assert (widget != null);
			if (isVisible(widget)) {
				setShownChild(widget);
			}
		}
	}

	@Override
	public synchronized void onMove(final int position) {
		MicroUI.callSerially(new Runnable() {
			@Override
			public void run() {
				onMoveInternal(position);
			}
		});
		requestRender();
	}

	private void onMoveInternal(int position) {
		boolean isHorizontal = (this.orientation == LayoutOrientation.HORIZONTAL);

		int currentSize = 0;
		int totalSize = this.totalSize;
		boolean cyclic = this.cyclic;
		int relativePosition;
		if (cyclic) {
			// If cyclic, the selected item is always on top.
			relativePosition = position;
		} else {
			// If not cyclic, the selected item is on top at the beginning but on bottom at the end.
			relativePosition = position * this.swipeSize / this.totalSizeMinusLast;
		}
		int shift;
		if (this.needToScroll) {
			shift = relativePosition - this.position;
		} else {
			shift = 0;
		}
		PagingIndicator cursor = this.cursor;
		int widgetsCount = getChildrenCount();
		if (cursor != null) {
			widgetsCount--;
		}
		// Move all children according to the move.
		int selectedIndex = Integer.MIN_VALUE;
		float percent = 1f;
		int index = 0;
		int previousSize = 0;
		for (Widget widget : getChildren()) {
			assert (widget != null);
			if (widget != cursor) {
				int widgetSize = moveWidget(widget, isHorizontal, totalSize, cyclic, shift);

				// Search for the currently selected item.
				int halfWidget = currentSize + widgetSize / 2;
				if (selectedIndex == Integer.MIN_VALUE && position < halfWidget) {
					if (position < currentSize) {
						// Above this item.
						selectedIndex = index - 1;
						int distance = currentSize - position;
						percent = (float) distance / previousSize;
					} else {
						// Below this item.
						selectedIndex = index;
						int distance = position - currentSize;
						percent = 1f - (float) distance / widgetSize;
					}
				}
				previousSize = widgetSize;
				currentSize += widgetSize;
				index++;
			}
		}
		if (selectedIndex <= -1 || selectedIndex == widgetsCount - 1) {
			if (position < 0) {
				// Before the first.
				selectedIndex = 0;
				percent = 1f;
			} else {
				// After the last.
				selectedIndex = widgetsCount - 1;
				if (cyclic) {
					int distance = position - currentSize;
					percent = -(float) distance / previousSize;
				} else {
					percent = 1f;
				}
			}
		}
		setSelectedIndex(selectedIndex);
		if (cursor != null) {
			cursor.setSelectedItem(selectedIndex, percent);
		}

		this.position = relativePosition;
	}

}
