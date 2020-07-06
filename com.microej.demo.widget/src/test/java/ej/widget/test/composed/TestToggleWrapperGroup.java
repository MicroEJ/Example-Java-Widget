/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.composed;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.widget.composed.ToggleWrapper;
import ej.widget.test.framework.TestHelper;
import ej.widget.toggle.ToggleGroup;

public class TestToggleWrapperGroup extends TestButtonWrapper {

	private static final String MY_GROUP = "my group";
	private static final String MY_GROUP2 = "my group 2";

	public static void main(String[] args) {
		TestHelper.launchTest(new TestToggleWrapperGroup());
	}

	@Override
	public void run(Display display) {
		int hash = test();

		System.gc();
		// The group must be garbage collected here.
		// Perhaps the hash is not the best way to test that a new instance is created…

		ToggleWrapper toggleContainer = new ToggleWrapper();
		toggleContainer.setGroup(MY_GROUP);
		ToggleGroup group = toggleContainer.getToggle().getGroup();
		CheckHelper.check(getClass(), "Not same instance", group != null);
		if (group != null) {
			CheckHelper.check(getClass(), "Not same instance", group.hashCode() != hash);
		}
	}

	private int test() {
		ToggleWrapper toggleContainer = new ToggleWrapper();
		toggleContainer.setGroup(MY_GROUP);
		ToggleGroup firstGroup = toggleContainer.getToggle().getGroup();
		CheckHelper.check(getClass(), "No group for first", firstGroup != null);

		ToggleWrapper toggleContainer2 = new ToggleWrapper();
		toggleContainer2.setGroup(MY_GROUP);
		CheckHelper.check(getClass(), "No group for second", toggleContainer2.getToggle().getGroup() != null);
		CheckHelper.check(getClass(), "Not same group for both", toggleContainer2.getToggle().getGroup() == firstGroup);

		toggleContainer2.setGroup(null);
		CheckHelper.check(getClass(), "Still group for second", toggleContainer2.getToggle().getGroup(), null);

		ToggleWrapper toggleContainer3 = new ToggleWrapper();
		toggleContainer3.setGroup(MY_GROUP);
		CheckHelper.check(getClass(), "No group for third", toggleContainer3.getToggle().getGroup() != null);
		CheckHelper.check(getClass(), "Not same group for all", toggleContainer3.getToggle().getGroup() == firstGroup);

		toggleContainer3.setGroup(MY_GROUP2);
		CheckHelper.check(getClass(), "Still same group for third",
				toggleContainer3.getToggle().getGroup() != firstGroup);

		if (firstGroup != null) {
			return firstGroup.hashCode();
		} else {
			throw new AssertionError();
		}
	}

}
