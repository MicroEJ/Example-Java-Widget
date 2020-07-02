/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.basic;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.MicroUIException;
import ej.microui.display.Display;
import ej.microui.display.GraphicsContext;
import ej.microui.display.ResourceImage;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.widget.basic.ImageWidget;
import ej.widget.test.framework.ContainerValidateHelper;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 *
 */
public class ImageWidgetTest extends Test {

	private static final String IMAGE_PATH = "/images/cowboy.png";

	public static void main(String[] args) {
		TestHelper.launchTest(new ImageWidgetTest());
	}

	@Override
	public void run(Display display) {
		try {
			validation(IMAGE_PATH);
			render(IMAGE_PATH);
		} catch (MicroUIException e) {
			e.printStackTrace();
		}
	}

	private void validation(String path) {
		try (ResourceImage displayImage = ResourceImage.loadImage(IMAGE_PATH)) {
			int displayImageWidth = displayImage.getWidth();
			int displayImageHeight = displayImage.getHeight();

			ImageWidget image = new ImageWidget(path);
			ContainerValidateHelper container = new ContainerValidateHelper(image);

			Desktop desktop = new Desktop();
			desktop.setWidget(container);
			desktop.requestShow();
			TestHelper.waitForAllEvents();

			container.computeChildOptimalSize(image, Widget.NO_CONSTRAINT, Widget.NO_CONSTRAINT);
			checkOptimalSize("Validate image none", image, displayImageWidth, displayImageHeight);

			container.computeChildOptimalSize(image, displayImageWidth * 2, displayImageHeight * 2);
			checkOptimalSize("Validate image double", image, displayImageWidth, displayImageHeight);

			container.computeChildOptimalSize(image, displayImageWidth / 2, displayImageHeight / 2);
			checkOptimalSize("Validate image half", image, displayImageWidth / 2, displayImageHeight / 2);
		}
	}

	private void render(String path) {
		try (ResourceImage displayImage = ResourceImage.loadImage(IMAGE_PATH)) {
			Display display = Display.getDisplay();
			GraphicsContext g = display.getGraphicsContext();

			ImageWidget image = new ImageWidget(path);

			Desktop desktop = new Desktop();
			desktop.setWidget(image);
			desktop.requestShow();
			TestHelper.waitForAllEvents();

			checkImage("Render", g, 0, 0, displayImage);
		}
		// TODO test alignment
	}

	private void checkOptimalSize(String message, Widget widget, int expectedWidth, int expectedHeight) {
		checkSize(message, widget.getWidth(), widget.getHeight(), expectedWidth, expectedHeight);
	}

	private void checkSize(String message, int width, int height, int expectedWidth, int expectedHeight) {
		CheckHelper.check(getClass(), message + " width", width, expectedWidth);
		CheckHelper.check(getClass(), message + " height", height, expectedHeight);
	}

	private void checkImage(String message, GraphicsContext g, int x, int y, ej.microui.display.Image displayImage) {
		Display display = Display.getDisplay();
		int imageWidth = displayImage.getWidth();
		int imageHeight = displayImage.getHeight();

		// int i = 0;
		for (int i = -1; ++i < imageWidth;) {
			for (int j = -1; ++j < imageHeight;) {
				int readPixel = g.readPixel(x + i, y + j) & 0xffffff;
				int imagePixel = display.getDisplayColor(displayImage.readPixel(i, j));
				// System.out.println("ImageBackgroundTest.checkImage() " + Integer.toHexString(readPixel) + " "
				// + Integer.toHexString(imagePixel));
				CheckHelper.check(getClass(), message + " pixel " + i + "," + j, readPixel, imagePixel);
			}
		}
	}

}
