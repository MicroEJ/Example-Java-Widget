/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.update;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.mwt.Desktop;
import ej.widget.basic.Box;
import ej.widget.test.framework.Instrumented;
import ej.widget.test.framework.InstrumentedCheckBox;
import ej.widget.test.framework.InstrumentedRadioBox;
import ej.widget.test.framework.InstrumentedSwitchBox;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 * This test verifies that the style is not updated when a method is called but the state is not modified (typically
 * removing a class selector that is not set).
 */
public class UpdateTest2 extends Test {

	public static void main(String[] args) {
		TestHelper.launchTest(new UpdateTest2());
	}

	@Override
	public void run(Display display) {
		testBox(new InstrumentedCheckBox());
		testBox(new InstrumentedRadioBox());
		testBox(new InstrumentedSwitchBox());
	}

	private <T extends Box & Instrumented> void testBox(T element) {
		Desktop desktop = new Desktop();
		desktop.setWidget(element);
		desktop.requestShow();
		TestHelper.waitForAllEvents();
		element.reset();

		element.setPressed(false);
		CheckHelper.check(getClass(), "Set not pressed already not pressed", !element.isStyleUpdated());
		element.reset();

		element.setPressed(true);
		CheckHelper.check(getClass(), "Set pressed", element.isStyleUpdated());
		element.reset();

		element.setPressed(true);
		CheckHelper.check(getClass(), "Set pressed already pressed", !element.isStyleUpdated());
		element.reset();

		element.setPressed(false);
		CheckHelper.check(getClass(), "Set not pressed", element.isStyleUpdated());
		element.reset();
	}

}
