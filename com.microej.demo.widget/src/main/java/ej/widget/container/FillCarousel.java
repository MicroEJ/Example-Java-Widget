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
 * A fill carousel is a container that contains a list of widget. Each widget takes the size of the carousel, it must be
 * be scrolled to access the other widgets.
 *
 * @see AbstractCarousel
 * @since 2.3.0
 */
public class FillCarousel extends AbstractCarousel {

	private int totalSize;

	private int position;

	/**
	 * Creates a fill carousel without cursor.
	 *
	 * @param orientation
	 *            the orientation of the carousel (see {@link LayoutOrientation}).
	 * @param cyclic
	 *            <code>true</code> if the carousel is cyclic, <code>false</code> otherwise.
	 */
	public FillCarousel(boolean orientation, boolean cyclic) {
		super(orientation, cyclic);
	}

	/**
	 * Creates a fill carousel with a cursor that can be hidden automatically or not.
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
	public FillCarousel(boolean orientation, boolean cyclic, PagingIndicator cursor, boolean autoHideCursor) {
		super(orientation, cyclic, cursor, autoHideCursor);
	}

	@Override
	protected Size validateChildren(int boundsWidth, int boundsHeight, boolean horizontal) {
		int width = boundsWidth;
		int height = boundsHeight;
		// Each child takes the full height and the width it needs when horizontal, the opposite when vertical.
		for (Widget widget : getChildren()) {
			assert (widget != null);
			if (widget != this.cursor) {
				computeChildOptimalSize(widget, boundsWidth, boundsHeight);
				width = Math.max(width, widget.getWidth());
				height = Math.max(height, widget.getHeight());
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
		// Each child takes the full height and the width it needs when horizontal, the opposite when vertical.
		for (Widget widget : getChildren()) {
			assert (widget != null);
			if (widget != cursor) {
				layOutChild(widget, currentX, currentY, boundsWidth, boundsHeight);
				if (isHorizontal) {
					currentX += boundsWidth;
				} else {
					currentY += boundsHeight;
				}
			}
		}

		// Compute swipe size.
		int boundsSize;
		if (isHorizontal) {
			boundsSize = boundsWidth;
			this.totalSize = currentX - boundsX;
		} else {
			boundsSize = boundsHeight;
			this.totalSize = currentY - boundsY;
		}

		this.position = 0;
		return new SwipeEventHandler(widgetsCount, boundsSize, this.cyclic, true, isHorizontal, this);
	}

	@Override
	protected void setShownChildren() {
		int selectedIndex = getSelectedIndex();
		setShownChild(getChild(selectedIndex));
	}

	@Override
	public void onMove(final int position) {
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

		int totalSize = this.totalSize;
		boolean cyclic = this.cyclic;
		int shift = position - this.position;
		PagingIndicator cursor = this.cursor;
		int widgetsCount = getChildrenCount();
		if (cursor != null) {
			widgetsCount--;
		}

		// Search for the currently selected widget.
		int selectedIndex = position * widgetsCount / totalSize;
		float percent = 1f - ((float) ((position * widgetsCount) % totalSize)) / totalSize;
		// Focus the nearest widget.
		if (!cyclic) {
			if (percent > 1f) {
				// Before the first.
				selectedIndex = 0;
				percent = 1f;
			} else if (selectedIndex >= widgetsCount - 1 && percent < 1f) {
				// After the last.
				selectedIndex = widgetsCount - 1;
				percent = 1;
			}
		}

		// Move all children according to the move.
		for (Widget widget : getChildren()) {
			assert (widget != null);
			if (widget != cursor) {
				moveWidget(widget, isHorizontal, totalSize, cyclic, shift);
			}
		}
		setSelectedIndex(selectedIndex);
		if (cursor != null) {
			cursor.setSelectedItem(selectedIndex, percent);
		}

		this.position = position;
	}

}
