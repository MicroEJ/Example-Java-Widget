/*
 * Copyright 2013-2024 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.common.scroll;

import ej.annotation.Nullable;
import ej.bon.XMath;
import ej.drawing.ShapePainter.Cap;
import ej.microui.MicroUI;
import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.animation.Animator;
import ej.mwt.util.Size;
import ej.widget.swipe.SwipeEventHandler;
import ej.widget.swipe.SwipeListener;
import ej.widget.swipe.Swipeable;

/**
 * Allows to scroll a widget horizontally or vertically.
 */
public class Scroll extends Container {

	private @Nullable Widget child;
	private @Nullable Scrollable scrollableChild;
	private boolean horizontal;

	// Scroll bar management
	private final Scrollbar scrollbar;
	private boolean sbVisible;
	private boolean sbBeforeContent;
	private boolean sbOverlap;

	// Swipe management.
	private @Nullable SwipeEventHandler swipeEventHandler;
	private final ScrollAssistant assistant;
	private int value;
	private boolean allowExcess;

	private int childCoordinate;

	/**
	 * Creates a scroll container specifying its orientation and the visibility of the scrollbar.
	 *
	 * @param horizontal
	 *            <code>true</code> to scroll horizontally, <code>false</code> to scroll vertically.
	 */
	public Scroll(boolean horizontal) {
		super(true);
		this.horizontal = horizontal;
		this.scrollbar = new Scrollbar(0);
		this.scrollbar.setHorizontal(horizontal);
		this.sbVisible = true;
		this.assistant = new ScrollAssistant();
		this.allowExcess = true;

		super.addChild(this.scrollbar);
	}

	@Override
	protected void setShownChildren() {
		Widget child = this.child;
		if (child != null) {
			setShownChild(child);
		}
		if (this.sbVisible) {
			setShownChild(this.scrollbar);
		}
	}

	/**
	 * Sets the child to scroll.
	 * <p>
	 * The given widget can implement {@link Scrollable} and be notified about when the visible area changes (for
	 * example for optimization purpose).
	 * <p>
	 * Should be called in the UI thread to avoid concurrency issues.
	 *
	 * @param child
	 *            the child to scroll.
	 * @see MicroUI#isUIThread()
	 */
	public void setChild(Widget child) {
		Widget oldChild = this.child;
		if (child != oldChild) {
			if (oldChild != null) {
				// replace old child by new child
				replaceChild(getChildIndex(oldChild), child);
			} else {
				// insert new child before scrollbar
				insertChild(child, 0);
			}

			// update fields
			this.child = child;
			if (child instanceof Scrollable) {
				this.scrollableChild = (Scrollable) child;
			} else {
				this.scrollableChild = null;
			}
		}
	}

	/**
	 * Sets the scroll orientation: horizontal or vertical.
	 *
	 * @param horizontal
	 *            <code>true</code> to scroll horizontally, <code>false</code> to scroll vertically.
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
		this.scrollbar.setHorizontal(horizontal);
	}

	/**
	 * Gets the scroll orientation: horizontal or vertical.
	 *
	 * @return horizontal <code>true</code> if scroll is horizontal, <code>false</code> if scroll is vertical.
	 */
	protected boolean isHorizontal() {
		return this.horizontal;
	}

	/**
	 * Sets whether the scrollbar is visible or not.
	 *
	 * @param visible
	 *            <code>true</code> to show the scrollbar, <code>false</code> to hide it.
	 */
	public void showScrollbar(boolean visible) {
		this.sbVisible = visible;
	}

	/**
	 * Sets the scrollbar layout.
	 * <p>
	 * By default, the scrollbar is laid out after the content.
	 *
	 * @param beforeContent
	 *            <code>true</code> to lay out the scrollbar before (top/left) the content, <code>false</code> to lay
	 *            out the scrollbar after (right/bottom) the content.
	 */
	public void setScrollbarBeforeContent(boolean beforeContent) {
		this.sbBeforeContent = beforeContent;
	}

	/**
	 * Sets the scrollbar overlapping the content.
	 * <p>
	 * By default, the scrollbar is laid out next to the content.
	 *
	 * @param overlap
	 *            <code>true</code> to lay out the scrollbar over the content, <code>false</code> to lay out the
	 *            scrollbar next to the content.
	 */
	public void setScrollbarOverlap(boolean overlap) {
		this.sbOverlap = overlap;
	}

