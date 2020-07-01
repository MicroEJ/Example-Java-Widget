/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.toggle;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.microui.event.EventGenerator;
import ej.microui.event.generator.Pointer;
import ej.mwt.Desktop;
import ej.widget.basic.Label;
import ej.widget.composed.ToggleWrapper;
import ej.widget.test.framework.TestHelper;
import ej.widget.toggle.RadioModel;
import ej.widget.toggle.ToggleGroup;
import ej.widget.toggle.ToggleModel;

/**
 *
 */
public class RadioTest extends ToggleTest {

	public static void main(String[] args) {
		TestHelper.launchTest(new RadioTest());
	}

	@Override
	public void run(Display display) {
		MyOnStateChangeListener myListener = new MyOnStateChangeListener();

		RadioModel radio = new RadioModel(false);
		CheckHelper.check(getClass(), "New unchecked radio", !radio.isChecked());

		radio.addOnStateChangeListener(myListener);

		myListener.notified = false;
		radio.toggle();
		CheckHelper.check(getClass(), "Toggle radio 1", radio.isChecked());
		CheckHelper.check(getClass(), "Toggle radio 1 notified", myListener.notified);

		myListener.notified = false;
		radio.toggle();
		CheckHelper.check(getClass(), "Toggle radio 2", radio.isChecked());
		CheckHelper.check(getClass(), "Toggle radio 2 not notified", !myListener.notified);

		radio = new RadioModel(false);
		CheckHelper.check(getClass(), "New unchecked radio", !radio.isChecked());

		radio.addOnStateChangeListener(myListener);

		myListener.notified = false;
		radio.setChecked(true);
		CheckHelper.check(getClass(), "Set check radio 1", radio.isChecked());
		CheckHelper.check(getClass(), "Set check radio 1 notified", myListener.notified);

		myListener.notified = false;
		radio.toggle();
		CheckHelper.check(getClass(), "Set uncheck radio 1", radio.isChecked());
		CheckHelper.check(getClass(), "Set uncheck radio 1 not notified", !myListener.notified);

		radio = new RadioModel(true);
		CheckHelper.check(getClass(), "New checked radio", radio.isChecked());

		radio.addOnStateChangeListener(myListener);

		myListener.notified = false;
		radio.toggle();
		CheckHelper.check(getClass(), "Toggle radio 3", radio.isChecked());
		CheckHelper.check(getClass(), "Toggle radio 3 notified", !myListener.notified);

		myListener.notified = false;
		radio.setChecked(false);
		CheckHelper.check(getClass(), "Set unchecked radio 2", radio.isChecked());
		CheckHelper.check(getClass(), "Set unchecked radio 2 not notified", !myListener.notified);

		myListener.notified = false;
		radio.setChecked(true);
		CheckHelper.check(getClass(), "Set checked radio 2", radio.isChecked());
		CheckHelper.check(getClass(), "Set checked radio 2 not notified", !myListener.notified);

		// group
		RadioModel radio1 = new RadioModel(false);
		RadioModel radio2 = new RadioModel(false);
		RadioModel radio3 = new RadioModel(false);
		MyOnStateChangeListener myListener1 = new MyOnStateChangeListener();
		radio1.addOnStateChangeListener(myListener1);
		MyOnStateChangeListener myListener2 = new MyOnStateChangeListener();
		radio2.addOnStateChangeListener(myListener2);
		MyOnStateChangeListener myListener3 = new MyOnStateChangeListener();
		radio3.addOnStateChangeListener(myListener3);

		ToggleGroup toggleGroup = new ToggleGroup();
		toggleGroup.addToggle(radio1);
		toggleGroup.addToggle(radio2);
		toggleGroup.addToggle(radio3);

		ToggleModel[] toggles = toggleGroup.getToggles();
		CheckHelper.check(getClass(), "Radio toggles group size", toggles.length, 3);
		CheckHelper.check(getClass(), "Radio toggles group 1", contains(toggles, radio1));
		CheckHelper.check(getClass(), "Radio toggles group 2", contains(toggles, radio2));
		CheckHelper.check(getClass(), "Radio toggles group 3", contains(toggles, radio3));

		ToggleModel groupChecked = toggleGroup.getChecked();
		CheckHelper.check(getClass(), "Radio no check group", groupChecked, null);

		radio1.setChecked(true);
		CheckHelper.check(getClass(), "Radio toggle 1 checked", radio1.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 2 not checked", !radio2.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 3 not checked", !radio3.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 1 notified", myListener1.notified);
		CheckHelper.check(getClass(), "Radio toggle 2 not notified", !myListener2.notified);
		CheckHelper.check(getClass(), "Radio toggle 3 not notified", !myListener3.notified);
		myListener1.notified = false;
		myListener2.notified = false;
		myListener3.notified = false;

		radio2.setChecked(true);
		CheckHelper.check(getClass(), "Radio toggle 1 not checked", !radio1.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 2 checked", radio2.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 3 not checked", !radio3.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 1 notified", myListener1.notified);
		CheckHelper.check(getClass(), "Radio toggle 2 notified", myListener2.notified);
		CheckHelper.check(getClass(), "Radio toggle 3 not notified", !myListener3.notified);
		myListener1.notified = false;
		myListener2.notified = false;
		myListener3.notified = false;

		radio3.toggle();
		CheckHelper.check(getClass(), "Radio toggle 1 not checked", !radio1.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 2 not checked", !radio2.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 3 checked", radio3.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 1 not notified", !myListener1.notified);
		CheckHelper.check(getClass(), "Radio toggle 2 notified", myListener2.notified);
		CheckHelper.check(getClass(), "Radio toggle 3 notified", myListener3.notified);
		myListener1.notified = false;
		myListener2.notified = false;
		myListener3.notified = false;

		radio3.toggle();
		CheckHelper.check(getClass(), "Radio toggle 1 not checked", !radio1.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 2 not checked", !radio2.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 3 checked", radio3.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 1 not notified", !myListener1.notified);
		CheckHelper.check(getClass(), "Radio toggle 2 not notified", !myListener2.notified);
		CheckHelper.check(getClass(), "Radio toggle 3 not notified", !myListener3.notified);
		myListener1.notified = false;
		myListener2.notified = false;
		myListener3.notified = false;

		radio1.setChecked(true);
		CheckHelper.check(getClass(), "Radio toggle 1 checked", radio1.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 2 not checked", !radio2.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 3 not checked", !radio3.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 1 notified", myListener1.notified);
		CheckHelper.check(getClass(), "Radio toggle 2 not notified", !myListener2.notified);
		CheckHelper.check(getClass(), "Radio toggle 3 notified", myListener3.notified);
		myListener1.notified = false;
		myListener2.notified = false;
		myListener3.notified = false;

		radio1.setChecked(false);
		CheckHelper.check(getClass(), "Radio toggle 1 checked", radio1.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 2 not checked", !radio2.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 3 not checked", !radio3.isChecked());
		CheckHelper.check(getClass(), "Radio toggle 1 not notified", !myListener1.notified);
		CheckHelper.check(getClass(), "Radio toggle 2 not notified", !myListener2.notified);
		CheckHelper.check(getClass(), "Radio toggle 3 not notified", !myListener3.notified);
		myListener1.notified = false;
		myListener2.notified = false;
		myListener3.notified = false;

		toggleGroup.removeToggle(radio1);
		toggleGroup.removeToggle(radio2);
		toggleGroup.removeToggle(radio3);

		toggles = toggleGroup.getToggles();
		CheckHelper.check(getClass(), "Radio toggles group size", toggles.length, 0);

		// UI
		radio = new RadioModel(false);
		myListener = new MyOnStateChangeListener();
		radio.addOnStateChangeListener(myListener);

		ToggleWrapper toggleContainer = new ToggleWrapper(radio);
		toggleContainer.setChild(new Label("Toggle Wrapper"));
		Desktop desktop = new Desktop();
		desktop.setWidget(toggleContainer);
		desktop.requestShow();
		TestHelper.waitForAllEvents();

		Pointer pointer = EventGenerator.get(Pointer.class, 0);
		assert pointer != null;
		pointer.move(toggleContainer.getAbsoluteX() + toggleContainer.getWidth() / 2,
				toggleContainer.getAbsoluteY() + toggleContainer.getHeight() / 2);

		myListener.notified = false;
		pointer.send(Pointer.PRESSED, 0);
		pointer.send(Pointer.RELEASED, 0);
		TestHelper.waitForEvent();
		CheckHelper.check(getClass(), "Toggle press radio 1", radio.isChecked());
		CheckHelper.check(getClass(), "Toggle press radio 1 notified", myListener.notified);

		myListener.notified = false;
		pointer.send(Pointer.PRESSED, 0);
		pointer.send(Pointer.RELEASED, 0);
		TestHelper.waitForEvent();
		CheckHelper.check(getClass(), "Toggle press radio 2", radio.isChecked());
		CheckHelper.check(getClass(), "Toggle press radio 2 notified", !myListener.notified);
		TestHelper.waitForAllEvents();

		radio = new RadioModel(false);
		radio.addOnStateChangeListener(myListener);
		ToggleWrapper toggleButton = new ToggleWrapper(radio);
		toggleButton.setChild(new Label("My Toggle"));
		desktop.setWidget(toggleButton);
		TestHelper.waitForAllEvents();

		pointer.move(toggleButton.getAbsoluteX() + toggleButton.getWidth() / 2,
				toggleButton.getAbsoluteY() + toggleButton.getHeight() / 2);

		myListener.notified = false;
		pointer.send(Pointer.PRESSED, 0);
		pointer.send(Pointer.RELEASED, 0);
		TestHelper.waitForEvent();
		CheckHelper.check(getClass(), "Toggle press radio 1", radio.isChecked());
		CheckHelper.check(getClass(), "Toggle press radio 1 notified", myListener.notified);

		myListener.notified = false;
		pointer.send(Pointer.PRESSED, 0);
		pointer.send(Pointer.RELEASED, 0);
		TestHelper.waitForEvent();
		CheckHelper.check(getClass(), "Toggle press radio 2", radio.isChecked());
		CheckHelper.check(getClass(), "Toggle press radio 2 notified", !myListener.notified);

	}

	private boolean contains(Object[] array, Object object) {
		for (Object candidate : array) {
			if (candidate == object) {
				return true;
			}
		}
		return false;
	}

}
