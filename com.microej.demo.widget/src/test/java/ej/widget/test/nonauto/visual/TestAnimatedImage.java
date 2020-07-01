/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.nonauto.visual;

import ej.microui.MicroUI;
import ej.mwt.Desktop;
import ej.widget.basic.AnimatedImage;

/**
 *
 */
public class TestAnimatedImage {

	public static void main(String[] args) {
		MicroUI.start();
		Desktop desktop = new Desktop() {
			// @Override
			// public void render(GraphicsContext g) {
			// g.setColor(Colors.WHITE);
			// Painter.fillRectangle(g, 0, 0, getWidth(), getHeight());
			// }
		};

		// String[] images = { "/images/cowboy0.png", "/images/cowboy1.png", "/images/cowboy2.png",
		// "/images/cowboy3.png" };
		String images = "/images/cowboy0.png /images/cowboy1.png /images/cowboy2.png /images/cowboy3.png";
		// String images = "/images/cowboy0.png";
		// String images = "";
		AnimatedImage animatedImage = new AnimatedImage(images, 80);
		desktop.setWidget(animatedImage);
		desktop.requestShow();
	}

}