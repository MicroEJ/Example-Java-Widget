/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.composed;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.microui.event.EventGenerator;
import ej.microui.event.generator.Pointer;
import ej.mwt.Desktop;
import ej.widget.basic.Label;
import ej.widget.basic.drawing.CheckBox;
import ej.widget.composed.Check;
import ej.widget.composed.Radio;
import ej.widget.composed.Switch;
import ej.widget.composed.Toggle;
import ej.widget.composed.ToggleWrapper;
import ej.widget.listener.OnStateChangeListener;
import ej.widget.test.framework.TestEvent;
import ej.widget.test.framework.TestHelper;
import ej.widget.util.States;

/**
 *
 */
public class ToggleTest extends TestEvent {

	public static void main(String[] args) {
		TestHelper.launchTest(new ToggleTest());
	}

	@Override
	public void run(Display display) {
		ToggleWrapper toggle = new ToggleWrapper();
		toggle.setChild(new Label("Toggle Wrapper"));
		testToggleContainer(toggle, "Toggle Wrapper");
		// testToggleContainerCommand(new ToggleWrapper(), "Toggle Wrapper");
		testToggleContainerActive(new ToggleWrapper(), "Toggle Wrapper");
		testToggleContainerActive(new Toggle(new CheckBox(), ""), "Toggle");
		testToggleContainerActive(new Check("Check"), "Check");
		testToggleContainerActive(new Switch("Switch"), "Switch");
		testToggleContainerActive(new Radio("Radio"), "Radio");
		testToggle(new Check("Check"), "Check");
		testToggle(new Switch("Switch"), "Switch");
		testToggle(new Radio("Radio"), "Radio");
	}

	private void testToggle(Toggle toggle, String message) {
		testToggleContainer(toggle, message);

		testToggleCommon(toggle, message);

		try {
			toggle.setChild(new Label());
			CheckHelper.check(getClass(), "Can change the widget of a " + message, false);
		} catch (Exception e) {
			CheckHelper.check(getClass(), "Can change the widget of a " + message, true);
		}
	}

	private void testToggleContainer(ToggleWrapper toggle, String message) {
		CheckHelper.check(getClass(), "Toggle " + message + " checked", !toggle.isChecked());
		toggle.toggle();
		CheckHelper.check(getClass(), "Toggle " + message + " not checked", toggle.isChecked());
		toggle.setChecked(false);
		CheckHelper.check(getClass(), "Toggle " + message + " checked", !toggle.isChecked());
		toggle.getToggle().setChecked(true);
		CheckHelper.check(getClass(), "Toggle " + message + " not checked", toggle.isChecked());

		MyOnStateChangeListener myListener = new MyOnStateChangeListener();
		toggle.addOnStateChangeListener(myListener);

		Display display = Display.getDisplay();
		Desktop desktop = new Desktop();
		desktop.setWidget(toggle);
		desktop.requestShow();
		TestHelper.waitForEvent();
		TestHelper.waitForEvent();

		Pointer pointer = getOrCreatePointer(display);
		pointer.move(toggle.getAbsoluteX() + toggle.getWidth() / 2, toggle.getAbsoluteY() + toggle.getHeight() / 2);

		myListener.notified = false;
		pointer.send(Pointer.PRESSED, 0);
		pointer.send(Pointer.RELEASED, 0);
		TestHelper.waitForEvent();
		CheckHelper.check(getClass(), "Toggle press " + message + " 1", !toggle.isChecked());
		CheckHelper.check(getClass(), "Toggle press " + message + " 1 notified",
				myListener.notified && !myListener.checked);

		myListener.notified = false;
		pointer.send(Pointer.PRESSED, 0);
		pointer.send(Pointer.RELEASED, 0);
		TestHelper.waitForEvent();
		CheckHelper.check(getClass(), "Toggle press " + message + " 2", toggle.isChecked());
		CheckHelper.check(getClass(), "Toggle press " + message + " 2 notified",
				myListener.notified && myListener.checked);

		toggle.removeOnStateChangeListener(myListener);

		myListener.notified = false;
		pointer.send(Pointer.PRESSED, 0);
		pointer.send(Pointer.RELEASED, 0);
		TestHelper.waitForEvent();
		CheckHelper.check(getClass(), "Toggle press " + message + " 3", !toggle.isChecked());
		CheckHelper.check(getClass(), "Toggle press " + message + " 3 notified", !myListener.notified);
	}

