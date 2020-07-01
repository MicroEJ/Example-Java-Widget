/*
 * Copyright 2016-2020 MicroEJ Corp. All rights reserved.
 * This library is provided in source code for use, modification and test, subject to license terms.
 * Any modification of the source code will break MicroEJ Corp. warranties on the whole library.
 */
package ej.widget.test.composed;

import com.is2t.testsuite.support.CheckHelper;

import ej.microui.display.Display;
import ej.microui.event.generator.Pointer;
import ej.mwt.Desktop;
import ej.widget.basic.Label;
import ej.widget.composed.ButtonWrapper;
import ej.widget.listener.OnClickListener;
import ej.widget.test.framework.MySplitContainer;
import ej.widget.test.framework.TestEvent;
import ej.widget.test.framework.TestHelper;
import ej.widget.util.States;

public class TestButtonWrapper extends TestEvent {

	public static void main(String[] args) {
		TestHelper.launchTest(new TestButtonWrapper());
	}

	@Override
	public void run(Display display) {
		testOnClickListenerNotifiedNoUI(new InstrumentedButtonWrapper());
		InstrumentedButtonWrapper button = new InstrumentedButtonWrapper();
		button.setChild(new Label("Click me"));
		testOnClickListenerNotifiedUI(button);
		button = new InstrumentedButtonWrapper();
		button.setChild(new Label("Click me"));
		testIsInActiveState(button);
		// button = new InstrumentedButtonWrapper();
		// button.setWidget(new Label("Click me"));
		// testOnClickListenerNotifiedUICommand(button);
		// button = new InstrumentedButtonWrapper();
		// button.setWidget(new Label("Click me"));
		// testHasFocusState(button);
	}

	protected void testOnClickListenerNotifiedNoUI(InstrumentedButtonWrapper button) {
		InstrumentedOnClickListener listener = new InstrumentedOnClickListener();
		button.addOnClickListener(listener);
		button.performClick();
		CheckHelper.check(getClass(), "button notified no ui", button.clicked);
		CheckHelper.check(getClass(), "listener notified no ui", listener.notified);
		button.clicked = false;
		listener.notified = false;

		button.removeOnClickListener(listener);
		button.performClick();
		CheckHelper.check(getClass(), "button notified no ui", button.clicked);
		CheckHelper.check(getClass(), "listener not notified no ui", !listener.notified);
	}

