/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.common;

import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.motion.Motion;
import ej.motion.quad.QuadEaseOutMotion;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.animation.Animation;
import ej.mwt.animation.Animator;

/**
 * Used by {@link Navigation} to do a transition between desktops.
 */
/* package */ class TransitionDisplayable extends Displayable implements Animation {

	private static final int DURATION = 250;

	private final Desktop newDesktop;
	private final boolean forward;
	private final Animator animator;

	private Motion motion;
	private int lastPosition;

	/* package */ TransitionDisplayable(Desktop newDesktop, boolean forward, Animator animator) {
		this.newDesktop = newDesktop;
		this.forward = forward;
		this.animator = animator;
		int width = Display.getDisplay().getWidth();
		if (forward) {
			this.motion = new QuadEaseOutMotion(width, PageHelper.TITLE_BAR_WIDTH, DURATION);
		} else {
			this.motion = new QuadEaseOutMotion(PageHelper.TITLE_BAR_WIDTH, width, DURATION);
		}
	}

	@Override
	protected void onShown() {
		super.onShown();

		this.motion.start();
		this.animator.startAnimation(this);
		this.lastPosition = PageHelper.TITLE_BAR_WIDTH;
	}

	@Override
	protected void render(GraphicsContext gc) {
		int currentPosition = this.motion.getCurrentValue();

		Display display = Display.getDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();

		if (this.forward) {
			renderNewDesktop(gc, currentPosition - PageHelper.TITLE_BAR_WIDTH, currentPosition,
					displayWidth - currentPosition);
		} else {
			int lastPosition = this.lastPosition;
			Painter.drawDisplayRegion(gc, lastPosition, 0, displayWidth - currentPosition, displayHeight,
					currentPosition, 0);
			renderNewDesktop(gc, 0, lastPosition, currentPosition - lastPosition);
			this.lastPosition = currentPosition;
		}
	}

	private void renderNewDesktop(GraphicsContext gc, int x, int clipX, int clipWidth) {
		gc.setClip(clipX, 0, clipWidth, gc.getHeight());
		gc.setTranslation(x, 0);

		Desktop newDesktop = this.newDesktop;
		newDesktop.setAttached();
		Widget widget = newDesktop.getWidget();
		assert (widget != null);
		widget.render(gc);
	}

	@Override
	public boolean tick(long currentTimeMillis) {
		if (this.motion.isFinished()) {
			this.newDesktop.requestShow();
			return false;
		} else {
			requestRender();
			return true;
		}
	}

	@Override
	public boolean handleEvent(int event) {
		return false;
	}

}
