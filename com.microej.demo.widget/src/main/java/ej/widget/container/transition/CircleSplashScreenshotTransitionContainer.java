/*
 * Copyright 2017-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.container.transition;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.Painter;

/**
 * A circle splash transition container grows the new widget when going forward, or shrinks the previous widget when
 * going backward, the widget being enclosed in a circle.
 *
 * @see SplashScreenshotTransitionContainer
 * @since 2.3.0
 */
public class CircleSplashScreenshotTransitionContainer extends SplashScreenshotTransitionContainer {

	/**
	 * Creates a circle splash transition container.
	 *
	 * @param clip
	 *            <code>true</code> if the widget is clipped, <code>false</code> if the widget is resized.
	 */
	public CircleSplashScreenshotTransitionContainer(boolean clip) {
		super(clip);
	}

	@Override
	protected void drawSplash(GraphicsContext g, Image widgetImage, float factor, int x, int y, int width, int height) {
		int maxSize = Math.max(width, height);
		int radius = (int) Math.sqrt(maxSize * maxSize / 2);
		g.setColor(g.getBackgroundColor());
		Painter.fillCircle(g, x + width / 2 - radius, y + height / 2 - radius, radius * 2);

		super.drawSplash(g, widgetImage, factor, x, y, width, height);
	}

}
