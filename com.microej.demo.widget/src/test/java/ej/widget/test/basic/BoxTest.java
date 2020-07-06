package ej.widget.test.basic;
/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.widget.basic.Box;
import ej.widget.basic.drawing.CheckBox;
import ej.widget.basic.drawing.RadioBox;
import ej.widget.basic.drawing.SwitchBox;
import ej.widget.basic.image.ImageCheck;
import ej.widget.basic.image.ImageRadio;
import ej.widget.basic.image.ImageSwitch;
import ej.widget.basic.picto.PictoCheck;
import ej.widget.basic.picto.PictoRadio;
import ej.widget.basic.picto.PictoSwitch;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;
import ej.widget.util.States;

/**
 *
 */
public class BoxTest extends Test {

	public static void main(String[] args) {
		TestHelper.launchTest(new BoxTest());
	}

	@Override
	public void run(Display display) {
		testToggle(new CheckBox(), "Check");
		testToggle(new SwitchBox(), "Switch");
		testToggle(new RadioBox(), "Radio");
		testToggle(new PictoCheck(), "Picto Check");
		testToggle(new PictoSwitch(), "Picto Switch");
		testToggle(new PictoRadio(), "Picto Radio");
		testToggle(new ImageCheck(), "Image Check");
		testToggle(new ImageSwitch(), "Image Switch");
		testToggle(new ImageRadio(), "Image Radio");
	}

	private void testToggle(Box box, String message) {
		CheckHelper.check(getClass(), "Toggle " + message + " checked", !box.isChecked());
		box.setChecked(true);
		CheckHelper.check(getClass(), "Toggle " + message + " not checked", box.isChecked());
		box.setChecked(false);
		CheckHelper.check(getClass(), "Toggle " + message + " checked", !box.isChecked());

		CheckHelper.check(getClass(), "Toggle " + message + " not active", !box.isInState(States.ACTIVE));
		box.setPressed(true);
		CheckHelper.check(getClass(), "Toggle " + message + " active", box.isInState(States.ACTIVE));
		box.setPressed(false);
		CheckHelper.check(getClass(), "Toggle " + message + " not active", !box.isInState(States.ACTIVE));
	}

}
