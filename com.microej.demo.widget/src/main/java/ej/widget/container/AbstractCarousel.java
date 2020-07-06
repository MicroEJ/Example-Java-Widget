/*
 * Copyright 2017-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.container;

import ej.annotation.Nullable;
import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.util.Size;
import ej.service.ServiceFactory;
import ej.widget.animation.AnimationListener;
import ej.widget.basic.PagingIndicator;
import ej.widget.container.util.LayoutOrientation;
import ej.widget.util.SwipeEventHandler;
import ej.widget.util.Swipeable;

/**
 * The abstract carousel is a container that contains a list of widget. It defines the framework to create any kind of
 * carousel.
 * <p>
 * If the carousel is cyclic, when the last element is reached, the next one is the first one, and the other way round.
 * <p>
 * The carousel also displays a cursor showing the selected item. This cursor can be hidden automatically.
 *
 * @since 2.3.0
 */
public abstract class AbstractCarousel extends Container implements Swipeable, AnimationListener {

	private static final int HIDE_DELAY = 500;

	/**
	 * The orientation of the carousel.
	 */
	protected boolean orientation;
	/**
	 * Whether the carousel is cyclic or not.
	 */
	protected final boolean cyclic;
	/**
	 * Whether the cursor is hidden automatically or not.
	 */
	protected final boolean autoHide;
	/**
	 * The cursor.
	 */
	@Nullable
	protected final PagingIndicator cursor;

	@Nullable
	private SwipeEventHandler swipeEventHandler;

	@Nullable
	private TimerTask hideTask;
	private int selectedIndex;

	/**
	 * Creates a carousel without cursor.
	 *
	 * @param orientation
	 *            the orientation of the carousel (see {@link LayoutOrientation}).
	 * @param cyclic
	 *            <code>true</code> if the carousel is cyclic, <code>false</code> otherwise.
	 */
	public AbstractCarousel(boolean orientation, boolean cyclic) {
		this.orientation = orientation;
		this.cyclic = cyclic;
		this.cursor = null;
		this.autoHide = false;
		setEnabled(true);
	}

	/**
	 * Creates a carousel with a cursor that can be hidden automatically or not.
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
	public AbstractCarousel(boolean orientation, boolean cyclic, PagingIndicator cursor, boolean autoHideCursor) {
		this.orientation = orientation;
		this.cyclic = cyclic;
		this.cursor = cursor;
		cursor.setHorizontal(orientation == LayoutOrientation.HORIZONTAL);
		setEnabled(true);
		// Adds the cursor here to make sure that a method that needs to count the widgets (getChildrenCount() - 1)
		// before validation get the right value!
		addChild(cursor);
		this.autoHide = autoHideCursor;
	}

	@Override
	protected void onShown() {
		super.onShown();

		if (this.autoHide) {
			hideCursorsAsynchronous();
		}
	}

	@Override
	protected void onHidden() {
		super.onHidden();

		SwipeEventHandler swipeEventHandler = this.swipeEventHandler;
		if (swipeEventHandler != null) {
			swipeEventHandler.stop();
		}

		cancelHideCursor();
	}

	@Override
	public void addChild(Widget widget) {
		super.addChild(widget);
	}

	@Override
	public void removeChild(Widget widget) {
		super.removeChild(widget);
	}

	@Override
	public void insertChild(Widget child, int index) {
		super.insertChild(child, index);
	}

	@Override
	public void removeAllChildren() {
		super.removeAllChildren();
	}

	/**
	 * Sets the currently selected widget index.
	 *
	 * @param selectedIndex
	 *            the index to set.
	 */
	protected void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	/**
	 * Gets the currently selected widget index.
	 *
	 * @return the selected widget index.
	 */
	public int getSelectedIndex() {
		return this.selectedIndex;
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		boolean horizontal = (this.orientation == LayoutOrientation.HORIZONTAL);

		int widthHint = size.getWidth();
		int heightHint = size.getHeight();

		boolean widthConstraint = widthHint != Widget.NO_CONSTRAINT;
		boolean heightConstraint = heightHint != Widget.NO_CONSTRAINT;

		PagingIndicator cursor = this.cursor;
		boolean autoHide = this.autoHide;
		int cursorWidth;
		int cursorHeight;
		if (cursor != null) {
			// Add cursor here to make sure it's the last one (it is over the other ones when autohide).
			removeChild(cursor);
			addChild(cursor);

			// The cursor takes the full width and the height it needs when horizontal, the opposite when vertical.
			cursor.setItemsCount(getChildrenCount() - 1);
			computeChildOptimalSize(cursor, Widget.NO_CONSTRAINT, heightHint);
			cursorWidth = cursor.getWidth();
			cursorHeight = cursor.getHeight();
			if (!autoHide) {
				// When the cursor is hidden automatically, it is above the list, otherwise it takes some space.
				if (horizontal) {
					if (heightConstraint) {
						heightHint -= cursorHeight;
					}
				} else {
					if (widthConstraint) {
						widthHint -= cursorWidth;
					}
				}
			}
		} else {
			cursorWidth = 0;
			cursorHeight = 0;
		}

		Size childrenSize = validateChildren(widthHint, heightHint, horizontal);
		widthHint = childrenSize.getWidth();
		heightHint = childrenSize.getHeight();

		// If cursor is null, its width and height are set to 0, do not need to test it.
		if (!autoHide) {
			if (horizontal) {
				heightHint += cursorHeight;
			} else {
				widthHint += cursorWidth;
			}
		}

		size.setSize(widthHint, heightHint);
	}

