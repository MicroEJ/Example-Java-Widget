/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.common;

import ej.annotation.Nullable;
import ej.microui.display.BufferedImage;
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

	@Nullable
	private BufferedImage buffer;
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
		Display display = Display.getDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();
		BufferedImage bufferedImage = new BufferedImage(displayWidth - PageHelper.TITLE_BAR_WIDTH, displayHeight);

		this.newDesktop.setAttached();
		GraphicsContext imageGraphicsContext = bufferedImage.getGraphicsContext();
		this.buffer = bufferedImage;
		imageGraphicsContext.translate(-PageHelper.TITLE_BAR_WIDTH, 0);
		Widget widget = this.newDesktop.getWidget();
		assert widget != null;
		widget.render(imageGraphicsContext);

		this.motion.start();
		this.animator.startAnimation(this);
		this.lastPosition = PageHelper.TITLE_BAR_WIDTH;
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		BufferedImage buffer = this.buffer;
		if (buffer != null) {
			buffer.close();
			this.buffer = null;
		}
	}

	@Override
	protected void render(GraphicsContext gc) {
		BufferedImage buffer = this.buffer;
		assert buffer != null;
		int currentValue = this.motion.getCurrentValue();
		if (this.forward) {
			Painter.drawImage(gc, buffer, currentValue, 0);
		} else {
			Display display = Display.getDisplay();
			int displayWidth = display.getWidth();
			int displayHeight = display.getHeight();
			int shift = currentValue - this.lastPosition;
			Painter.drawDisplayRegion(gc, this.lastPosition, 0, displayWidth, displayHeight, currentValue, 0);
			gc.setClip(this.lastPosition, 0, shift, displayHeight);
			Painter.drawImage(gc, buffer, PageHelper.TITLE_BAR_WIDTH, 0);
			this.lastPosition = currentValue;
		}
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
