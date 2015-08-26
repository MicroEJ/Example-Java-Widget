/*
 * Java
 *
 * Copyright 2015 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.is2t.demo.widgets.widget;

import ej.mwt.Composite;
import ej.mwt.MWT;
import ej.mwt.Widget;

/**
 * Lays out its children in two regions, giving a each one a ratio of its size.
 * 
 * @see #setRatio(float)
 */
public class SplitComposite extends Composite {

	private boolean horizontal;
	private float ratio;

	/**
	 * Sets the composite orientation: horizontal or vertical.
	 * 
	 * @param horizontal
	 *            <code>true</code> to set the composite horizontal, <code>false</code> to set the composite vertical.
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	/**
	 * Sets the position of the separation between the children, that is the first composite size relatively to this
	 * composite size.
	 * <p>
	 * Example: setting the ratio to <tt>1/3</tt> in horizontal mode will give a third of the width to the first child,
	 * and two third for the second one. Both will be given all available height.
	 * 
	 * @param ratio
	 *            the ratio to set.
	 */
	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws IllegalArgumentException
	 *             if there is already two widgets registered.
	 */
	@Override
	public void add(Widget widget) {
		if (getWidgetsCount() < 2) {
			super.add(widget);
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void validate(int widthHint, int heightHint) {
		if (!isVisible()) {
			// optim: do not validate its hierarchy
			setPreferredSize(0, 0);
			return;
		}

		Widget[] widgets = getWidgets();
		int length = widgets.length;
		if (length == 0) {
			// nothing to do
			return;
		}

		boolean computeWidth = widthHint == MWT.NONE;
		boolean computeHeight = heightHint == MWT.NONE;

		// compute widgets expected width
		int firstWidth;
		int secondWidth;
		if (computeWidth) {
			firstWidth = MWT.NONE;
			secondWidth = MWT.NONE;
		} else {
			if (this.horizontal) {
				firstWidth = (int) (widthHint * this.ratio);
				secondWidth = widthHint - firstWidth;
			} else {
				firstWidth = widthHint;
				secondWidth = widthHint;
			}
		}
		// compute widgets expected height
		int firstHeight;
		int secondHeight;
		if (computeHeight) {
			firstHeight = MWT.NONE;
			secondHeight = MWT.NONE;
		} else {
			if (this.horizontal) {
				firstHeight = heightHint;
				secondHeight = heightHint;
			} else {
				firstHeight = (int) (widthHint * this.ratio);
				secondHeight = widthHint - firstHeight;
			}
		}

		// validate widgets and get their preferred widgets
		Widget first = widgets[0];
		first.validate(firstWidth, firstHeight);
		int firstPreferredWidth = first.getPreferredWidth();
		int firstPreferredHeight = first.getPreferredHeight();
		int secondPreferredWidth;
		int secondPreferredHeight;
		if (length > 1) {
			Widget second = widgets[1];
			second.validate(secondWidth, secondHeight);
			secondPreferredWidth = second.getPreferredWidth();
			secondPreferredHeight = second.getPreferredHeight();
		} else {
			secondPreferredWidth = 0;
			secondPreferredHeight = 0;
		}

		// compute composite preferred size if necessary
		if (computeWidth) {
			if (this.horizontal) {
				float firstU = firstPreferredWidth / this.ratio;
				float secondU = secondPreferredWidth / (1 - this.ratio);
				if (firstU > secondU) {
					widthHint = (int) firstU;
				} else {
					widthHint = (int) secondU;
				}
			} else {
				widthHint = Math.max(firstPreferredWidth, secondPreferredWidth);
			}
		}
		if (computeHeight) {
			if (this.horizontal) {
				heightHint = Math.max(firstPreferredHeight, secondPreferredHeight);
			} else {
				float firstU = firstPreferredHeight / this.ratio;
				float secondU = secondPreferredHeight / (1 - this.ratio);
				if (firstU > secondU) {
					heightHint = (int) firstU;
				} else {
					heightHint = (int) secondU;
				}
			}
		}

		// set composite preferred size
		setPreferredSize(widthHint, heightHint);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		Widget[] widgets = getWidgets();
		int length = widgets.length;
		if (length > 0) {
			Widget first = widgets[0];
			Widget second;
			if (length > 1) {
				second = widgets[1];
			} else {
				second = null;
			}

			// compute widgets bounds
			int firstX = 0;
			int firstY = 0;
			int firstWidth;
			int firstHeight;
			int secondX;
			int secondY;
			int secondWidth;
			int secondHeight;
			if (this.horizontal) {
				firstWidth = (int) (width * this.ratio);
				firstHeight = height;
				secondX = firstWidth;
				secondY = 0;
				secondWidth = width - firstWidth;
				secondHeight = height;
			} else {
				firstWidth = width;
				firstHeight = (int) (height * this.ratio);
				secondX = 0;
				secondY = firstHeight;
				secondWidth = width;
				secondHeight = height - firstHeight;
			}

			// set widgets bounds
			first.setBounds(firstX, firstY, firstWidth, firstHeight);
			if (second != null) {
				second.setBounds(secondX, secondY, secondWidth, secondHeight);
			}
		}

		super.setBounds(x, y, width, height);
	}

}
