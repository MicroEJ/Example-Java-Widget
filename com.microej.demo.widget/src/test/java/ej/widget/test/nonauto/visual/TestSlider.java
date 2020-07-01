/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.nonauto.visual;

import ej.microui.MicroUI;
import ej.mwt.Desktop;
import ej.widget.basic.drawing.Slider;

/**
 *
 */
public class TestSlider {

	public static void main(String[] args) {
		MicroUI.start();
		Desktop desktop = new Desktop();

		Slider slider = new Slider(0, 100, 50);
		slider.setHorizontal(false);
		desktop.setWidget(slider);
		desktop.requestShow();
	}
}
