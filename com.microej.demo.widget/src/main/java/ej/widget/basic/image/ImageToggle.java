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
import ej.mwt.util.Alignment;
import ej.mwt.util.Size;
import ej.widget.basic.Box;

/**
 * A toggle using some images to render.
 */
public abstract class ImageToggle extends Box {

	@Override
	protected void renderContent(GraphicsContext g, int contentWidth, int contentHeight) {
		Style style = getStyle();

		g.setColor(style.getColor());

		Image image;
		if (isChecked()) {
			image = getCheckedImage();
		} else {
			image = getUncheckedImage();
		}
		int x = Alignment.computeLeftX(image.getWidth(), 0, contentWidth, Alignment.HCENTER);
		int y = Alignment.computeTopY(image.getHeight(), 0, contentHeight, Alignment.VCENTER);
		Painter.drawImage(g, image, x, y);
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		Image checkedImage = getCheckedImage();
		Image uncheckedImage = getUncheckedImage();
		int width = Math.max(checkedImage.getWidth(), uncheckedImage.getWidth());
		int height = Math.max(checkedImage.getHeight(), uncheckedImage.getHeight());
		size.setSize(width, height);
	}

	private Image getCheckedImage() {
		return ImageHelper.loadImageFromTheme(getCheckedImagePath());
	}

	private Image getUncheckedImage() {
		return ImageHelper.loadImageFromTheme(getUncheckedImagePath());
	}

	/**
	 * Gets the path of the image corresponding to the checked state of the toggle.
	 *
	 * @return the path of the image corresponding to the checked state of the toggle.
	 */
	protected abstract String getCheckedImagePath();

	/**
	 * Gets the path of the image corresponding to the unchecked state of the toggle.
	 *
	 * @return the path of the image corresponding to the unchecked state of the toggle.
	 */
	protected abstract String getUncheckedImagePath();

}
