/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.basic;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.widget.basic.RenderableLabel;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 *
 */
public class RenderableLabelTest extends Test {

	private static final String TEXT = "Text";

	public static void main(String[] args) {
		TestHelper.launchTest(new RenderableLabelTest());
	}

	@Override
	public void run(Display display) {
		RenderableLabel label = new RenderableLabel();

		checkText("New empty label", label, "");

		label.setText(TEXT);
		checkText("Set text", label, TEXT);

		label.setText("");
		checkText("Set text 2", label, "");

		label.updateText(TEXT);
		checkText("Update text", label, TEXT);

		label.updateText("");
		checkText("Update text 2", label, "");

		label = new RenderableLabel(TEXT);
		checkText("New text label", label, TEXT);
	}

	private void checkText(String message, RenderableLabel label, String expectedValue) {
		CheckHelper.check(getClass(), message, label.getText(), expectedValue);
	}

}
