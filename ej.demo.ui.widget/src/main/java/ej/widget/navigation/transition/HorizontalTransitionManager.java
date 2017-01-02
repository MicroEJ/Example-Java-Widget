/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * Use of this source code is subject to license terms.
 */
package ej.widget.navigation.transition;

import ej.animation.Animation;
import ej.animation.Animator;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.display.Display;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.motion.Motion;
import ej.motion.quart.QuartEaseOutMotion;
import ej.style.container.Rectangle;
import ej.widget.navigation.Navigator;
import ej.widget.navigation.TransitionManager;
import ej.widget.navigation.page.Page;

/**
 * A transition manager that makes the pages slide horizontally.
 * <p>
 * For each step of the transition, all widgets are drawn.
 */
public class HorizontalTransitionManager extends TransitionManager {

	private static final long DURATION = 400;
	/**
	 * Arbitrary desktop ratio to decide to go back or to stay on current page.
	 */
	private static final int MIN_SHIFT_RATIO = 10;

	// Drag state.
	private boolean pressed;
	private int pressedX;
	private boolean dragged;

	private Page previousPage;
	private Page nextPage;

	@Override
	public void animate(Page oldPage, Page newPage, boolean forward) {
		if (oldPage == null) {
			showPage(oldPage, newPage);
			return;
		}
		Navigator navigation = getNavigator();
		Rectangle contentBounds = getContentBounds();

		int width = navigation.getWidth();
		int contentX = contentBounds.getX();
		int contentY = contentBounds.getY();
		int contentWidth = contentBounds.getWidth();
		int contentHeight = contentBounds.getHeight();

		int shift;
		if (forward) {
			shift = width;
		} else {
			shift = -width;
		}

		int startX;
		if (this.dragged) {
			if (oldPage == newPage) {
				startX = newPage.getX() - contentX - shift;
				if (forward) {
					oldPage = this.previousPage;
				} else {
					oldPage = this.nextPage;
				}
			} else {
				startX = oldPage.getX() - contentX;
			}
		} else {
			startX = 0;
			// oldPage.onTransitionStart();
		}

		if (newPage.getParent() == null) {
			addPage(newPage, contentWidth, contentHeight);
			newPage.setLocation(startX + contentX + shift, contentY);
		}

		notifyTransitionStart(startX, -shift, oldPage, newPage);

		long duration = DURATION - (DURATION * Math.abs(startX) / Math.abs(shift));
		Motion motion = new QuartEaseOutMotion(startX, -shift, duration);

		Animator animator = ServiceLoaderFactory.getServiceLoader().getService(Animator.class);
		animator.startAnimation(
				new HorizontalAnimation(navigation, newPage, oldPage, motion, contentX, contentY, shift, forward));
	}

	private void addPage(Page page, int contentWidth, int contentHeight) {
		addPage(page);
		page.validate(contentWidth, contentHeight);
		page.setSize(contentWidth, contentHeight);
		// page.onTransitionStart();
	}

	@Override
	public boolean handleEvent(int event) {
		if (canGoBackward() || canGoForward()) {
			if (Event.getType(event) == Event.POINTER) {
				Pointer pointer = (Pointer) Event.getGenerator(event);
				int pointerX = pointer.getX();
				switch (Pointer.getAction(event)) {
				case Pointer.PRESSED:
					return onPointerPressed(pointerX);
				case Pointer.RELEASED:
					return onPointerReleased(pointerX);
				case Pointer.DRAGGED:
					return onPointerDragged(pointerX);
				}
			}
		}
		return super.handleEvent(event);
	}

	private boolean onPointerPressed(int pointerX) {
		// Initiate drag.
		this.pressed = true;
		this.pressedX = pointerX;
		this.dragged = false;
		return false;
	}

	private void resetDragState() {
		// Reset drag state.
		this.dragged = false;
		this.previousPage = null;
		this.nextPage = null;
	}

	private boolean onPointerDragged(int pointerX) {
		if (this.pressed) {
			Page currentPage = getNavigator().getCurrentPage();
			// Move pages.
			this.dragged = onDrag(currentPage, pointerX);

			return true;
		}
		return false;
	}