	private void testToggleContainerActive(ToggleWrapper toggle, String message) {
		Display display = Display.getDisplay();
		Desktop desktop = new Desktop();
		desktop.setWidget(toggle);
		desktop.requestShow();
		TestHelper.waitForEvent();
		TestHelper.waitForEvent();
		Pointer pointer = getOrCreatePointer(display);

		CheckHelper.check(getClass(), "Toggle " + message + " not active", !toggle.isInState(States.ACTIVE));
		pointer.send(Pointer.PRESSED, 0);
		TestHelper.waitForEvent();
		CheckHelper.check(getClass(), "Toggle press " + message + " avtive", toggle.isInState(States.ACTIVE));

		pointer.send(Pointer.RELEASED, 0);
		TestHelper.waitForEvent();
		CheckHelper.check(getClass(), "Toggle " + message + " not active", !toggle.isInState(States.ACTIVE));
	}

	private void testToggle(Radio toggle, String message) {
		CheckHelper.check(getClass(), "Toggle " + message + " checked", !toggle.isChecked());

		MyOnStateChangeListener myListener = new MyOnStateChangeListener();
		toggle.addOnStateChangeListener(myListener);

		Desktop desktop = new Desktop();
		desktop.setWidget(toggle);
		desktop.requestShow();
		TestHelper.waitForEvent();
		TestHelper.waitForEvent();

		Pointer pointer = EventGenerator.get(Pointer.class, 0);
		assert pointer != null;
		pointer.move(toggle.getAbsoluteX() + toggle.getWidth() / 2, toggle.getAbsoluteY() + toggle.getHeight() / 2);

		myListener.notified = false;
		pointer.send(Pointer.PRESSED, 0);
		pointer.send(Pointer.RELEASED, 0);
		TestHelper.waitForEvent();
		CheckHelper.check(getClass(), "Toggle press " + message + " 1", toggle.isChecked());
		CheckHelper.check(getClass(), "Toggle press " + message + " 1 notified",
				myListener.notified && myListener.checked);

		myListener.notified = false;
		pointer.send(Pointer.PRESSED, 0);
		pointer.send(Pointer.RELEASED, 0);
		TestHelper.waitForEvent();
		CheckHelper.check(getClass(), "Toggle press " + message + " 2", toggle.isChecked());
		CheckHelper.check(getClass(), "Toggle press " + message + " 2 notified", !myListener.notified);

		testToggleCommon(toggle, message);
	}

	private void testToggleCommon(Toggle toggle, String message) {
		CheckHelper.check(getClass(), "Toggle " + message + " text", toggle.getText(), message);
		CheckHelper.check(getClass(), "Toggle " + message + " text", toggle.getLabel().getText(), message);
		String message2 = "2";
		toggle.setText(message2);
		CheckHelper.check(getClass(), "Toggle " + message + " text", toggle.getText(), message2);
		CheckHelper.check(getClass(), "Toggle " + message + " text", toggle.getLabel().getText(), message2);
		toggle.getLabel().setText(message);
		CheckHelper.check(getClass(), "Toggle " + message + " text", toggle.getText(), message);
		CheckHelper.check(getClass(), "Toggle " + message + " text", toggle.getLabel().getText(), message);

		try {
			toggle.setChild(new Label());
			CheckHelper.check(getClass(), "Toggle " + message + " can change widget", false);
		} catch (IllegalArgumentException e) {
			CheckHelper.check(getClass(), "Toggle " + message + " cannot change widget", true);
		}
	}

	class MyOnStateChangeListener implements OnStateChangeListener {
		boolean notified;
		boolean checked;

		@Override
		public void onStateChange(boolean newState) {
			this.notified = true;
			this.checked = newState;
		}
	}

}
