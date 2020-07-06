/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.container.split;

import ej.microui.display.Display;
import ej.widget.container.Split;
import ej.widget.container.util.LayoutOrientation;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 *
 */
public class TestSplitContainerNone extends Test {

	public static void main(String[] args) {
		TestHelper.launchTest(new TestSplitContainerNone());
	}

	@Override
	public void run(Display display) {
		testHorizontal();
		testVertical();
	}

	private void testHorizontal() {
		Split container = new Split(LayoutOrientation.HORIZONTAL, 0.5f);
		TestHelper.showAndWait(container, false);

		// get widgets size
		TestHelper.checkWidget(getClass(), "h container", container, 0, 0, 0, 0);

		TestHelper.hide(container);
		TestHelper.showAndWait(container, true);

		Display display = Display.getDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();
		// get widgets size
		TestHelper.checkWidget(getClass(), "h container", container, 0, 0, displayWidth, displayHeight);
	}

	private void testVertical() {
		Split container = new Split(LayoutOrientation.VERTICAL, 0.5f);
		TestHelper.showAndWait(container, false);

		// get widgets size
		TestHelper.checkWidget(getClass(), "v container", container, 0, 0, 0, 0);

		TestHelper.hide(container);
		TestHelper.showAndWait(container, true);

		Display display = Display.getDisplay();
		int displayWidth = display.getWidth();
		int displayHeight = display.getHeight();
		// get widgets size
		TestHelper.checkWidget(getClass(), "v container", container, 0, 0, displayWidth, displayHeight);
	}

}
