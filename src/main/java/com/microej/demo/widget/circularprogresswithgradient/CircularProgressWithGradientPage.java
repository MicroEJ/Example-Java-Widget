/*
 * Copyright 2021-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.circularprogresswithgradient;

import com.microej.demo.widget.circularprogresswithgradient.widget.CircularProgressBarWithGradient;
import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Page;

import ej.bon.Util;
import ej.mwt.Widget;
import ej.mwt.animation.Animation;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;

/**
 * Page showing a circular progress with gradient.
 */
public class CircularProgressWithGradientPage implements Page {

	/** Thickness of the progress bar. This value should match with the bar thickness in the gradient image. */
	private static final int BAR_THICKNESS = 4;

	private static final String GRADIENT_RING_IMAGE = "/images/gradient-ring.png"; //$NON-NLS-1$

	private static final String BAR_CURSOR_IMAGE = "/images/bar-cursor.png"; //$NON-NLS-1$

	@Override
	public String getName() {
		return "Circular Progress With Gradient"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(CircularProgressBarWithGradient.class));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		// Use the same color as the background to hide the gradient image.
		style.setColor(DemoColors.DEFAULT_BACKGROUND);
	}

	@Override
	public Widget getContentWidget() {
		return new AnimatedCircularProgressBarWithGradient(GRADIENT_RING_IMAGE, BAR_CURSOR_IMAGE, BAR_THICKNESS);
	}

	private static class AnimatedCircularProgressBarWithGradient extends CircularProgressBarWithGradient
			implements Animation {

		private static final int ANIMATION_DURATION = 5_000;

		private long startTime;

		/* package */ AnimatedCircularProgressBarWithGradient(String circlegradientImagePath, String cursorImagePath,
				int thickness) {
			super(circlegradientImagePath, cursorImagePath, thickness);
		}

		@Override
		protected void onShown() {
			this.startTime = Util.platformTimeMillis();
			getDesktop().getAnimator().startAnimation(this);
		}

		@Override
		protected void onHidden() {
			getDesktop().getAnimator().stopAnimation(this);
		}

		@Override
		public boolean tick(long platformTimeMillis) {
			long elapsedTime = platformTimeMillis - this.startTime;
			setProgress(1.0f * elapsedTime / ANIMATION_DURATION);
			requestRender();
			return (elapsedTime < ANIMATION_DURATION);
		}
	}
}
