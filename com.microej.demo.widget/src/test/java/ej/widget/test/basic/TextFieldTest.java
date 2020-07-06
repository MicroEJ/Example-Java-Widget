/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.basic;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.widget.basic.TextField;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 *
 */
public class TextFieldTest extends Test {

	private static final String TEXT = "Text";
	private static final String PLACE_HOLDER = "Place Holder";

	public static void main(String[] args) {
		TestHelper.launchTest(new TextFieldTest());
	}

	@Override
	public void run(Display display) {
		TextField textField = new TextField();

		checkText("New empty label", textField, "", "");

		textField.setText(TEXT);
		checkText("Set text", textField, TEXT, "");

		textField.setText("");
		checkText("Set text 2", textField, "", "");

		textField.setPlaceHolder(PLACE_HOLDER);
		checkText("Set place holder", textField, "", PLACE_HOLDER);

		textField.setPlaceHolder("");
		checkText("Set place holder 2", textField, "", "");

		textField = new TextField(TEXT);
		checkText("New text label", textField, TEXT, "");

		textField = new TextField(TEXT, PLACE_HOLDER);
		checkText("New text place holder label", textField, TEXT, PLACE_HOLDER);
	}

	private void checkText(String message, TextField field, String expectedValue, String expectedPlaceHolder) {
		CheckHelper.check(getClass(), message, field.getText(), expectedValue);
		CheckHelper.check(getClass(), message, field.getPlaceHolder(), expectedPlaceHolder);
	}

}
