/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic.image;

import ej.microui.display.GraphicsContext;
import ej.microui.display.Image;
import ej.microui.display.Painter;
import ej.mwt.style.Style;
import ej.mwt.util.Size;
import ej.widget.basic.AbstractProgress;
import ej.widget.model.BoundedRangeModel;

/**
 * A progress using some images to render.
 */
public class ImageProgress extends AbstractProgress {

	private boolean horizontal;

	/**
	 * Creates a progress with the given model.
	 *
	 * @param model
	 *            the model to use.
	 */
	public ImageProgress(BoundedRangeModel model) {
		super(model);
		this.horizontal = true;
	}

	/**
	 * Creates a progress with a default bounded range as model.
	 *
	 * @param min
	 *            the minimum value of the progress bar.
	 * @param max
	 *            the maximum value of the progress bar.
	 * @param initialValue
	 *            the initial value of the progress bar.
	 * @see ej.widget.model.DefaultBoundedRangeModel
	 */
	public ImageProgress(int min, int max, int initialValue) {
		super(min, max, initialValue);
		this.horizontal = true;
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
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();
		g.setColor(style.getColor());

		float percentComplete = getPercentComplete();

		if (this.horizontal) {
			Image bar = loadBarImage(true);
			int barWidth = bar.getWidth();
			int barHeight = bar.getHeight();
			int barX = (contentWidth - barWidth) >> 1;
			int regionWidth = (int) (percentComplete * barWidth);
			int y = -barHeight >> 1;
			Painter.drawImageRegion(g, bar, 0, y, regionWidth, barHeight, barX, contentHeight >> 1);
		} else {
			Image bar = loadBarImage(false);
			int barWidth = bar.getWidth();
			int barHeight = bar.getHeight();
			int barY = (contentHeight - barHeight) >> 1;
			int regionHeight = (int) (percentComplete * barHeight);
			int x = -barWidth >> 1;
			Painter.drawImageRegion(g, bar, x, 0, bar.getWidth(), regionHeight, contentWidth >> 1, barY);
		}
	}

	private Image loadBarImage(boolean horizontal) {
		String imagePath = horizontal ? ImageHelper.getTheme().getProgressBarHorizontalPath()
				: ImageHelper.getTheme().getProgressBarVerticalPath();
		return ImageHelper.loadImageFromTheme(imagePath);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Image bar = loadBarImage(this.horizontal);
		size.setSize(bar.getWidth(), bar.getHeight());
	}

}
