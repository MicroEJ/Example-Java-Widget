/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.progressbar;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.Page;
import com.microej.demo.widget.common.PageHelper;
import com.microej.demo.widget.progressbar.widget.ProgressBar;

import ej.microui.display.Colors;
import ej.mwt.Widget;
import ej.mwt.animation.Animation;
import ej.mwt.animation.Animator;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.style.outline.border.RectangularBorder;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.ClassSelector;

/**
 * Page showing progress bars.
 */
public class ProgressBarPage implements Page {

	private static final int PROGRESS_BAR = 1300;

	@Override
	public String getName() {
		return "Progress Bar"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {
		EditableStyle style = stylesheet.getSelectorStyle(new ClassSelector(PROGRESS_BAR));
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_Y);
		style.setBackground(new RectangularBackground(DemoColors.CORAL));
		style.setBorder(new RectangularBorder(Colors.BLACK, 3));
		style.setColor(DemoColors.POMEGRANATE);
		style.setExtraInt(ProgressBar.STRING_COLOR_FIELD, Colors.WHITE);
		style.setMargin(new UniformOutline(6));
	}

	@Override
	public Widget getContentWidget() {
		ProgressBar progressBar = new AnimatedProgressBar(PageHelper.getAnimator());
		progressBar.addClassSelector(PROGRESS_BAR);
		return progressBar;
	}

	private static class AnimatedProgressBar extends ProgressBar implements Animation {

		private static final int ANIMATION_DURATION = 5_000;

		private final Animator animator;
		private long startTime;

		public AnimatedProgressBar(Animator animator) {
			this.animator = animator;
		}

		@Override
		protected void onShown() {
			this.startTime = System.currentTimeMillis();
			this.animator.startAnimation(this);
		}

		@Override
		protected void onHidden() {
			this.animator.stopAnimation(this);
		}

		@Override
		public boolean tick(long currentTimeMillis) {
			long elapsedTime = currentTimeMillis - this.startTime;
			setProgress(1.0f * elapsedTime / ANIMATION_DURATION);
			requestRender();
			return (elapsedTime < ANIMATION_DURATION);
		}
	}
}
