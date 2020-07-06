/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.nonauto.visual;

import ej.bon.Timer;
import ej.bon.TimerTask;
import ej.microui.MicroUI;
import ej.mwt.Desktop;
import ej.mwt.Widget;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.dimension.FixedDimension;
import ej.mwt.style.outline.UniformOutline;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.widget.basic.drawing.ProgressBar;

/**
 *
 */
public class TestProgressBar {

	public static void main(String[] args) {
		MicroUI.start();

		CascadingStylesheet stylesheet = new CascadingStylesheet();
		EditableStyle style = stylesheet.getSelectorStyle(new TypeSelector(ProgressBar.class));
		UniformOutline padding = new UniformOutline(3);
		style.setPadding(padding);
		style.setDimension(new FixedDimension(Widget.NO_CONSTRAINT, 20));

		final ProgressBar progressBar = new ProgressBar(0, 100, 50);
		progressBar.setIndeterminate(true);
		progressBar.setHorizontal(true);

		Desktop desktop = new Desktop();
		desktop.setStylesheet(stylesheet);
		desktop.setWidget(progressBar);
		desktop.requestShow();

		progressBar.setValue(progressBar.getMaximum());
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				int currentValue = progressBar.getValue();
				int maximum = progressBar.getMaximum();

				if (currentValue < maximum) {
					progressBar.setValue(currentValue + maximum / 100);
				} else {
					progressBar.reset();
				}
			}
		}, 100, 50);
	}
}
