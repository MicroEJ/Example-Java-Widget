/*
 * Copyright 2017-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.container.transition;

import ej.annotation.Nullable;
import ej.microui.display.BufferedImage;
import ej.microui.display.GraphicsContext;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Size;
import ej.mwt.util.OutlineHelper;

/**
 * A screenshot transition container uses a screenshot to realize the animation.
 *
 * @since 2.3.0
 */
public abstract class ScreenshotTransitionContainer extends TransitionContainer {

	/**
	 * <code>true</code> if going forward, <code>false</code> otherwise.
	 *
	 * @see #show(Widget, boolean)
	 */
	protected boolean forward;
	/**
	 * The new widget to show.
	 *
	 * @see #show(Widget, boolean)
	 */
	@Nullable
	protected Widget newWidget;
	/**
	 * The screenshot of the new widget.
	 */
	@Nullable
	protected BufferedImage newScreenshot;

	/**
	 * The stop value of the motion.
	 *
	 * @see #getStop(int, int)
	 */
	protected int stop;
	/**
	 * The current step value of the motion.
	 */
	protected int step;

	private boolean isAnimating;

	@Override
	public boolean isTransparent() {
		if (this.isAnimating) {
			// During the transition, the container cannot be transparent as long as the content of the display is used
			// for the rendering. Its parent must not clean the screen in this area.
			return false;
		} else {
			return super.isTransparent();
		}
	}

	@Override
	public void show(Widget widget, boolean forward) {
		// /!\ Beware that if some parameters can be changed between animation, the context of the previous
		// animation must be saved in order to finalize it correctly!
		cancelAnimation();

		// Update context after cleaning the previous one.
		this.forward = forward;

		int widgetsCount = getChildrenCount();
		if (!isAttached()) {
			removeAllChildren();
			addChild(widget);
		} else if (widgetsCount == 0) {
			removeAllChildren();
			addChild(widget);
			requestLayOut();
		} else {
			// Save new widget only for animation.
			this.newWidget = widget;

			int contentWidth = getContentWidth();
			int contentHeight = getContentHeight();

			// Add the widget in the hierarchy to inherit the style of the parent (this).
			addChild(widget);
			takeScreenshot(widget, contentWidth, contentHeight);

			Widget previousWidget = getChild(0);
			removeChild(previousWidget);

			this.stop = getStop(contentWidth, contentHeight);
			this.isAnimating = true;
			startAnimation(widget, previousWidget, forward, 0, this.stop);
		}
	}

	/**
	 * Takes a screenshot of a widget.
	 *
	 * @param widget
	 *            the widget.
	 * @param contentWidth
	 *            the content width.
	 * @param contentHeight
	 *            the content height.
	 */
	protected void takeScreenshot(Widget widget, int contentWidth, int contentHeight) {
		BufferedImage newScreenshot = new BufferedImage(contentWidth, contentHeight);
		GraphicsContext screenshotGraphicsContext = newScreenshot.getGraphicsContext();

		// Prepare widget.
		computeChildOptimalSize(widget, contentWidth, contentHeight);
		layOutChild(widget, 0, 0, contentWidth, contentHeight);

		// Draw the background of this container.
		Style style = getStyle();
		screenshotGraphicsContext.translate(-getContentX(), -getContentY());
		Size size = new Size(getWidth(), getHeight());
		OutlineHelper.applyOutlinesAndBackground(screenshotGraphicsContext, size, style);

		// Draw the widget.
		widget.render(screenshotGraphicsContext);

		this.newScreenshot = newScreenshot;
	}

	/**
	 * Gets the stop value of the motion. The start value being <code>0</code>.
	 *
	 * @param contentWidth
	 *            the content width.
	 * @param contentHeight
	 *            the content height.
	 * @return the stop value of the motion.
	 */
	protected abstract int getStop(int contentWidth, int contentHeight);

	/**
	 * Gets whether the transition container is doing the transition or not.
	 *
	 * @return <code>true</code> during the animation, <code>false</code> otherwise.
	 */
	protected boolean isAnimating() {
		return this.isAnimating;
	}

	/**
	 * Actually shows the new widget (add it as a child) and cleans the fields.
	 */
	@Override
	protected void resetContext() {
		super.resetContext();
		// Add and lay out the widget correctly.
		Widget newWidget = this.newWidget;
		if (newWidget != null) {
			this.newWidget = null;
			setShownChild(newWidget);
		}
		this.step = 0;
		final BufferedImage newScreenshot = this.newScreenshot;
		if (newScreenshot != null) {
			newScreenshot.close();
			this.newScreenshot = null;
		}
		this.isAnimating = false;
	}

	@Override
	protected void updateStep(int step) {
		this.step = step;
		requestRender();
	}

}
