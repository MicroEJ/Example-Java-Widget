/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.nonauto.visual;

import ej.microui.MicroUI;
import ej.microui.display.BufferedImage;
import ej.microui.display.Colors;
import ej.microui.display.GraphicsContext;
import ej.microui.display.Painter;
import ej.mwt.Desktop;
import ej.widget.basic.ImageView;

/**
 *
 */
public class TestImage {

	public static void main(String[] args) {
		MicroUI.start();
		Desktop desktop = new Desktop();

		ej.microui.display.Image source = createImage();
		ImageView image = new ImageView(source);
		// ImagePath image = new ImagePath("/images/cowboy.png");
		desktop.setWidget(image);
		desktop.requestShow();
	}

	private static ej.microui.display.Image createImage() {
		int imageSize = 128;
		BufferedImage source = new BufferedImage(imageSize, imageSize);
		GraphicsContext g = source.getGraphicsContext();
		g.setColor(Colors.YELLOW);
		Painter.fillRectangle(g, 0, 0, imageSize, imageSize);
		return source;
	}
}
