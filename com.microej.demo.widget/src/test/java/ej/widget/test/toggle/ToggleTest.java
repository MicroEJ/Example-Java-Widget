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
import ej.widget.basic.drawing.CheckBox;
import ej.widget.basic.drawing.SwitchBox;
import ej.widget.composed.Check;
import ej.widget.composed.Switch;
import ej.widget.composed.ToggleBox;
import ej.widget.composed.ToggleWrapper;
import ej.widget.listener.OnStateChangeListener;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;
import ej.widget.toggle.ToggleGroup;
import ej.widget.toggle.ToggleModel;

/**
 *
 */
public class ToggleTest extends Test {

	public static void main(String[] args) {
		TestHelper.launchTest(new ToggleTest());
	}

	@Override
	public void run(Display display) {
		CheckHelper.check(getClass(), "New unchecked check", !new ToggleModel(false).isChecked());
		CheckHelper.check(getClass(), "New checked check", new ToggleModel(true).isChecked());
		testToggle(new ToggleModel(true), "check");

		testToggleGroup("Check", new ToggleModel(false), new ToggleModel(false), new ToggleModel(false));

		testToggleUI(new ToggleModel(false), "model");
		testToggleUI(new Check("check"), "check");
		// testToggleUI(new Radio("radio"), "radio");
		testToggleUI(new Switch("switch"), "switch");
		testToggleUI(new ToggleBox(new CheckBox()), "togglebox check");
		// testToggleUI(new ToggleBox(new RadioModel(), new RadioBox()), "radiobox check");
		testToggleUI(new ToggleBox(new SwitchBox()), "switchbox check");
	}

	/**
	 * Checks a toggle. The given toggle must be checked.
	 *
	 * @param toggle
	 *            the toggle to check.
	 * @param message
	 *            the name of the toggle implementation.
	 */
	protected void testToggle(ToggleModel toggle, String message) {
		if (!toggle.isChecked()) {
			throw new IllegalArgumentException();
		}

		MyOnStateChangeListener myListener = new MyOnStateChangeListener();

		toggle.addOnStateChangeListener(myListener);

		myListener.notified = false;
		toggle.toggle();
		CheckHelper.check(getClass(), "Toggle " + message + " 1", !toggle.isChecked());
		CheckHelper.check(getClass(), "Toggle " + message + " 1 notified", myListener.notified && !myListener.checked);

		myListener.notified = false;
		toggle.setChecked(false);
		CheckHelper.check(getClass(), "Set checked " + message + " 1", !toggle.isChecked());
		CheckHelper.check(getClass(), "Set checked " + message + " 1 not notified", !myListener.notified);

		myListener.notified = false;
		toggle.toggle();
		CheckHelper.check(getClass(), "Toggle " + message + " 2", toggle.isChecked());
		CheckHelper.check(getClass(), "Toggle " + message + " 2 notified", myListener.notified && myListener.checked);

		myListener.notified = false;
		toggle.setChecked(true);
		CheckHelper.check(getClass(), "Set checked " + message + " 2", toggle.isChecked());
		CheckHelper.check(getClass(), "Set checked " + message + " 2 notified", !myListener.notified);

		myListener.notified = false;
		toggle.setChecked(false);
		CheckHelper.check(getClass(), "Set checked " + message + " 3", !toggle.isChecked());
		CheckHelper.check(getClass(), "Set checked " + message + " 3 not notified",
				myListener.notified && !myListener.checked);
	}

	/**
	 * Checks a toggle in a desktop and panel.
	 *
	 * @param message
	 *            the name of the toggle implementation.
	 */
	protected void testToggleUI(ToggleModel toggleModel, String message) {
		if (toggleModel.isChecked()) {
			throw new IllegalArgumentException();
		}

		ToggleWrapper toggle = new ToggleWrapper(toggleModel);
		toggle.setChild(new Label("Toggle Wrapper"));

		testToggleUI(toggle, message);
	}

	private void testToggleUI(ToggleWrapper toggle, String message) {
		if (toggle.isChecked()) {
			throw new IllegalArgumentException();
		}
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
		CheckHelper.check(getClass(), "Toggle press " + message + " 2", !toggle.isChecked());
		CheckHelper.check(getClass(), "Toggle press " + message + " 2 notified",
				myListener.notified && !myListener.checked);

		toggle.toggle();
		CheckHelper.check(getClass(), "Toggle toggle " + message + " 1", toggle.isChecked());

		toggle.toggle();
		CheckHelper.check(getClass(), "Toggle toggle " + message + " 2", !toggle.isChecked());

		toggle.setChecked(true);
		CheckHelper.check(getClass(), "Toggle checked " + message + " 1", toggle.isChecked());

		toggle.setChecked(false);
		CheckHelper.check(getClass(), "Toggle checked " + message + " 2", !toggle.isChecked());
	}

