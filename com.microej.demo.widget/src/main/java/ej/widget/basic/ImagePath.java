/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.basic;

import ej.annotation.Nullable;
import ej.microui.display.GraphicsContext;
import ej.microui.display.ResourceImage;
import ej.mwt.style.Style;
import ej.mwt.util.Size;

/**
 * A widget that displays an image.
 * <p>
 * The widget holds a path to the image. The actual image is allocated only when the widget is attached. It is also
 * closed when the widget is detached.
 *
 * @see ej.microui.display.Image
 * @see #onAttached()
 * @see #onDetached()
 */
public class ImagePath extends AbstractImage {

	private String sourcePath;
	@Nullable
	private ResourceImage source;

	/**
	 * Creates an image with the path to the source to display.
	 *
	 * @param sourcePath
	 *            the path to the source to display.
	 */
	public ImagePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	/**
	 * Sets the source to display for this image without asking for a new render.
	 *
	 * @param sourcePath
	 *            the path to new source to display.
	 */
	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;

		if (isAttached()) {
			loadImageSource();
		}
	}

	@Override
	protected void onAttached() {
		super.onAttached();
		loadImageSource();
	}

	@Override
	protected void onDetached() {
		super.onDetached();
		updateImageSource(null);
	}

	private void loadImageSource() {
		ResourceImage image = ResourceImage.loadImage(this.sourcePath);
		updateImageSource(image);
	}

	private void updateImageSource(@Nullable ResourceImage newImage) {
		ResourceImage source = this.source;
		if (source != null) {
			source.close();
		}
		this.source = newImage;
	}

	@Override
	protected void renderContent(GraphicsContext g, Size size) {
		ResourceImage source = this.source;
		if (source != null) {
			Style style = getStyle();
			renderImage(g, style, size, source);
		}
	}

	@Override
	protected void computeContentOptimalSize(Size size) {
		ResourceImage source = this.source;
		if (source != null) {
			Style style = getStyle();
			computeImageSize(style, size, source);
		} else {
			size.setSize(0, 0);
		}
	}

}
