/*
 * Copyright 2015-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.model;

import com.is2t.testsuite.support.CheckHelper;

import ej.annotation.Nullable;
import ej.microui.display.Display;
import ej.widget.listener.OnValueChangeListener;
import ej.widget.model.DefaultBoundedRangeModel;
import ej.widget.test.framework.Test;
import ej.widget.test.framework.TestHelper;

/**
 *
 */
public class TestDefaultBoundedRangeModel extends Test {

	private class OnStateChangeListenerDebug implements OnValueChangeListener {

		private boolean changed;
		int value;
		int maximum;
		int minimum;

		boolean hasChanged() {
			return this.changed;
		}

		void reset() {
			this.changed = false;
		}

		@Override
		public void onValueChange(int newValue) {
			this.changed = true;
			this.value = newValue;
		}

		@Override
		public void onMaximumValueChange(int newMaximum) {
			this.changed = true;
			this.maximum = newMaximum;
		}

		@Override
		public void onMinimumValueChange(int newMinimum) {
			this.changed = true;
			this.minimum = newMinimum;
		}
	}

	@Nullable
	private DefaultBoundedRangeModel model;
	@Nullable
	private OnStateChangeListenerDebug listener;

	@Override
	public void run(Display display) {
		testCorrectConstructor();
		testBadConstructor();
		testSetGetMaximum();
		testSetGetMinimum();
		testSetGetValue();
		testPercent();
	}

	private void testCorrectConstructor() {
		int minimum = 5;
		int maximum = 40;
		int initialValue = 20;
		DefaultBoundedRangeModel model = new DefaultBoundedRangeModel(minimum, maximum, initialValue);
		CheckHelper.check(DefaultBoundedRangeModel.class, "constructor minimum", model.getMinimum(), minimum);
		CheckHelper.check(DefaultBoundedRangeModel.class, "constructor maximum", model.getMaximum(), maximum);
		CheckHelper.check(DefaultBoundedRangeModel.class, "constructor value", model.getValue(), initialValue);
	}

	private void testBadConstructor() {
		try {
			DefaultBoundedRangeModel model = new DefaultBoundedRangeModel(5, 10, 100);
			CheckHelper.check(DefaultBoundedRangeModel.class, "bad constructor", false);
		} catch (IllegalArgumentException e) {
			CheckHelper.check(DefaultBoundedRangeModel.class, "", true);
		}

		try {
			DefaultBoundedRangeModel model = new DefaultBoundedRangeModel(5, 10, 1);
			CheckHelper.check(DefaultBoundedRangeModel.class, "bad constructor", false);
		} catch (IllegalArgumentException e) {
			CheckHelper.check(DefaultBoundedRangeModel.class, "", true);
		}
	}

	private void beforeSetGetTest() {
		DefaultBoundedRangeModel model = this.model = new DefaultBoundedRangeModel(0, 100, 50);
		this.listener = new OnStateChangeListenerDebug();
		model.addOnValueChangeListener(this.listener);
	}

	private void testSetGetMaximum() {
		beforeSetGetTest();

		DefaultBoundedRangeModel model = this.model;
		OnStateChangeListenerDebug listener = this.listener;
		assert model != null && listener != null;
		int newMaximum = model.getMaximum() + 10;
		model.setMaximum(newMaximum);
		checkConstraint(model);
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get maximum listener notified", listener.hasChanged());
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get maximum listener notified",
				listener.maximum == newMaximum);
		listener.reset();

		newMaximum = model.getValue() - 1;
		model.setMaximum(newMaximum);
		checkConstraint(model);
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get maximum listener notified", listener.hasChanged());
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get maximum listener notified",
				listener.maximum == newMaximum);
		listener.reset();

		newMaximum = model.getMinimum() - 10;
		model.setMaximum(newMaximum);
		checkConstraint(model);
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get maximum listener notified", listener.hasChanged());
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get maximum listener notified",
				listener.maximum == model.getMinimum());
		listener.reset();

		model.setMaximum(model.getMaximum());
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get maximum listener not notified",
				!listener.hasChanged());
	}

	private void testSetGetMinimum() {
		beforeSetGetTest();

		DefaultBoundedRangeModel model = this.model;
		OnStateChangeListenerDebug listener = this.listener;
		assert model != null && listener != null;
		int newMinimum = model.getMinimum() - 10;
		model.setMinimum(newMinimum);
		checkConstraint(model);
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get minimum listener notified", listener.hasChanged());
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get minimum listener notified",
				listener.minimum == newMinimum);
		listener.reset();

		newMinimum = model.getValue() + 1;
		model.setMinimum(newMinimum);
		checkConstraint(model);
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get minimum listener notified", listener.hasChanged());
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get minimum listener notified",
				listener.minimum == newMinimum);
		listener.reset();

		newMinimum = model.getMaximum() + 10;
		model.setMinimum(newMinimum);
		checkConstraint(model);
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get minimum listener notified", listener.hasChanged());
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get minimum listener notified",
				listener.minimum == model.getMaximum());
		listener.reset();

		model.setMinimum(model.getMinimum());
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get minimum listener not notified",
				!listener.hasChanged());
	}

	private void testSetGetValue() {
		beforeSetGetTest();

		DefaultBoundedRangeModel model = this.model;
		OnStateChangeListenerDebug listener = this.listener;
		assert model != null && listener != null;
		int newValue = model.getMinimum();
		model.setValue(model.getMinimum() - 10);
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get value", model.getValue(), newValue);
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get value listener notified", listener.hasChanged());
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get value listener notified",
				listener.value == newValue);
		listener.reset();

		newValue = model.getMaximum();
		model.setValue(model.getMaximum() + 10);
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get value", model.getValue(), newValue);
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get value listener notified", listener.hasChanged());
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get value listener notified",
				listener.value == newValue);
		listener.reset();

		model.setValue(model.getValue());
		CheckHelper.check(DefaultBoundedRangeModel.class, "set get value listener not notified",
				!listener.hasChanged());
	}

	private void testPercent() {
		testPercent(0, 100, 50, 0.5f);
		testPercent(50, 150, 100, 0.5f);
		testPercent(-50, 50, 0, 0.5f);
		testPercent(-100, 0, -50, 0.5f);
	}

	private void testPercent(int min, int max, int value, float expected) {
		DefaultBoundedRangeModel model = new DefaultBoundedRangeModel(min, max, value);
		CheckHelper.check(DefaultBoundedRangeModel.class, "Check", model.getPercentComplete(), expected);
	}

	private static void checkConstraint(DefaultBoundedRangeModel model) {
		CheckHelper.check(DefaultBoundedRangeModel.class, "check model constraint",
				model.getValue() >= model.getMinimum());
		CheckHelper.check(DefaultBoundedRangeModel.class, "check model constraint",
				model.getValue() <= model.getMaximum());
	}

	public static void main(String[] args) {
		TestHelper.launchTest(new TestDefaultBoundedRangeModel());
	}
}
