/*
 * Copyright 2017-2021 MicroEJ Corp. All rights reserved.
 * Use of this source code is governed by a BSD-style license that can be found with this software.
 */
package com.microej.demo.widget.carousel.widget;

import ej.annotation.Nullable;
import ej.bon.Util;
import ej.bon.XMath;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.event.Event;
import ej.microui.event.generator.Buttons;
import ej.microui.event.generator.Pointer;
import ej.motion.Motion;
import ej.motion.linear.LinearFunction;
import ej.motion.quad.QuadEaseOutFunction;
import ej.mwt.Widget;
import ej.mwt.animation.Animation;
import ej.mwt.style.Style;
import ej.mwt.util.OutlineHelper;
import ej.mwt.util.Size;

/**
 * Represents a carousel.
 */
public class Carousel extends Widget {

	private static final float SPACING_RATIO = 0.35f; // entry spacing
	private static final float SIZE_FACTOR = 1.5f; // entry size factor
	private static final float SIZE_DISPLAY_THRESHOLD = 0.40f; // don't draw entry if lower

	private static final int GOTO_ANIM_STEPS = 1000;
	private static final int GOTO_ANIM_DURATION = 600;
	private static final float GOTO_ANIM_SPEED = 0.6f;

	private static final int DND_START_TIME = 500;
	private static final float DND_DRAG_SPEED = 3.0f;
	private static final int DND_ANIM_STEPS = 200;
	private static final long DND_ANIM_DURATION = 200;

	private static final int STOPPED_START_TIME = 80;

	private static final long RELEASE_WITH_NO_MOVE_DELAY = 80;

	private static final float EXPECTED_FPS = 15.0f;

	private final CarouselEntry[] entries;
	private final int entryWidth;
	private final int entryHeight;

	private final Animation repaintAnimation;
	private boolean stopped;

	private long lastDragTime;
	private int lastDragX;
	private int lastDragY;
	private long lastPressTime;
	private int lastPressX;
	private int currentDrag;
	private int startDragX;
	private int endDragX;
	private boolean noDrag;

	private final Motion gotoAnimMotion;
	private long gotoAnimStartTime;
	private int gotoAnimDistance;
	private int gotoAnimStep;

	private boolean dnd;
	private int dndIndex;
	private @Nullable CarouselEntry dndEntry;
	private float dndDragX;
	private final Motion dndAnimMotion;
	private long dndAnimStartTime;
	private int dndAnimDir;

	/**
	 * Constructor for the Carousel.
	 *
	 * @param entries
	 *            the carousel entries
	 * @param initialEntryIndex
	 *            the initial entry index
	 * @param entryWidth
	 *            the width of an entry
	 * @param entryHeight
	 *            the maximum height of an entry
	 */
	public Carousel(CarouselEntry[] entries, int initialEntryIndex, int entryWidth, int entryHeight) {
		this.entries = entries.clone();
		this.entryWidth = entryWidth;
		this.entryHeight = entryHeight;

		// init drag
		this.currentDrag = -initialEntryIndex * this.entryWidth;
		this.startDragX = 0;
		this.endDragX = 0;
		this.noDrag = false;

		// init goto anim
		this.gotoAnimMotion = new Motion(QuadEaseOutFunction.INSTANCE, 0, GOTO_ANIM_STEPS, GOTO_ANIM_DURATION);
		this.gotoAnimDistance = 0;
		this.gotoAnimStep = 0;

		// init DND
		this.dnd = false;
		this.dndIndex = -1;
		this.dndEntry = null;
		this.dndDragX = 0.0f;
		this.dndAnimMotion = new Motion(LinearFunction.INSTANCE, 0, DND_ANIM_STEPS, DND_ANIM_DURATION);
		this.dndAnimDir = 0;

		// init repaint task
		this.repaintAnimation = new Animation() {
			@Override
			public boolean tick(long currentTimeMillis) {
				repaintTick();
				return true;
			}
		};
		this.stopped = false;
	}

