/*
 * Java
 *
 * Copyright  2015-2019 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 * MicroEJ Corp. PROPRIETARY. Use is subject to license terms.
 */
package ej.widget.wheel;

import java.util.ListIterator;

import ej.bon.Timer;
import ej.color.GradientHelper;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.motion.Motion;
import ej.motion.MotionManager;
import ej.motion.quad.QuadMotionManager;
import ej.motion.util.MotionAnimator;
import ej.motion.util.MotionListener;
import ej.mwt.MWT;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.style.util.ElementAdapter;
import ej.style.util.StyleHelper;
import ej.widget.StyledWidget;
import ej.widget.util.TouchConfiguration;

/**
 * Represents a wheel from which the user can choose among a set of choices
 */
public class Wheel extends StyledWidget {

	/**
	 * The class selector for the horizontal lines
	 */
	public static final String CLASS_SELECTOR_LINE = "wheel-line"; //$NON-NLS-1$

	private static final int ANIMATION_PERIOD = 30;
	private static final int STEP_TIME = ANIMATION_PERIOD * 3;
	private static final int MAX_DURATION = 400;

	private final ElementAdapter lineElement;

	private final WheelGroup wheelGroup;
	private Choice model;
	private int spinOffset;
	private int currentIndexDiff;
	private boolean dragged;
	private int pressPointerCoordinate;
	private int lastPointerCoordinate;
	private long pressTime;
	private long lastPointerTime;

	private final MotionManager motionManager;
	private MotionAnimator motionAnimator;

	/**
	 * Constructor
	 *
	 * @param wheelGroup
	 *            the wheel group
	 */
	public Wheel(WheelGroup wheelGroup) {
		this.lineElement = new ElementAdapter(this);
		this.lineElement.addClassSelector(CLASS_SELECTOR_LINE);
		this.wheelGroup = wheelGroup;
		this.motionManager = new QuadMotionManager();
	}

	/**
	 * Gets the wheel group
	 *
	 * @return the wheel group
	 */
	public WheelGroup getGroup() {
		return this.wheelGroup;
	}

	/**
	 * Sets the wheel model
	 *
	 * @param model
	 *            the new wheel model
	 */
	public void setModel(Choice model) {
		this.model = model;
	}