	/**
	 * Lays out the children.
	 *
	 * @param boundsWidth
	 *            the width hint.
	 * @param boundsHeight
	 *            the height hint.
	 * @param horizontal
	 *            <code>true</code> if the carousel is horizontal, <code>false</code> otherwise.
	 * @return the optimal size of the children.
	 */
	protected abstract Size validateChildren(int boundsWidth, int boundsHeight, boolean horizontal);

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		boolean isHorizontal = (this.orientation == LayoutOrientation.HORIZONTAL);

		// The cursor takes the full width and the height it needs when horizontal, the opposite when vertical.
		PagingIndicator cursor = this.cursor;
		boolean autoHide = this.autoHide;
		if (cursor != null) {
			int cursorWidth = cursor.getWidth();
			int cursorHeight = cursor.getHeight();
			if (isHorizontal) {
				layOutChild(cursor, 0, contentHeight - cursorHeight, contentWidth, cursorHeight);
			} else {
				layOutChild(cursor, contentWidth - cursorWidth, 0, cursorWidth, contentHeight);
			}

			if (!autoHide) {
				if (isHorizontal) {
					contentHeight -= cursorHeight;
				} else {
					contentWidth -= cursorWidth;
				}
			}
		}

		int widgetsCount = getChildrenCount();
		if (cursor != null) {
			widgetsCount--;
		}
		this.selectedIndex = Math.min(this.selectedIndex, widgetsCount - 1);

