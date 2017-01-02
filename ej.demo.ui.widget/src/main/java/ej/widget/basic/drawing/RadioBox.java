/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.widget.basic.drawing;

import ej.microui.display.GraphicsContext;
import ej.microui.display.shape.AntiAliasedShapes;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.style.util.StyleHelper;
import ej.widget.basic.Box;
import ej.widget.composed.Radio;

/**
 * Implementation of a box that represents a radio button.
 * <p>
 * The size of a radio box is dependent on the font size.
 *
 * @see Radio
 */
public class RadioBox extends Box {

	private static final int ANTIALIASING_THICKNESS = 2;
	private static final int MINIMUM_SIZE = 8;

	@Override
	public void renderContent(GraphicsContext g, Style style, Rectangle bounds) {
		if (isChecked()) {
			int width = bounds.getWidth();
			int height = bounds.getHeight();

			g.setColor(style.getForegroundColor());
			int size = Math.min(width, height);
			if ((size & 1) == 0) {
				size--;
			}
			AntiAliasedShapes antiAliasedShapes = AntiAliasedShapes.Singleton;
			antiAliasedShapes.setFade(1);
			antiAliasedShapes.setThickness(ANTIALIASING_THICKNESS);
			antiAliasedShapes.drawCircle(g, ANTIALIASING_THICKNESS, ANTIALIASING_THICKNESS,
					size - (ANTIALIASING_THICKNESS * 2 + 1));
			g.fillCircle(1, 1, size - (2 + 1));
		}
	}

	@Override
	public Rectangle validateContent(Style style, Rectangle availableSize) {
		int halfHeight = StyleHelper.getFont(style).getHeight() >> 1;
		int size = Math.max(halfHeight, MINIMUM_SIZE);
		if ((size & 1) == 0) {
			size++;
		}
		return new Rectangle(0, 0, size, size);
	}

}
