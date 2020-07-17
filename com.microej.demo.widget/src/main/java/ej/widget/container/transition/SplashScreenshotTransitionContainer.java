/*
 * Copyright 2017-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.container.transition;

import ej.annotation.Nullable;
import ej.drawing.TransformPainter;
import ej.microui.display.BufferedImage;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.Painter;
import ej.mwt.Widget;

/**
 * A splash transition container grows the new widget when going forward, or shrinks the previous widget when going
 * backward.
 * <p>
 * The transition can be parameterized by the way the widget grow or shrink:
 * <ul>
 * <li>the size of the widget changes over time (dynamic interpolation),</li>
 * <li>or a window reveals (or hides) the widget (clipping).</li>
 * </ul>
 * <p>
 * This transition can be parameterized by the position from where the widget starts growing (or where it disappears).
 *
 * @since 2.3.0
 */
public class SplashScreenshotTransitionContainer extends ScreenshotTransitionContainer {

	private final boolean clip;

	@Nullable
	private BufferedImage previousScreenshot;

	private int anchorX;
	private int anchorY;

	/**
	 * Creates a splash transition container.
	 *
	 * @param clip
	 *            <code>true</code> if the widget is clipped, <code>false</code> if the widget is resized.
	 */
	public SplashScreenshotTransitionContainer(boolean clip) {
		this.clip = clip;
	}

	/**
	 * Sets the anchor position of the splash animation, that means the point around which the widget will grow
	 * (forward) or shrink (backward).
	 *
	 * @param x
	 *            the x coordinate.
	 * @param y
	 *            the y coordinate.
	 */
	public void setAnchor(int x, int y) {
		this.anchorX = x;
		this.anchorY = y;
	}

	@Override
	public void render(GraphicsContext g) {
		if (isAnimating()) {
			int contentX = getContentX();
			int contentY = getContentY();
			int contentWidth = getContentWidth();
			int contentHeight = getContentHeight();
			g.translate(contentX, contentY);
			g.intersectClip(0, 0, contentWidth, contentHeight);

			int stop = this.stop;
			Image frontImage;
			int step;
			if (this.forward) {
				frontImage = this.newScreenshot;
				step = this.step;
				// Optim: the background image does not need to be rendered when going forward.
			} else {
				frontImage = this.previousScreenshot;
				step = stop - this.step;
				assert this.newScreenshot != null;
				Painter.drawImage(g, this.newScreenshot, 0, 0);
				// TODO Optim: paint only changed part: previous position of front or better, just around the new front.
			}
			float factor = (float) step / stop;
			if (frontImage != null && factor > 0) {
				int anchorX = this.anchorX;
				int anchorY = this.anchorY;
				int anchorFrontX = (int) (anchorX * factor);
				int anchorFrontY = (int) (anchorY * factor);
				int x = anchorX - anchorFrontX;
				int y = anchorY - anchorFrontY;
				int width = (int) (frontImage.getWidth() * factor);
				int height = (int) (frontImage.getHeight() * factor);
				drawSplash(g, frontImage, factor, x, y, width, height);
			}
		} else {
			super.render(g);
		}
	}

	/**
	 * Draws the growing/shrinking widget.
	 *
	 * @param g
	 *            the graphics context.
	 * @param widgetImage
	 *            the image of the growing/shrinking widget.
	 * @param factor
	 *            the size factor.
	 * @param x
	 *            the x of the splash.
	 * @param y
	 *            the y of the splash.
	 * @param width
	 *            the width of the splash.
	 * @param height
	 *            the height of the splash.
	 */
	protected void drawSplash(GraphicsContext g, Image widgetImage, float factor, int x, int y, int width, int height) {
		if (this.clip) {
			Painter.drawImageRegion(g, widgetImage, x, y, width, height, x, y);
		} else {
			TransformPainter.drawScaledImageNearestNeighbor(g, widgetImage, x, y, factor, factor);
		}
	}

	@Override
	protected int getStop(int contentWidth, int contentHeight) {
		return 1000;
	}

	@Override
	protected void takeScreenshot(Widget widget, int contentWidth, int contentHeight) {
		super.takeScreenshot(widget, contentWidth, contentHeight);

		BufferedImage previousScreenshot = new BufferedImage(contentWidth, contentHeight);
		Painter.drawDisplayRegion(previousScreenshot.getGraphicsContext(), getAbsoluteX() + getContentX(),
				getAbsoluteY() + getContentY(), contentWidth, contentHeight, 0, 0);
		this.previousScreenshot = previousScreenshot;
	}

	@Override
	protected void resetContext() {
		super.resetContext();
		final BufferedImage previousScreenshot = this.previousScreenshot;
		if (previousScreenshot != null) {
			previousScreenshot.close();
			this.previousScreenshot = null;
		}
	}

}
