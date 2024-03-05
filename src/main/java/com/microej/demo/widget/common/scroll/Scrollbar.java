/*
 * Copyright 2009-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.common.scroll;

import ej.annotation.Nullable;
import ej.bon.XMath;
import ej.drawing.ShapePainter;
import ej.drawing.ShapePainter.Cap;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;

/**
 * A scrollbar holds the current step of a scrolling element.
 * <p>
 * The size of a scrollbar is dependent on the font size.
 * <p>
 * This example shows a simple scrollbar:
 *
 * <pre>
 * Scrollbar scrollbar = new Scrollbar(100);
 * scrollbar.setHorizontal(false);
 * scrollbar.setValue(50);
 * </pre>
 *
 * <img src="../doc-files/scrollbar-simple.png" alt="Simple scrollbar.">
 * <p>
 * This example shows a styled scrollbar:
 *
 * <pre>
 * Scrollbar scrollbar = new Scrollbar(100);
 * scrollbar.show();
 * scrollbar.setHorizontal(false);
 * scrollbar.setValue(50);
 *
 * CascadingStylesheet stylesheet = new CascadingStylesheet();
 * StyleHelper.setStylesheet(stylesheet);
 *
 * EditableStyle scrollbarStyle = stylesheet.addRule(new TypeSelector(Scrollbar.class));
 * scrollbarStyle.setColor(Colors.BLUE);
 * scrollbarStyle.setBackground(new PlainRectangularBackground(Colors.SILVER));
 * scrollbarStyle.setPadding(new UniformOutline(1));
 * </pre>
 *
 * <img src="../doc-files/scrollbar-styled.png" alt="Styled scrollbar.">
 */
public class Scrollbar extends Widget {

	private static final int MINIMUM_RATIO = 4;
	private static final int MINIMUM_SIZE = 4;
	private static final int MINIMUM_LENGTH_RATIO = 25;
	private static final int MAXIMUM_LENGTH_RATIO = 75;

	private static final int PERCENTAGE_DIVIDER = 100;

	private boolean horizontal;
	private int maximum;
	private int value;
	private int requiredValue;
	private int visibilityLevel;

	private @Nullable Cap caps;

	/**
	 * Creates a new scrollbar with the specified size.
	 *
	 * @param maximum
	 *            the maximum bound.
	 */
	public Scrollbar(int maximum) {
		this.maximum = Math.max(maximum, 0);
		internalSetVisibilityLevel(GraphicsContext.TRANSPARENT);
	}