	/**
	 * Checks a toggle group. All toggles must be unchecked.
	 *
	 * @param toggle1
	 *            the first toggle.
	 * @param toggle2
	 *            the second toggle.
	 * @param toggle3
	 *            the third toggle.
	 * @param message
	 *            the name of the toggle implementation.
	 */
	protected void testToggleGroup(String message, ToggleModel toggle1, ToggleModel toggle2, ToggleModel toggle3) {
		if (toggle1.isChecked() || toggle2.isChecked() || toggle3.isChecked()) {
			throw new IllegalArgumentException();
		}

		MyOnStateChangeListener myListener1 = new MyOnStateChangeListener();
		toggle1.addOnStateChangeListener(myListener1);
		MyOnStateChangeListener myListener2 = new MyOnStateChangeListener();
		toggle2.addOnStateChangeListener(myListener2);
		MyOnStateChangeListener myListener3 = new MyOnStateChangeListener();
		toggle3.addOnStateChangeListener(myListener3);

		ToggleGroup toggleGroup = new ToggleGroup();
		toggleGroup.addToggle(toggle1);
		toggleGroup.addToggle(toggle2);
		toggleGroup.addToggle(toggle3);

		ToggleModel[] toggles = toggleGroup.getToggles();
		CheckHelper.check(getClass(), message + " toggles group size", toggles.length, 3);
		CheckHelper.check(getClass(), message + " toggles group 1", contains(toggles, toggle1));
		CheckHelper.check(getClass(), message + " toggles group 1", toggle1.getGroup(), toggleGroup);
		CheckHelper.check(getClass(), message + " toggles group 2", contains(toggles, toggle2));
		CheckHelper.check(getClass(), message + " toggles group 2", toggle2.getGroup(), toggleGroup);
		CheckHelper.check(getClass(), message + " toggles group 3", contains(toggles, toggle3));
		CheckHelper.check(getClass(), message + " toggles group 3", toggle3.getGroup(), toggleGroup);

		ToggleModel groupChecked = toggleGroup.getChecked();
		CheckHelper.check(getClass(), message + " no check group", groupChecked, null);

		toggle1.setChecked(true);
		CheckHelper.check(getClass(), message + " toggle 1 checked", toggle1.isChecked());
		CheckHelper.check(getClass(), message + " toggle 2 not checked", !toggle2.isChecked());
		CheckHelper.check(getClass(), message + " toggle 3 not checked", !toggle3.isChecked());
		CheckHelper.check(getClass(), message + " toggle 1 notified", myListener1.notified && myListener1.checked);
		CheckHelper.check(getClass(), message + " toggle 2 not notified", !myListener2.notified);
		CheckHelper.check(getClass(), message + " toggle 3 not notified", !myListener3.notified);
		myListener1.notified = false;
		myListener2.notified = false;
		myListener3.notified = false;

		myListener1.notified = false;
		toggle2.setChecked(true);
		CheckHelper.check(getClass(), message + " toggle 1 not checked", !toggle1.isChecked());
		CheckHelper.check(getClass(), message + " toggle 2 checked", toggle2.isChecked());
		CheckHelper.check(getClass(), message + " toggle 3 not checked", !toggle3.isChecked());
		CheckHelper.check(getClass(), message + " toggle 1 notified", myListener1.notified && !myListener1.checked);
		CheckHelper.check(getClass(), message + " toggle 2 notified", myListener2.notified && myListener2.checked);
		CheckHelper.check(getClass(), message + " toggle 3 not notified", !myListener3.notified);
		myListener1.notified = false;
		myListener2.notified = false;
		myListener3.notified = false;

		toggle3.toggle();
		CheckHelper.check(getClass(), message + " toggle 1 not checked", !toggle1.isChecked());
		CheckHelper.check(getClass(), message + " toggle 2 not checked", !toggle2.isChecked());
		CheckHelper.check(getClass(), message + " toggle 3 checked", toggle3.isChecked());
		CheckHelper.check(getClass(), message + " toggle 1 not notified", !myListener1.notified);
		CheckHelper.check(getClass(), message + " toggle 2 notified", myListener2.notified && !myListener2.checked);
		CheckHelper.check(getClass(), message + " toggle 3 notified", myListener3.notified && myListener3.checked);
		myListener1.notified = false;
		myListener2.notified = false;
		myListener3.notified = false;

		toggle3.toggle();
		CheckHelper.check(getClass(), message + " toggle 1 not checked", !toggle1.isChecked());
		CheckHelper.check(getClass(), message + " toggle 2 not checked", !toggle2.isChecked());
		CheckHelper.check(getClass(), message + " toggle 3 not checked", !toggle3.isChecked());
		CheckHelper.check(getClass(), message + " toggle 1 not notified", !myListener1.notified);
		CheckHelper.check(getClass(), message + " toggle 2 not notified", !myListener2.notified);
		CheckHelper.check(getClass(), message + " toggle 3 notified", myListener3.notified && !myListener3.checked);
		myListener1.notified = false;
		myListener2.notified = false;
		myListener3.notified = false;

		toggle1.setChecked(true);
		CheckHelper.check(getClass(), message + " toggle 1 checked", toggle1.isChecked());
		CheckHelper.check(getClass(), message + " toggle 2 not checked", !toggle2.isChecked());
		CheckHelper.check(getClass(), message + " toggle 3 not checked", !toggle3.isChecked());
		CheckHelper.check(getClass(), message + " toggle 1 notified", myListener1.notified && myListener1.checked);
		CheckHelper.check(getClass(), message + " toggle 2 not notified", !myListener2.notified);
		CheckHelper.check(getClass(), message + " toggle 3 not notified", !myListener3.notified);
		myListener1.notified = false;
		myListener2.notified = false;
		myListener3.notified = false;

		toggle1.setChecked(false);
		CheckHelper.check(getClass(), message + " toggle 1 not checked", !toggle1.isChecked());
		CheckHelper.check(getClass(), message + " toggle 2 not checked", !toggle2.isChecked());
		CheckHelper.check(getClass(), message + " toggle 3 not checked", !toggle3.isChecked());
		CheckHelper.check(getClass(), message + " toggle 1 notified", myListener1.notified && !myListener1.checked);
		CheckHelper.check(getClass(), message + " toggle 2 not notified", !myListener2.notified);
		CheckHelper.check(getClass(), message + " toggle 3 not notified", !myListener3.notified);
		myListener1.notified = false;
		myListener2.notified = false;
		myListener3.notified = false;

		toggle1.toggle();
		CheckHelper.check(getClass(), message + " toggle 1 checked", toggle1.isChecked());
		CheckHelper.check(getClass(), message + " toggle 2 not checked", !toggle2.isChecked());
		CheckHelper.check(getClass(), message + " toggle 3 not checked", !toggle3.isChecked());
		CheckHelper.check(getClass(), message + " toggle 1 notified", myListener1.notified && myListener1.checked);
		CheckHelper.check(getClass(), message + " toggle 2 not notified", !myListener2.notified);
		CheckHelper.check(getClass(), message + " toggle 3 not notified", !myListener3.notified);
		myListener1.notified = false;
		myListener2.notified = false;
		myListener3.notified = false;

		// Add a checked toggle model.
		toggle1.setChecked(false);
		ToggleModel toggle4 = new ToggleModel(true);
		CheckHelper.check(getClass(), message + " toggle 4 checked", toggle4.isChecked());
		toggleGroup.addToggle(toggle4);
		CheckHelper.check(getClass(), message + " toggle 4 checked", toggle4.isChecked());
		CheckHelper.check(getClass(), message + " toggles group 4", toggle4.getGroup(), toggleGroup);

		ToggleModel toggle5 = new ToggleModel(true);
		CheckHelper.check(getClass(), message + " toggle 5 checked", toggle5.isChecked());
		toggleGroup.addToggle(toggle5);
		CheckHelper.check(getClass(), message + " toggle 4 checked", toggle4.isChecked());
		CheckHelper.check(getClass(), message + " toggle 5 not checked", !toggle5.isChecked());
		CheckHelper.check(getClass(), message + " toggles group 5", toggle5.getGroup(), toggleGroup);

		toggleGroup.removeToggle(toggle1);
		toggleGroup.removeToggle(toggle2);
		toggleGroup.removeToggle(toggle3);
		toggleGroup.removeToggle(toggle4);
		toggleGroup.removeToggle(toggle5);
		CheckHelper.check(getClass(), message + " toggles group 1", toggle1.getGroup(), null);
		CheckHelper.check(getClass(), message + " toggles group 2", toggle2.getGroup(), null);
		CheckHelper.check(getClass(), message + " toggles group 3", toggle3.getGroup(), null);
		CheckHelper.check(getClass(), message + " toggles group 4", toggle4.getGroup(), null);
		CheckHelper.check(getClass(), message + " toggles group 5", toggle5.getGroup(), null);

		toggles = toggleGroup.getToggles();
		CheckHelper.check(getClass(), message + " toggles group size", toggles.length, 0);
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

	private boolean contains(Object[] array, Object object) {
		for (Object candidate : array) {
			if (candidate == object) {
				return true;
			}
		}
		return false;
	}

}
