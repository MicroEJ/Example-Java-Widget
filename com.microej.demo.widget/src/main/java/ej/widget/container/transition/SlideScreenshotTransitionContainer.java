/*
 * Copyright 2017-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.container.transition;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;

/**
 * A slide transition container slides the widgets during the transition.
 *
 * @since 2.3.0
 */
public class SlideScreenshotTransitionContainer extends ScreenshotTransitionContainer {

	private final int direction;
	private final boolean overlap;
	private final boolean clip;

	private int previousStep;

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
	 * @param clip
	 *            <code>true</code> if the new widget does not move, <code>false</code> if the new widget slides.
	 */
	public SlideScreenshotTransitionContainer(int direction, boolean overlap, boolean clip) {
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
		this.clip = clip;
	}

	private boolean isHorizontal() {
		switch (this.direction) {
		case SlideDirection.UP:
		case SlideDirection.DOWN:
			return false;
		case SlideDirection.LEFT:
		case SlideDirection.RIGHT:
			return true;
		default:
			// Cannot occur.
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void render(GraphicsContext g) {
		if (isAnimating()) {
			g.translate(getContentX(), getContentY());
			int contentWidth = getContentWidth();
			int contentHeight = getContentHeight();
			g.intersectClip(0, 0, contentWidth, contentHeight);
			renderAnimation(g);
		} else {
			super.render(g);
		}
	}

	private void renderAnimation(GraphicsContext g) {
		int contentWidth = getContentWidth();
		int contentHeight = getContentHeight();

		int stop = this.stop;
		int step = this.step;
		int previousStep = this.previousStep;

		boolean newIsBelow = newIsBelow();
		int xPreviousDisplay;
		int yPreviousDisplay;
		int xDisplay;
		int yDisplay;
		int xScreenshot;
		int yScreenshot;
		int widthScreenshot;
		int heightScreenshot;
		if (isHorizontal()) {
			xDisplay = step;
			yDisplay = 0;
			xPreviousDisplay = previousStep;
			yPreviousDisplay = 0;
			yScreenshot = 0;
			if (newIsBelow) {
				if (this.direction == SlideDirection.LEFT) {
					xScreenshot = 0;
					widthScreenshot = xDisplay;
				} else {
					xScreenshot = xDisplay - stop;
					widthScreenshot = -xDisplay;
				}
			} else {
				xScreenshot = xDisplay - stop;
				widthScreenshot = contentWidth;
			}
			heightScreenshot = contentHeight;
		} else {
			xDisplay = 0;
			yDisplay = step;
			xPreviousDisplay = 0;
			yPreviousDisplay = previousStep;
			xScreenshot = 0;
			widthScreenshot = contentWidth;
			if (newIsBelow) {
				if (this.direction == SlideDirection.UP) {
					yScreenshot = 0;
					heightScreenshot = yDisplay;
				} else {
					yScreenshot = yDisplay - stop;
					heightScreenshot = -yDisplay;
				}
			} else {
				yScreenshot = yDisplay - stop;
				heightScreenshot = contentHeight;
			}
		}

		// Draw display on itself.
		if (!this.forward ? !this.clip : !this.overlap) {
			Painter.drawDisplayRegion(g, g.getTranslationX() + xPreviousDisplay, g.getTranslationY() + yPreviousDisplay,
					contentWidth, contentHeight, xDisplay, yDisplay);
		}

		// Draw new widget screenshot.
		if (newIsBelow) {
			g.intersectClip(xScreenshot, yScreenshot, widthScreenshot, heightScreenshot);
		} else if (this.clip && this.forward) {
			g.intersectClip(xScreenshot, yScreenshot, widthScreenshot, heightScreenshot);
		} else {
			g.translate(xScreenshot, yScreenshot);
			g.intersectClip(0, 0, contentWidth, contentHeight);
		}
		assert this.newScreenshot != null;
		Painter.drawImage(g, this.newScreenshot, 0, 0);

		this.previousStep = step;
	}

	@Override
	protected int getStop(int contentWidth, int contentHeight) {
		switch (getActualDirection(this.forward)) {
		case SlideDirection.UP:
			return -contentHeight;
		case SlideDirection.DOWN:
			return contentHeight;
		case SlideDirection.LEFT:
			return -contentWidth;
		case SlideDirection.RIGHT:
			return contentWidth;
		default:
			// Cannot occur.
			throw new IllegalArgumentException();
		}
	}

	@Override
	protected void resetContext() {
		super.resetContext();
		this.previousStep = 0;
	}

	// The new one is below if overlap and not forward.
	private boolean newIsBelow() {
		return this.overlap && !this.forward;
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
