/*
 * Copyright 2017-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.carousel.widget;

import ej.annotation.Nullable;
import ej.drawing.TransformPainter;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.Painter;
import ej.mwt.util.Alignment;
import ej.widget.basic.OnClickListener;
import ej.widget.util.render.StringPainter;

/**
 * Represents one of the entries of a carousel.
 */
public class CarouselEntry {

	private static final float DRAW_STRING_RATIO = 0.75f; // don't draw string if lower
	private static final int IMAGE_ALPHA = 255;
	private static final int IMAGE_TEXT_MARGIN = 10;

	private static final int MAX_ENTRY_MARGIN = 20;
	private static final int ENTRY_TEXT_HEIGHT_ADDITION = 30;

	private final int id;
	private @Nullable OnClickListener onClickListener;

	private final Image image;
	private final String string;

	/**
	 * Constructor for a single CarouselEntry inside the Carousel.
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
		this.onClickListener = null;

		this.image = image;
		this.string = string;
	}

	/**
	 * Gets the entry id.
	 *
	 * @return the entry id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Gets the Margin between the Image and the Text.
	 *
	 * @return the margin in px
	 */
	public static int getImageTextMargin() {
		return IMAGE_TEXT_MARGIN;
	}

	/**
	 * Sets the listener on the click events of the entry.
	 *
	 * @param listener
	 *            the listener to set.
	 */
	public void setOnClickListener(@Nullable OnClickListener listener) {
		this.onClickListener = listener;
	}

	/**
	 * Handles a click event.
	 */
	public void handleClick() {
		OnClickListener listener = this.onClickListener;
		if (listener != null) {
			listener.onClick();
		}
	}

	/**
	 * Draws the carousel entry on the given graphics context.
	 *
	 * @param g
	 *            the graphics context to draw on
	 * @param contentWidth
	 *            the content width of the carousel
	 * @param contentHeight
	 *            the content height of the carousel
	 * @param font
	 *            the font to render with.
	 * @param stopped
	 *            whether the carousel is currently stopped
	 * @param sizeRatio
	 *            the size ratio
	 * @param offsetX
	 *            the offset on the X-axis
	 * @param offsetY
	 *            the offset on the Y-axis
	 * @param isDnd
	 *            whether the entry is being dragged
	 */
	public void render(GraphicsContext g, int contentWidth, int contentHeight, Font font, boolean stopped,
			float sizeRatio, int offsetX, int offsetY, boolean isDnd) {
		// draw background
		int imageWidth = Math.round(this.image.getWidth() * sizeRatio);
		int imageHeight = Math.round(this.image.getHeight() * sizeRatio);
		int imageX = Alignment.computeLeftX(imageWidth, offsetX, contentWidth, Alignment.HCENTER);
		int imageY = Alignment.computeTopY(imageHeight + ENTRY_TEXT_HEIGHT_ADDITION, offsetY, contentHeight, Alignment.VCENTER);
		drawScaled(g, this.image, imageX, imageY, IMAGE_ALPHA, sizeRatio, stopped);

		// draw string
		if (sizeRatio >= DRAW_STRING_RATIO && !isDnd) {
			int marginX = MAX_ENTRY_MARGIN - (this.image.getWidth() - imageWidth) / 2;
			int stringX = imageX + marginX;
			int stringY = imageY + imageHeight + IMAGE_TEXT_MARGIN;
			int stringWidth = imageWidth - 2 * marginX;
			int stringHeight = 2 * font.getHeight();
			StringPainter.drawStringInArea(g, this.string, font, stringX, stringY, stringWidth, stringHeight,
					Alignment.HCENTER, Alignment.TOP);
		}
	}

	private void drawScaled(GraphicsContext g, Image image, int x, int y, int alpha, float sizeRatio, boolean stopped) {
		if (sizeRatio == 1.0f) {
			Painter.drawImage(g, image, x, y, alpha);
		} else {
			if (stopped) {
				TransformPainter.drawScaledImageBilinear(g, image, x, y, sizeRatio, sizeRatio, alpha);
			} else {
				TransformPainter.drawScaledImageNearestNeighbor(g, image, x, y, sizeRatio, sizeRatio, alpha);
			}
		}
	}
}
