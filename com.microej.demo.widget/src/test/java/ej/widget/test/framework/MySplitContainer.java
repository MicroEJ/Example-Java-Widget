/*
 * Copyright 2014-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */

package ej.widget.test.framework;

import ej.annotation.Nullable;
import ej.mwt.Container;
import ej.mwt.Widget;
import ej.mwt.util.Size;

public class MySplitContainer extends Container {

	@Nullable
	private Widget left;
	@Nullable
	private Widget right;

	public void setWidgets(Widget left, Widget right) throws NullPointerException, IllegalArgumentException {
		this.left = left;
		this.right = right;
		removeAllChildren();
		super.addChild(left);
		super.addChild(right);
	}

	public void setWidget(Widget widget) throws NullPointerException, IllegalArgumentException {
		this.left = widget;
		this.right = null;
		super.addChild(widget);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		int widthHint = size.getWidth();
		int heightHint = size.getHeight();

		boolean computeWidth = widthHint == Widget.NO_CONSTRAINT;
		boolean computeHeight = heightHint == Widget.NO_CONSTRAINT;

		// compute widgets expected width
		int childWidth;
		if (computeWidth) {
			childWidth = Widget.NO_CONSTRAINT;
		} else {
			childWidth = widthHint / 2;
		}
		// compute widgets expected height
		int childHeight;
		if (computeHeight) {
			childHeight = Widget.NO_CONSTRAINT;
		} else {
			childHeight = heightHint;
		}

		// validate widgets and get their optimal widgets
		Widget left = this.left;
		assert left != null;
		computeChildOptimalSize(left, childWidth, childHeight);
		int firstOptimalWidth = left.getWidth();
		int firstOptimalHeight = left.getHeight();
		int secondOptimalWidth;
		int secondOptimalHeight;
		Widget right = this.right;
		if (right != null) {
			computeChildOptimalSize(right, childWidth, childHeight);
			secondOptimalWidth = right.getWidth();
			secondOptimalHeight = right.getHeight();
		} else {
			secondOptimalWidth = 0;
			secondOptimalHeight = 0;
		}

		// compute container optimal size if necessary
		if (computeWidth) {
			widthHint = Math.max(firstOptimalWidth, secondOptimalWidth) * 2;
		}
		if (computeHeight) {
			heightHint = Math.max(firstOptimalHeight, secondOptimalHeight);
		}

		size.setSize(widthHint, heightHint);
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		int leftWidth = contentWidth / 2;
		int rightWidth = contentWidth - leftWidth;

		Widget left = this.left;
		if (left != null) {
			layOutChild(left, 0, 0, leftWidth, contentHeight);
		}

		Widget right = this.right;
		if (right != null) {
			layOutChild(right, leftWidth, 0, rightWidth, contentHeight);
		}
	}

}