	protected void testOnClickListenerNotifiedUI(InstrumentedButtonWrapper button) {
		InstrumentedOnClickListener listener = new InstrumentedOnClickListener();
		button.addOnClickListener(listener);
		Display display = Display.getDisplay();
		Desktop desktop = new Desktop();
		MySplitContainer splitContainer = new MySplitContainer();
		splitContainer.setWidget(button);

		desktop.setWidget(splitContainer);
		desktop.requestShow();

		TestHelper.waitForAllEvents();

		Pointer pointer = getOrCreatePointer(display);

		// Press and release on the button.
		moveCenter(display, pointer, button);
		CheckHelper.check(getClass(), "listener not notified after move", !listener.notified);
		listener.notified = false;

		press(display, pointer);
		CheckHelper.check(getClass(), "listener not notified after press", !listener.notified);
		listener.notified = false;

		release(display, pointer);
		CheckHelper.check(getClass(), "button notified after release", button.clicked);
		CheckHelper.check(getClass(), "listener notified after release", listener.notified);
		button.clicked = false;
		listener.notified = false;

		// Press, drag inside and release on the button.
		moveCenter(display, pointer, button);
		press(display, pointer);

		move(display, pointer, button.getAbsoluteX() + 1, button.getAbsoluteY() + 1);
		CheckHelper.check(getClass(), "listener not notified after drag", !listener.notified);
		listener.notified = false;

		release(display, pointer);
		CheckHelper.check(getClass(), "button notified after drag and release", button.clicked);
		CheckHelper.check(getClass(), "listener notified after drag and release", listener.notified);
		button.clicked = false;
		listener.notified = false;

		// Press, drag and release not on the button.
		moveCenter(display, pointer, button);
		press(display, pointer);

		move(display, pointer, display.getWidth(), display.getHeight());
		CheckHelper.check(getClass(), "listener not notified after drag", !listener.notified);
		listener.notified = false;

		release(display, pointer);
		CheckHelper.check(getClass(), "button not notified after drag and release outside", !button.clicked);
		CheckHelper.check(getClass(), "listener not notified after drag and release outside", !listener.notified);
		button.clicked = false;
		listener.notified = false;

		// Press, drag outside, drag inside and release on the button.
		moveCenter(display, pointer, button);
		press(display, pointer);

		move(display, pointer, display.getWidth(), display.getHeight());
		moveCenter(display, pointer, button);
		CheckHelper.check(getClass(), "listener not notified after drag", !listener.notified);
		listener.notified = false;

		release(display, pointer);
		CheckHelper.check(getClass(), "button not notified after drag and release inside", !button.clicked);
		CheckHelper.check(getClass(), "listener not notified after drag outside and release inside",
				!listener.notified);
		button.clicked = false;
		listener.notified = false;

		// Press not on the button and release on the button.
		move(display, pointer, display.getWidth(), display.getHeight());
		press(display, pointer);
		moveCenter(display, pointer, button);
		CheckHelper.check(getClass(), "listener not notified after press not on the button", !listener.notified);
		listener.notified = false;

		release(display, pointer);
		CheckHelper.check(getClass(), "button not notified after release", !button.clicked);
		CheckHelper.check(getClass(), "listener not notified after release", !listener.notified);
		button.clicked = false;
		listener.notified = false;

		// Press on the button and release not on the button.
		moveCenter(display, pointer, button);
		press(display, pointer);
		CheckHelper.check(getClass(), "listener not notified after press on the button", !listener.notified);
		listener.notified = false;

		move(display, pointer, display.getWidth(), display.getHeight());
		release(display, pointer);
		CheckHelper.check(getClass(), "button not notified after release", !button.clicked);
		CheckHelper.check(getClass(), "listener not notified after release", !listener.notified);
		button.clicked = false;
		listener.notified = false;

		// Press and release on the button after removing the listener.
		button.removeOnClickListener(listener);
		moveCenter(display, pointer, button);
		press(display, pointer);
		release(display, pointer);
		CheckHelper.check(getClass(), "button notified after release", button.clicked);
		CheckHelper.check(getClass(), "listener not notified after release", !listener.notified);
		button.clicked = false;
		listener.notified = false;

	}

	protected void testIsInActiveState(InstrumentedButtonWrapper button) {
		Display display = Display.getDisplay();
		Desktop desktop = new Desktop();
		MySplitContainer splitContainer = new MySplitContainer();
		splitContainer.setWidget(button);

		desktop.setWidget(splitContainer);
		desktop.requestShow();

		TestHelper.waitForAllEvents();

		Pointer pointer = getOrCreatePointer(display);

		// Press and release on the button.
		moveCenter(display, pointer, button);
		CheckHelper.check(getClass(), "button not in active state after move", !button.isInState(States.ACTIVE));

		press(display, pointer);
		CheckHelper.check(getClass(), "button in active state after press", button.isInState(States.ACTIVE));

		release(display, pointer);
		CheckHelper.check(getClass(), "button not in active state after release", !button.isInState(States.ACTIVE));

		// Press and exit from the button.
		moveCenter(display, pointer, button);
		CheckHelper.check(getClass(), "button not in active state after move", !button.isInState(States.ACTIVE));

		press(display, pointer);
		CheckHelper.check(getClass(), "button in active state after press", button.isInState(States.ACTIVE));

		move(display, pointer, display.getWidth(), display.getHeight());
		CheckHelper.check(getClass(), "button not in active state after exit from the button ",
				!button.isInState(States.ACTIVE));
	}

	private class InstrumentedButtonWrapper extends ButtonWrapper {

		boolean clicked;

		public InstrumentedButtonWrapper() {
			super();
		}

		@Override
		protected void onClick() {
			super.onClick();
			this.clicked = true;
		}
	}

	private class InstrumentedOnClickListener implements OnClickListener {

		boolean notified;

		@Override
		public void onClick() {
			this.notified = true;
		}

	}
}
