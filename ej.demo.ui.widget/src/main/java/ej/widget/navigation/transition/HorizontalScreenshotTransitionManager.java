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
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.motion.Motion;
import ej.motion.linear.LinearMotion;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.widget.navigation.Navigator;
import ej.widget.navigation.TransitionManager;
import ej.widget.navigation.page.Page;
import ej.widget.util.DrawScreenHelper;

/**
 * A transition manager that makes the pages slide horizontally.
 * <p>
 * A screenshot of the pages is done at the beginning of the transition, then for each step of the transition, these
 * images are drawn.
 * <p>
 * This transition manager allows faster transitions than {@link HorizontalTransitionManager} but requires more runtime
 * memory.
 */
public class HorizontalScreenshotTransitionManager extends TransitionManager {

	private static final long DURATION = 400;
	/**
	 * Arbitrary desktop ratio to decide to go back or to stay on current page.
	 */
	private static final int MIN_SHIFT_RATIO = 10;

	private int contentX;
	private int contentY;
	private Image oldImage;
	private Image newImage;
	private boolean animating;
	private int shift;
	private int currentX;

	// Drag state.
	private boolean pressed;
	private int pressedX;
	private boolean dragged;

	private Page previousPage;
	private Page nextPage;

	@Override
	public void animate(Page oldPage, final Page newPage, final boolean forward) {
		final Navigator navigation = getNavigator();
		final Rectangle contentBounds = getContentBounds();

		final int width = navigation.getWidth();
		this.contentX = contentBounds.getX();
		this.contentY = contentBounds.getY();
		final int contentWidth = contentBounds.getWidth();
		final int contentHeight = contentBounds.getHeight();

		if (forward) {
			this.shift = width;
		} else {
			this.shift = -width;
		}


		int startX;
		if (this.dragged) {
			if (oldPage == newPage) {
				final Image tempImage = this.newImage;
				this.newImage = this.oldImage;
				this.oldImage = tempImage;
				if (forward) {
					startX = -width + this.currentX;
					oldPage = this.previousPage;
				} else {
					startX = width + this.currentX;
					oldPage = this.nextPage;
				}
			} else {
				startX = this.currentX;
			}

			notifyTransitionStart(startX, -shift, oldPage, newPage);
		} else {
			startX = 0;

			notifyTransitionStart(startX, -shift, oldPage, newPage);
			// oldPage.onTransitionStart();
			// newPage.onTransitionStart();

			// Create image of the current page.
			createCurrentScreenshot(oldPage, contentWidth, contentHeight);

			// Create image of the new page.
			createNewPageScreenshot(newPage, contentWidth, contentHeight);
		}


		final long duration = DURATION - (DURATION * Math.abs(startX) / Math.abs(this.shift));
		final Motion motion = new LinearMotion(startX, -this.shift, duration);

		this.animating = true;
		final Animator animator = ServiceLoaderFactory.getServiceLoader().getService(Animator.class);
		animator.startAnimation(new HorizontalScreenshotAnimation(navigation, newPage, oldPage, motion));
	}

	private void createCurrentScreenshot(final Page oldPage, final int contentWidth, final int contentHeight) {
		this.oldImage = Image.createImage(contentWidth, contentHeight);
		final GraphicsContext g = this.oldImage.getGraphicsContext();
		g.drawRegion(Display.getDefaultDisplay(), oldPage.getAbsoluteX(), oldPage.getAbsoluteY(), contentWidth,
				contentHeight, 0, 0, GraphicsContext.LEFT | GraphicsContext.TOP);
		removePage(oldPage);
	}

	private void createNewPageScreenshot(final Page newPage, final int contentWidth, final int contentHeight) {
		this.newImage = createPageScreenshot(newPage, contentWidth, contentHeight);
	}

	private Image createPageScreenshot(final Page page, final int contentWidth, final int contentHeight) {
		page.validate(contentWidth, contentHeight);
		page.setSize(contentWidth, contentHeight);
		final Image image = Image.createImage(contentWidth, contentHeight);
		final GraphicsContext g = image.getGraphicsContext();
		DrawScreenHelper.draw(g, page);
		return image;
	}

	@Override
	public void render(final GraphicsContext g, final Style style, final Rectangle bounds) {
		if (this.animating || this.dragged) {
			g.translate(this.currentX, 0);
			final int contentX = this.contentX;
			g.drawImage(this.oldImage, contentX, this.contentY,
					GraphicsContext.LEFT | GraphicsContext.TOP);
			g.drawImage(this.newImage, contentX + this.shift, this.contentY,
					GraphicsContext.LEFT | GraphicsContext.TOP);
		}
	}