	/**
	 * Sets the scrollbar caps.
	 *
	 * @param caps
	 *            the cap style.
	 */
	public void setScrollBarCaps(Cap caps) {
		this.scrollbar.setCaps(caps);
	}

	/**
	 * Sets if the viewport is allowed to scroll beyond the child bounds.
	 * <p>
	 * By default, the viewport can scroll beyond the child bounds.
	 *
	 * @param allowExcess
	 *            {@code true} if the viewport can be scrolled beyond the child bounds, {@code false} otherwise.
	 */
	public void setAllowExcess(boolean allowExcess) {
		this.allowExcess = allowExcess;
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		int width = 0;
		int height = 0;

		Widget child = this.child;
		if (child != null) {
			int widthHint = size.getWidth();
			int heightHint = size.getHeight();

			if (this.horizontal) {
				computeChildOptimalSize(child, Widget.NO_CONSTRAINT, heightHint);
				if (this.sbVisible) {
					computeChildOptimalSize(this.scrollbar, widthHint, Widget.NO_CONSTRAINT);
				} else {
					computeChildOptimalSize(this.scrollbar, 0, 0);
				}
			} else {
				computeChildOptimalSize(child, widthHint, Widget.NO_CONSTRAINT);
				if (this.sbVisible) {
					computeChildOptimalSize(this.scrollbar, Widget.NO_CONSTRAINT, heightHint);
				} else {
					computeChildOptimalSize(this.scrollbar, 0, 0);
				}
			}

			width = child.getWidth();
			height = child.getHeight();
		}

		// Set container optimal size.
		size.setSize(width, height);
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		Scrollable scrollableChild = this.scrollableChild;
		if (scrollableChild != null) {
			scrollableChild.initializeViewport(contentWidth, contentHeight);
		}

		layoutOnScroll(contentWidth, contentHeight);

		int childCoordinate = -this.scrollbar.getValue();
		updateViewport(childCoordinate);
	}

	private void layoutOnScroll(int contentWidth, int contentHeight) {
		Widget child = this.child;
		int childOptimalWidth;
		int childOptimalHeight;
		if (child != null) {
			childOptimalWidth = child.getWidth();
			childOptimalHeight = child.getHeight();
		} else {
			childOptimalWidth = 0;
			childOptimalHeight = 0;
		}

		int excess = treatExcess(child, contentWidth, contentHeight, childOptimalWidth, childOptimalHeight);
		if (excess > 0) {
			SwipeEventHandler swipeEventHandler = this.swipeEventHandler;
			if (swipeEventHandler != null) {
				swipeEventHandler.stop();
			}

			Animator animator = getDesktop().getAnimator();
			swipeEventHandler = createSwipeEventHandler(excess, this.horizontal, this.assistant, animator);
			swipeEventHandler.setSwipeListener(this.assistant);
			swipeEventHandler.moveTo(this.value);
			this.swipeEventHandler = swipeEventHandler;
		}
	}

	private SwipeEventHandler createSwipeEventHandler(int size, boolean horizontal, Swipeable assistant,
			Animator animator) {
		Scrollable scrollable = this.scrollableChild;
		int[] itemSizes;
		if (scrollable != null && scrollable.snapToItems()) {
			itemSizes = scrollable.getItemSizes();
		} else {
			itemSizes = null;
		}
		if (itemSizes != null) {
			return new SwipeEventHandler(this, itemSizes, false, true, horizontal, assistant, animator);
		} else {
			return new SwipeEventHandler(this, size, false, horizontal, assistant, animator);
		}
	}

	private int treatExcess(@Nullable Widget child, int contentWidth, int contentHeight, int childOptimalWidth,
			int childOptimalHeight) {
		int excess = computeExcess(contentWidth, contentHeight, childOptimalWidth, childOptimalHeight);
		int scrollbarWidth = 0;
		int scrollbarHeight = 0;
		if (excess > 0) {
			Scrollbar scrollbar = this.scrollbar;
			scrollbar.setMaximum(excess);
			int scrollbarX = 0;
			int scrollbarY = 0;
			if (this.sbVisible) {
				if (this.horizontal) {
					scrollbarWidth = contentWidth;
					scrollbarHeight = scrollbar.getHeight();
					scrollbarY = this.sbBeforeContent ? 0 : contentHeight - scrollbarHeight;
				} else {
					scrollbarWidth = scrollbar.getWidth();
					scrollbarHeight = contentHeight;
					scrollbarX = this.sbBeforeContent ? 0 : contentWidth - scrollbarWidth;
				}
			}
			this.layOutChild(scrollbar, scrollbarX, scrollbarY, scrollbarWidth, scrollbarHeight);
		}

		layOutChild(child, contentWidth, contentHeight, childOptimalWidth, childOptimalHeight, scrollbarHeight,
				scrollbarWidth);
		return excess;
	}

