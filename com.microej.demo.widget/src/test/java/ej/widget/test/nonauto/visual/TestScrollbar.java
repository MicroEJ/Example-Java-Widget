/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.nonauto.visual;

import ej.microui.MicroUI;
import ej.mwt.Desktop;
import ej.widget.basic.drawing.Scrollbar;

/**
 *
 */
public class TestScrollbar {

	private static final int STEP = 5;

	public static void main(String[] args) {
		MicroUI.start();
		Desktop desktop = new Desktop();

		Scrollbar scrollbar = new Scrollbar(100);
		scrollbar.setHorizontal(true);
		desktop.setWidget(scrollbar);
		desktop.requestShow();

		scrollbar.show();

		boolean increment = true;
		while (true) {
			int currentStep = scrollbar.getValue();
			if (increment) {
				currentStep += STEP;
				if (currentStep >= scrollbar.getMaximum()) {
					increment = false;
				}
			} else {
				currentStep -= STEP;
				if (currentStep <= 0) {
					increment = true;
				}
			}
			scrollbar.setValue(currentStep);

			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
			}
		}
	}
}
