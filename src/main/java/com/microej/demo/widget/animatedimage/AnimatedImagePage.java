/*
 * Copyright 2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.animatedimage;

import com.microej.demo.widget.animatedimage.widget.AnimatedImage;
import com.microej.demo.widget.common.Page;

import ej.bon.Immutables;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;

/**
 * Page showing an animated image widget.
 */
public class AnimatedImagePage implements Page {

	private static final String[] BUMPING_ICON_FRAMES = (String[]) Immutables.get("hrAnimationPaths"); //$NON-NLS-1$

	private static final long BUMPING_PERIOD = 40;

	@Override
	public String getName() {
		return "Animated image"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(AnimatedImage.class));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
	}

	@Override
	public Widget getContentWidget() {
		// Animated image widget
		return new AnimatedImage(BUMPING_ICON_FRAMES, BUMPING_PERIOD);
	}

}
