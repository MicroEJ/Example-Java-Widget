/*
 * Java
 *
 * Copyright 2014-2015 IS2T. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found at http://www.is2t.com/open-source-bsd-license/.
 */
package com.is2t.demo.widgets.widget;

import ej.microui.io.DisplayFont;
import ej.mwt.rendering.Look;
import ej.widgets.widgets.LookExtension;
import ej.widgets.widgets.spinner.renderer.WheelRenderer;

/**
 * Adaptation for the application.
 */
public class WidgetsWheelRenderer extends WheelRenderer {

	@Override
	protected DisplayFont getBiggerFont() {
		Look look = getLook();
		int fontIndex = look.getProperty(LookExtension.GET_X_BIG_FONT_INDEX);
		return look.getFonts()[fontIndex];
	}
}