	@Override
	public boolean handleEvent(final int event) {
		if (canGoBackward() || canGoForward()) {
			if (Event.getType(event) == Event.POINTER) {
				final Pointer pointer = (Pointer) Event.getGenerator(event);
				final int pointerX = pointer.getX();
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

	private boolean onPointerPressed(final int pointerX) {
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

	private boolean onPointerDragged(final int pointerX) {
		if (this.pressed) {
			final Page currentPage = getNavigator().getCurrentPage();
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
	protected boolean onDrag(final Page currentPage, final int pointerX) {
		// Compute drag distance.
		int dragWidth = pointerX - this.pressedX;

		final Rectangle contentBounds = getContentBounds();
		final int contentX = contentBounds.getX();
		final int contentY = contentBounds.getY();
		final int contentWidth = contentBounds.getWidth();
		final int contentHeight = contentBounds.getHeight();

		final Navigator navigation = getNavigator();
		final int width = navigation.getWidth();

		Page previousPage = this.previousPage;
		Page nextPage = this.nextPage;
		if (dragWidth < 0) {
			// Next is visible.
			removePreviousPage();

			if (nextPage == null && canGoForward()) {
				nextPage = getNextPage();
				this.nextPage = nextPage;
				createNewPageScreenshot(nextPage, contentWidth, contentHeight);
				this.shift = width;
			}
			dragWidth = initPage(currentPage, pointerX, dragWidth, width + contentX + dragWidth, contentY, nextPage,
					contentWidth, contentHeight);
		} else if (dragWidth > 0) {
			// Previous is visible.
			removeNextPage();

			if (previousPage == null && canGoBackward()) {
				previousPage = getPreviousPage();
				this.previousPage = previousPage;
				createNewPageScreenshot(previousPage, contentWidth, contentHeight);
				this.shift = -width;
			}
			dragWidth = initPage(currentPage, pointerX, dragWidth, -width + contentX + dragWidth, contentY,
					previousPage, contentWidth, contentHeight);
		} else {
			if (this.dragged) {
				addPage(currentPage);
				// currentPage.onTransitionStop();
			}
			removePreviousPage();
			removeNextPage();
		}

		this.currentX = dragWidth;
		navigation.repaint();

		return dragWidth != 0;
	}

	private int initPage(final Page currentPage, final int pointerX, int dragWidth, final int pageX, final int pageY, final Page page,
			final int contentWidth, final int contentHeight) {
		if (page == null) {
			// Can't go backward.
			dragWidth = 0;
			this.pressedX = pointerX;
			if (this.dragged) {
				addPage(currentPage);
				// currentPage.onTransitionStop();
			}
		} else {
			if (!this.dragged) {
				createCurrentScreenshot(currentPage, contentWidth, contentHeight);
				// this.oldImage = createPageScreenshot(currentPage, getNavigator(), contentWidth, contentHeight);
				removePage(currentPage);
				// currentPage.onTransitionStart();
				// page.onTransitionStart();
			}
			page.setLocation(pageX, pageY);
		}
		return dragWidth;
	}

	private void removeNextPage() {
		final Page nextPage = this.nextPage;
		if (nextPage != null) {
			// nextPage.onTransitionStop();
			removePage(nextPage);
			this.nextPage = null;
			this.newImage = null;
		}
	}

	private void removePreviousPage() {
		final Page previousPage = this.previousPage;
		if (previousPage != null) {
			// previousPage.onTransitionStop();
			removePage(previousPage);
			this.previousPage = null;
			this.newImage = null;
		}
	}

	private boolean onPointerReleased(final int pointerX) {
		this.pressed = false;
		if (this.dragged) {
			final Navigator navigation = getNavigator();
			final Page currentPage = navigation.getCurrentPage();
			final int width = navigation.getWidth();
			final int moveX = pointerX - this.pressedX;
			final int minShiftX = width / MIN_SHIFT_RATIO;
			if (moveX > minShiftX) {
				// Go backward.
				removePage(this.previousPage);
				goBackward();
			} else if (-moveX > minShiftX) {
				// Go forward.
				removePage(this.nextPage);
				goForward();
			} else {
				// Restore current page.
				show(currentPage, moveX > 0);
			}
			return true;
		}
		return false;
	}

	private void resetAnimationState() {
		this.animating = false;
		this.oldImage = null;
		this.newImage = null;
	}

	private final class HorizontalScreenshotAnimation implements Animation {
		private final Page newPage;
		private final Page oldPage;
		private final Navigator navigation;
		private final Motion motion;
		private final Display display;

		private HorizontalScreenshotAnimation(final Navigator navigation, final Page newPage, final Page oldPage, final Motion motion) {
			this.newPage = newPage;
			this.oldPage = oldPage;
			this.navigation = navigation;
			this.motion = motion;
			this.display = Display.getDefaultDisplay();
			startTransition();
		}

		@Override
		public boolean tick(final long currentTimeMillis) {
			final int currentValue = this.motion.getCurrentValue();
			final boolean finished = motion.isFinished();
			HorizontalScreenshotTransitionManager.this.currentX = currentValue;
			this.navigation.repaint();
			if (finished) {
				this.display.callSerially(new Runnable() {
					@Override
					public void run() {
						HorizontalScreenshotAnimation.this.display.callSerially(new Runnable() {
							@Override
							public void run() {
								end();
							}
						});
					}
				});
			}
			return !finished;
		}

		private void end() {
			final Page newPage = this.newPage;
			addPage(newPage);
			setCurrentPage(newPage);
			setChildBounds(newPage);
			// this.oldPage.onTransitionStop();
			// newPage.onTransitionStop();
			resetAnimationState();
			resetDragState();
			stopTransition();
			notifyTransitionStop();
		}

	}

}