	private int computeExcess(int contentWidth, int contentHeight, int childOptimalWidth, int childOptimalHeight) {
		return this.horizontal ? childOptimalWidth - contentWidth : childOptimalHeight - contentHeight;
	}

	private void layOutChild(Widget child, int contentWidth, int contentHeight, int childOptimalWidth,
			int childOptimalHeight, int scrollbarHeight, int scrollbarWidth) {
		if (child == null) {
			return;
		}
		int childX;
		int childY;
		int childWidth;
		int childHeight;
		if (this.horizontal) {
			childX = 0;
			childY = (this.sbBeforeContent && !this.sbOverlap) ? scrollbarHeight : 0;
			childWidth = childOptimalWidth;
			childHeight = this.sbOverlap ? contentHeight : contentHeight - scrollbarHeight;
		} else {
			childX = (this.sbBeforeContent && !this.sbOverlap) ? scrollbarWidth : 0;
			childY = 0;
			childHeight = childOptimalHeight;
			childWidth = this.sbOverlap ? contentWidth : contentWidth - scrollbarWidth;
		}
		layOutChild(child, childX, childY, childWidth, childHeight);
	}

	@Override
	protected void onHidden() {
		super.onHidden();

		SwipeEventHandler swipeEventHandler = this.swipeEventHandler;
		if (swipeEventHandler != null) {
			swipeEventHandler.stop();
			swipeEventHandler.moveTo(limit(this.value));
		}
	}

	/**
	 * Scrolls to an item without animation.
	 * <p>
	 * Should be called in the UI thread to avoid concurrency issues.
	 *
	 * @param index
	 *            the item index.
	 * @throws UnsupportedOperationException
	 *             if this scroll content is not {@link Scrollable}.
	 * @throws UnsupportedOperationException
	 *             if this scroll content does not provide item sizes.
	 * @see Scrollable#getItemSizes()
	 * @see MicroUI#isUIThread()
	 */
	public void scrollToIndex(int index) {
		scrollToIndex(index, false);
	}

	/**
	 * Scrolls to an item.
	 * <p>
	 * Should be called in the UI thread to avoid concurrency issues.
	 *
	 * @param index
	 *            the item index.
	 * @param animate
	 *            whether the scrolling action should be animated.
	 * @throws UnsupportedOperationException
	 *             if this scroll content is not {@link Scrollable}.
	 * @throws UnsupportedOperationException
	 *             if this scroll content does not provide item sizes.
	 * @see Scrollable#getItemSizes()
	 * @see MicroUI#isUIThread()
	 */
	public void scrollToIndex(int index, boolean animate) {
		Scrollable child = this.scrollableChild;
		if (child == null) {
			throw new UnsupportedOperationException();
		}
		int[] itemSizes = child.getItemSizes();
		int position = 0;
		for (int i = 0; i < index; i++) {
			position += itemSizes[i];
		}
		scrollTo(position, animate);
	}

	/**
	 * Scrolls to a position.
	 * <p>
	 * Should be called in the UI thread to avoid concurrency issues.
	 *
	 * @param position
	 *            the x or y target (depending on the orientation).
	 * @param animate
	 *            whether the scrolling action should be animated.
	 * @see MicroUI#isUIThread()
	 */
	public void scrollTo(int position, boolean animate) {
		position = limit(position);
		this.scrollbar.setValue(position);
		SwipeEventHandler swipeEventHandler = this.swipeEventHandler;
		if (swipeEventHandler != null) {
			if (animate) {
				swipeEventHandler.moveTo(position, SwipeEventHandler.DEFAULT_DURATION);
			} else {
				swipeEventHandler.moveTo(position);
			}
		}
	}

	private int limit(int position) {
		int max;
		Widget child = this.child;
		if (child != null) {
			if (this.horizontal) {
				max = child.getWidth() - getContentWidth();
			} else {
				max = child.getHeight() - getContentHeight();
			}
			max = Math.max(0, max);
		} else {
			max = 0;
		}
		return XMath.limit(position, 0, max);
	}

