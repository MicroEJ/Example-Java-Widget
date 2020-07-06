/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.composed;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Colors;
import ej.microui.display.Display;
import ej.microui.event.generator.Pointer;
import ej.mwt.Desktop;
import ej.mwt.style.EditableStyle;
import ej.mwt.style.background.RectangularBackground;
import ej.mwt.style.dimension.NoDimension;
import ej.mwt.style.dimension.OptimalDimension;
import ej.mwt.stylesheet.Stylesheet;
import ej.mwt.stylesheet.cascading.CascadingStylesheet;
import ej.mwt.stylesheet.selector.TypeSelector;
import ej.mwt.util.Alignment;
import ej.widget.basic.Label;
import ej.widget.composed.ToggleWrapper;
import ej.widget.listener.OnStateChangeListener;
import ej.widget.test.framework.MySplitContainer;
import ej.widget.test.framework.TestEvent;
import ej.widget.test.framework.TestHelper;

public class TestToggleWrapperOver extends TestEvent {

	public static void main(String[] args) {
		TestHelper.launchTest(new TestToggleWrapperOver());
	}

	@Override
	public void run(Display display) {
		CascadingStylesheet stylesheet = new CascadingStylesheet();
		EditableStyle editableStyle = stylesheet.getSelectorStyle(new TypeSelector(ToggleWrapper.class));
		editableStyle.setHorizontalAlignment(Alignment.HCENTER);
		editableStyle.setVerticalAlignment(Alignment.VCENTER);
		editableStyle = stylesheet.getSelectorStyle(new TypeSelector(Label.class));
		editableStyle.setBackground(new RectangularBackground(Colors.MAGENTA));
		editableStyle.setHorizontalAlignment(Alignment.HCENTER);
		editableStyle.setVerticalAlignment(Alignment.VCENTER);

		adjustedToChild(stylesheet);
		notAdjustedToChild(stylesheet);
		noChild(stylesheet);
	}

	private void adjustedToChild(CascadingStylesheet stylesheet) {
		Display display = Display.getDisplay();
		Desktop desktop = new Desktop();
		desktop.setStylesheet(stylesheet);
		MySplitContainer splitContainer = new MySplitContainer();
		ToggleWrapper toggle = new ToggleWrapper();
		InstrumentedOnClickListener listener = new InstrumentedOnClickListener();
		toggle.addOnStateChangeListener(listener);
		Label label = new Label("test");
		TestHelper.mergeStyle(stylesheet, label, NoDimension.NO_DIMENSION);
		toggle.setChild(label);
		splitContainer.setWidget(toggle);

		desktop.setWidget(splitContainer);
		desktop.requestShow();

		TestHelper.waitForAllEvents();

		Pointer pointer = getOrCreatePointer(display);

		// Press and release on the center of the button.
		moveCenter(display, pointer, toggle);
		press(display, pointer);
		release(display, pointer);
		CheckHelper.check(getClass(), "listener not notified after click", listener.notified);
		listener.notified = false;

		// Press and release on the top left of the button.
		moveTo(display, pointer, toggle.getAbsoluteX() + 1, toggle.getAbsoluteY() + 1);
		press(display, pointer);
		release(display, pointer);
		CheckHelper.check(getClass(), "listener not notified after click", listener.notified);
		listener.notified = false;

		// Press and release on the bottom right of the button.
		moveTo(display, pointer, toggle.getAbsoluteX() + toggle.getWidth() - 1,
				toggle.getAbsoluteY() + toggle.getHeight() - 1);
		press(display, pointer);
		release(display, pointer);
		CheckHelper.check(getClass(), "listener not notified after click", listener.notified);
		listener.notified = false;
	}

	private void notAdjustedToChild(CascadingStylesheet stylesheet) {
		Display display = Display.getDisplay();
		Desktop desktop = new Desktop();
		desktop.setStylesheet(stylesheet);
		MySplitContainer splitContainer = new MySplitContainer();
		ToggleWrapper toggle = new ToggleWrapper();
		InstrumentedOnClickListener listener = new InstrumentedOnClickListener();
		toggle.addOnStateChangeListener(listener);
		Label label = new Label("test");
		TestHelper.mergeStyle(stylesheet, label, OptimalDimension.OPTIMAL_DIMENSION_XY);
		toggle.setChild(label);
		splitContainer.setWidget(toggle);

		desktop.setWidget(splitContainer);
		desktop.requestShow();

		TestHelper.waitForAllEvents();

		Pointer pointer = getOrCreatePointer(display);

		// Press and release on the center of the button.
		moveCenter(display, pointer, toggle);
		press(display, pointer);
		release(display, pointer);
		CheckHelper.check(getClass(), "listener not notified after click", listener.notified);
		listener.notified = false;

		// Press and release on the top left of the button.
		moveTo(display, pointer, toggle.getAbsoluteX() + 1, toggle.getAbsoluteY() + 1);
		press(display, pointer);
		release(display, pointer);
		CheckHelper.check(getClass(), "listener notified after click", !listener.notified);
		listener.notified = false;

		// Press and release on the bottom right of the button.
		moveTo(display, pointer, toggle.getAbsoluteX() + toggle.getWidth() - 1,
				toggle.getAbsoluteY() + toggle.getHeight() - 1);
		press(display, pointer);
		release(display, pointer);
		CheckHelper.check(getClass(), "listener notified after click", !listener.notified);
		listener.notified = false;
	}

	private void noChild(Stylesheet stylesheet) {
		Display display = Display.getDisplay();
		Desktop desktop = new Desktop();
		desktop.setStylesheet(stylesheet);
		MySplitContainer splitContainer = new MySplitContainer();
		ToggleWrapper toggle = new ToggleWrapper();
		InstrumentedOnClickListener listener = new InstrumentedOnClickListener();
		toggle.addOnStateChangeListener(listener);
		splitContainer.setWidget(toggle);

		desktop.setWidget(splitContainer);
		desktop.requestShow();

		TestHelper.waitForAllEvents();

		Pointer pointer = getOrCreatePointer(display);

		// Press and release on the center of the button.
		moveCenter(display, pointer, toggle);
		press(display, pointer);
		release(display, pointer);
		CheckHelper.check(getClass(), "listener not notified after click", listener.notified);
		listener.notified = false;

		// Press and release on the top left of the button.
		moveTo(display, pointer, toggle.getAbsoluteX() + 1, toggle.getAbsoluteY() + 1);
		press(display, pointer);
		release(display, pointer);
		CheckHelper.check(getClass(), "listener not notified after click", listener.notified);
		listener.notified = false;

		// Press and release on the bottom right of the button.
		moveTo(display, pointer, toggle.getAbsoluteX() + toggle.getWidth() - 1,
				toggle.getAbsoluteY() + toggle.getHeight() - 1);
		press(display, pointer);
		release(display, pointer);
		CheckHelper.check(getClass(), "listener not notified after click", listener.notified);
		listener.notified = false;
	}

	private class InstrumentedOnClickListener implements OnStateChangeListener {

		boolean notified;

		@Override
		public void onStateChange(boolean newState) {
			this.notified = true;
		}

	}

}
