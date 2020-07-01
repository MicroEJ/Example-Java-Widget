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
import ej.widget.composed.ButtonWrapper;
import ej.widget.listener.OnClickListener;
import ej.widget.test.framework.MySplitContainer;
import ej.widget.test.framework.TestEvent;
import ej.widget.test.framework.TestHelper;

public class TestButtonWrapperOver extends TestEvent {

	public static void main(String[] args) {
		TestHelper.launchTest(new TestButtonWrapperOver());
	}

	@Override
	public void run(Display display) {
		CascadingStylesheet stylesheet = new CascadingStylesheet();
		EditableStyle editableStyle = stylesheet.getSelectorStyle(new TypeSelector(ButtonWrapper.class));
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
		ButtonWrapper button = new ButtonWrapper();
		InstrumentedOnClickListener listener = new InstrumentedOnClickListener();
		button.addOnClickListener(listener);
		Label label = new Label("test");
		TestHelper.mergeStyle(stylesheet, label, NoDimension.NO_DIMENSION);
		button.setChild(label);
		splitContainer.setWidget(button);

		desktop.setWidget(splitContainer);
		desktop.requestShow();

		TestHelper.waitForAllEvents();

		Pointer pointer = getOrCreatePointer(display);

		// Press and release on the center of the button.
		moveCenter(display, pointer, button);
		press(display, pointer);
		release(display, pointer);
		CheckHelper.check(getClass(), "listener not notified after click", listener.notified);
		listener.notified = false;

		// Press and release on the top left of the button.
		moveTo(display, pointer, button.getAbsoluteX() + 1, button.getAbsoluteY() + 1);
		press(display, pointer);
		release(display, pointer);
		CheckHelper.check(getClass(), "listener not notified after click", listener.notified);
		listener.notified = false;

		// Press and release on the bottom right of the button.
		moveTo(display, pointer, button.getAbsoluteX() + button.getWidth() - 1,
				button.getAbsoluteY() + button.getHeight() - 1);
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
		ButtonWrapper button = new ButtonWrapper();
		InstrumentedOnClickListener listener = new InstrumentedOnClickListener();
		button.addOnClickListener(listener);
		Label label = new Label("test");
		TestHelper.mergeStyle(stylesheet, label, OptimalDimension.OPTIMAL_DIMENSION_XY);
		button.setChild(label);
		splitContainer.setWidget(button);

		desktop.setWidget(splitContainer);
		desktop.requestShow();

		TestHelper.waitForAllEvents();

		Pointer pointer = getOrCreatePointer(display);

		// Press and release on the center of the button.
		moveCenter(display, pointer, button);
		press(display, pointer);
		release(display, pointer);
		CheckHelper.check(getClass(), "listener not notified after click", listener.notified);
		listener.notified = false;

		// Press and release on the top left of the button.
		moveTo(display, pointer, button.getAbsoluteX() + 1, button.getAbsoluteY() + 1);
		press(display, pointer);
		release(display, pointer);
		CheckHelper.check(getClass(), "listener notified after click", !listener.notified);
		listener.notified = false;

		// Press and release on the bottom right of the button.
		moveTo(display, pointer, button.getAbsoluteX() + button.getWidth() - 1,
				button.getAbsoluteY() + button.getHeight() - 1);
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
		ButtonWrapper button = new ButtonWrapper();
		InstrumentedOnClickListener listener = new InstrumentedOnClickListener();
		button.addOnClickListener(listener);
		splitContainer.setWidget(button);

		desktop.setWidget(splitContainer);
		desktop.requestShow();

		TestHelper.waitForAllEvents();

		Pointer pointer = getOrCreatePointer(display);

		// Press and release on the center of the button.
		moveCenter(display, pointer, button);
		press(display, pointer);
		release(display, pointer);
		CheckHelper.check(getClass(), "listener not notified after click", listener.notified);
		listener.notified = false;

		// Press and release on the top left of the button.
		moveTo(display, pointer, button.getAbsoluteX() + 1, button.getAbsoluteY() + 1);
		press(display, pointer);
		release(display, pointer);
		CheckHelper.check(getClass(), "listener not notified after click", listener.notified);
		listener.notified = false;

		// Press and release on the bottom right of the button.
		moveTo(display, pointer, button.getAbsoluteX() + button.getWidth() - 1,
				button.getAbsoluteY() + button.getHeight() - 1);
		press(display, pointer);
		release(display, pointer);
		CheckHelper.check(getClass(), "listener not notified after click", listener.notified);
		listener.notified = false;
	}

	private class InstrumentedOnClickListener implements OnClickListener {

		boolean notified;

		@Override
		public void onClick() {
			this.notified = true;
		}

	}

}
