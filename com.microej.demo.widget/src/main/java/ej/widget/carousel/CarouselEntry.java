/*
 * Java
 *
 * Copyright  2017-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package ej.widget.carousel;

import java.util.ArrayList;
import java.util.List;

import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.transform.ImageScale;
import ej.style.container.AlignmentHelper;
import ej.style.container.Rectangle;
import ej.style.text.TextManager;
import ej.widget.listener.OnClickListener;

/**
 * Represents one of the entries of a carousel
 */
public class CarouselEntry {

	private static final float DRAW_STRING_RATIO = 0.75f; // don't draw string if lower

	private final int id;
	private final List<OnClickListener> onClickListeners;

	private final Image image;
	private final String string;

	/**
	 * Constructor
	 *
	 * @param id
	 *            the entry id
	 * @param image
	 *            the image to draw on top
	 * @param string
	 *            the entry string
	 */
	public CarouselEntry(int id, Image image, String string) {
		this.id = id;
		this.onClickListeners = new ArrayList<>();

		this.image = image;
		this.string = string;
	}

	/**
	 * Gets the entry id
	 *
	 * @return the entry id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Adds a listener on the click events of the entry
	 *
	 * @param listener
	 *            the listener to add.
	 */
	public void addOnClickListener(OnClickListener listener) {
		this.onClickListeners.add(listener);
	}

	/**
	 * Notifies the listeners that a click event happened
	 */
	public void notifyOnClickListeners() {
		for (OnClickListener onClickListener : this.onClickListeners) {
			onClickListener.onClick();
		}
	}

	/**
	 * Draws the carousel entry on the given graphics context
	 *
	 * @param g
	 *            the graphics context to draw on
	 * @param bounds
	 *            the bounds of the container
	 * @param tm
	 *            the text manager to use
	 * @param inDND
	 *            whether an entry is being dragged
	 * @param stopped
	 *            whether the carousel is currently stopped
	 * @param clicked
	 *            whether the entry is getting clicked
	 * @param selected
	 *            whether the entry is currently selected
	 * @param sizeRatio
	 *            the size ratio
	 * @param offsetX
	 *            the offset on the X-axis
	 * @param offsetY
	 *            the offset on the Y-axis
	 * @param isDND
	 *            whether the entry is being dragged
	 */
	public void render(GraphicsContext g, Rectangle bounds, TextManager tm, boolean inDND, boolean stopped,
			boolean clicked, boolean selected, float sizeRatio, int offsetX, int offsetY, boolean isDND) {
		// draw background
		int imageWidth = Math.round(this.image.getWidth() * sizeRatio);
		int imageHeight = Math.round(this.image.getHeight() * sizeRatio);
		int imageX = AlignmentHelper.computeXLeftCorner(imageWidth, offsetX, bounds.getWidth(),
				GraphicsContext.HCENTER);
		int imageY = AlignmentHelper.computeYTopCorner(imageHeight + 30, offsetY, bounds.getHeight(),
				GraphicsContext.VCENTER);
		drawScaled(g, this.image, imageX, imageY, 255, sizeRatio, stopped);

		// draw string
		if (sizeRatio >= DRAW_STRING_RATIO && !isDND) {
			Font font = g.getFont();
			int marginX = 20 - (this.image.getWidth() - imageWidth) / 2;
			int stringX = imageX + marginX;
			int stringY = imageY + imageHeight + 10;
			int stringAlignment = GraphicsContext.HCENTER | GraphicsContext.TOP;
			Rectangle stringBounds = new Rectangle(0, 0, imageWidth - 2 * marginX, 2 * font.getHeight());
			g.translate(stringX, stringY);
			tm.drawText(g, this.string, font, g.getColor(), stringBounds, stringAlignment);
			g.translate(-stringX, -stringY);
		}
	}

	private void drawScaled(GraphicsContext g, Image image, int x, int y, int alpha, float sizeRatio, boolean stopped) {
		if (sizeRatio == 1.0f) {
			g.drawImage(image, x, y, GraphicsContext.LEFT | GraphicsContext.TOP, alpha);
		} else {
			ImageScale s = ImageScale.Singleton;
			s.setAlpha(alpha);
			s.setFactor(sizeRatio);

			s.draw(g, image, x, y, GraphicsContext.LEFT | GraphicsContext.TOP);

			// if (stopped) {
			// s.drawBilinear(g, image, x, y, GraphicsContext.LEFT | GraphicsContext.TOP);
			// } else {
			// s.drawNearestNeighbor(g, image, x, y, GraphicsContext.LEFT | GraphicsContext.TOP);
			// }
		}
	}
}