	/**
	 * Gets (a clone of) the carousel entries.
	 *
	 * @return the carousel entries
	 */
	public CarouselEntry[] getEntries() {
		return this.entries.clone();
	}

	/**
	 * Scrolls the carousel to the given entry.
	 *
	 * @param entryIndex
	 *            the entry index to scroll to
	 * @param anim
	 *            whether we should use an animation or scroll there directly
	 */
	public void scroll(int entryIndex, boolean anim) {
		stopGotoAnim();

		int destination = -entryIndex * this.entryWidth;
		if (anim) {
			handleGoto(destination - this.currentDrag);
		} else {
			this.currentDrag = destination;
		}
	}

	/**
	 * Gets the current entry (the entry on top).
	 *
	 * @return the current entry
	 */
	public int getCurrentEntry() {
		int totalDrag = getTotalDrag();
		return getEntryAtDrag(totalDrag);
	}

	@Override
	protected void onShown() {
		super.onShown();
		// start the repaint task
		getDesktop().getAnimator().startAnimation(this.repaintAnimation);
	}

	private void repaintTick() {
		Size size = new Size(getWidth(), getHeight());
		OutlineHelper.applyOutlines(size, getStyle());
		int halfWidth = size.getWidth() / 2;
		// roll DND
		if (this.dnd) {
			this.dndDragX += (halfWidth - this.lastDragX) / EXPECTED_FPS * DND_DRAG_SPEED;
			this.dndDragX = capDragFloat(this.dndDragX, this.endDragX - this.startDragX + this.currentDrag);
		}

		// start DND
		long currentTime = System.currentTimeMillis();
		boolean dndNotStarted = !this.dnd && this.noDrag && currentTime - this.lastPressTime >= DND_START_TIME;
		if (dndNotStarted && (this.lastPressX > halfWidth - this.entryWidth / 2
				&& this.lastPressX < halfWidth + this.entryWidth / 2)) {
			startDND();
		}

		// check if the carousel is currently stopped
		boolean stopped = (currentTime - this.lastDragTime >= STOPPED_START_TIME && this.gotoAnimDistance == 0
				&& !this.dnd);

		// Repaint one more time for an optimized rendering.
		if (!this.stopped || !stopped) {
			this.stopped = stopped;
			requestRender();
		}
		this.stopped = stopped;
	}

	@Override
	protected void onHidden() {
		getDesktop().getAnimator().stopAnimation(this.repaintAnimation);
	}

	@Override
	public void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		int halfWidth = contentWidth / 2;
		int halfHeight = contentHeight / 2;

		// set text style
		Font font = style.getFont();

		// update goto anim step
		if (this.gotoAnimDistance != 0) {
			this.gotoAnimStep = this.gotoAnimMotion.getValue(Util.platformTimeMillis() - this.gotoAnimStartTime);
			if (this.gotoAnimStep >= GOTO_ANIM_STEPS) {
				stopGotoAnim();
			}
		}

		// calculate drag
		int totalDrag = getTotalDrag();

		// get top entry
		int topEntry = getEntryAtDrag(totalDrag);
		if (this.dnd) {
			updateDND(topEntry);
		}

		// draw entries
		g.setColor(style.getColor());
		for (int e = 0; e < topEntry; e++) {
			drawEntry(g, contentWidth, contentHeight, font, e, false, totalDrag);
		}
		for (int e = this.entries.length - 1; e > topEntry; e--) {
			drawEntry(g, contentWidth, contentHeight, font, e, false, totalDrag);
		}
		drawEntry(g, contentWidth, contentHeight, font, topEntry, true, totalDrag);

