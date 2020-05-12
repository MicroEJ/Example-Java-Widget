/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.wheel;

import java.util.ListIterator;

import ej.bon.Timer;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.motion.Motion;
import ej.motion.MotionManager;
import ej.motion.quad.QuadMotionManager;
import ej.motion.util.MotionAnimator;
import ej.motion.util.MotionListener;
import ej.mwt.Widget;
import ej.mwt.event.DesktopEventGenerator;
import ej.mwt.event.PointerEventDispatcher;
import ej.mwt.style.Style;
import ej.mwt.style.container.Alignment;
import ej.mwt.util.Size;
import ej.service.ServiceFactory;
import ej.widget.util.color.GradientHelper;

/**
 * Represents a wheel from which the user can choose among a set of choices
 */
public class Wheel extends Widget {

	private static final int RELEASE_WITH_NO_MOVE_DELAY = 150;
	private static final int ANIMATION_PERIOD = 30;
	private static final int STEP_TIME = ANIMATION_PERIOD * 3;
	private static final int MAX_DURATION = 400;

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
		this.wheelGroup = wheelGroup;
		this.motionManager = new QuadMotionManager();
		setEnabled(true);
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
	public void renderContent(GraphicsContext g, Size size) {
		Style style = getStyle();
		int width = size.getWidth();
		int remainingHeight = size.getHeight();

		int lineHeight = getLineHeight();
		int itemOnSideCount = this.wheelGroup.getNumSideValues();
		int maxItemOnSideCount = itemOnSideCount + 1;
		int currentValueY = (remainingHeight >> 1) + this.spinOffset;
		int currentVisibleIndex = this.model.getCurrentIndex() + this.currentIndexDiff;
		int windowHeight = lineHeight * (itemOnSideCount * 2 + 1);
		g.setClip(0, (remainingHeight - windowHeight) >> 1, width, windowHeight);

		// Draws the current value.
		int x = width >> 1;
		int y = currentValueY;

		int foregroundColor = style.getForegroundColor();
		int backgroundColor = style.getBackgroundColor();
		g.setColor(foregroundColor);
		Font font = getDesktop().getFont(style);
		drawString(g, font, this.model.getValueAsString(currentVisibleIndex), x, y, Alignment.HCENTER_VCENTER);

		// Draws the previous values.
		ListIterator<String> valueIterator = this.model.listIterator(currentVisibleIndex);
		int previousValueCount = maxItemOnSideCount;
		while (previousValueCount-- > 0 && valueIterator.hasPrevious()) {
			y -= lineHeight;
			float fontRatio = computeFontRatio(y, remainingHeight);
			g.setColor(computeFontColor(y, remainingHeight, foregroundColor, backgroundColor));
			drawString(g, font, valueIterator.previous(), x, y, Alignment.HCENTER_VCENTER, fontRatio);
		}

		// Draws the next values.
		valueIterator = this.model.listIterator(currentVisibleIndex);
		int nextValueCount = maxItemOnSideCount;
		y = currentValueY;
		while (nextValueCount-- > 0 && valueIterator.hasNext()) {
			y += lineHeight;
			float fontRatio = computeFontRatio(y, remainingHeight);
			g.setColor(computeFontColor(y, remainingHeight, foregroundColor, backgroundColor));
			drawString(g, font, valueIterator.next(), x, y, Alignment.HCENTER_VCENTER, fontRatio);
		}

		// Draws the horizontal lines.
		g.setColor(GradientHelper.blendColors(foregroundColor, backgroundColor, 0.9f));

		y = (remainingHeight >> 1) - (lineHeight >> 1);
		Painter.drawHorizontalLine(g, 0, y - 1, width);
		Painter.drawHorizontalLine(g, 0, y, width);

		y = (remainingHeight >> 1) + (lineHeight >> 1);
		Painter.drawHorizontalLine(g, 0, y - 1, width);
		Painter.drawHorizontalLine(g, 0, y, width);
	}

	private void drawString(GraphicsContext g, Font font, String string, int anchorX, int anchorY, int alignment) {
		int x = Alignment.computeLeftX(font.stringWidth(string), anchorX, alignment);
		int y = Alignment.computeTopY(font.getHeight(), anchorY, alignment);
		Painter.drawString(g, font, string, x, y);
	}

	private void drawString(GraphicsContext g, Font font, String string, int anchorX, int anchorY, int alignment,
			float fontRatio) {
		int x = Alignment.computeLeftX((int) (font.stringWidth(string) * fontRatio), anchorX, alignment);
		int y = Alignment.computeTopY((int) (font.getHeight() * fontRatio), anchorY, alignment);
		Painter.drawString(g, font, string, x, y, fontRatio, fontRatio);
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
	public void computeContentOptimalSize(Size size) {
		int currentVisibleIndex = this.model.getCurrentIndex() + this.currentIndexDiff;
		String string = this.model.getValueAsString(currentVisibleIndex);

		Style style = getStyle();
		Font font = getDesktop().getFont(style);
		style.getTextStyle().computeContentSize(string, font, size);
	}

	private void stopAnimation() {
		if (this.motionAnimator != null) {
			this.motionAnimator.stop();
		}
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		if (type == Event.POINTER) {
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int pointerY = getRelativeY(pointer.getY());
			int action = Pointer.getAction(event);
			switch (action) {
			case Pointer.PRESSED:
				return onPointerPressed(pointerY);
			case Pointer.RELEASED:
				return onPointerReleased(pointerY);
			case Pointer.DRAGGED:
				return onPointerDragged(pointerY);
			}
		} else if (type == DesktopEventGenerator.EVENT_TYPE) {
			int action = DesktopEventGenerator.getAction(event);
			if (action == PointerEventDispatcher.EXITED) {
				return onPointerReleased(this.lastPointerCoordinate);
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
			this.spinOffset = computeSpinOffset(distanceDragged, this.spinOffset);
			this.currentIndexDiff = computeIndexDiff(this.pressPointerCoordinate - pointerCoordinate);
			this.lastPointerCoordinate = pointerCoordinate;
			requestRender();
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

	private boolean onPointerReleased(int pointerCoordinate) {
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
			if (now - this.lastPointerTime > RELEASE_WITH_NO_MOVE_DELAY) {
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
				requestRender();
			}

			@Override
			public void step(int value) {
				Wheel wheel = Wheel.this;
				wheel.getGroup().setWheelActive(wheel, true);
				wheel.spinOffset = computeSpinOffset(value, 0);
				wheel.currentIndexDiff = computeIndexDiff(-value);
				requestRender();
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
		Timer timer = ServiceFactory.getRequiredService(Timer.class);
		this.motionAnimator.start(timer, ANIMATION_PERIOD);
	}
}
