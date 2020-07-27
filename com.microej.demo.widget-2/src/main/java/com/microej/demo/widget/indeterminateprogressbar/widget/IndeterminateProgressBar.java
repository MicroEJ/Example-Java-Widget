/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package com.microej.demo.widget.indeterminateprogressbar.widget;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.Widget;
import ej.mwt.animation.Animation;
import ej.mwt.animation.Animator;
import ej.mwt.style.Style;
import ej.mwt.util.Size;
import ej.service.ServiceFactory;

/**
 * A indeterminate progress bar is a widget which displays an animated bar indicating that the user should wait for an
 * indeterminate amount of time.
 */
public class IndeterminateProgressBar extends Widget implements Animation {

	private static final int ANIM_PERIOD = 1_000;

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();

		int timeSpent = (int) (System.currentTimeMillis() % ANIM_PERIOD);
		int startX = contentWidth * timeSpent / ANIM_PERIOD;
		int endX = startX + contentWidth / 2;

		g.setColor(style.getColor());
		if (endX > contentWidth) {
			Painter.fillRectangle(g, startX, 0, contentWidth - startX, contentHeight);
			Painter.fillRectangle(g, 0, 0, endX - contentWidth, contentHeight);
		} else {
			Painter.fillRectangle(g, startX, 0, endX - startX, contentHeight);
		}
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		int fontHeight = style.getFont().getHeight() / 2;
		size.setSize(fontHeight, fontHeight);
	}

	@Override
	protected void onShown() {
		Animator animator = ServiceFactory.getService(Animator.class, Animator.class);
		animator.startAnimation(this);
	}

	@Override
	protected void onHidden() {
		Animator animator = ServiceFactory.getService(Animator.class, Animator.class);
		animator.stopAnimation(this);
	}

	@Override
	public boolean tick(long currentTimeMillis) {
		requestRender();
		return true;
	}
}