		// draw DND entry
		if (this.dnd) {
			int offsetX = this.lastDragX - halfWidth;
			int offsetY = this.lastDragY - halfHeight;
			final CarouselEntry dndEntry = this.dndEntry;
			if (dndEntry != null) {
				dndEntry.render(g, contentWidth, contentHeight, font, this.dnd, this.stopped, true, false, 1.0f,
						offsetX, offsetY, true);
			}
		}
	}

	private void drawEntry(GraphicsContext g, int contentWidth, int contentHeight, Font font, int entryIndex,
			boolean selected, int totalDrag) {
		// calculate position and size
		int offsetX = entryIndex * this.entryWidth + totalDrag;
		if (this.dnd && this.dndAnimDir != 0 && entryIndex == this.dndIndex - this.dndAnimDir) {
			int dndOffset = this.dndAnimDir * this.entryWidth;
			int dndAnimValue = this.dndAnimMotion.getValue(Util.platformTimeMillis() - this.dndAnimStartTime);
			offsetX -= dndOffset * dndAnimValue / DND_ANIM_STEPS;
			offsetX += dndOffset;
		}
		float factor = Math.abs((float) offsetX / contentWidth);
		float sizeRatio = 1.0f - (float) Math.pow(factor, SIZE_FACTOR);

		// draw if big enough
		if (sizeRatio > SIZE_DISPLAY_THRESHOLD) {
			// recalculate position
			offsetX = (int) ((SPACING_RATIO + sizeRatio) / 2.0f * offsetX);

			// draw entry
			CarouselEntry entry = this.entries[entryIndex];
			if (entry != null) {
				boolean clicked = (this.noDrag && !this.dnd && selected);
				if (clicked) {
					int halfWidth = getWidth() / 2;
					clicked &= (this.lastPressX > halfWidth - this.entryWidth / 2
							&& this.lastPressX < halfWidth + this.entryWidth / 2);
				}
				entry.render(g, contentWidth, contentHeight, font, this.dnd, this.stopped, clicked, selected, sizeRatio,
						offsetX, 0, false);
			}
		}
	}

	@Override
	public void computeContentOptimalSize(Size availableSize) {
		Style style = getStyle();
		Font font = style.getFont();
		int height = this.entryHeight + CarouselEntry.getImageTextMargin() + font.getHeight();
		availableSize.setHeight(height);

		int width = (int) (this.entryWidth * (SIZE_FACTOR * 2));
		availableSize.setWidth(width);
	}

	@Override
	public boolean handleEvent(int event) {
		int type = Event.getType(event);
		if (type == Pointer.EVENT_TYPE) {
			Pointer pointer = (Pointer) Event.getGenerator(event);
			int pointerX = pointer.getX() - getAbsoluteX();
			int pointerY = pointer.getY() - getAbsoluteY();
			int action = Buttons.getAction(event);

			long currentTime = System.currentTimeMillis();

			// update last press/drag vars
			if (action == Buttons.PRESSED || action == Pointer.DRAGGED) {
				if (action == Buttons.PRESSED || currentTime - this.lastDragTime >= RELEASE_WITH_NO_MOVE_DELAY) {
					this.lastPressTime = currentTime;
					this.lastPressX = pointerX;
				}
				this.lastDragTime = currentTime;
				this.lastDragX = pointerX;
				this.lastDragY = pointerY;
			}

			// handle drag
			handlePointer(action, pointerX);
			return true;
		}

		return super.handleEvent(event);
	}

	private void handlePointer(int action, int pointerX) {
		this.stopped = false;

		if (action == Buttons.PRESSED) {
			// stop goto animation
			stopGotoAnim();

			// start drag
			this.startDragX = pointerX;
			this.endDragX = pointerX;
			this.noDrag = true;
		} else if (action == Pointer.DRAGGED) {
			if (!this.dnd) {
				// update drag
				this.endDragX = capDrag(pointerX, -this.startDragX + this.currentDrag, false);
			}
			this.noDrag = false;
		} else if (action == Buttons.RELEASED) {
			handleRelease(pointerX);
		}
	}

	private void handleRelease(int pointerX) {
		if (this.dnd) {
			// stop DND
			stopDND();
		} else if (this.noDrag) {
			// this is just a click
			int halfWidth = getWidth() / 2;
			if (this.lastPressX > halfWidth - this.entryWidth / 2
					&& this.lastPressX < halfWidth + this.entryWidth / 2) {
				// clicked on top entry: notify its click listeners
				int totalDrag = getTotalDrag();
				int topEntry = getEntryAtDrag(totalDrag);
				this.entries[topEntry].handleClick();
			} else {
				// clicked on side entry: go to the target entry
				int distance = getWidth() / 2 - pointerX;
				handleGoto(distance);
			}
		} else {
			// end drag
			this.currentDrag += capDrag(this.endDragX - this.startDragX, this.currentDrag, false);
			long currentTime = System.currentTimeMillis();
			if (currentTime - this.lastDragTime < RELEASE_WITH_NO_MOVE_DELAY) {
				// throw the carousel!
				float speed = (float) (pointerX - this.lastPressX) / (currentTime - this.lastPressTime);
				int distance = (int) (speed * GOTO_ANIM_DURATION * GOTO_ANIM_SPEED);
				handleGoto(distance);
			} else {
				// just stop the carousel
				handleGoto(0);
			}
		}

		// reset drag
		this.startDragX = 0;
		this.endDragX = 0;
		this.noDrag = false;
	}

	private void handleGoto(int distance) {
		// start the goto animation with the given distance
		this.gotoAnimDistance = capDrag(distance, this.currentDrag, true);
		this.gotoAnimStep = 0;

		if (this.gotoAnimDistance != 0) {
			this.gotoAnimStartTime = Util.platformTimeMillis();
			this.stopped = false;
		}
	}

	private void stopGotoAnim() {
		this.currentDrag += this.gotoAnimDistance * this.gotoAnimStep / GOTO_ANIM_STEPS;
		this.gotoAnimDistance = 0;
	}

	private void startDND() {
		int totalDrag = getTotalDrag();
		int topEntry = getEntryAtDrag(totalDrag);

		this.dnd = true;
		this.dndIndex = topEntry;
		this.dndEntry = this.entries[topEntry];
		this.dndDragX = 0.0f;

		this.entries[topEntry] = null;
	}

	private void updateDND(int newDndIndex) {
		if (newDndIndex != this.dndIndex) {
			int dndDiff = newDndIndex - this.dndIndex;
			int dndDir = (dndDiff > 0 ? 1 : -1); // 1 or -1

			int numMoves = Math.abs(dndDiff);
			for (int i = 0; i < numMoves; i++) {
				int destIndex = this.dndIndex + i * dndDir;
				int srcIndex = destIndex + dndDir;
				this.entries[destIndex] = this.entries[srcIndex];
			}
			this.entries[newDndIndex] = null;

			this.dndIndex = newDndIndex;
			this.dndAnimDir = dndDir;
			this.dndAnimStartTime = Util.platformTimeMillis();
		}
	}

	private void stopDND() {
		this.entries[this.dndIndex] = this.dndEntry;

		this.currentDrag += this.dndDragX + this.endDragX - this.startDragX;
		handleGoto(0);

		this.dnd = false;
		this.dndIndex = -1;
		this.dndEntry = null;
		this.dndDragX = 0.0f;
	}

	private int getTotalDrag() {
		int totalDrag = this.currentDrag + (this.endDragX - this.startDragX);
		totalDrag += this.gotoAnimDistance * this.gotoAnimStep / GOTO_ANIM_STEPS;
		totalDrag += Math.round(this.dndDragX);
		return totalDrag;
	}

	private int capDrag(int value, int extra, boolean magnetize) {
		int total = value + extra;
		if (magnetize) {
			int closest = getEntryAtDrag(total);
			total = -closest * this.entryWidth;
		} else {
			int min = -((this.entries.length - 1) * this.entryWidth);
			int max = 0;
			total = XMath.limit(total, min, max);
		}
		return total - extra;
	}

	private float capDragFloat(float value, int extra) {
		float total = value + extra;
		float min = -((this.entries.length - 1) * this.entryWidth);
		float max = 0;
		total = XMath.limit(total, min, max);
		return total - extra;
	}

	private int getEntryAtDrag(int drag) {
		int closest = Math.round((float) -drag / this.entryWidth);
		closest = XMath.limit(closest, 0, this.entries.length - 1);
		return closest;
	}
}
