/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.is2t.demo.widgets.widget;

import ej.microui.io.GraphicsContext;
import ej.mwt.Renderable;
import ej.mwt.rendering.Look;
import ej.mwt.rendering.Renderer;

/**
 * Use a color for the background.
 */
public class BorderCompositeWithBackgroundRenderer extends Renderer {

	@Override
	public Class<BorderCompositeWithBackground> getManagedType() {
		return BorderCompositeWithBackground.class;
	}

	@Override
	public void render(GraphicsContext g, Renderable renderable) {
		// Fill the background.
		int backgroundColor = getLook().getProperty(Look.GET_BACKGROUND_COLOR_DEFAULT);
		g.setColor(backgroundColor);
		g.fillRect(0, 0, renderable.getWidth(), renderable.getHeight());
	}

}
