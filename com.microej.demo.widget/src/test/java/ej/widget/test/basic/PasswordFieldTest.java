/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.basic;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.widget.basic.PasswordField;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 *
 */
public class PasswordFieldTest extends Test {

	private static final String TEXT = "Text";
	private static final String PLACE_HOLDER = "Place Holder";

	public static void main(String[] args) {
		TestHelper.launchTest(new PasswordFieldTest());
	}

	@Override
	public void run(Display display) {
		PasswordField passwordField = new PasswordField();

		checkText("New empty label", passwordField, "", "");

		passwordField.setText(TEXT);
		checkText("Set text", passwordField, TEXT, "");

		passwordField.reveal(true);
		checkText("Reveal", passwordField, TEXT, "");

		passwordField.setText("");
		checkText("Set text 2", passwordField, "", "");

		passwordField.setPlaceHolder(PLACE_HOLDER);
		checkText("Set place holder", passwordField, "", PLACE_HOLDER);

		passwordField.setPlaceHolder("");
		checkText("Set place holder 2", passwordField, "", "");

		passwordField = new PasswordField(TEXT);
		checkText("New text label", passwordField, TEXT, "");

		passwordField.reveal(true);
		checkText("Reveal 2", passwordField, TEXT, "");

		passwordField = new PasswordField(TEXT, PLACE_HOLDER);
		checkText("New text place holder label", passwordField, TEXT, PLACE_HOLDER);

		passwordField.reveal(true);
		checkText("Reveal 3", passwordField, TEXT, PLACE_HOLDER);
	}

	private void checkText(String message, PasswordField field, String expectedValue, String expectedPlaceHolder) {
		if (field.isRevealed()) {
			CheckHelper.check(getClass(), message, field.getText(), expectedValue);
		} else if (expectedValue.length() != 0) {
			CheckHelper.check(getClass(), message, !field.getText().equals(expectedValue));
		}
		CheckHelper.check(getClass(), message, field.getText().length(), expectedValue.length());
		CheckHelper.check(getClass(), message, field.getPlaceHolder(), expectedPlaceHolder);
		CheckHelper.check(getClass(), message, field.getPassword(), expectedValue);
	}

}
