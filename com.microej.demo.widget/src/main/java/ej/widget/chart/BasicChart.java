/*
 * Java
 *
 * Copyright 2016 IS2T. All rights reserved.
 * IS2T PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package ej.widget.chart;

import ej.animation.Animation;
import ej.animation.Animator;
import ej.components.dependencyinjection.ServiceLoaderFactory;
import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.motion.Motion;
import ej.motion.linear.LinearMotion;
import ej.mwt.MWT;
import ej.style.Style;
import ej.style.container.Rectangle;
import ej.style.util.ElementAdapter;
import ej.style.util.StyleHelper;
import ej.widget.animation.AnimationListener;
import ej.widget.animation.AnimationListenerRegistry;

/** IPR start **/

/**
 * Represents a chart with basic functionality.
 */
public abstract class BasicChart extends Chart implements Animation, AnimationListener {

	protected static final int LEFT_PADDING = 30;

	private static final int APPARITION_DURATION = 300;
	private static final int APPARITION_STEPS = 100;

	private static final int SELECTED_VALUE_PADDING = 5;

	public static final String CLASS_SELECTOR_SELECTED_VALUE = "chart-selected-value";

	/**
	 * Elements
	 */
	private final ElementAdapter selectedValueElement;

	/**
	 * Animation
	 */
	private Motion motion;
	private int currentApparitionStep;

	/**
	 * Constructor
	 */
	public BasicChart() {
		super();
		this.selectedValueElement = new ElementAdapter();
		this.selectedValueElement.addClassSelector(CLASS_SELECTOR_SELECTED_VALUE);
	}

	/**
	 * Animation
	 */
	@Override
	public void showNotify() {
		super.showNotify();
		if (isEnabled()) {
			this.currentApparitionStep = 0;
		} else {
			this.currentApparitionStep = APPARITION_STEPS;
		}
		AnimationListenerRegistry.register(this);
	}

	@Override
	public void hideNotify() {
		Animator animator = ServiceLoaderFactory.getServiceLoader().getService(Animator.class);
		animator.stopAnimation(this);
		AnimationListenerRegistry.unregister(this);
		super.hideNotify();
	}

	@Override
	public void onStartAnimation() {
	}

	@Override
	public void onStopAnimation() {
		this.motion = new LinearMotion(0, APPARITION_STEPS, APPARITION_DURATION);
		Animator animator = ServiceLoaderFactory.getServiceLoader().getService(Animator.class);
		animator.startAnimation(BasicChart.this);
	}

	@Override
	public boolean tick(long currentTimeMillis) {
		this.currentApparitionStep = this.motion.getCurrentValue();
		repaint();
		return !this.motion.isFinished();
	}

	protected float getAnimationRatio() {
		return (float) this.currentApparitionStep / APPARITION_STEPS;
	}

	/**
	 * Handle pointer events
	 */
	@Override
	public boolean handleEvent(int event) {
		if (Event.getType(event) == Event.POINTER) {
			Rectangle margin = new Rectangle();
			getStyle().getMargin().unwrap(margin);

			Pointer pointer = (Pointer) Event.getGenerator(event);
			int pointerX = pointer.getX() - getAbsoluteX() - margin.getX();
			int pointerY = pointer.getY() - getAbsoluteY() - margin.getY();

			int action = Pointer.getAction(event);
			switch (action) {
			case Pointer.PRESSED:
			case Pointer.DRAGGED:
				onPointerPressed(pointerX, pointerY);
				return true;
			}
		}
		return super.handleEvent(event);
	}

	/**
	 * Handles pointer pressed events
	 */
	private void onPointerPressed(int pointerX, int pointerY) {
		int xStart = getContentX();
		int xEnd = xStart + getContentWidth();
		if (pointerX >= xStart && pointerX < xEnd) {
			int selectedPoint = getPoints().size() * (pointerX - xStart) / (xEnd - xStart);
			selectPoint(new Integer(selectedPoint));
		} else {
			selectPoint(null);
		}
	}

	/**
	 * Render scale
	 */
	protected void renderScale(GraphicsContext g, Style style, Rectangle bounds, float topValue) {
		Font font = StyleHelper.getFont(style);
		int fontHeight = font.getHeight();

		int yBarBottom = getBarBottom(fontHeight, bounds);
		int yBarTop = getBarTop(fontHeight, bounds);
		int xScale = LEFT_PADDING - fontHeight / 2;

		g.setFont(font);
		g.setColor(style.getForegroundColor());

		// draw values and lines
		int numScaleValues = getScale().getNumValues();
		for (int i = 0; i < numScaleValues + 1; i++) {
			float scaleValue = topValue * i / numScaleValues;
			String scaleString = getFormat().formatShort(scaleValue);
			int yScale = yBarBottom + (yBarTop - yBarBottom) * i / numScaleValues;

			g.drawString(scaleString, xScale, yScale, GraphicsContext.RIGHT | GraphicsContext.VCENTER);
			g.setStrokeStyle(GraphicsContext.DOTTED);
			g.drawLine(LEFT_PADDING, yScale, bounds.getWidth(), yScale);
		}

		// draw unit
		g.drawString(getUnit(), xScale, 0, GraphicsContext.RIGHT | GraphicsContext.TOP);
	}

	/**
	 * Render selected point value
	 */
	protected void renderSelectedPointValue(GraphicsContext g, Style style, Rectangle bounds) {
		ChartPoint selectedPoint = getSelectedPoint();
		if (selectedPoint != null) {
			String labelInfoString = selectedPoint.getFullName();
			float labelValue = selectedPoint.getValue();
			String labelValueString = getFormat().formatLong(labelValue);
			String labelString = labelInfoString + " : " + labelValueString;
			if (getUnit() != null) {
				labelString += " " + getUnit();
			}

			Style labelStyle = this.selectedValueElement.getStyle();
			Font labelFont = StyleHelper.getFont(labelStyle);

			int labelW = labelFont.stringWidth(labelString) + 2 * SELECTED_VALUE_PADDING;
			int labelX = (bounds.getWidth() - labelW) / 2;
			int labelY = 0;

			g.setFont(labelFont);
			g.setColor(labelStyle.getForegroundColor());
			g.drawString(labelString, labelX + SELECTED_VALUE_PADDING, labelY,
					GraphicsContext.LEFT | GraphicsContext.TOP);
		}
	}

	@Override
	public Rectangle validateContent(Style style, Rectangle bounds) {
		int height = bounds.getHeight();
		int width = bounds.getWidth();
		int fontHeight = StyleHelper.getFont(style).getHeight();
		if (height == MWT.NONE) {
			height = 4 * fontHeight;
		}
		if (width == MWT.NONE) {
			width = LEFT_PADDING;
		}
		return new Rectangle(0, 0, width, height);
	}

	@Override
	protected void setBoundsContent(Rectangle bounds) {
		// do nothing
	}

	/**
	 * Gets the top position of the chart content
	 */
	protected int getBarTop(int fontHeight, Rectangle bounds) {
		return fontHeight + 5;
	}

	/**
	 * Gets the bottom position of the chart content
	 */
	protected int getBarBottom(int fontHeight, Rectangle bounds) {
		return bounds.getHeight() - fontHeight - fontHeight / 5;
	}

	/**
	 * Gets content X
	 */
	protected abstract int getContentX();

	/**
	 * Gets content width
	 */
	protected abstract int getContentWidth();
}

/** IPR end **/
