/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.basic.drawing;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.widget.basic.drawing.CircularProgressBar;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 *
 */
public class CircularProgressBarTest extends Test {

	public static void main(String[] args) {
		TestHelper.launchTest(new CircularProgressBarTest());
	}

	@Override
	public void run(Display display) {
		CircularProgressBar circularProgressBar = new CircularProgressBar(0, 100, 50);
		CheckHelper.check(getClass(), "Minimum", circularProgressBar.getMinimum(), 0);
		CheckHelper.check(getClass(), "Maximum", circularProgressBar.getMaximum(), 100);
		CheckHelper.check(getClass(), "Value", circularProgressBar.getValue(), 50);

		circularProgressBar.setValue(0);
		CheckHelper.check(getClass(), "Value", circularProgressBar.getValue(), 0);

		circularProgressBar.setValue(100);
		CheckHelper.check(getClass(), "Value", circularProgressBar.getValue(), 100);

		// Visual test with even and odd size.
		// StyledDesktop desktop = new StyledDesktop();
		// StyledPanel panel = new StyledPanel();
		// panel.setWidget(circularProgressBar);
		// panel.setBounds(10, 10, 51, 51);
		// panel.showUsingBounds(desktop);
		//
		// StyledPanel panel2 = new StyledPanel();
		// panel2.setWidget(new CircularProgressBar(0, 50, 50));
		// panel2.setBounds(120, 10, 50, 50);
		// panel2.showUsingBounds(desktop);
		// desktop.requestShow();
	}

}
