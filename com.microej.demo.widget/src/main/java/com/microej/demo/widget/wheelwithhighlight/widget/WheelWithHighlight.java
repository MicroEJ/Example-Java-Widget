/*
 * Copyright 2015-2023 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.wheelwithhighlight.widget;

import com.microej.demo.widget.wheel.widget.Choice;

import ej.annotation.Nullable;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.motion.Motion;
import ej.motion.quad.QuadEaseOutFunction;
import ej.mwt.Widget;
import ej.mwt.animation.Animator;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;
import ej.widget.motion.MotionAnimation;
import ej.widget.motion.MotionAnimationListener;
import ej.widget.render.StringPainter;

/**
 * Represents a wheel from which the user can choose among a set of choices.
 * <p>
 * The selection is highlighted using a larger font for text of the selected item.
 */
public class WheelWithHighlight extends Widget {

	/** The extra field ID for the color of the horizontal lines. */
	public static final int LINE_COLOR_FIELD = 0;

	/** The extra field ID for the font of the highlighted item. */
	public static final int HIGHLIGHTED_FONT_FIELD = 1;

	private static final int RELEASE_WITH_NO_MOVE_DELAY = 150;
	private static final int ANIMATION_PERIOD = 30;
	private static final int STEP_TIME = ANIMATION_PERIOD * 3;
	private static final int MAX_DURATION = 400;
	private static final int THROW_DISTANCE_DIVIDER = 20;

	private final int sideItemsCount;
	private final Choice model;
	private int spinOffset;
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
	public WheelWithHighlight(int numSideValues, Choice model) {
		super(true);
		this.sideItemsCount = numSideValues;
		this.model = model;
	}

	@Override
	public void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();

		int lineHeight = getLineHeight();
		Choice items = this.model;
		int currentIndex = items.getCurrentIndex();
		int currentItemY = contentHeight / 2 + this.spinOffset;
		int topLineY = contentHeight / 2 - lineHeight / 2;
		int bottomLineY = topLineY + lineHeight;
		int centerX = contentWidth / 2;
		int clipX = g.getClipX();
		int clipY = g.getClipY();
		int clipWidth = g.getClipWidth();
		int clipHeight = g.getClipHeight();

		int color = style.getColor();
		Font font = style.getFont();
		Font highlightedFont = style.getExtraObject(HIGHLIGHTED_FONT_FIELD, Font.class, font);

		// Draws the current value.
		g.setColor(color);
		g.intersectClip(0, topLineY, contentWidth, lineHeight);
		if (this.spinOffset > 0) {
			StringPainter.drawStringAtPoint(g, items.getPrevious(currentIndex, 1), highlightedFont, centerX,
					currentItemY - lineHeight, Alignment.HCENTER, Alignment.VCENTER);
		} else {
			StringPainter.drawStringAtPoint(g, items.getNext(currentIndex, 1), highlightedFont, centerX,
					currentItemY + lineHeight, Alignment.HCENTER, Alignment.VCENTER);
		}
		StringPainter.drawStringAtPoint(g, items.getValueAsString(currentIndex), highlightedFont, centerX, currentItemY,
				Alignment.HCENTER, Alignment.VCENTER);

		// Draws the top items
		int y = currentItemY;
		int itemsCount = this.sideItemsCount;
		g.setClip(clipX, clipY, clipWidth, clipHeight);
		g.intersectClip(0, 0, contentWidth, topLineY);
		for (int i = 0; i <= itemsCount; i++) {
			String valueText = items.getPrevious(currentIndex, i);
			StringPainter.drawStringAtPoint(g, valueText, font, centerX, y, Alignment.HCENTER, Alignment.VCENTER);
			y -= lineHeight;
		}

		// Draws the bottom items
		y = currentItemY;
		g.setClip(clipX, clipY, clipWidth, clipHeight);
		g.intersectClip(0, bottomLineY, contentWidth, contentHeight - bottomLineY);
		for (int i = 0; i <= itemsCount; i++) {
			String valueText = items.getNext(currentIndex, i);
			StringPainter.drawStringAtPoint(g, valueText, font, centerX, y, Alignment.HCENTER, Alignment.VCENTER);
			y += lineHeight;
		}

		// Draws the horizontal lines.
		g.setClip(clipX, clipY, clipWidth, clipHeight);
		g.setColor(style.getExtraInt(LINE_COLOR_FIELD, color));
		Painter.fillRectangle(g, 0, topLineY, contentWidth, 2);
		Painter.fillRectangle(g, 0, bottomLineY, contentWidth, 2);
	}

	private int getLineHeight() {
		return getContentBounds().getHeight() / valuesCount();
	}

	@Override
	public void computeContentOptimalSize(Size size) {
		String string = this.model.getValueAsString(this.model.getCurrentIndex());

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
			if (now - this.lastPointerTime > RELEASE_WITH_NO_MOVE_DELAY) {
				// The closer step will be the current value.
				stop = 0;
				start = stop + this.spinOffset;
				currentIndexDiffToBe = 0;
			} else {
				int deltaDistance = pointerCoordinate - this.pressPointerCoordinate;
				long deltaTime = now - this.pressTime;
				currentIndexDiffToBe = computeThrow(deltaDistance, deltaTime);
				stop = -currentIndexDiffToBe * lineHeight;
				start = this.spinOffset;
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
		requestRender();
	}

	/**
	 * Gets the total values count.
	 *
	 * @return values count
	 */
	private int valuesCount() {
		return this.sideItemsCount * 2 + 1;
	}

	/**
	 * Gets the relative index from center.
	 *
	 * @param indexPosition
	 *            computed position
	 * @return relative index from center index
	 */
	private int computeRelativeIndex(int indexPosition) {
		return indexPosition - this.sideItemsCount;
	}

	@Override
	protected void onHidden() {
		stop();
	}

}
