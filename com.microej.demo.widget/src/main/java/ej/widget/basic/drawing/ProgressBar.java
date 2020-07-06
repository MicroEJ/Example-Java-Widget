/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic.drawing;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.motion.Motion;
import ej.motion.linear.LinearMotion;
import ej.mwt.style.Style;
import ej.mwt.util.Size;
import ej.widget.basic.AbstractProgress;
import ej.widget.model.BoundedRangeModel;
import ej.widget.model.DefaultBoundedRangeModel;

/**
 * Implementation of a progress bar that can be horizontal or vertical.
 * <p>
 * The size of a progress bar is dependent on the font size.
 * <p>
 * This example shows a simple progress bar:
 *
 * <pre>
 * progressBar progressBar = new ProgressBar(0, 100, 50);
 * </pre>
 *
 * <img src="../doc-files/progressbar-simple.png" alt="Progress bar.">
 * <p>
 * This example shows a styled progress bar:
 *
 * <pre>
 * ProgressBar progressBar = new ProgressBar(0, 100, 50);
 *
 * CascadingStylesheet stylesheet = new CascadingStylesheet();
 * StyleHelper.setStylesheet(stylesheet);
 *
 * EditableStyle progressBarStyle = stylesheet.addRule(new TypeSelector(ProgressBar.class));
 * progressBarStyle.setBorder(new UniformRectangularBorder(Colors.BLACK, 2));
 * progressBarStyle.setColor(Colors.BLUE);
 * progressBarStyle.setBackground(new PlainRectangularBackground(Colors.SILVER));
 * progressBarStyle.setPadding(new UniformOutline(1));
 * </pre>
 *
 * <img src="../doc-files/progressbar-styled.png" alt="Styled progress bar.">
 */
public class ProgressBar extends AbstractProgress {

	private static final float STOP_MOTION_VALUE = 100f;
	private static final long MOTION_DURATION = 800;

	private boolean horizontal;
	private float indeterminateProgress;
	private final Motion motion;

	/**
	 * Creates a progress bar with the given model.
	 *
	 * @param model
	 *            the model to use.
	 */
	public ProgressBar(BoundedRangeModel model) {
		super(model);
		this.horizontal = true;
		this.motion = new LinearMotion(0, (int) STOP_MOTION_VALUE, MOTION_DURATION);
	}

	/**
	 * Creates a progress bar with a default bounded range as model.
	 *
	 * @param min
	 *            the minimum value of the progress bar.
	 * @param max
	 *            the maximum value of the progress bar.
	 * @param initialValue
	 *            the initial value of the progress bar.
	 */
	public ProgressBar(int min, int max, int initialValue) {
		this(new DefaultBoundedRangeModel(min, max, initialValue));
	}

	/**
	 * Sets the slider orientation: horizontal or vertical.
	 *
	 * @param horizontal
	 *            <code>true</code> to set the slider horizontal, <code>false</code> to set the slider vertical.
	 */
	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}

	@Override
	protected void renderContent(GraphicsContext g, Size size) {
		Style style = getStyle();
		int width = size.getWidth();
		int height = size.getHeight();

		g.setColor(style.getColor());

		int completeBarStartX;
		int completeBarStartY;
		int completeBarWidth;
		int completeBarHeight;

		if (this.horizontal) {
			completeBarStartY = 0;
			completeBarHeight = height;

			if (isIndeterminate()) {
				completeBarStartX = (int) (this.indeterminateProgress * width);
				completeBarWidth = width >> 1;
				int excess = (completeBarStartX + completeBarWidth + 1) - width;
				if (excess > 0) {
					Painter.fillRectangle(g, 0, 0, excess, completeBarHeight + 1);
				}
			} else {
				completeBarStartX = 0;
				completeBarWidth = (int) (getPercentComplete() * width);
			}
		} else {
			completeBarWidth = width;
			completeBarStartX = 0;

			if (isIndeterminate()) {
				completeBarStartY = (int) (this.indeterminateProgress * height);
				completeBarHeight = height >> 1;
				int excess = (completeBarStartY + completeBarHeight + 1) - height;
				if (excess > 0) {
					Painter.fillRectangle(g, 0, 0, completeBarWidth + 1, excess);
				}
			} else {
				completeBarStartY = 0;
				completeBarHeight = (int) (getPercentComplete() * height);
			}
		}

		// Draws complete part.
		Painter.fillRectangle(g, completeBarStartX, completeBarStartY, completeBarWidth + 1, completeBarHeight + 1);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		int referenceSize = style.getFont().getHeight() >> 1;
		int minimum = getMinimum();
		int maximum = getMaximum();
		int optimalWidth;
		int optimalHeight;
		if (this.horizontal) {
			optimalWidth = referenceSize + (maximum - minimum);
			optimalHeight = referenceSize;
		} else {
			optimalWidth = referenceSize;
			optimalHeight = referenceSize + (maximum - minimum);
		}
		size.setSize(optimalWidth, optimalHeight);
	}

	@Override
	protected void indeterminateTick() {
		super.indeterminateTick();

		Motion motion = this.motion;
		int value = motion.getCurrentValue();
		if (motion.isFinished()) {
			motion.start();
		}

		this.indeterminateProgress = value / STOP_MOTION_VALUE;
		requestRender();
	}

}