	/**
	 * Sets the scrollbar orientation: horizontal or vertical.
	 *
	 * @param horizontal
	 *            <code>true</code> to set the slider horizontal, <code>false</code> to set the slider vertical.
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	@Override
	protected void onHidden() {
		super.onHidden();
		hide();
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		int referenceSize = getSize(style);
		int optimalWidth;
		int optimalHeight;
		// The length of the scrollbar is minimum MINIMUM_RATIO times bigger than the thickness.
		// The length of the scrollbar area is twice the scrollbar length.
		// Add 1 for the fade.
		int length = (referenceSize * MINIMUM_RATIO) << 1;
		if (this.horizontal) {
			optimalWidth = length;
			optimalHeight = referenceSize;
		} else {
			optimalWidth = referenceSize;
			optimalHeight = length;
		}
		size.setSize(optimalWidth + 1, optimalHeight + 1);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		if (this.visibilityLevel == GraphicsContext.TRANSPARENT) {
			// nothing to paint
			return;
		}

		Style style = getStyle();
		int barSize = getSize(style);
		g.setColor(style.getColor());

		int width = contentWidth;
		int height = contentHeight;

		Cap caps = this.caps;
		if (caps != null) {
			// Remove 1 for the fade.
			width--;
			height--;
		}

		int barX;
		int barY;
		int barLength;
		if (this.horizontal) {
			int verticalAlignment = style.getVerticalAlignment();
			barY = Alignment.computeTopY(barSize, 0, height, verticalAlignment);
			if (caps != null) {
				barY += (barSize >> 1);
			}
			barLength = getBarLength(width, barSize);
			barX = getBarPosition(width, barLength, barSize);
		} else {
			int horizontalAlignment = style.getHorizontalAlignment();
			barX = Alignment.computeLeftX(barSize, 0, width, horizontalAlignment);
			if (caps != null) {
				barX += (barSize >> 1);
			}
			barLength = getBarLength(height, barSize);
			barY = getBarPosition(height, barLength, barSize);
		}

		if (caps != null) {
			int barX2;
			int barY2;
			if (this.horizontal) {
				barX2 = barX + barLength;
				barY2 = barY;
			} else {
				barX2 = barX;
				barY2 = barY + barLength;
			}
			ShapePainter.drawThickFadedLine(g, barX, barY, barX2, barY2, barSize - 1, 1, caps, caps);
		} else {
			int barWidth;
			int barHeight;
			if (this.horizontal) {
				barWidth = barLength;
				barHeight = barSize;
			} else {
				barWidth = barSize;
				barHeight = barLength;
			}
			Painter.fillRectangle(g, barX, barY, barWidth, barHeight);
		}
	}

	private int getBarLength(int availableLength, int barSize) {
		availableLength = availableLength - (barSize << 1);
		int excess = Math.abs(this.requiredValue - this.value) >> 1;
		int expectedLength = Math.max(availableLength * availableLength / this.maximum, barSize * MINIMUM_RATIO);
		// Don't know why the minimum size is '2', but with lower values the result is not correct.
		int finalLength = XMath.limit(expectedLength - excess, 2, availableLength - excess);
		int minimumLength = availableLength * MINIMUM_LENGTH_RATIO / PERCENTAGE_DIVIDER;
		int maximumLength = availableLength * MAXIMUM_LENGTH_RATIO / PERCENTAGE_DIVIDER;
		if (this.caps != null) {
			finalLength -= barSize; // Subtract site of two caps
		}
		return XMath.limit(finalLength - 1, minimumLength, maximumLength);
	}

	private int getBarPosition(int totalSize, int barLength, int barSize) {
		int position = this.value * (totalSize - barLength - barSize) / this.maximum;
		if (this.caps != null) { // Add the size of one cap to the position, so it does not get cut off at the end
			position = position + barSize / 2;
		}
		return position;
	}

	private int getSize(Style style) {
		Font font = style.getFont();
		return Math.max(font.getHeight() >> 2, MINIMUM_SIZE);
	}

	/**
	 * Shows the scrollbar.
	 */
	public void show() {
		if (this.visibilityLevel != GraphicsContext.OPAQUE) {
			internalSetVisibilityLevel(GraphicsContext.OPAQUE);
		}
	}

	/**
	 * Hides the scrollbar.
	 */
	public void hide() {
		internalSetVisibilityLevel(GraphicsContext.TRANSPARENT);
	}

	/**
	 * From {@link GraphicsContext#TRANSPARENT} (hidden) to {@link GraphicsContext#OPAQUE} (fully visible).
	 *
	 * @return the visibilityLevel
	 */
	public int getVisibilityLevel() {
		return this.visibilityLevel;
	}

	private void internalSetVisibilityLevel(int visibilityLevel) {
		visibilityLevel = XMath.limit(visibilityLevel, GraphicsContext.TRANSPARENT, GraphicsContext.OPAQUE);
		if (visibilityLevel != this.visibilityLevel) {
			this.visibilityLevel = visibilityLevel;
		}
	}

	/**
	 * Gets whether the scrollbar is horizontal or vertical.
	 *
	 * @return <code>true</code> if the scrollbar is horizontal, <code>false</code> otherwise.
	 */
	public boolean isHorizontal() {
		return this.horizontal;
	}

	/**
	 * Gets the max step.
	 *
	 * @return the max step.
	 */
	public int getMaximum() {
		return this.maximum;
	}

	/**
	 * Gets the current value.
	 *
	 * @return the current value.
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Updates the current value with an increment.
	 *
	 * @param increment
	 *            the increment to apply to the current value.
	 */
	public void updateValue(int increment) {
		setValue(this.requiredValue + increment);
	}

	/**
	 * Sets the current value.
	 * <p>
	 * The scrollbar is not rendered. The caller is responsible of requesting a new render.
	 *
	 * @param value
	 *            the value to set.
	 */
	public void setValue(int value) {
		this.requiredValue = value;
		this.value = XMath.limit(value, 0, this.maximum);
		// Do not request render here. The scroll container containing the scrollbar is responsible for this. Avoid
		// rendering two times the bar and slowing down the scroll!
	}

	/**
	 * Sets the maximum bound of the scrollbar.
	 *
	 * @param maximum
	 *            the maximum to set.
	 */
	public void setMaximum(int maximum) {
		this.maximum = Math.max(maximum, 0);
	}

	/**
	 * Sets the caps style.
	 *
	 * @param caps
	 *            the caps style.
	 */
	public void setCaps(Cap caps) {
		this.caps = caps;
	}

}
