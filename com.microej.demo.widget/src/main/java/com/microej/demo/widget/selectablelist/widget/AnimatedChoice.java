/*
 * Copyright 2021-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.selectablelist.widget;

import com.microej.demo.widget.common.DemoColors;
import com.microej.demo.widget.common.RectanglePainter;

import ej.basictool.ThreadUtils;
import ej.bon.XMath;
import ej.microui.display.Colors;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.mwt.Widget;
import ej.mwt.animation.Animation;
import ej.mwt.animation.Animator;
import ej.mwt.style.Style;
import ej.mwt.util.OutlineHelper;
import ej.mwt.util.Rectangle;
import ej.mwt.util.Size;
import ej.widget.color.GradientHelper;
import ej.widget.render.StringPainter;

/**
 * Label that is selectable with an animation when selected or deselected.
 */
public class AnimatedChoice extends Widget implements Animation {

	/** Extra style to set the starting color for the transition to the selected color. */
	public static final int START_SELECTED_COLOR = 0;
	/** Extra style to set the color of the rectangle when selected. */
	public static final int SELECTED_COLOR = 1;
	/** Extra style to set the text color to use when selected. */
	public static final int SELECTED_TEXT_COLOR = 2;
	/** Extra style to set the thickness of the outer rectangle. */
	public static final int RECTANGLE_THICKNESS = 3;

	private static final int DEFAULT_RECTANGLE_THICKNESS = 5;

	private static final long ANIMATION_DURATION = 200;

	private String text;
	private boolean selected;

	private long animationEndTime;

	/**
	 * Creates the AnimatedChoice.
	 *
	 * @param text
	 *            text of the AnimatedChoice.
	 */
	public AnimatedChoice(String text) {
		super(true);
		this.text = text;
		this.selected = false;
		this.animationEndTime = 0;
	}

	/**
	 * Gets the text displayed on this AnimatedChoice.
	 *
	 * @return the text displayed on this AnimatedChoice.
	 */
	public String getText() {
		return this.text;
	}

	/**
	 * Sets the text to display on this AnimatedChoice.
	 *
	 * @param text
	 *            the text to display on this AnimatedChoice.
	 */
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public boolean handleEvent(int event) {
		if (Event.getType(event) == Pointer.EVENT_TYPE && Buttons.getAction(event) == Buttons.RELEASED) {
			setSelected(!this.selected);
			return true;
		}
		return super.handleEvent(event);
	}

	/**
	 * Sets the selected state of the AnimatedChoice.
	 *
	 * @param selected
	 *            <code>true</code> to set the AnimatedChoice as selected, <code>false</code> otherwise.
	 */
	public void setSelected(boolean selected) {
		if (this.selected != selected) {
			this.selected = selected;
			this.animationEndTime = System.currentTimeMillis() + ANIMATION_DURATION;
			requestRender();
			Animator animator = getDesktop().getAnimator();
			animator.stopAnimation(this);
			animator.startAnimation(this);
		}
	}

	@Override
	public boolean tick(long currentTimeMillis) {
		requestRender();
		return (isShown() && currentTimeMillis < this.animationEndTime);
	}

	@Override
	protected void onHidden() {
		getDesktop().getAnimator().stopAnimation(this);
	}

	@Override
	public void render(GraphicsContext g) {
		Style style = getStyle();
		assert style != null;

		Rectangle paddingRect = new Rectangle(0, 0, getWidth(), getHeight());
		OutlineHelper.applyOutlines(paddingRect, style);

		Size contentSize = new Size(getWidth(), getHeight());
		// Apply outline styles separately because padding will be applied after animation.
		style.getMargin().apply(g, contentSize);
		style.getBackground().apply(g, contentSize.getWidth(), contentSize.getHeight());
		style.getBorder().apply(g, contentSize);

		try {
			int leftPadding = paddingRect.getX();
			int rightPadding = contentSize.getWidth() - leftPadding - paddingRect.getWidth();
			renderSelectAnimation(g, contentSize.getWidth(), contentSize.getHeight(), leftPadding, rightPadding);

			style.getPadding().apply(g, contentSize); // Padding is only applied for content render
			renderContent(g, contentSize.getWidth(), contentSize.getHeight());
		} catch (Exception e) {
			ThreadUtils.handleUncaughtException(e);
		}
	}

	/**
	 * Renders the animation when selecting an AnimatedChoice.
	 * <p>
	 * The given graphics context is translated and clipped according to the given bounds (the border and margin are
	 * applied on this graphics context before).<br>
	 * Padding is not applied to the graphics context, since the animation renders in the area between content and
	 * padding box.
	 * <p>
	 * This implementation modifies the graphics contexts background color so text renders correctly when selected.
	 *
	 * @param g
	 *            the graphics context where to render the content of the widget.
	 * @param animationWidth
	 *            the width of the area for the animation.
	 * @param animationHeight
	 *            the width of the area for the animation.
	 * @param leftPadding
	 *            the left padding before the content, inside the animationWidth.
	 * @param rightPadding
	 *            the right padding of the content, inside the animationWidth.
	 */
	private void renderSelectAnimation(GraphicsContext g, int animationWidth, int animationHeight, int leftPadding,
			int rightPadding) {
		Style style = getStyle();
		int selectedColor = style.getExtraInt(SELECTED_COLOR, Colors.BLUE);
		int selectedColorStart = style.getExtraInt(START_SELECTED_COLOR, DemoColors.DEFAULT_BACKGROUND);

		int rectangleThickness = style.getExtraInt(RECTANGLE_THICKNESS, DEFAULT_RECTANGLE_THICKNESS);

		// compute checked ratio (1 = checked, 0 = unchecked, 0.5 = middle)
		float ratio = (float) (this.animationEndTime - System.currentTimeMillis()) / ANIMATION_DURATION;
		ratio = XMath.limit(ratio, 0.0f, 1.0f);
		if (!this.selected) {
			ratio = 1.0f - ratio;
		}

		if (ratio < 1.0f) {
			int areaHeight = animationHeight;
			int areaY = 0;

			int areaLeftPadding = (int) (ratio * (leftPadding - rectangleThickness));
			int areaRightPadding = (int) (ratio * (rightPadding - rectangleThickness));
			int areaWidth = animationWidth - areaLeftPadding - areaRightPadding;
			int areaX = areaLeftPadding;

			g.setColor(GradientHelper.blendColors(selectedColor, selectedColorStart, ratio));
			Painter.fillRectangle(g, areaX, areaY, areaWidth, areaHeight);

			g.setColor(selectedColor);
			RectanglePainter.drawThickRectangle(g, areaX, areaY, areaWidth, areaHeight, rectangleThickness);

			if (g.getBackgroundColor() != 0) {
				if (ratio == 0.0f) {
					// Set selected color as background when animation is finished.
					g.setBackgroundColor(selectedColor);
				} else {
					// Remove background color during animation transition.
					g.removeBackgroundColor();
				}
			}
		}

	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();

		int textColor = style.getColor();
		if (this.selected) {
			textColor = style.getExtraInt(SELECTED_TEXT_COLOR, textColor);
		}

		// draw label
		g.setColor(textColor);
		StringPainter.drawStringInArea(g, this.text, style.getFont(), 0, 0, contentWidth, contentHeight,
				style.getHorizontalAlignment(), style.getVerticalAlignment());

	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		Font font = style.getFont();
		StringPainter.computeOptimalSize(this.text, font, size);
	}
}
