/*
 * Copyright 2017-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.container.transition;

import ej.microui.MicroUI;
import ej.mwt.Widget;

/**
 * A slide transition container slides the widgets during the transition.
 *
 * @since 2.3.0
 */
public class SlideTransitionContainer extends TransitionContainer {

	private final int direction;
	private final boolean overlap;

	private boolean forward;
	private int distance;

	/**
	 * Creates a slide transition manager.
	 * <p>
	 * The given direction is the way the widgets slide when forward. For example, passing {@link SlideDirection#UP},
	 * the new widget starts below and goes upward.
	 *
	 * @param direction
	 *            one of {@link SlideDirection#LEFT}, {@link SlideDirection#RIGHT}, {@link SlideDirection#UP} and
	 *            {@link SlideDirection#DOWN}.
	 * @param overlap
	 *            <code>true</code> if the new widget overlaps the previous one, <code>false</code> if the new widget
	 *            pushes the previous one.
	 */
	public SlideTransitionContainer(int direction, boolean overlap) {
		// Validate given direction.
		switch (direction) {
		case SlideDirection.UP:
		case SlideDirection.DOWN:
		case SlideDirection.LEFT:
		case SlideDirection.RIGHT:
			break;
		default:
			throw new IllegalArgumentException();
		}
		this.direction = direction;
		this.overlap = overlap;
	}

	@Override
	public void show(Widget widget, boolean forward) {
		// /!\ Beware that if 'direction' or 'overlap' can be changed between animation, the context of the previous
		// animation must be saved in order to finalize it correctly!
		cancelAnimation();

		int widgetsCount = getChildrenCount();
		if (!isAttached()) {
			removeAllChildren();
			addChild(widget);
		} else if (widgetsCount == 0) {
			removeAllChildren();
			addChild(widget);
			requestLayOut();
		} else {
			// Since the add is not done in this thread, test that the widget is already attached here.
			if (widget.isAttached()) {
				throw new IllegalArgumentException();
			}
			int contentX = getContentX();
			int contentY = getContentY();
			int contentWidth = getContentWidth();
			int contentHeight = getContentHeight();

			boolean newIsBelow = newIsBelow(forward);
			int distance;
			switch (getActualDirection(forward)) {
			case SlideDirection.UP:
				distance = -contentHeight;
				if (!newIsBelow) {
					contentY -= distance;
				}
				break;
			case SlideDirection.DOWN:
				distance = contentHeight;
				if (!newIsBelow) {
					contentY -= distance;
				}
				break;
			case SlideDirection.LEFT:
				distance = -contentWidth;
				if (!newIsBelow) {
					contentX -= distance;
				}
				break;
			case SlideDirection.RIGHT:
				distance = contentWidth;
				if (!newIsBelow) {
					contentX -= distance;
				}
				break;
			default:
				// Cannot occur.
				throw new IllegalArgumentException();
			}

			start(widget, forward, contentX, contentY, contentWidth, contentHeight, newIsBelow, distance);

			this.distance = distance;
		}
	}

	private void start(final Widget widget, final boolean forward, final int contentX, final int contentY,
			final int contentWidth, final int contentHeight, final boolean newIsBelow, final int distance) {
		MicroUI.callSerially(new Runnable() {
			@Override
			public void run() {
				SlideTransitionContainer.this.forward = forward;
				final Widget previousWidget = getChild(0);
				addChild(widget);
				computeChildOptimalSize(widget, contentWidth, contentHeight);
				layOutChild(widget, contentX, contentY, contentWidth, contentHeight);
				if (newIsBelow) {
					bringChildBackward(widget);
				}
				setShownChild(widget);
				startAnimation(widget, previousWidget, forward, 0, distance);
			}
		});
	}

	@Override
	protected void resetContext() {
		super.resetContext();
		// Ensure there is only one widget left.
		MicroUI.callSerially(new Runnable() {
			@Override
			public void run() {
				if (getChildrenCount() == 2) {
					int indexToRemove;
					if (newIsBelow()) {
						indexToRemove = 1;
					} else {
						indexToRemove = 0;
					}
					final Widget widget = getChild(indexToRemove);
					removeChild(widget);
					// Lay out the remaining widget correctly.
					setChildBounds();
				}
			}
		});
		requestRender();
	}

	@Override
	protected void updateStep(final int step) {
		MicroUI.callSerially(new Runnable() {
			@Override
			public void run() {
				moveWidget(step);
			}
		});
		requestRender();
	}

	private void moveWidget(int step) {
		if (getChildrenCount() != 2) {
			// Animation cancelled.
			return;
		}

		int stopValue = this.distance;
		// Move widgets.
		Widget firstWidget = getChild(0);
		Widget secondWidget = getChild(1);
		switch (this.direction) {
		case SlideDirection.LEFT:
		case SlideDirection.RIGHT:
			int contentX = getContentX();
			int widgetY = firstWidget.getY();
			// The first one remains still when overlap.
			if (!this.overlap) {
				firstWidget.setPosition(contentX + step, widgetY);
			}
			// When the new one is below, the widgets order is reversed.
			if (newIsBelow()) {
				secondWidget.setPosition(contentX + step, widgetY);
			} else {
				secondWidget.setPosition(contentX + step - stopValue, widgetY);
			}
			break;
		case SlideDirection.UP:
		case SlideDirection.DOWN:
			int widgetX = firstWidget.getX();
			int contentY = getContentY();
			// The first one remains still when overlap.
			if (!this.overlap) {
				firstWidget.setPosition(widgetX, contentY + step);
			}
			// When the new one is below, the widgets order is reversed.
			if (newIsBelow()) {
				secondWidget.setPosition(widgetX, contentY + step);
			} else {
				secondWidget.setPosition(widgetX, contentY + step - stopValue);
			}
			break;
		}
	}

	// The new one is below if overlap and not forward.
	private boolean newIsBelow() {
		return newIsBelow(this.forward);
	}

	private boolean newIsBelow(boolean forward) {
		return this.overlap && !forward;
	}

	private int getActualDirection(boolean forward) {
		if (forward) {
			return this.direction;
		} else {
			switch (this.direction) {
			case SlideDirection.UP:
				return SlideDirection.DOWN;
			case SlideDirection.DOWN:
				return SlideDirection.UP;
			case SlideDirection.LEFT:
				return SlideDirection.RIGHT;
			case SlideDirection.RIGHT:
				return SlideDirection.LEFT;
			default:
				// Cannot occur.
				throw new IllegalArgumentException();
			}
		}
	}

}
