/*
 * Copyright 2015-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.wheel.widget;

import ej.annotation.Nullable;
import ej.drawing.TransformPainter;
import ej.microui.display.Colors;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.motion.Motion;
import ej.motion.quad.QuadEaseInFunction;
import ej.motion.quad.QuadEaseOutFunction;
import ej.mwt.Widget;
import ej.mwt.animation.Animator;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;
import ej.widget.color.GradientHelper;
import ej.widget.motion.MotionAnimation;
import ej.widget.motion.MotionAnimationListener;
import ej.widget.render.StringPainter;

/**
 * Represents a wheel from which the user can choose among a set of choices.
 */
public class Wheel extends Widget {

	/** The extra field ID for the color of the horizontal lines. */
	public static final int LINE_COLOR_FIELD = 0;

	private static final int RELEASE_WITH_NO_MOVE_DELAY = 150;
	private static final int ANIMATION_PERIOD = 30;
	private static final int STEP_TIME = ANIMATION_PERIOD * 3;
	private static final int MAX_DURATION = 400;

	private static final float TRANSPARENCY_TEXT_CENTER_DISTANCE = 0.35f; // = 35%
	private static final float TRANSPARENCY_VALUE_DIVIDER = 2.8f;
	private static final float MILLISECOND_MULTIPLIER = 1000.f;
	private static final int THROW_DISTANCE_DIVIDER = 20;

	/**
	 * When the distance from value to center is more than 35% we are adding a supplementary transparency on the blended
	 * color.
	 */
	private static final Motion MOTION = new Motion(QuadEaseInFunction.INSTANCE, 0, 100, 1000);

	private final int numSideValues;
	private final Choice model;
	private int spinOffset;
	private int currentIndexDiff;
	private boolean dragged;
	private int pressPointerCoordinate;
	private int lastPointerCoordinate;
	private long pressTime;
	private long lastPointerTime;
	private @Nullable MotionAnimation motionAnimation;

	/**
	 * Creates a wheel.
	 *
	 * @param numSideValues
	 *            how many values are visible above and below the selected item.
	 * @param model
	 *            the wheel model.
	 */
	public Wheel(int numSideValues, Choice model) {
		super(true);
		this.numSideValues = numSideValues;
		this.model = model;
	}

	@Override
	public void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();

		int lineHeight = getLineHeight();
		int itemOnSideCount = this.numSideValues;
		int maxItemOnSideCount = itemOnSideCount + 1;
		int currentValueY = (contentHeight >> 1) + this.spinOffset;
		int currentVisibleIndex = this.model.setCurrentIndex(this.model.getCurrentIndex() + this.currentIndexDiff);
		int windowHeight = lineHeight * (itemOnSideCount * 2 + 1);
		g.intersectClip(0, (contentHeight - windowHeight) >> 1, contentWidth, windowHeight);

		// Draws the current value.
		int x = contentWidth >> 1;
		int y = currentValueY;

		int color = style.getColor();
		int backgroundColor = (g.hasBackgroundColor() ? g.getBackgroundColor() : Colors.WHITE);
		g.setColor(color);
		Font font = style.getFont();
		StringPainter.drawStringAtPoint(g, this.model.getValueAsString(currentVisibleIndex), font, x, y,
				Alignment.HCENTER, Alignment.VCENTER);

		int previousValueCount = 0;
		while (previousValueCount < maxItemOnSideCount) {
			previousValueCount++;
			y -= lineHeight;
			float fontRatio = computeFontRatio(y, contentHeight);

			g.setColor(computeFontColor(y, contentHeight, color, backgroundColor));
			String valueText = this.model.getPrevious(currentVisibleIndex, previousValueCount);
			drawString(g, font, valueText, x, y, fontRatio);
		}

		int nextValueCount = 0;
		y = currentValueY;
		while (nextValueCount < maxItemOnSideCount) {
			nextValueCount++;
			y += lineHeight;
			float fontRatio = computeFontRatio(y, contentHeight);

			g.setColor(computeFontColor(y, contentHeight, color, backgroundColor));
			String valueText = this.model.getNext(currentVisibleIndex, nextValueCount);
			drawString(g, font, valueText, x, y, fontRatio);
		}

		// Draws the horizontal lines.
		g.setColor(style.getExtraInt(LINE_COLOR_FIELD, Colors.BLACK));

		y = (contentHeight >> 1) - (lineHeight >> 1);
		Painter.drawHorizontalLine(g, 0, y - 1, contentWidth);
		Painter.drawHorizontalLine(g, 0, y, contentWidth);