	/**
	 * Scrolls to a position without animation.
	 * <p>
	 * Should be called in the UI thread to avoid concurrency issues.
	 *
	 * @param position
	 *            the x or y target (depending on the orientation).
	 * @see MicroUI#isUIThread()
	 */
	public void scrollTo(int position) {
		scrollTo(position, false);
	}

	/**
	 * Scrolls to the beginning of the content.
	 * <p>
	 * Should be called in the UI thread to avoid concurrency issues.
	 *
	 * @param animate
	 *            whether the scrolling action should be animated.
	 * @see MicroUI#isUIThread()
	 */
	public void scrollToBeginning(boolean animate) {
		SwipeEventHandler swipeEventHandler = this.swipeEventHandler;
		if (swipeEventHandler != null) {
			if (animate) {
				swipeEventHandler.moveToBeginning(SwipeEventHandler.DEFAULT_DURATION);
			} else {
				swipeEventHandler.moveToBeginning();
			}
		}
	}

	/**
	 * Scrolls to the end of the content.
	 * <p>
	 * Should be called in the UI thread to avoid concurrency issues.
	 *
	 * @param animate
	 *            whether the scrolling action should be animated.
	 * @see MicroUI#isUIThread()
	 */
	public void scrollToEnd(boolean animate) {
		SwipeEventHandler swipeEventHandler = this.swipeEventHandler;
		if (swipeEventHandler != null) {
			if (animate) {
				swipeEventHandler.moveToEnd(SwipeEventHandler.DEFAULT_DURATION);
			} else {
				swipeEventHandler.moveToEnd();
			}
		}
	}

	@Override
	public boolean handleEvent(int event) {
		SwipeEventHandler swipeEventHandler = this.swipeEventHandler;
		if (swipeEventHandler != null && swipeEventHandler.handleEvent(event)) {
			return true;
		}
		return super.handleEvent(event);
	}

	private void updateViewport(int x, int y) {
		Scrollable scrollableChild = this.scrollableChild;
		if (scrollableChild != null) {
			scrollableChild.updateViewport(x, y);
		}
		Widget child = this.child;
		if (child != null) {
			child.setPosition(x, y);
		}
	}

	private void updateViewport(int childCoordinate) {
		Widget child = this.child;
		if (child != null) {
			if (this.horizontal) {
				updateViewport(childCoordinate, child.getY());
			} else {
				updateViewport(child.getX(), childCoordinate);
			}
		}
	}

	/**
	 * Gets the child.
	 *
	 * @return this scroll child.
	 */
	protected @Nullable Widget getChild() {
		return this.child;
	}

	/**
	 * Gets the child coordinate.
	 *
	 * @return this scroll child coordinate.
	 */
	protected int getChildCoordinate() {
		return this.childCoordinate;
	}

	/**
	 * Gets the scrollbar.
	 *
	 * @return the scrollbar.
	 */
	protected Widget getScrollbar() {
		return this.scrollbar;
	}

	/**
	 * Returns whether the scrollbar should be shown.
	 *
	 * @return {@code true} if the scrollbar should be shown, {@code false} otherwise.
	 */
	protected boolean shouldShowScrollbar() {
		return this.sbVisible;
	}

	private void shift() {
		if (isShown()) {
			int value = this.value;
			this.scrollbar.setValue(value);

			int childCoordinate = computeChildCoordinate(value);
			updateViewport(childCoordinate);
			this.childCoordinate = childCoordinate;
		}
	}

	private int computeChildCoordinate(int value) {
		Scrollable scrollable = this.scrollableChild;
		if (scrollable != null && scrollable.snapToItems()) {
			return -value;
		} else {
			return -(value + this.scrollbar.getValue()) / 2;
		}
	}

	class ScrollAssistant implements Swipeable, SwipeListener {

		@Override
		public void onSwipeStarted() {
			Scroll.this.scrollbar.show();
		}

		@Override
		public void onSwipeStopped() {
			Scrollbar scrollbar = Scroll.this.scrollbar;
			scrollbar.hide();
			scrollbar.requestRender();
		}

		@Override
		public void onMove(int position) {
			Scroll scroll = Scroll.this;
			if (scroll.value != position) {
				scroll.value = scroll.allowExcess ? position : limit(position);
				scroll.shift();
				requestRender();
			}
		}

	}

}