		if (widgetsCount > 0) {
			this.swipeEventHandler = setBoundsChildren(0, 0, contentWidth, contentHeight, widgetsCount);
			if (autoHide) {
				this.swipeEventHandler.setAnimationListener(this);
			}

			if (this.selectedIndex != -1) {
				goTo(this.selectedIndex);
			}
		} else {
			this.swipeEventHandler = null;
		}
	}

	/**
	 * Sets the bounds of the children.
	 *
	 * @param boundsX
	 *            the x coordinate of the content.
	 * @param boundsY
	 *            the y coordinate of the content.
	 * @param boundsWidth
	 *            the width of the content.
	 * @param boundsHeight
	 *            the height of the content.
	 * @param widgetsCount
	 *            the number of widgets in the carousel.
	 * @return the swipe event handler to scroll the widgets.
	 */
	protected abstract SwipeEventHandler setBoundsChildren(int boundsX, int boundsY, int boundsWidth, int boundsHeight,
			int widgetsCount);

	/**
	 * Moves a widget when scrolling.
	 *
	 * @param widget
	 *            the widget to move.
	 * @param horizontal
	 *            <code>true</code> if the carousel is horizontal, <code>false</code> otherwise.
	 * @param totalSize
	 *            the cumulated size of the children (width if horizontal, height if vertical).
	 * @param cyclic
	 *            <code>true</code> if the carousel is cyclic, <code>false</code> otherwise.
	 * @param shift
	 *            the move shift.
	 * @return the size of the widget (width if horizontal, height if vertical).
	 */
	protected int moveWidget(Widget widget, boolean horizontal, int totalSize, boolean cyclic, int shift) {
		int widgetX = widget.getX();
		int widgetY = widget.getY();
		int widgetWidth = widget.getWidth();
		int widgetHeight = widget.getHeight();
		int widgetSize;
		if (horizontal) {
			widgetX -= shift;
			widgetSize = widgetWidth;
		} else {
			widgetY -= shift;
			widgetSize = widgetHeight;
		}
		if (cyclic) {
			// If cyclic, items need to be moved at the end or at the beginning.
			if (shift > 0) {
				if (horizontal) {
					if (widgetX + widgetSize < 0) {
						// Push at the end.
						widgetX += totalSize;
					}
				} else {
					if (widgetY + widgetSize < 0) {
						// Push at the end.
						widgetY += totalSize;
					}
				}
			} else {
				if (horizontal) {
					if (widgetX > getWidth()) {
						// Push at the beginning.
						widgetX -= totalSize;
					}
				} else {
					if (widgetY > getHeight()) {
						// Push at the beginning.
						widgetY -= totalSize;
					}
				}
			}
		}
		widget.setPosition(widgetX, widgetY);
		if (isVisible(widgetX, widgetY, widgetWidth, widgetHeight) && isShown()) {
			setShownChild(widget);
		} else {
			setHiddenChild(widget);
		}
		return widgetSize;
	}

	/**
	 * Gets whether the widget is visible or not.
	 *
	 * @param widgetX
	 *            the x coordinate of the widget.
	 * @param widgetY
	 *            the y coordinate of the widget.
	 * @param widgetWidth
	 *            the width of the widget.
	 * @param widgetHeight
	 *            the height of the widget.
	 *
	 * @return <code>true</code> if the widget is visible, <code>false</code> otherwise.
	 */
	protected boolean isVisible(int widgetX, int widgetY, int widgetWidth, int widgetHeight) {
		return widgetX + widgetWidth > 0 && widgetX < getWidth() && widgetY + widgetHeight > 0 && widgetY < getHeight();
	}

	/**
	 * Gets whether the widget is visible or not.
	 *
	 * @param widget
	 *            the widget to check.
	 * @return <code>true</code> if the widget is visible, <code>false</code> otherwise.
	 */
	protected boolean isVisible(Widget widget) {
		int widgetX = widget.getX();
		int widgetY = widget.getY();
		int widgetWidth = widget.getWidth();
		int widgetHeight = widget.getHeight();
		return isVisible(widgetX, widgetY, widgetWidth, widgetHeight);
	}

	@Override
	public boolean handleEvent(int event) {
		SwipeEventHandler swipeEventHandler = this.swipeEventHandler;
		if (swipeEventHandler != null && swipeEventHandler.handleEvent(event)) {
			return true;
		}
		return super.handleEvent(event);

	}

	/**
	 * Selects the previous widget.
	 */
	public void goToPrevious() {
		goToIncrement(false, 0);
	}

	/**
	 * Selects the next widget.
	 */
	public void goToNext() {
		goToIncrement(true, 0);
	}

	/**
	 * Animates the selection of the previous widget.
	 *
	 * @param duration
	 *            the duration of the animation.
	 * @throws IllegalArgumentException
	 *             if the given duration is less or equal than <code>0</code>.
	 */
	public void goToPrevious(int duration) {
		if (duration <= 0) {
			throw new IllegalArgumentException();
		}
		goToIncrement(false, duration);
	}

	/**
	 * Animates the selection of the next widget.
	 *
	 * @param duration
	 *            the duration of the animation.
	 * @throws IllegalArgumentException
	 *             if the given duration is less or equal than <code>0</code>.
	 */
	public void goToNext(int duration) {
		if (duration <= 0) {
			throw new IllegalArgumentException();
		}
		goToIncrement(true, duration);
	}

	private void goToIncrement(boolean next, int duration) {
		int increment = next ? 1 : -1;
		int nextItem = this.selectedIndex + increment;
		int widgetsCount = getChildrenCount();
		if (this.cursor != null) {
			widgetsCount--;
		}
		if (next) {
			if (nextItem == widgetsCount) {
				if (this.cyclic) {
					nextItem = 0;
				} else {
					// Cannot go downward.
					return;
				}
			}
		} else {
			if (nextItem == -1) {
				if (this.cyclic) {
					nextItem = getChildrenCount() - 1;
					if (this.cursor != null) {
						nextItem--;
					}
				} else {
					// Cannot go upward.
					return;
				}
			}
		}
		goToInternal(nextItem, duration);
	}

	/**
	 * Selects a widget from its index.
	 *
	 * @param index
	 *            the widget index.
	 * @throws IllegalArgumentException
	 *             if the given index is not valid (between <code>0</code> and the number of items in the carousel).
	 */
	public void goTo(int index) {
		goToInternal(index, 0);
	}

	/**
	 * Animates the selection of a widget from its index.
	 *
	 * @param index
	 *            the widget index.
	 * @param duration
	 *            the duration of the animation.
	 * @throws IllegalArgumentException
	 *             if the given duration is less or equal than <code>0</code>.
	 * @throws IllegalArgumentException
	 *             if the given index is not valid (between <code>0</code> and the number of items in the carousel).
	 */
	public void goTo(int index, long duration) {
		if (duration <= 0) {
			throw new IllegalArgumentException();
		}
		goToInternal(index, duration);
	}

	private void goToInternal(int index, long duration) {
		int widgetsCount = getChildrenCount();
		if (this.cursor != null) {
			widgetsCount--;
		}
		if (index < 0 || index >= widgetsCount) {
			throw new IllegalArgumentException();
		}

		SwipeEventHandler swipeEventHandler = this.swipeEventHandler;
		if (swipeEventHandler == null) {
			this.selectedIndex = index;
			return;
		}

		if (this.autoHide) {
			showCursor();
			hideCursorsAsynchronous();
		}
		boolean isHorizontal = (this.orientation == LayoutOrientation.HORIZONTAL);
		PagingIndicator cursor = this.cursor;
		int size = 0;
		int currentIndex = 0;
		for (Widget widget : getChildren()) {
			if (widget != cursor) {
				if (currentIndex++ == index) {
					if (duration == 0) {
						swipeEventHandler.moveTo(size);
					} else {
						swipeEventHandler.moveTo(size, duration);
					}
					break;
				}
				if (isHorizontal) {
					size += widget.getWidth();
				} else {
					size += widget.getHeight();
				}
			}
		}
	}

	@Override
	public void onAnimationStarted() {
		showCursor();
	}

	@Override
	public void onAnimationStopped() {
		hideCursorsAsynchronous();
	}

	private void showCursor() {
		cancelHideCursor();
		final PagingIndicator cursor = this.cursor;
		if (cursor != null) {
			cursor.show();
		}
	}

	/**
	 * Schedules a task to hide the cursor.
	 */
	private void hideCursorsAsynchronous() {
		Timer timer = ServiceFactory.getService(Timer.class, Timer.class);
		this.hideTask = new TimerTask() {
			@Override
			public void run() {
				hideCursor();
			}
		};
		timer.schedule(this.hideTask, HIDE_DELAY);
	}

	/**
	 * Cancels the task that hides the cursor.
	 */
	private void cancelHideCursor() {
		TimerTask hideTask = this.hideTask;
		if (hideTask != null) {
			hideTask.cancel();
		}
	}

	private void hideCursor() {
		final PagingIndicator cursor = this.cursor;
		if (cursor != null) {
			cursor.hide();
		}
	}

}
