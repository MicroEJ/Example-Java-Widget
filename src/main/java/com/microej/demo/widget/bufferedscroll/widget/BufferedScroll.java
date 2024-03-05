/*
 * Copyright 2013-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.bufferedscroll.widget;

import com.microej.demo.widget.common.scroll.Scroll;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.OutlineHelper;
import ej.mwt.util.Size;

/**
 * Allows to scroll a widget horizontally or vertically.
 * <p>
 * This scroll reuses the display buffer to scroll faster.
 * <p>
 * This requires that:
 * <ul>
 * <li>the background is not transparent,</li>
 * <li>the background is plain,</li>
 * <li>the screen buffer is fully readable.</li>
 * </ul>
 * <p>
 * If the background is transparent, an {@link IllegalStateException} is thrown when trying to repaint it (see
 * {@link #isTransparent()}).
 */
public class BufferedScroll extends Scroll {

	private int previousPaintChildCoordinate;

	/**
	 * Creates a horizontal scroll container with a visible scrollbar.
	 */
	public BufferedScroll() {
		this(true, true);
	}

	/**
	 * Creates a scroll container specifying its orientation and the visibility of the scrollbar.
	 *
	 * @param horizontal
	 *            <code>true</code> to scroll horizontally, <code>false</code> to scroll vertically.
	 * @param showScrollbar
	 *            <code>true</code> to show the scrollbar, <code>false</code> otherwise.
	 */
	public BufferedScroll(boolean horizontal, boolean showScrollbar) {
		super(horizontal);
		showScrollbar(showScrollbar);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @throws IllegalStateException
	 *             if the buffered scroll is set transparent.
	 */
	@Override
	public boolean isTransparent() {
		if (super.isTransparent()) {
			throw new IllegalStateException();
		}
		return false;
	}

	@Override
	public void render(GraphicsContext g) {
		int currentValue = -getChildCoordinate();
		int previousPaintValue = this.previousPaintChildCoordinate;
		this.previousPaintChildCoordinate = currentValue;
		if (isShown() && previousPaintValue != Integer.MIN_VALUE) {
			int shift = currentValue - previousPaintValue;
			g.translate(getContentX(), getContentY());
			g.intersectClip(0, 0, getContentWidth(), getContentHeight());

			// Save paint context for the scrollbar rendering
			int translateX = g.getTranslationX();
			int translateY = g.getTranslationY();
			int x = g.getClipX();
			int y = g.getClipY();
			int width = g.getClipWidth();
			int height = g.getClipHeight();

			renderShiftedContent(g, shift);

			if (shouldShowScrollbar()) {
				g.setTranslation(translateX, translateY);
				g.setClip(x, y, width, height);

				Widget scrollbar = getScrollbar();

				g.intersectClip(scrollbar.getX(), scrollbar.getY(), scrollbar.getWidth(), scrollbar.getHeight());
				Style style = getStyle();
				g.translate(-getContentX(), -getContentY());
				Size size = new Size(getWidth(), getHeight());
				OutlineHelper.applyOutlinesAndBackground(g, size, style);
				renderChild(scrollbar, g);
			}
		} else {
			super.render(g);
		}
	}

	private void renderShiftedContent(GraphicsContext g, int shift) {
		Widget child = getChild();
		assert child != null;

		// Copy the display content.
		int contentX = getContentX();
		int contentY = getContentY();
		int absoluteContentX = getAbsoluteX() + contentX;
		int absoluteContentY = getAbsoluteY() + contentY;
		int width = getContentWidth();
		int height = getContentHeight();
		if (isHorizontal()) {
			Painter.drawDisplayRegion(g, absoluteContentX, absoluteContentY, width, height, -shift, 0);
			int xChild;
			int widthChild;
			if (shift > 0) {
				xChild = width - shift;
				widthChild = shift;
			} else {
				xChild = 0;
				widthChild = -shift;
			}
			g.intersectClip(xChild, 0, widthChild, child.getHeight());
		} else {
			Painter.drawDisplayRegion(g, absoluteContentX, absoluteContentY, width, height, 0, -shift);
			int yChild;
			int heightChild;
			if (shift > 0) {
				yChild = height - shift;
				heightChild = shift;
			} else {
				yChild = 0;
				heightChild = -shift;
			}
			g.intersectClip(0, yChild, child.getWidth(), heightChild);
		}

		// Draw the part of the child that appears.
		Style style = getStyle();
		g.translate(-contentX, -contentY);
		Size size = new Size(getWidth(), getHeight());
		OutlineHelper.applyOutlinesAndBackground(g, size, style);
		renderChild(child, g);
	}

	@Override
	protected void onAttached() {
		super.onAttached();

		// Force to paint all the first time.
		this.previousPaintChildCoordinate = Integer.MIN_VALUE;
	}

	@Override
	protected void onShown() {
		super.onShown();

		// Force to paint all the first time.
		this.previousPaintChildCoordinate = Integer.MIN_VALUE;
	}

	@Override
	public boolean handleEvent(int event) {
		if (Event.getType(event) == Pointer.EVENT_TYPE && Buttons.getAction(event) == Buttons.RELEASED) {
			this.previousPaintChildCoordinate = Integer.MIN_VALUE;
			requestRender();
		}
		return super.handleEvent(event);
	}

	/**
	 * Unsupported setting: throws an {@link UnsupportedOperationException} if {@code overlap} is {@code true}.
	 */
	@Override
	public void setScrollbarOverlap(boolean overlap) {
		if (overlap) {
			throw new UnsupportedOperationException();
		}
	}

}