	@Override
	public void renderContent(GraphicsContext g, Style style, Rectangle bounds) {
		int width = bounds.getWidth();
		int remainingHeight = bounds.getHeight();

		int lineHeight = getLineHeight();
		int itemOnSideCount = this.wheelGroup.getNumSideValues();
		int maxItemOnSideCount = itemOnSideCount + 1;
		int currentValueY = (remainingHeight >> 1) + this.spinOffset;
		int currentVisibleIndex = this.model.getCurrentIndex() + this.currentIndexDiff;
		int windowHeight = lineHeight * (itemOnSideCount * 2 + 1);
		g.clipRect(0, (remainingHeight - windowHeight) >> 1, width, windowHeight);

		// Draws the current value.
		int x = width >> 1;
		int y = currentValueY;

		int foregroundColor = style.getForegroundColor();
		int backgroundColor = style.getBackgroundColor();
		g.setColor(foregroundColor);
		Font font = StyleHelper.getFont(style);
		g.setFont(font);
		g.drawString(this.model.getValueAsString(currentVisibleIndex), x, y,
				GraphicsContext.HCENTER | GraphicsContext.VCENTER);

		// Draws the previous values.
		ListIterator<String> valueIterator = this.model.listIterator(currentVisibleIndex);
		int previousValueCount = maxItemOnSideCount;
		while (previousValueCount-- > 0 && valueIterator.hasPrevious()) {
			y -= lineHeight;
			float fontRatio = computeFontRatio(y, remainingHeight);
			font.setRatios(fontRatio, fontRatio);
			g.setColor(computeFontColor(y, remainingHeight, foregroundColor, backgroundColor));
			g.drawString(valueIterator.previous(), x, y, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		}

		// Draws the next values.
		valueIterator = this.model.listIterator(currentVisibleIndex);
		int nextValueCount = maxItemOnSideCount;
		y = currentValueY;
		while (nextValueCount-- > 0 && valueIterator.hasNext()) {
			y += lineHeight;
			float fontRatio = computeFontRatio(y, remainingHeight);
			font.setRatios(fontRatio, fontRatio);
			g.setColor(computeFontColor(y, remainingHeight, foregroundColor, backgroundColor));
			g.drawString(valueIterator.next(), x, y, GraphicsContext.HCENTER | GraphicsContext.VCENTER);
		}

		// Draws the horizontal lines.
		Style hLineStyle = this.lineElement.getStyle();
		g.setColor(hLineStyle.getForegroundColor());

		y = (remainingHeight >> 1) - (lineHeight >> 1);
		g.drawHorizontalLine(0, y - 1, width - 1);
		g.drawHorizontalLine(0, y, width - 1);

		y = (remainingHeight >> 1) + (lineHeight >> 1);
		g.drawHorizontalLine(0, y - 1, width - 1);
		g.drawHorizontalLine(0, y, width - 1);

		font.resetRatios();
	}

	private float computeFontRatio(int y, int height) {
		int distance = Math.abs(y - (height >> 1));
		return 1.0f - (float) distance / height;
	}

	private int computeFontColor(int y, int height, int color, int color2) {
		int distance = Math.abs(y - (height >> 1));
		return GradientHelper.blendColors(color, color2, distance / (float) height);
	}

	private int getLineHeight() {
		return this.getHeight() / (this.wheelGroup.getNumSideValues() * 2 + 1);
	}

	@Override
	public Rectangle validateContent(Style style, Rectangle bounds) {
		int availableWidth = bounds.getWidth();
		int availableHeight = bounds.getHeight();
		Rectangle contentSize = new Rectangle();
		int preferredWidth = availableWidth;
		int width = availableWidth == MWT.NONE ? preferredWidth : Math.min(availableWidth, preferredWidth);
		int preferredHeight = availableHeight;
		int height = availableHeight == MWT.NONE ? preferredHeight : Math.min(availableHeight, preferredHeight);
		contentSize.setSize(width, height);
		return contentSize;
	}

	private void stopAnimation() {
		if (this.motionAnimator != null) {
			this.motionAnimator.stop();
		}
	}

	@Override
	public boolean handleEvent(int event) {
		if (Event.getType(event) == Event.POINTER) {
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int pointerX = getRelativeX(pointer.getX());
			int pointerY = getRelativeY(pointer.getY());
			int action = Pointer.getAction(event);
			switch (action) {
			case Pointer.PRESSED:
				return onPointerPressed(pointerX, pointerY);
			case Pointer.RELEASED:
				return onPointerReleased(pointerX, pointerY);
			case Pointer.DRAGGED:
				return onPointerDragged(pointerX, pointerY);
			}
		}
		return super.handleEvent(event);
	}

	private boolean onPointerPressed(int pointerX, int pointerY) {
		stopAnimation();
		this.dragged = false;
		this.pressPointerCoordinate = pointerY;
		this.lastPointerCoordinate = pointerY;
		this.pressTime = System.currentTimeMillis();
		this.lastPointerTime = this.pressTime;
		return false;
	}

	private boolean onPointerDragged(int pointerX, int pointerY) {
		this.dragged = true;
		int pointerCoordinate = pointerY;
		int distanceDragged = pointerCoordinate - this.lastPointerCoordinate;

		if (distanceDragged != 0) {
			this.lastPointerTime = System.currentTimeMillis();
			this.spinOffset = computeSpinOffset(distanceDragged, this.spinOffset);
			this.currentIndexDiff = computeIndexDiff(this.pressPointerCoordinate - pointerCoordinate);
			this.lastPointerCoordinate = pointerCoordinate;
			repaint();
		}
		return true;
	}

	private int computeIndexDiff(int distance) {
		int n = (int) Math.floor(distance / (float) (getLineHeight() / 2));
		return (n & 1) == 0 ? n >> 1 : (n + 1) >> 1;
	}

	private int computeSpinOffset(int distance, int spinOffset) {
		int halfLineHeight = getLineHeight() / 2;
		int max = halfLineHeight;
		int min = -halfLineHeight + 1;
		int range = max - min + 1;
		int newSpinOffset = spinOffset + distance % range;

		// Overflow case.
		if (newSpinOffset > max) {
			newSpinOffset = newSpinOffset - range;
		}

		// Underflow case.
		if (newSpinOffset < min) {
			newSpinOffset = range + newSpinOffset;
		}

		return newSpinOffset;
	}

	private boolean onPointerReleased(int pointerX, int pointerY) {
		int pointerCoordinate = pointerY;
		int start;
		int stop;
		int currentIndexDiffToBe;
		if (this.wheelGroup.getNumActiveWheels() >= this.wheelGroup.getMaxActiveWheels()) {
			currentIndexDiffToBe = computeIndexDiff(pointerCoordinate - (getHeight() >> 1));
			start = -currentIndexDiffToBe * getLineHeight();
			stop = -currentIndexDiffToBe * getLineHeight();
		} else if (!this.dragged) {
			currentIndexDiffToBe = computeIndexDiff(pointerCoordinate - (getHeight() >> 1));
			start = 0;
			stop = -currentIndexDiffToBe * getLineHeight();
		} else {
			long now = System.currentTimeMillis();
			int currentIndexDiff = this.currentIndexDiff;
			if (now - this.lastPointerTime > TouchConfiguration.RELEASE_WITH_NO_MOVE_DELAY) {
				// The closer step will be the current value.
				stop = -currentIndexDiff * getLineHeight();
				start = stop + this.spinOffset;
				currentIndexDiffToBe = currentIndexDiff;
			} else {
				int deltaDistance = pointerCoordinate - this.pressPointerCoordinate;
				long deltaTime = now - this.pressTime;
				int indexDiffWithThrow = computeThrow(deltaDistance, deltaTime);
				currentIndexDiffToBe = this.currentIndexDiff + indexDiffWithThrow;
				stop = -currentIndexDiffToBe * getLineHeight();
				start = -currentIndexDiff * getLineHeight() + this.spinOffset;
			}
		}
		spin(start, stop, currentIndexDiffToBe);
		return false;
	}

	/**
	 * Computes the index difference when swiping the wheel
	 *
	 * @param deltaDistance
	 *            the swipe distance in pixels
	 * @param deltaTime
	 *            the amount of time since the beginning of the swipe
	 * @return the index difference
	 */
	protected int computeThrow(int deltaDistance, long deltaTime) {
		return -deltaDistance / 20;
	}

	private void spin(int start, int stop, final int currentIndexDiffToBe) {
		int motionTime = (int) ((Math.abs(start - stop) / (float) getLineHeight()) * STEP_TIME);
		motionTime = Math.min(motionTime, MAX_DURATION);
		Motion spinMotion = this.motionManager.easeOut(start, stop, motionTime);
		this.motionAnimator = new MotionAnimator(spinMotion, new MotionListener() {

			@Override
			public void stop(int value) {
				Wheel wheel = Wheel.this;
				wheel.getGroup().setWheelActive(wheel, false);
				wheel.spinOffset = 0;
				wheel.currentIndexDiff = 0;
				int newCurrentIndex = wheel.model.getCurrentIndex() + currentIndexDiffToBe;
				wheel.model.setCurrentIndex(newCurrentIndex);
				repaint();
			}

			@Override
			public void step(int value) {
				Wheel wheel = Wheel.this;
				wheel.getGroup().setWheelActive(wheel, true);
				wheel.spinOffset = computeSpinOffset(value, 0);
				wheel.currentIndexDiff = computeIndexDiff(-value);
				repaint();
			}

			@Override
			public void start(int value) {
				// Nothing to do.
			}

			@Override
			public void cancel() {
				stop(0);
			}
		});
		Timer timer = ServiceLoaderFactory.getServiceLoader().getService(Timer.class);
		this.motionAnimator.start(timer, ANIMATION_PERIOD);
	}

}