	/**
	 * Called when the user drags the pages to go back to the previous page.
	 *
	 * @param currentPage
	 *            the current page.
	 * @param pointerX
	 *            the pointer current x coordinate.
	 * @return <code>true</code> if two pages are visible, <code>false</code> otherwise.
	 */
	protected boolean onDrag(Page currentPage, int pointerX) {
		// Compute drag distance.
		int dragWidth = pointerX - this.pressedX;

		Rectangle contentBounds = getContentBounds();
		int contentX = contentBounds.getX();
		int contentY = contentBounds.getY();
		int contentWidth = contentBounds.getWidth();
		int contentHeight = contentBounds.getHeight();

		Navigator navigation = getNavigator();
		int width = navigation.getWidth();

		Page previousPage = this.previousPage;
		Page nextPage = this.nextPage;
		if (dragWidth < 0) {
			// Next is visible.
			removePreviousPage();

			if (nextPage == null && canGoForward()) {
				nextPage = getNextPage();
				this.nextPage = nextPage;
				addPage(nextPage, contentWidth, contentHeight);
			}
			dragWidth = initPage(currentPage, pointerX, dragWidth, width + contentX + dragWidth, contentY, nextPage);
		} else if (dragWidth > 0) {
			// Previous is visible.
			removeNextPage();

			if (previousPage == null && canGoBackward()) {
				previousPage = getPreviousPage();
				this.previousPage = previousPage;
				addPage(previousPage, contentWidth, contentHeight);
			}
			dragWidth = initPage(currentPage, pointerX, dragWidth, -width + contentX + dragWidth, contentY,
					previousPage);
		} else {
			// if (this.dragged) {
			// currentPage.onTransitionStop();
			// }
			removePreviousPage();
			removeNextPage();
		}

		notifyTransitionTick(dragWidth);
		currentPage.setLocation(contentX + dragWidth, contentY);
		navigation.repaint();
		return dragWidth != 0;
	}

	private int initPage(Page currentPage, int pointerX, int dragWidth, int pageX, int pageY, Page page) {
		if (page == null) {
			// Can't go backward.
			dragWidth = 0;
			this.pressedX = pointerX;
			// if (this.dragged) {
			// currentPage.onTransitionStop();
			// }
		} else {
			// if (!this.dragged) {
			// currentPage.onTransitionStart();
			// }
			page.setLocation(pageX, pageY);
		}
		notifyTransitionStart(0, currentPage.getWidth(), currentPage, page);
		return dragWidth;
	}

	private void removeNextPage() {
		Page nextPage = this.nextPage;
		if (nextPage != null) {
			// nextPage.onTransitionStop();
			removePage(nextPage);
			this.nextPage = null;
		}
	}

	private void removePreviousPage() {
		Page previousPage = this.previousPage;
		if (previousPage != null) {
			// previousPage.onTransitionStop();
			removePage(previousPage);
			this.previousPage = null;
		}
	}

	private boolean onPointerReleased(int pointerX) {
		this.pressed = false;
		if (this.dragged) {
			Navigator navigation = getNavigator();
			Page currentPage = navigation.getCurrentPage();
			int width = navigation.getWidth();
			int moveX = pointerX - this.pressedX;
			int minShiftX = width / MIN_SHIFT_RATIO;
			if (moveX > minShiftX) {
				// Go backward.
				removePage(this.previousPage);
				goBackward();
			} else if (-moveX > minShiftX) {
				// Go forward.
				removePage(this.nextPage);
				goForward();
			} else {
				notifyTransitionStop();
				// Restore current page.
				show(currentPage, moveX > 0);
			}
			return true;
		}
		notifyTransitionStop();
		return false;
	}

	private final class HorizontalAnimation implements Animation {
		private final Navigator navigation;
		private final Page newPage;
		private final Page oldPage;
		private final int contentX;
		private final Motion motion;
		private final int shift;
		private final int contentY;
		private final long startTimeMillis;
		private final Display display;

		private HorizontalAnimation(Navigator navigation, Page newPage, Page oldPage, Motion motion, int contentX,
				int contentY, int shift, boolean forward) {
			this.navigation = navigation;
			this.newPage = newPage;
			this.oldPage = oldPage;
			this.contentX = contentX;
			this.motion = motion;
			this.shift = shift;
			this.contentY = contentY;
			this.startTimeMillis = System.currentTimeMillis();
			this.display = Display.getDefaultDisplay();
			startTransition();
		}

		@Override
		public boolean tick(long currentTimeMillis) {
			long elapsed = currentTimeMillis - this.startTimeMillis;

			final int currentValue = this.motion.getValue(elapsed);
			notifyTransitionTick(currentValue);
			boolean finished = elapsed > DURATION;// motion.isFinished();
			if (finished) {
				this.display.callSerially(new Runnable() {
					@Override
					public void run() {
						end();
					}
				});
			} else {
				this.display.callSerially(new Runnable() {
					@Override
					public void run() {
						step(currentValue);
					}
				});
			}
			this.navigation.repaint();
			return !finished;
		}

		private void step(int currentValue) {
			int currentX = this.contentX + currentValue;
			this.oldPage.setLocation(currentX, this.contentY);
			this.newPage.setLocation(currentX + this.shift, this.contentY);
		}

		private void end() {
			Page oldPage = this.oldPage;
			Page newPage = this.newPage;
			removePage(oldPage);
			setCurrentPage(newPage);
			setChildBounds(newPage);
			// oldPage.onTransitionStop();
			// newPage.onTransitionStop();
			resetDragState();
			stopTransition();
			notifyTransitionStop();
		}

	}

}
