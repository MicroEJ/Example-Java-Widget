/*
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.circularindeterminateprogress.widget;

import ej.annotation.Nullable;
import ej.bon.Util;
import ej.drawing.ShapePainter;
import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.motion.Motion;
import ej.motion.linear.LinearFunction;
import ej.motion.quart.QuartEaseOutFunction;
import ej.mwt.Widget;
import ej.mwt.animation.Animation;
import ej.mwt.animation.Animator;
import ej.mwt.style.Style;
import ej.mwt.util.Size;

/**
 * Circular Indeterminate Progress.
 */
public class CircularIndeterminateProgress extends Widget {

	// Indeterminate management.
	private static final int FULL_ANGLE = 360;
	private static final int MAX_FILL_ANGLE = 300;
	private static final long EASEOUT_MOTION_DURATION = 1200;
	private static final long LINEAR_MOTION_DURATION = 1400;

	private static final int DEFAULT_DIAMETER = 100;
	private static final int DEFAULT_THICK = 5;

	private static final int FADING = 1;

	/**
	 * Background color ID.
	 */
	public static final int BACKGROUND_COLOR = 0;
	/**
	 * Progress diameter ID.
	 */
	public static final int PROGRESS_DIAMETER = 1;
	/**
	 * Progress thick ID.
	 */
	public static final int PROGRESS_THICK = 2;

	private final Motion linearMotion;
	private final Motion easeOutMotion;
	private long linearStartTime;
	private long easeOutStartTime;
	private int startAngle;
	private int arcAngle;
	private int currentStartAngle;
	private boolean clockwise;
	@Nullable
	private Animation indeterminateAnimation;

	/**
	 * Creates a circular indeterminate progress.
	 */
	public CircularIndeterminateProgress() {
		this.linearMotion = new Motion(LinearFunction.INSTANCE, FULL_ANGLE, 0, LINEAR_MOTION_DURATION);
		this.easeOutMotion = new Motion(QuartEaseOutFunction.INSTANCE, 0, MAX_FILL_ANGLE, EASEOUT_MOTION_DURATION);
		this.linearStartTime = Util.platformTimeMillis();
		this.easeOutStartTime = this.linearStartTime;
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		int diameter = style.getExtraInt(PROGRESS_DIAMETER, DEFAULT_DIAMETER) + FADING * 2;
		size.setSize(diameter, diameter);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		int diameter = style.getExtraInt(PROGRESS_DIAMETER, DEFAULT_DIAMETER);
		int thick = style.getExtraInt(PROGRESS_THICK, DEFAULT_THICK);
		int progressDiameter = diameter - (thick << 2);

		// background
		g.setColor(style.getExtraInt(BACKGROUND_COLOR, Colors.BLACK));
		ShapePainter.drawThickFadedPoint(g, contentWidth / 2, contentHeight / 2, diameter, FADING);
		// Fills the complete part, from 90° anti-clockwise.
		int startAngle = this.startAngle;
		int arcAngle = this.arcAngle;

		// progress
		int position = (thick << 1);
		g.setColor(style.getColor());
		ShapePainter.drawThickFadedCircleArc(g, position, position, progressDiameter, startAngle, arcAngle, thick, 1,
				ShapePainter.Cap.ROUNDED, ShapePainter.Cap.ROUNDED);
	}

	@Override
	protected void onShown() {
		startIndeterminateAnimation();
	}

	@Override
	protected void onHidden() {
		stopIndeterminateAnimation();
	}

	private void indeterminateTick() {
		long currentTime = Util.platformTimeMillis();

		long linearElapsedTime = currentTime - this.linearStartTime;
		if (linearElapsedTime >= LINEAR_MOTION_DURATION) {
			this.linearStartTime = currentTime;
		}

		long easeOutElapsedTime = currentTime - this.easeOutStartTime;
		if (easeOutElapsedTime >= EASEOUT_MOTION_DURATION) {
			this.clockwise = !this.clockwise;
			if (!this.clockwise) {
				this.currentStartAngle += FULL_ANGLE - MAX_FILL_ANGLE;
			}
			this.easeOutStartTime = currentTime;
			easeOutElapsedTime = 0;
		}
		if (this.clockwise) {
			this.startAngle = this.currentStartAngle + -MAX_FILL_ANGLE - this.arcAngle
					+ this.linearMotion.getValue(linearElapsedTime);
			this.arcAngle = -(MAX_FILL_ANGLE - this.easeOutMotion.getValue(easeOutElapsedTime));
		} else {
			this.startAngle = this.currentStartAngle + this.linearMotion.getValue(linearElapsedTime);
			this.arcAngle = -this.easeOutMotion.getValue(easeOutElapsedTime);
		}

		requestRender();
	}

	/**
	 * Starts the indeterminate animation.
	 */
	private void startIndeterminateAnimation() {
		// Ensures it is not already running.
		stopIndeterminateAnimation();

		Animation animation = new Animation() {

			@Override
			public boolean tick(long currentTimeMillis) {
				indeterminateTick();
				return true;
			}
		};
		this.indeterminateAnimation = animation;
		Animator animator = getDesktop().getAnimator();
		animator.startAnimation(animation);
	}

	/**
	 * Stops the indeterminate animation.
	 */
	private void stopIndeterminateAnimation() {
		Animation indeterminateAnimation = this.indeterminateAnimation;
		if (indeterminateAnimation != null) {
			Animator animator = getDesktop().getAnimator();
			animator.stopAnimation(indeterminateAnimation);
		}
	}

}
