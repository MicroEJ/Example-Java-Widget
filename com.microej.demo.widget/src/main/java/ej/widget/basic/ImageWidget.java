/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic;

import ej.annotation.Nullable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.ResourceImage;
import ej.mwt.Widget;
import ej.mwt.style.Style;
import ej.mwt.util.Size;
import ej.widget.util.ImagePainter;

/**
 * A widget that displays an image.
 * <p>
 * The widget holds a path to the image. The actual image is allocated only when the widget is attached. It is also
 * closed when the widget is detached.
 */
public class ImageWidget extends Widget {

	private final String imagePath;

	@Nullable
	private ResourceImage image;

	/**
	 * Creates an image widget with the resource path of the image to display.
	 *
	 * @param imagePath
	 *            the resource path of the image to display.
	 */
	public ImageWidget(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	protected void onAttached() {
		super.onAttached();

		this.image = ResourceImage.loadImage(this.imagePath);
	}

	@Override
	protected void onDetached() {
		super.onDetached();

		ResourceImage image = this.image;
		if (image != null) {
			image.close();
			this.image = null;
		}
	}

	@Override
	protected void renderContent(GraphicsContext g, Size size) {
		ResourceImage image = this.image;
		if (image != null) {
			Style style = getStyle();
			ImagePainter.drawImageInArea(g, image, 0, 0, size.getWidth(), size.getHeight(),
					style.getHorizontalAlignment(), style.getVerticalAlignment());
		}
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		ResourceImage image = this.image;
		if (image != null) {
			ImagePainter.computeOptimalSize(image, size);
		} else {
			size.setSize(0, 0);
		}
	}
}
