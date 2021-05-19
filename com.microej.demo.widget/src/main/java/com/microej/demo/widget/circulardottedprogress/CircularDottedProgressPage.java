/*
 * Copyright 2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.circulardottedprogress;

import com.microej.demo.widget.circulardottedprogress.widget.CircularDottedProgress;
import com.microej.demo.widget.common.Page;

import ej.microui.display.Colors;
import ej.motion.Motion;
import ej.motion.circ.CircEaseInOutFunction;
import ej.mwt.Widget;
import ej.mwt.animation.Animation;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.util.Alignment;
import ej.widget.container.LayoutOrientation;
import ej.widget.container.SimpleDock;

/**
 * Circular Dotted Progress page.
 */
public class CircularDottedProgressPage implements Page {

	private static final int BAR_COLOR = 0xEE502E;
	private static final int CIRCULAR_SIZE = 100;
	private static final int DOT_SIZE = 5;
	private static final int START_ANGLE = 270;
	private static final int PROGRESS_MAX = 360;

	@Override
	public String getName() {
		return "Circular Dotted Progress"; //$NON-NLS-1$
	}

	@Override
	public void populateStylesheet(CascadingStylesheet stylesheet) {

		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(CircularDottedProgress.class));
		style.setColor(BAR_COLOR);
		style.setExtraInt(CircularDottedProgress.BACKGROUND_COLOR, Colors.BLACK);
		style.setExtraInt(CircularDottedProgress.PROGRESS_DIAMETER, CIRCULAR_SIZE);
		style.setExtraInt(CircularDottedProgress.DOT_SIZE, DOT_SIZE);
		style.setDimension(OptimalDimension.OPTIMAL_DIMENSION_XY);
		style.setHorizontalAlignment(Alignment.HCENTER);
		style.setVerticalAlignment(Alignment.VCENTER);

	}

	@Override
	public Widget getContentWidget() {
		SimpleDock dottedDock = new SimpleDock(LayoutOrientation.VERTICAL);

		// circular dotted progress bar, progress clockwise
		AnimatedCircularDottedProgress dottedProgress = new AnimatedCircularDottedProgress(START_ANGLE, true);

		dottedDock.setCenterChild(dottedProgress);

		return dottedDock;
	}

	private static class AnimatedCircularDottedProgress extends CircularDottedProgress implements Animation {

		private static final int ANIMATION_DURATION = 5_000;

		private final Motion progressMotion;
		private long startTime;

		AnimatedCircularDottedProgress(float startAngle, boolean clockwise) {
			super(startAngle, clockwise);
			this.progressMotion = new Motion(CircEaseInOutFunction.INSTANCE, 0, PROGRESS_MAX, ANIMATION_DURATION);
		}

		@Override
		protected void onShown() {
			this.startTime = System.currentTimeMillis();
			getDesktop().getAnimator().startAnimation(this);
		}

		@Override
		protected void onHidden() {
			getDesktop().getAnimator().stopAnimation(this);
		}

		@Override
		public boolean tick(long currentTimeMillis) {
			long elapsedTime = currentTimeMillis - this.startTime;
			float angle = this.progressMotion.getValue(elapsedTime);
			setProgress(angle);

			requestRender();
			return elapsedTime < ANIMATION_DURATION;
		}
	}

}
