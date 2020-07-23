/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic.picto;

import ej.microui.display.Font;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.motion.Motion;
import ej.motion.linear.LinearMotion;
import ej.mwt.style.Style;
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;
import ej.widget.basic.AbstractProgress;
import ej.widget.model.BoundedRangeModel;

/**
 * A progress using some pictos to render.
 */
public class PictoProgress extends AbstractProgress {

	private static final int PROGRESS_STEP_COUNT = 10;
	private static final long MOTION_DURATION = 500;

	private final Motion motion;
	private int indeterminateStep;

	/**
	 * Creates a progress bar with the given model.
	 *
	 * @param model
	 *            the model to use.
	 */
	public PictoProgress(BoundedRangeModel model) {
		super(model);
		this.motion = new LinearMotion(0, PROGRESS_STEP_COUNT, MOTION_DURATION);
	}

	/**
	 * Creates a progress bar with a default bounded range model as model.
	 *
	 * @param min
	 *            the minimum value of the progress bar.
	 * @param max
	 *            the maximum value of the progress bar.
	 * @param initialValue
	 *            the initial value of the progress bar.
	 * @see ej.widget.model.DefaultBoundedRangeModel
	 */
	public PictoProgress(int min, int max, int initialValue) {
		super(min, max, initialValue);
		this.motion = new LinearMotion(0, PROGRESS_STEP_COUNT, MOTION_DURATION);
	}

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		g.setColor(style.getColor());
		Font font = style.getFont();
		char character = getPicto();
		int x = Alignment.computeLeftX(font.charWidth(character), 0, contentWidth, Alignment.HCENTER);
		int y = Alignment.computeTopY(font.getHeight(), 0, contentHeight, Alignment.VCENTER);
		Painter.drawChar(g, character, font, x, y);
	}

	private char getPicto() {
		if (isIndeterminate()) {
			return getIndeterminatePicto();
		} else {
			return getProgressPicto(getPercentComplete());
		}
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Style style = getStyle();
		Font font = style.getFont();
		int width = font.charWidth(getPicto());
		int height = font.getHeight();
		size.setSize(width, height);
	}

	private char getProgressPicto(float percentComplete) {
		int step = Math.round(percentComplete * PROGRESS_STEP_COUNT);
		return (char) (Pictos.PROGRESS_0 + step);
	}

	private char getIndeterminatePicto() {
		return (char) (Pictos.PROGRESS_INDETERMINATE_0 + this.indeterminateStep);
	}

	@Override
	protected void indeterminateTick() {
		super.indeterminateTick();

		Motion motion = this.motion;
		int value = motion.getCurrentValue();
		if (motion.isFinished()) {
			motion.start();
		}

		this.indeterminateStep = value % PROGRESS_STEP_COUNT;
		requestRender();
	}

}
