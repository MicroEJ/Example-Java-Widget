/*
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.scrollablelist;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.style.outline.Outline;
import ej.mwt.util.Outlineable;
import ej.mwt.util.Size;

/**
 * The top border draws a single horizontal line on top.
 */
public class TopBorder implements Outline {

	private final int color;

	/**
	 * Creates a top border.
	 *
	 * @param color
	 *            the border color
	 */
	public TopBorder(int color) {
		this.color = color;
	}

	@Override
	public void apply(Outlineable outlineable) {
		outlineable.removeOutline(0, 1, 0, 0);
	}

	@Override
	public void apply(GraphicsContext g, Size size) {
		int width = size.getWidth();

		g.setColor(this.color);
		Painter.drawHorizontalLine(g, 0, 0, width);

		apply(size);

		g.translate(0, 1);
		g.intersectClip(0, 0, size.getWidth(), size.getHeight());
	}

}
