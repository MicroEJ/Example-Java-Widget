/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.chart;

import com.microej.demo.widget.style.ClassSelectors;

import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.microui.event.Event;
import ej.microui.event.generator.Pointer;
import ej.motion.Motion;
import ej.motion.quart.QuartEaseInOutMotion;
import ej.mwt.Widget;
import ej.mwt.animation.Animation;
import ej.mwt.animation.Animator;
import ej.mwt.style.Style;
import ej.mwt.style.container.Alignment;
import ej.mwt.style.util.StyleHelper;
import ej.mwt.util.Rectangle;
import ej.mwt.util.Size;
import ej.service.ServiceFactory;
import ej.widget.ElementAdapter;

/**
 * Represents a chart with basic functionality.
 */
public abstract class BasicChart extends Chart implements Animation {

	static final int LEFT_PADDING = 30;

	private static final int APPARITION_DURATION = 300;
	private static final int APPARITION_STEPS = 100;

	private static final int SELECTED_VALUE_PADDING = 5;

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
		this.selectedValueElement.addClassSelector(ClassSelectors.SELECTED_VALUE);
	}

	/**
	 * Animation
	 */
	@Override
	protected void onShown() {
		super.onShown();
		if (isEnabled()) {
			this.currentApparitionStep = 0;
		} else {
			this.currentApparitionStep = APPARITION_STEPS;
		}
		this.motion = new QuartEaseInOutMotion(0, APPARITION_STEPS, APPARITION_DURATION);
		Animator animator = ServiceFactory.getService(Animator.class);
		animator.startAnimation(BasicChart.this);
	}

	@Override
	protected void onHidden() {
		Animator animator = ServiceFactory.getService(Animator.class);
		animator.stopAnimation(this);
		super.onHidden();
	}

	@Override
	public boolean tick(long currentTimeMillis) {
		this.currentApparitionStep = this.motion.getCurrentValue();
		repaint();
		return !this.motion.isFinished();
	}

	/**
	 * Gets the animation ratio.
	 *
	 * @return the animation ratio.
	 */
	protected float getAnimationRatio() {
		return (float) this.currentApparitionStep / APPARITION_STEPS;
	}

	/**
	 * Handle pointer events
	 */
	@Override
	public boolean handleEvent(int event) {
		if (Event.getType(event) == Event.POINTER) {
			Rectangle margin = new Rectangle(0, 0, 0, 0);
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
	 * Render scale.
	 *
	 * @param g
	 *            the graphics context.
	 * @param style
	 *            the chart style.
	 * @param size
	 *            the chart bounds.
	 * @param topValue
	 *            the value on the top of the chart.
	 */
	protected void renderScale(GraphicsContext g, Style style, Size size, float topValue) {
		Font font = StyleHelper.getFont(style);
		int fontHeight = font.getHeight();

		int yBarBottom = getBarBottom(fontHeight, size);
		int yBarTop = getBarTop(fontHeight, size);
		int xScale = LEFT_PADDING - fontHeight / 2;

		g.setColor(style.getForegroundColor());

		// draw values and lines
		int numScaleValues = getScale().getNumValues();
		for (int i = 0; i < numScaleValues + 1; i++) {
			float scaleValue = topValue * i / numScaleValues;
			String scaleString = getFormat().formatShort(scaleValue);
			int yScale = yBarBottom + (yBarTop - yBarBottom) * i / numScaleValues;

			drawString(g, font, scaleString, xScale, yScale, Alignment.RIGHT_VCENTER);
			Painter.drawLine(g, LEFT_PADDING, yScale, size.getWidth(), yScale);
		}

		// draw unit
		drawString(g, font, getUnit(), xScale, 0, Alignment.RIGHT_TOP);
	}

	/**
	 * Render string considering alignment.
	 *
	 * @param g
	 *            the graphics context.
	 * @param font
	 *            the font to use.
	 * @param string
	 *            the string to draw.
	 * @param anchorX
	 *            the x anchor.
	 * @param anchorY
	 *            the y anchor.
	 * @param alignment
	 *            the string alignment.
	 */
	protected void drawString(GraphicsContext g, Font font, String string, int anchorX, int anchorY, int alignment) {
		int x = Alignment.computeLeftX(font.stringWidth(string), anchorX, alignment);
		int y = Alignment.computeTopY(font.getHeight(), anchorY, alignment);
		Painter.drawString(g, font, string, x, y);
	}

	/**
	 * Render selected point value.
	 *
	 * @param g
	 *            the graphics context.
	 * @param style
	 *            the chart style.
	 * @param size
	 *            the chart size.
	 */
	protected void renderSelectedPointValue(GraphicsContext g, Style style, Size size) {
		ChartPoint selectedPoint = getSelectedPoint();
		if (selectedPoint != null) {
			String labelInfoString = selectedPoint.getFullName();
			float labelValue = selectedPoint.getValue();
			String labelValueString = getFormat().formatLong(labelValue);
			String labelString = labelInfoString + " : " + labelValueString; //$NON-NLS-1$
			if (getUnit() != null) {
				labelString += " " + getUnit(); //$NON-NLS-1$
			}

			Style labelStyle = this.selectedValueElement.getStyle();
			Font labelFont = StyleHelper.getFont(labelStyle);

			int labelW = labelFont.stringWidth(labelString) + 2 * SELECTED_VALUE_PADDING;
			int labelX = (size.getWidth() - labelW) / 2;
			int labelY = 0;

			g.setColor(labelStyle.getForegroundColor());
			Painter.drawString(g, labelFont, labelString, labelX + SELECTED_VALUE_PADDING, labelY);
		}
	}

	@Override
	protected void computeContentOptimalSize(Size availableSize) {
		Style style = getStyle();
		initializePointsStyle();
		this.selectedValueElement.initializeStyle();

		int height = availableSize.getHeight();
		int width = availableSize.getWidth();
		int fontHeight = StyleHelper.getFont(style).getHeight();
		if (height == Widget.NO_CONSTRAINT) {
			height = 4 * fontHeight;
		}
		if (width == Widget.NO_CONSTRAINT) {
			width = LEFT_PADDING;
		}
		availableSize.setSize(width, height);
	}

	@Override
	protected void layOutChildren(int contentWidth, int contentHeight) {
		// do nothing
	}

	/**
	 * Gets the top position of the chart content.
	 *
	 * @param fontHeight
	 *            the font height.
	 * @param size
	 *            the chart bounds.
	 * @return the top position of the chart content.
	 */
	protected int getBarTop(int fontHeight, Size size) {
		return fontHeight + 5;
	}

	/**
	 * Gets the bottom position of the chart content.
	 *
	 * @param fontHeight
	 *            the font height.
	 * @param size
	 *            the chart size.
	 * @return the bottom position of the chart content.
	 */
	protected int getBarBottom(int fontHeight, Size size) {
		return size.getHeight() - fontHeight - fontHeight / 5;
	}

	/**
	 * Gets the content x coordinate.
	 *
	 * @return the content x coordinate.
	 */
	@Override
	protected abstract int getContentX();

	/**
	 * Gets the content width.
	 *
	 * @return the content width.
	 */
	@Override
	protected abstract int getContentWidth();
}
