/*
 * Copyright 2021-2022 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.snapscroll.widget;

import ej.annotation.Nullable;
import ej.microui.MicroUI;
import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.util.Size;
import ej.widget.container.LayoutOrientation;
import ej.widget.util.swipe.SwipeEventHandler;
import ej.widget.util.swipe.Swipeable;

/**
 * The snap scroll organizes its children in a scrollable vertical list.
 * <p>
 * Each widget has its optimal height and the full width of the snap scroll.
 * <p>
 * When scrolling, the widgets are "magnetized". Meaning that when the user stops scrolling, the top edge of the
 * uppermost widget is aligned with the container's top edge.
 */
public class SnapScroll extends Container {

	private final ScrollAssistant scrollAssistant;
	@Nullable
	private SwipeEventHandler swipeEventHandler;

	/**
	 * Creates a snap scroll.
	 */
	public SnapScroll() {
		super(true);
		this.scrollAssistant = new ScrollAssistant();
	}

	@Override
	public void addChild(Widget child) {
		super.addChild(child);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		int width = size.getWidth();
		Widget[] children = getChildren();
		for (Widget child : children) {
			assert child != null;
			computeChildOptimalSize(child, width, NO_CONSTRAINT);
		}
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		int y = 0;
		Widget[] children = getChildren();
		int childrenLength = children.length;
		int[] sizes = new int[childrenLength];
		for (int i = 0; i < childrenLength; i++) {
			Widget child = children[i];
			assert child != null;
			int height = child.getHeight();
			layOutChild(child, 0, y, contentWidth, height);
			y += height;
			sizes[i] = height;
		}

		SwipeEventHandler swipeEventHandler = this.swipeEventHandler;
		if (swipeEventHandler != null) {
			swipeEventHandler.stop();
		}
		if (y > contentHeight) {
			this.swipeEventHandler = new SwipeEventHandler(sizes, false, true, LayoutOrientation.VERTICAL,
					this.scrollAssistant, getDesktop().getAnimator());
		}
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
			this.position = position;
			MicroUI.callSerially(this);
			requestRender();
		}

		@Override
		public void run() {
			int y = -this.position;
			Widget[] children = getChildren();
			int childrenLength = children.length;
			for (int i = 0; i < childrenLength; i++) {
				Widget child = children[i];
				assert child != null;
				int height = child.getHeight();
				layOutChild(child, 0, y, getContentWidth(), height);
				y += height;
			}
		}

	}

}