		y = (contentHeight >> 1) + (lineHeight >> 1);
		Painter.drawHorizontalLine(g, 0, y - 1, contentWidth);
		Painter.drawHorizontalLine(g, 0, y, contentWidth);
	}

	private void drawString(GraphicsContext g, Font font, String string, int anchorX, int anchorY, float fontRatio) {
		int x = Alignment.computeLeftX((int) (font.stringWidth(string) * fontRatio), anchorX, Alignment.HCENTER);
		int y = Alignment.computeTopY((int) (font.getHeight() * fontRatio), anchorY, Alignment.VCENTER);
		TransformPainter.drawScaledStringBilinear(g, string, font, x, y, fontRatio, fontRatio);
	}

	/**
	 * Gets the font ratio for an item depending on its distance to the wheel center.
	 */
	private float computeFontRatio(int y, int height) {
		int distance = getDistance(y, height);
		return 1.0f - (float) distance / height;
	}

	/**
	 * Gets the color for an item depending on its distance to the wheel center.
	 * <p>
	 * The result is a blend between the color and background color.
	 * <p>
	 * If the text to center distance is greater than 35% we add a quadratic evolving transparency.
	 */
	private int computeFontColor(int y, int height, int color, int background) {
		int distance = getDistance(y, height);
		float colorBlending = distance / (float) height;
		if (colorBlending > TRANSPARENCY_TEXT_CENTER_DISTANCE) {
			float transparencyPosition = (colorBlending - TRANSPARENCY_TEXT_CENTER_DISTANCE) * MILLISECOND_MULTIPLIER;
			float transparencyValue = MOTION.getValue((long) transparencyPosition) / TRANSPARENCY_VALUE_DIVIDER;
			colorBlending = TRANSPARENCY_TEXT_CENTER_DISTANCE + transparencyValue;
		}

		return GradientHelper.blendColors(color, background, colorBlending);
	}

	/**
	 * Gets the distance between an item and the wheel center.
	 */
	private int getDistance(int y, int height) {
		return Math.abs(y - (height >> 1));
	}

	private int getLineHeight() {
		return getContentBounds().getHeight() / valuesCount();
	}

	@Override
	public void computeContentOptimalSize(Size size) {
		int currentVisibleIndex = this.model.getCurrentIndex() + this.currentIndexDiff;
		String string = this.model.getValueAsString(currentVisibleIndex);

		Font font = getStyle().getFont();
		StringPainter.computeOptimalSize(string, font, size);
	}

	private void stopAnimation() {
		MotionAnimation animation = this.motionAnimation;
		if (animation != null) {
			animation.stop();
			this.motionAnimation = null;
		}
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		if (type == Pointer.EVENT_TYPE) {
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int pointerY = pointer.getY() - getAbsoluteY();
			int action = Buttons.getAction(event);
			switch (action) {
			case Buttons.PRESSED:
				return onPointerPressed(pointerY);
			case Buttons.RELEASED:
				return onPointerReleased(pointerY);
			case Pointer.DRAGGED:
				return onPointerDragged(pointerY);
			default:
				break;
			}
		}
		return super.handleEvent(event);
	}

	private boolean onPointerPressed(int pointerCoordinate) {
		stopAnimation();
		this.dragged = false;
		this.pressPointerCoordinate = pointerCoordinate;
		this.lastPointerCoordinate = pointerCoordinate;
		this.pressTime = System.currentTimeMillis();
		this.lastPointerTime = this.pressTime;
		return false;
	}

	private boolean onPointerDragged(int pointerCoordinate) {
		this.dragged = true;
		int distanceDragged = pointerCoordinate - this.lastPointerCoordinate;

		if (distanceDragged != 0) {
			this.lastPointerTime = System.currentTimeMillis();
			this.spinOffset = computeSpinOffset(distanceDragged, this.spinOffset, -1);
			this.currentIndexDiff = 0;
			this.lastPointerCoordinate = pointerCoordinate;
			requestRender();
		}
		return true;
	}

	private int computeIndexDiff(int distance) {
		int n = (int) Math.floor(distance / (getLineHeight() / 2.0));
		return (n & 1) == 0 ? n >> 1 : (n + 1) >> 1;
	}

	/**
	 * Computes offset in pixels. Changes the current index on overflows.
	 * <p>
	 * It is always less than a multiple row height.
	 *
	 * @param distance
	 *            current distance for computing offset
	 * @param spinOffset
	 *            current spin offset
	 * @param targetIndex
	 *            where to stop the index changes (click) or negative to always change the index(drag)
	 * @return spin offset
	 */
	private int computeSpinOffset(int distance, int spinOffset, int targetIndex) {
		int halfLineHeight = getLineHeight() / 2;
		int max = halfLineHeight;
		int min = -halfLineHeight + 1;
		int range = halfLineHeight << 1;

		int newSpinOffset = spinOffset + distance % range;

		if (newSpinOffset > max) {
			// Overflow case.
			int currentIndex = this.model.getCurrentIndex();
			if (targetIndex < 0 || currentIndex != targetIndex) {
				newSpinOffset = newSpinOffset - range;
				this.model.setCurrentIndex(currentIndex - 1);
			}
		} else if (newSpinOffset < min) {
			// Underflow case.
			int currentIndex = this.model.getCurrentIndex();
			if (targetIndex < 0 || currentIndex != targetIndex) {
				newSpinOffset = range + newSpinOffset;
				this.model.setCurrentIndex(currentIndex + 1);
			}
		}

		return newSpinOffset;
	}

	private boolean onPointerReleased(int pointerCoordinate) {
		int start;
		int stop;
		int currentIndexDiffToBe;
		int lineHeight = getLineHeight();
		if (this.dragged) {
			long now = System.currentTimeMillis();
			int currentIndexDifference = this.currentIndexDiff;
			if (now - this.lastPointerTime > RELEASE_WITH_NO_MOVE_DELAY) {
				// The closer step will be the current value.
				stop = -currentIndexDifference * lineHeight;
				start = stop + this.spinOffset;
				currentIndexDiffToBe = currentIndexDifference;
			} else {
				int deltaDistance = pointerCoordinate - this.pressPointerCoordinate;
				long deltaTime = now - this.pressTime;
				int indexDiffWithThrow = computeThrow(deltaDistance, deltaTime);
				currentIndexDiffToBe = this.currentIndexDiff + indexDiffWithThrow;
				stop = -currentIndexDiffToBe * lineHeight;
				start = -currentIndexDifference * lineHeight + this.spinOffset;
			}
		} else {
			currentIndexDiffToBe = computeRelativeIndex(computeIndexDiff(pointerCoordinate - (lineHeight >> 1)));
			start = 0;
			stop = -currentIndexDiffToBe * lineHeight;
		}

		spin(start, stop, currentIndexDiffToBe, this.dragged);
		return false;
	}

	/**
	 * Computes the index difference when swiping the wheel.
	 *
	 * @param deltaDistance
	 *            the swipe distance in pixels
	 * @param deltaTime
	 *            the amount of time since the beginning of the swipe
	 * @return the index difference
	 */
	protected int computeThrow(int deltaDistance, long deltaTime) {
		return -deltaDistance / THROW_DISTANCE_DIVIDER;
	}

	private void spin(int start, int stop, final int currentIndexDiffToBe, final boolean dragged) {
		int motionTime = (int) ((Math.abs(start - stop) / (float) getLineHeight()) * STEP_TIME);
		motionTime = Math.min(motionTime, MAX_DURATION);
		Motion spinMotion = new Motion(QuadEaseOutFunction.INSTANCE, start, stop, motionTime);
		Animator animator = getDesktop().getAnimator();

		final int targetIndex = dragged ? -1
				: this.model.checkIndex(this.model.getCurrentIndex() + currentIndexDiffToBe);

		this.motionAnimation = new MotionAnimation(animator, spinMotion, new MotionAnimationListener() {
			@Override
			public void tick(int value, boolean finished) {
				if (finished) {
					stop();
				} else {
					step(value, targetIndex);
				}
			}
		});
		this.motionAnimation.start();
	}

	private void step(int value, int targetIndex) {
		this.spinOffset = computeSpinOffset(value, 0, targetIndex);
		requestRender();
	}

	private void stop() {
		this.spinOffset = 0;
		this.currentIndexDiff = 0;
		requestRender();
	}

	/**
	 * Gets the total values count.
	 *
	 * @return values count
	 */
	private int valuesCount() {
		return this.numSideValues * 2 + 1;
	}

	/**
	 * Gets the relative index from center.
	 *
	 * @param indexPosition
	 *            computed position
	 * @return relative index from center index
	 */
	private int computeRelativeIndex(int indexPosition) {
		return indexPosition - this.numSideValues;
	}

	@Override
	protected void onHidden() {
		stop();
	}

}
