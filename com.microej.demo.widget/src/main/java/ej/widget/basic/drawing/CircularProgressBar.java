/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic.drawing;

import ej.drawing.ShapePainter;
import ej.drawing.ShapePainter.Cap;
import ej.microui.display.GraphicsContext;
import ej.motion.Motion;
import ej.motion.linear.LinearMotion;
import ej.motion.quart.QuartEaseOutMotion;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;
import ej.widget.basic.AbstractProgress;
import ej.widget.model.BoundedRangeModel;
import ej.widget.model.DefaultBoundedRangeModel;

/**
 * Circular representation of a progress bar.
 * <p>
 * The size of a circular progress bar is dependent on the font size.
 * <p>
 * This example shows a simple circular progress bar:
 *
 * <pre>
 * CircularProgressBar circularProgressBar = new CircularProgressBar(0, 100, 50);
 * </pre>
 *
 * <img src="../doc-files/circularprogressbar-simple.png" alt="Simple circular progress bar.">
 * <p>
 * This example shows a styled circular progress bar:
 *
 * <pre>
 * CircularProgressBar circularProgressBar = new CircularProgressBar(0, 100, 50);
 *
 * CascadingStylesheet stylesheet = new CascadingStylesheet();
 * StyleHelper.setStylesheet(stylesheet);
 *
 * EditableStyle circularProgressBarStyle = stylesheet.addRule(new TypeSelector(CircularProgressBar.class));
 * circularProgressBarStyle.setColor(Colors.BLUE);
 * </pre>
 *
 * <img src="../doc-files/circularprogressbar-styled.png" alt="Styled circular progress bar.">
 */
public class CircularProgressBar extends AbstractProgress {

	// Indeterminate management.
	private static final int START_ANGLE = 90;
	private static final int FULL_ANGLE = 360;
	private static final int MAX_FILL_ANGLE = 300;
	private static final int MIN_FILL_ANGLE = 5;
	private static final long EASEOUT_MOTION_DURATION = 1200;
	private static final long LINEAR_MOTION_DURATION = 1400;

	private static final int THICKNESS = 2;

	private final Motion linearMotion;
	private final Motion easeOutMotion;
	private int indeterminateStartAngle;
	private int indeterminateArcAngle;
	private int currentStartAngle;
	private boolean moveStart;

	/**
	 * Creates a circular progress bar with the given model.
	 *
	 * @param model
	 *            the model to use.
	 */
	public CircularProgressBar(BoundedRangeModel model) {
		super(model);
		this.linearMotion = new LinearMotion(FULL_ANGLE, 0, LINEAR_MOTION_DURATION);
		this.easeOutMotion = new QuartEaseOutMotion(0, MAX_FILL_ANGLE, EASEOUT_MOTION_DURATION);
	}

	/**
	 * Creates a circular progress bar with a default bounded range as model.
	 *
	 * @param min
	 *            the minimum value of the progress bar.
	 * @param max
	 *            the maximum value of the progress bar.
	 * @param initialValue
	 *            the initial value of the progress bar.
	 */
	public CircularProgressBar(int min, int max, int initialValue) {
		this(new DefaultBoundedRangeModel(min, max, initialValue));
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		int diameter = Math.min(contentWidth, contentHeight);
		int shiftX = Alignment.computeLeftX(diameter, 0, contentWidth, style.getHorizontalAlignment());
		int shiftY = Alignment.computeTopY(diameter, 0, contentHeight, style.getVerticalAlignment());

		// Fills the complete part, from 90° anti-clockwise.
		int startAngle;
		int arcAngle;

		if (isIndeterminate()) {
			arcAngle = this.indeterminateArcAngle;
			startAngle = this.indeterminateStartAngle;
		} else {
			arcAngle = (int) (-FULL_ANGLE * getPercentComplete());
			startAngle = START_ANGLE;
		}

		g.setColor(style.getColor());
		int finalDiameter = diameter - THICKNESS - 3;
		if ((finalDiameter & 0x1) == 0x0) {
			finalDiameter -= 1;
		}
		ShapePainter.drawThickFadedCircleArc(g, shiftX + (THICKNESS / 2) + 1, shiftY + (THICKNESS / 2) + 1,
				finalDiameter, startAngle, arcAngle, THICKNESS, 1, Cap.ROUNDED, Cap.ROUNDED);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		int referenceSize = style.getFont().getHeight() << 1;
		size.setSize(referenceSize, referenceSize);
	}

	@Override
	protected void indeterminateTick() {
		super.indeterminateTick();

		Motion linearMotion = this.linearMotion;
		if (linearMotion.isFinished()) {
			linearMotion.start();
		}
		Motion easeOutMotion = this.easeOutMotion;
		if (easeOutMotion.isFinished()) {
			// System.out.println("switch");
			this.moveStart = !this.moveStart;
			if (!this.moveStart) {
				this.currentStartAngle += FULL_ANGLE - MAX_FILL_ANGLE;
			}
			easeOutMotion.start();
		}
		if (this.moveStart) {
			this.indeterminateArcAngle = -(MAX_FILL_ANGLE - easeOutMotion.getCurrentValue() + MIN_FILL_ANGLE);
			this.indeterminateStartAngle = this.currentStartAngle + -MAX_FILL_ANGLE - this.indeterminateArcAngle
					+ linearMotion.getCurrentValue();
		} else {
			this.indeterminateStartAngle = this.currentStartAngle + linearMotion.getCurrentValue();
			this.indeterminateArcAngle = -(MIN_FILL_ANGLE + easeOutMotion.getCurrentValue());
		}

		requestRender();
	}

}
