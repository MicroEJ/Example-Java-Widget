/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.indeterminateprogressbar.widget;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.Widget;
import ej.mwt.animation.Animation;
import ej.mwt.animation.Animator;
import ej.mwt.style.Style;
import ej.mwt.util.Size;

/**
 * A indeterminate progress bar is a widget which displays an animated bar indicating that the user should wait for an
 * indeterminate amount of time.
 */
public class IndeterminateProgressBar extends Widget implements Animation {

	private static final int ANIM_PERIOD = 1_000;

	private final Animator animator;

	/**
	 * Creates an indeterminate progress bar.
	 *
	 * @param animator
	 *            the animator to use.
	 */
	public IndeterminateProgressBar(Animator animator) {
		this.animator = animator;
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();

		int timeSpent = (int) (System.currentTimeMillis() % ANIM_PERIOD);
		int startX = contentWidth * timeSpent / ANIM_PERIOD;
		int barWidth = contentWidth / 2;

		g.setColor(style.getColor());
		Painter.fillRectangle(g, startX, 0, barWidth, contentHeight);
		Painter.fillRectangle(g, startX - contentWidth, 0, barWidth, contentHeight);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		int fontHeight = style.getFont().getHeight() / 2;
		size.setSize(fontHeight, fontHeight);
	}

	@Override
	protected void onShown() {
		this.animator.startAnimation(this);
	}

	@Override
	protected void onHidden() {
		this.animator.stopAnimation(this);
	}

	@Override
	public boolean tick(long currentTimeMillis) {
		requestRender();
		return true;
	}
}
