/*
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.secretscroll.widget;

import ej.annotation.Nullable;
import ej.bon.XMath;
import ej.microui.MicroUI;
import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.util.Size;
import ej.widget.container.LayoutOrientation;
import ej.widget.util.swipe.SwipeEventHandler;
import ej.widget.util.swipe.Swipeable;

/**
 * The secret scroll contains 2 items: one visible and one hidden.
 * <p>
 * The hidden widget can be shown by scrolling the container downward.
 * <p>
 * Both widgets have the same width as the container (minus the boxes). The hidden widget has its optimal height. The
 * other widget has the same height as the container (minus the boxes).
 */
public class SecretScroll extends Container {

	private final ScrollAssistant scrollAssistant;
	private final Widget hiddenChild;
	private final Widget visibleChild;
	@Nullable
	private SwipeEventHandler swipeEventHandler;

	/**
	 * Creates an secret scroll container.
	 *
	 * @param hiddenChild
	 *            the hidden child.
	 * @param visibleChild
	 *            the visible child.
	 */
	public SecretScroll(Widget hiddenChild, Widget visibleChild) {
		super(true);
		this.scrollAssistant = new ScrollAssistant();
		this.hiddenChild = hiddenChild;
		this.visibleChild = visibleChild;
		super.addChild(this.hiddenChild);
		super.addChild(this.visibleChild);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		int width = size.getWidth();
		int height = size.getHeight();
		computeChildOptimalSize(this.hiddenChild, width, NO_CONSTRAINT);
		computeChildOptimalSize(this.visibleChild, width, height);
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		int hiddenHeight = this.hiddenChild.getHeight();
		layOutShiftedChildren(-hiddenHeight, contentWidth, contentHeight);

		int[] sizes = new int[] { hiddenHeight, contentHeight };

		SwipeEventHandler swipeEventHandler = this.swipeEventHandler;
		if (swipeEventHandler != null) {
			swipeEventHandler.stop();
		}
		this.swipeEventHandler = new SwipeEventHandler(sizes, false, true, LayoutOrientation.VERTICAL,
				this.scrollAssistant, getDesktop().getAnimator());
		this.swipeEventHandler.moveTo(hiddenHeight);
	}

	private void layOutShiftedChildren(int shift, int contentWidth, int contentHeight) {
		int hiddenHeight = this.hiddenChild.getHeight();
		layOutChild(this.hiddenChild, 0, shift, contentWidth, hiddenHeight);
		layOutChild(this.visibleChild, 0, hiddenHeight + shift, contentWidth, contentHeight);
	}

	@Override
	public boolean handleEvent(int event) {
		SwipeEventHandler swipeEventHandler = this.swipeEventHandler;
		if (swipeEventHandler != null && swipeEventHandler.handleEvent(event)) {
			return true;
		}
		return super.handleEvent(event);
	}

	class ScrollAssistant implements Runnable, Swipeable {

		private int position;

		@Override
		public void onMove(int position) {
			this.position = XMath.limit(position, 0, SecretScroll.this.hiddenChild.getHeight());
			MicroUI.callSerially(this);
			requestRender();
		}

		@Override
		public void run() {
			layOutShiftedChildren(-this.position, getContentWidth(), getContentHeight());
		}

	}

}
