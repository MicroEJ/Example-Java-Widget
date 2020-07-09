/*
 * Copyright 2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.common;

import ej.microui.display.BufferedImage;
import ej.microui.display.Display;
import ej.microui.display.Displayable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.motion.Motion;
import ej.motion.quad.QuadEaseOutMotion;
import ej.mwt.Desktop;
import ej.mwt.animation.Animation;
import ej.mwt.animation.Animator;
import ej.service.ServiceFactory;

/**
 * Used by {@link Navigation} to do a transition between desktops.
 */
/* package */ class TransitionDisplayable extends Displayable implements Animation {

	private static final int DURATION = 250;

	private final Desktop newDesktop;
	private final boolean forward;

	private BufferedImage buffer;
	private Motion motion;
	private int lastPosition;

	/* package */ TransitionDisplayable(Desktop newDesktop, boolean forward) {
		this.newDesktop = newDesktop;
		this.forward = forward;
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
		this.buffer = new BufferedImage(displayWidth - PageHelper.TITLE_BAR_WIDTH, displayHeight);

		this.newDesktop.setAttached();
		GraphicsContext imageGraphicsContext = this.buffer.getGraphicsContext();
		imageGraphicsContext.translate(-PageHelper.TITLE_BAR_WIDTH, 0);
		this.newDesktop.getWidget().render(imageGraphicsContext);

		this.motion.start();
		ServiceFactory.getRequiredService(Animator.class).startAnimation(this);
		this.lastPosition = PageHelper.TITLE_BAR_WIDTH;
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		this.buffer.close();
	}

	@Override
	protected void render(GraphicsContext gc) {
		int currentValue = this.motion.getCurrentValue();
		if (this.forward) {
			Painter.drawImage(gc, this.buffer, currentValue, 0);
		} else {
			Display display = Display.getDisplay();
			int displayWidth = display.getWidth();
			int displayHeight = display.getHeight();
			int shift = currentValue - this.lastPosition;
			Painter.drawDisplayRegion(gc, this.lastPosition, 0, displayWidth, displayHeight, currentValue, 0);
			gc.setClip(this.lastPosition, 0, shift, displayHeight);
			Painter.drawImage(gc, this.buffer, PageHelper.TITLE_BAR_WIDTH, 0);
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
